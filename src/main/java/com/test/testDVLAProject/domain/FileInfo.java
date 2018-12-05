package com.test.testDVLAProject.domain;

/**
 * Domain class to represent file information
 *
 */
public class FileInfo {

	private final String fileName;
	private final String mimeType;
	private final long size;
	private final String extension;

	public FileInfo(String fileName, String mimeType, long size, String extension) {
		super();
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.size = size;
		this.extension = extension;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public long getSize() {
		return size;
	}

	public String getExtension() {
		return extension;
	}

}
