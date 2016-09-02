package com.jngld.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZipUtil {

	
	/**
	 * 压缩文件夹
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29  
	 * @param folderPath 文件夹路径
	 * @param zipFilePath 压缩文件路径
	 * @throws IOException 压缩失败，抛出IO异常
	 */
	public static void zip(String folderPath, String zipFilePath) throws IOException{
		if(folderPath == null || zipFilePath == null) {
			throw new IllegalArgumentException("The folderPath or zipFilePath must not be null");
		}
		//获取当前系统文件分隔符
		String fileSeparator = System.getProperty("file.separator");
		//若传入路径最后没有文件分隔符，加上
		if(folderPath.lastIndexOf(fileSeparator) != (folderPath.length() - 1)){
			folderPath = folderPath + fileSeparator;
		}
		//获取文件夹下所有文件路径
		List<String> filesPath = getFilesPathInFolder(folderPath);
		//文件输出流
		FileOutputStream fos = null;
		//缓冲输出流
		BufferedOutputStream bos = null;
		//zip输出流
		ZipOutputStream zos = null;
		
		try {
			fos = new FileOutputStream(zipFilePath);
			//没有输出缓冲流，会慢许多
			bos = new BufferedOutputStream(fos);
			zos = new ZipOutputStream(bos);
			for(String filePath : filesPath){
				//将文件写入zip输出流
				writeFile2Zip(folderPath, filePath, zos);
			}
			zos.flush();
		} finally{
			//关闭zip输出流
			IOUtils.closeQuietly(zos);
			//关闭缓冲输出流
			IOUtils.closeQuietly(bos);
			//关闭文件输出流
			IOUtils.closeQuietly(fos);
		}
	}
	
	/**
	 * 将文件写入zip输出流
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29 
	 * @param folderPath	待压缩文件夹路径
	 * @param filePath		待写入文件
	 * @param zos			zip输出流
	 * @throws IOException 
	 */
	private static void writeFile2Zip(String folderPath, String filePath, ZipOutputStream zos) throws IOException{
		//获取文件的相对路径
		String entryName = filePath.substring(filePath.indexOf(folderPath) + folderPath.length());
		//创建zip实体
		ZipEntry entry = new ZipEntry(entryName);
		//输入流
		FileInputStream fis = null;
		try {
			//将zip实体加入zip输出流
			zos.putNextEntry(entry);
			fis = new FileInputStream(filePath);
			IOUtils.copy(fis, zos);
		} finally{
			//关闭FileInputStream
			IOUtils.closeQuietly(fis);
		}
	}
	
	public static void unzip(String zipFilePath, String folderPath) throws ZipException, IOException {
		if(folderPath == null || zipFilePath == null) {
			throw new IllegalArgumentException("The zipFilePath or folderPath must not be null");
		}
		unzip(new File(zipFilePath), folderPath);
	}
	
	public static void unzip(File file, String folderPath) throws ZipException, IOException {
		if(folderPath == null || file == null) {
			throw new IllegalArgumentException("The file or folderPath must not be null");
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ZipInputStream zis = new ZipInputStream(bis);
		ZipEntry zipEntry = null;
		while((zipEntry = zis.getNextEntry()) != null) {
			String path = folderPath + File.separator + zipEntry.getName();
			FileUtil.createParentFolders(path);
			if(!zipEntry.isDirectory()) {
//				FileUtil.deleteFile(path);
				FileOutputStream fos = new FileOutputStream(path);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				IOUtils.copy(zis, bos);
				bos.flush();
				IOUtils.closeQuietly(bos);
				IOUtils.closeQuietly(fos);
			}
			zis.closeEntry();
		}
		IOUtils.closeQuietly(zis);
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(fis);
	}
	
	
	/**
	 * 获取文件夹中所有文件路径
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29 
	 * @param folderPath 文件夹路径
	 * @return 文件夹中所有文件路径
	 */
	private static List<String> getFilesPathInFolder(String folderPath){
		if(folderPath == null) {
			throw new IllegalArgumentException("The folderPath must not be null");
		}
		List<String> filesPath = new ArrayList<String>();
		File folder = new File(folderPath);
		File[] fileList = folder.listFiles();
		for(File file : fileList){
			if(file.isDirectory()){
				//是文件夹，递归遍历
				filesPath.addAll(getFilesPathInFolder(file.getPath()));
			}else{
				//是文件，加入文件列表
				filesPath.add(file.getPath());
			}
		}
		return filesPath;
	}
	
	
	
	/*public static void main(String[] args) {
		long a = System.currentTimeMillis();
		try {
			//zip("c:\\1", "c:\\temp.zip");
			unzip("c:\\temp.zip", "c:\\234");
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}*/
	
	/* public static void main(String[] args) throws IOException { 
		 byte[] buf = new byte[1024];
			FileInputStream fis = new FileInputStream("c:\\temp.zip");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry zn = null;
			while ((zn = zis.getNextEntry()) != null) {
				File f = new File("c:\\1" + "/" + zn.getName());
				if (zn.isDirectory()) {
					f.mkdirs();
				} else {
					
					 * 父目录不存在则创建
					 
					File parent = f.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(f);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int len;
					while ((len = zis.read(buf)) != -1) {
						bos.write(buf, 0, len);
					}
					bos.flush();
					bos.close();
				}
				zis.closeEntry();
			}
			zis.close();
	    } */

}