package org.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import com.jngld.utils.FileUtil;

import junit.framework.TestCase;

public class FileUtilTest extends TestCase{
	
	private String basePath = FileUtilTest.class.getResource("/").getPath();
	
	public void testSaveReadDelete() {
		String path = basePath + System.currentTimeMillis();
		String content = "测试";
		FileUtil.saveFile(path, content);
		String result = "";
		try {
			result = FileUtil.readFile(path);
		} catch (IOException e) {
			//文件未找到，saveFile方法保存失败
			assertTrue(false);
		}
		//测试saveFile与readFile
		assertEquals(content, result);
		//测试deleteFile
		assertTrue(FileUtil.deleteFile(path));
	}
	
	public void testCreateFolders() {
		String path = basePath + System.currentTimeMillis() + "1";
		assertTrue(FileUtil.createFolders(path));
		assertTrue(FileUtil.deleteFile(path));
	}
	
	public void testCreateParentFolders() {
		String path = basePath + System.currentTimeMillis() + "1";
		assertTrue(FileUtil.createParentFolders(path+ File.separator + "2"));
		assertTrue(FileUtil.deleteFile(new File(path)));
	}
	
	public void testException() {
		try {
			FileUtil.createFolders(null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			FileUtil.createParentFolders(null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			File file = null;
			FileUtil.deleteFile(file);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			String path = null;
			FileUtil.deleteFile(path);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			String path = null;
			FileUtil.readFile(path);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			String path = null;
			OutputStream os = null;
			FileUtil.readFile(path, os);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			String path = null;
			Writer writer = null;
			FileUtil.readFile(path, writer);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		try {
			FileUtil.saveFile(null, null);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
	}

}
