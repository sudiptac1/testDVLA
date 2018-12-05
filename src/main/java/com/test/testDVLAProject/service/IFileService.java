package com.test.testDVLAProject.service;

import java.io.File;
import java.util.List;

import com.test.testDVLAProject.domain.FileInfo;

public interface IFileService {
	/* method to scan entire directory and return the list FileInfo objects */
	public List<FileInfo> scanDirectory();

	/* method to retrun list of files which matches the configured Mime types */
	public List<File> getFiles();

}
