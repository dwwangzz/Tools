package com.jngld.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileUtil {
	
	/**
	 * 将字符串保存为文件
	 * @author liuy-8
	 * @date 2015年5月20日 下午1:48:18 
	 * @param filePath
	 * @param content
	 */
	public static void saveFile(String filePath, String content){
		File file = new File(filePath);
		FileWriter fw = null;
		//这里没有使用BufferWrite，是因为数据是一次性写入
		//BufferWrite的优势在于调用write方法时，使用缓冲区
		//而FileWriter每次调用write方法，都调用StreamEncoder的write方法
		try {
			fw = new FileWriter(file);//缓冲区1024字符
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(fw);
		}
	}
	
	/**
	 * 读取文本文件
	 * @author liuy-8
	 * @date 2015年12月4日 下午4:04:22 
	 * @param path 文本文件路径
	 * @return 文本文件内容	
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		if(path == null) {
			throw new IllegalArgumentException("The path must not be null");
		}
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			readFile(path, bos);
			result = bos.toString();
		} finally {
			IOUtils.closeQuietly(bos);
		}
		return result;
	}
	
	
	/**
	 * 读取文件到字符输出流
	 * 可用于response.getWriter()输出内容
	 * 方法内不关闭字符输出流
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:54 
	 * @param path 文件路径
	 * @param output 字符输出流
	 * @throws IOException 读取失败，发生IO异常
	 */
	public static void readFile(String path, Writer output) throws IOException {
		if(path == null || output == null) {
			throw new IllegalArgumentException("The path or output must not be null");
		}
		FileReader intput = null;
		try {
			intput = new FileReader(new File(path));
			IOUtils.copy(intput, output);
		} finally {
			IOUtils.closeQuietly(intput);
		}
		
	}
	
	/**
	 * 读取文件到字节输出流<br/>
	 * 可用于response.getOutPutStream()提供文件下载<br/>
	 * 方法内不关闭字节输出流<br/>
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:52 
	 * @param path 文件路径
	 * @param output 字符输出流
	 * @throws IOException 读取失败，发生IO异常
	 */
	public static void readFile(String path, OutputStream output) throws IOException {
		if(path == null || output == null) {
			throw new IllegalArgumentException("The path or output must not be null");
		}
		FileInputStream intput = null;
		try {
			intput = new FileInputStream(new File(path));
			IOUtils.copy(intput, output);
		} finally {
			IOUtils.closeQuietly(intput);
		}
	}
	
	/**
	 * 创建文件夹
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:14:54 
	 * @param folderPath 文件夹路径
	 * @return 创建文件夹是否成功
	 */
	public static boolean createFolders(String folderPath) {
		if(folderPath == null) {
			throw new IllegalArgumentException("The folderPath must not be null");
		}
		File file = new File(folderPath);
		//创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		if(!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
	
	/**
	 * 创建父文件夹
	 * @author liuy-8
	 * @date 2015年11月27日 下午4:00:23 
	 * @param folderPath 文件路径
	 * @return 创建父文件夹是否成功
	 */
	public static boolean createParentFolders(String folderPath) {
		if(folderPath == null) {
			throw new IllegalArgumentException("The folderPath must not be null");
		}
		File file = new File(folderPath);
		return createFolders(file.getParent());
	}
	
	/**
	 * 删除文件或文件夹
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29 
	 * @param path 文件或文件夹路径
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(String path) {
		if(path == null) {
			throw new IllegalArgumentException("The path must not be null");
		}
		File file = new File(path);
		return deleteFile(file);
	}
	
	/**
	 * 删除文件或文件夹
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29 
	 * @param file 文件或文件夹对象
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(File file) {
		if(file == null) {
			throw new IllegalArgumentException("The file must not be null");
		}
		if(file.exists()) {
			if(file.isDirectory()) {
				String[] childrens = file.list();
				for(String children : childrens) {
					deleteFile(new File(file, children));
				}
			}
			return file.delete();
		} else {
			return false;
		}
	}
	
	/**
	 * 获取文件夹中所有文件
	 * @author liuy-8
	 * @date 2015年11月26日 下午8:11:29 
	 * @param folderPath 文件夹路径
	 * @return 文件夹中所有文件
	 */
	public static List<File> getFilesInFolder(String folderPath){
		return getFilesInFolder(new File(folderPath));
	}
	
	/**
	 * @Description 获取文件夹中所有文件
	 * @author liuy-8
	 * @date 2015年12月1日 下午3:11:54 
	 * @param folder 文件夹
	 * @return 文件夹中所有文件
	 */
	public static List<File> getFilesInFolder(File folder){
		if(folder == null) {
			throw new IllegalArgumentException("The folder must not be null");
		}
		List<File> filesPath = new ArrayList<File>();
		if(folder.isDirectory()) {
			File[] fileList = folder.listFiles();
			for(File file : fileList){
				if(file.isDirectory()){
					//是文件夹，递归遍历
					filesPath.addAll(getFilesInFolder(file.getPath()));
				}else{
					//是文件，加入文件列表
					filesPath.add(file);
				}
			}
		}
		return filesPath;
	}

	/**
     * 
     * @Description 根据路径创建一个文件，如果已有则删除原文件，创建并返回新的文件
     * @author youps-a
     * @date 2015年12月8日 上午10:42:54
     * @param filePath      文件路径及文件名
     * @return              创建的File文件
     * @throws IOException
     */
    public static File getNewValidateFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileUtil.createParentFolders(filePath);
        if (file.exists()){
            FileUtil.deleteFile(file);
        }
        file.createNewFile();
        return file;
    }
}
