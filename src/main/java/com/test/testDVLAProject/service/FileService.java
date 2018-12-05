package com.test.testDVLAProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;

import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import com.test.testDVLAProject.constant.DVLAConstant;
import com.test.testDVLAProject.domain.FileInfo;

/* Concrete Service class */
public class FileService implements IFileService {

	private final static Logger logger = Logger.getLogger(FileService.class);

	/*method to scan entire directory and return the list FileInfo objects */
	public List<FileInfo> scanDirectory() {

		List<FileInfo> infoList = new ArrayList<>();
		Path path = new File(DVLAConstant.filePath).toPath();
		Tika tika = new Tika();
		try (Stream<Path> paths = Files.walk(path)) {
			paths.filter(Files::isRegularFile).forEach(pathObj -> {
				String fileName = pathObj.getFileName().toString();
				String extension = getExtension(fileName);
				String mimeType = null;

				try {

					mimeType = tika.detect(pathObj);
				} catch (IOException ex) {
					logger.error("Problem getting mimeType of file:" + fileName, ex);
				}
				long size = pathObj.toFile().length();
				infoList.add(new FileInfo(fileName, mimeType, size, extension));

			});
		} catch (IOException ex) {
			logger.error("Problem scanning directory:" + DVLAConstant.filePath, ex);
		}

		return infoList;
	}
    
	/* method to retrun list of files which matches the configured Mime types */
	public List<File> getFiles() {
		Tika tika = new Tika();
		List<File> fileList = new ArrayList<>();

		Path path = new File(DVLAConstant.filePath).toPath();
		try (Stream<Path> paths = Files.walk(path)) {
			paths.filter(Files::isRegularFile).forEach(pathObj -> {
				String thisMimeType = null;
				try {

					thisMimeType = tika.detect(pathObj);
					logger.info("**Mime type:" + thisMimeType);
				} catch (Exception ex) {
					logger.error("Problem getting mimeType of file:" + pathObj.getFileName().toString(), ex);
				}

				if (thisMimeType != null && DVLAConstant.MIME_TYPES.contains(thisMimeType)) {
					fileList.add(pathObj.toFile());
				}
			});
		} catch (IOException ex) {
			logger.error("Problem scanning directory:" + DVLAConstant.filePath, ex);
		}

		return fileList;

	}

	private String getExtension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

}
