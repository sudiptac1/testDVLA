/**
 * 
 */
package com.test.testDVLAProject.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.test.testDVLAProject.domain.FileInfo;

/**
 * @author Sudipta Chatterjee
 *
 */
public class FileServiceTest {

	FileService fs = new FileService();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testScanDirectory() {

		List<FileInfo> fileInfoList = fs.scanDirectory();

		FileInfo fileInfo = fileInfoList.get(0);

		Assert.assertEquals("testData.csv", fileInfo.getFileName());
		Assert.assertEquals("text/csv", fileInfo.getMimeType());
		Assert.assertEquals("csv", fileInfo.getExtension());

	}

	@Test
	public void testGetFiles() {

		List<File> fileList = fs.getFiles();
		File file = fileList.get(0);
		Assert.assertEquals("testData.csv", file.getName());

	}

}
