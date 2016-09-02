package org.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;

import com.jngld.utils.FileUtil;
import com.jngld.utils.ZipUtil;

import junit.framework.TestCase;

public class ZipUtilTest extends TestCase{
	
	private String basePath = FileUtilTest.class.getResource("/").getPath();
	
	public void testZip() {
		String zipPath = basePath + "org";
		try {
			ZipUtil.zip(zipPath, basePath + "org.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(basePath + "org.zip");
		assertTrue(file.exists());
		String unzipPath = basePath + System.currentTimeMillis();
		try {
			ZipUtil.unzip(file, unzipPath);
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File unzipFile = new File(unzipPath);
		assertTrue(unzipFile.isDirectory());
		List<File> orginFiles = FileUtil.getFilesInFolder(zipPath);
		List<File> unzipFiles = FileUtil.getFilesInFolder(unzipFile);
		//没有漏文件
		assertTrue(orginFiles.size() == unzipFiles.size());
		//解压后文件大小一致
		for(int i = 0; i < orginFiles.size(); i++) {
			assertTrue(orginFiles.get(0).length() == unzipFiles.get(0).length());
		}
		//删除压缩文件
		assertTrue(FileUtil.deleteFile(file));
		//删除解压后的文件夹
		assertTrue(FileUtil.deleteFile(unzipFile));
	}
	
	public void testRuntimeException() {
		try {
			ZipUtil.zip(null, null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
	}

}
