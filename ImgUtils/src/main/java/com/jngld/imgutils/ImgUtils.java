/**
 * glodon.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 * com.gcj.utils
 */
package com.jngld.imgutils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.jngld.imgutils.entity.Watermark;
import com.jngld.imgutils.exception.ImgUtilsException;

/**
 * 图片工具类
 * @author wangzz-a
 * @version $Id: ImageUtils.java, v 0.1 2014年10月27日 上午9:49:02 wangzz-a Exp $
 */
public class ImgUtils {
	
	/**私有构造函数*/
	private ImgUtils(){}

	/** 
	 * 生成缩略图
	 * @author wangzz-a
	 * @param width 缩略图的宽度
	 * @param height 缩略图的高度
	 * @param type 缩略图的后缀名 如：（.jpg、.png等）
	 * @param relativePath 原图片路径
	 * 如：D:\\原图片名称.jpg
	 * @param thumbnailsPath 要生成缩略图的绝对路径<br>
	 * 如：D:\\thumb.jpg
	 * @return boolean
	 * @throws SimpleImageException 
	 * @throws FileNotFoundException 
	 * @date 2015年11月26日 下午4:45:34 
	 */
	public static boolean imgThumb(int width, int height, String type, String relativePath, String thumbnailsPath) throws FileNotFoundException, SimpleImageException {
		boolean result = false;
		File sourceFile = new File(relativePath);
		result = imgThumb(width, height, type, sourceFile, thumbnailsPath);
		return result;
	}
	
	/** 
	 * 生成缩略图
	 * @author wangzz-a
	 * @param width 缩略图的宽度
	 * @param height 缩略图的高度
	 * @param type 缩略图的后缀名 如：（.jpg、.png等）
	 * @param sourceFile 原图片
	 * @param thumbnailsPath 要生成缩略图的绝对路径<br>
	 * 如：D:\\thumb.jpg
	 * @return boolean
	 * @throws FileNotFoundException 
	 * @throws SimpleImageException 
	 * @date 2015年11月26日 下午4:45:34 
	 */
	public static boolean imgThumb(int width, int height, String type, File sourceFile, String thumbnailsPath) throws FileNotFoundException, SimpleImageException{
		boolean result = false;
		InputStream sourceFileIS = null;
		try {
			sourceFileIS = new FileInputStream(sourceFile);
			result = imgThumb(width, height, type, sourceFileIS, thumbnailsPath);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ImgUtilsException("imgThumb is exception");
		}finally{
			try {
				if(sourceFileIS != null){
					sourceFileIS.close();
				}
			} catch (IOException e) {}
		}
		return result;
	}
	
	/** 
	 * 生成缩略图
	 * @author wangzz-a
	 * @param width 缩略图的宽度
	 * @param height 缩略图的高度
	 * @param type 缩略图的后缀名 如：（.jpg、.png等）
	 * @param sourceFileIS 原图片输入流
	 * @param thumbnailsPath 要生成缩略图的绝对路径<br>
	 * 如：D:\\thumb.jpg
	 * @return boolean
	 * @throws SimpleImageException 
	 * @throws FileNotFoundException 
	 * @date 2015年11月26日 下午4:45:34 
	 */
	public static boolean imgThumb(int width, int height, String type, InputStream sourceFileIS, String thumbnailsPath) throws FileNotFoundException, SimpleImageException{
		boolean result = false;
		InputStream in = null;
		ImageRender sr = null;
		ImageRender rr = null;
		try {
			in = sourceFileIS;
			rr = new ReadRender(in, true);
			ScaleParameter sp = new ScaleParameter(width, height);
			sr = new ScaleRender(rr, sp);
			write(thumbnailsPath, sr);
			result = true;
		} finally {
			try {
				if (sr != null) {
					sr.dispose();
				}
			} catch (Exception e) {}
			try {
				if(rr != null){
					rr.dispose();
				}
			} catch (Exception e) {}
		}
		return result;
	}

	private static void write(String resultDir, ImageRender sr) throws FileNotFoundException, SimpleImageException {
		OutputStream output = null;
		ImageRender wr = null;
		try {
			output = new FileOutputStream(new File(resultDir));
			wr = new WriteRender(sr, output);
			wr.render();
		} finally {
			try {
				if (wr != null) {
					wr.dispose();
				}
			} catch (Exception e) {}
			try {
				output.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImg 需要加水印的图片路径，如：D://test//1.jpg
	 * @param waterImg 水印图片路径，如：D://test//shuiyin.png
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(String targetImg, String waterImg, String prefix, Watermark watermark) throws IOException {
		FileInputStream waterImgIS = null;
		try {
			waterImgIS = new FileInputStream(new File(waterImg));
			pressImage(new File(targetImg), waterImgIS, prefix, watermark);
		}finally{
			if(waterImgIS != null){
				waterImgIS.close();
			}
		}
	}
	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImg 需要加水印的图片路径，如：D://test//1.jpg
	 * @param waterImgFile 水印图片文件
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(String targetImg, File waterImgFile, String prefix, Watermark watermark) throws IOException {
		FileInputStream waterImgIS = null;
		try {
			waterImgIS = new FileInputStream(waterImgFile);
			pressImage(new File(targetImg), waterImgIS, prefix, watermark);
		}finally{
			if(waterImgIS != null){
				waterImgIS.close();
			}
		}
	}
	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImg 需要加水印的图片路径，如：D://test//1.jpg
	 * @param waterImgIS 水印图片输入流
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(String targetImg, InputStream waterImgIS, String prefix, Watermark watermark) throws IOException {
		pressImage(new File(targetImg), waterImgIS, prefix, watermark);
	}
	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImgFile 需要加水印的图片文件
	 * @param waterImg 水印图片路径，如：D://test//shuiyin.png
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(File targetImgFile, String waterImg, String prefix, Watermark watermark) throws IOException {
		pressImage(targetImgFile, new File(waterImg), prefix, watermark);
	}
	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImgFile 需要加水印的图片文件
	 * @param waterImgFile 水印文件，如：new File("D://test//shuiyin.png")
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(File targetImgFile, File waterImgFile, String prefix, Watermark watermark) throws IOException {
		InputStream waterImgIS = null;
		try {
			waterImgIS = new FileInputStream(waterImgFile);
			pressImage(targetImgFile, waterImgIS, prefix, watermark);
		}finally{
			if(waterImgIS != null){
				waterImgIS.close();
			}
		}
	}
	/**
	 * 添加图片水印
	 * @author wangzz-a
	 * @param targetImgFile 需要加水印的图片文件
	 * @param waterImgIS 水印图片输入流
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(File targetImgFile, InputStream waterImgIS, String prefix, Watermark watermark) throws IOException {
		if(watermark==null){
			throw new IllegalArgumentException("The watermark must not be null");
		}
		// 是否按比例缩放水印
		boolean autoScale = watermark.isAutoScale();
		//如果此属性为空的话就在原图片加水印，如果非空则按照此路径新生成一个加水印的文件
		String newFilePath = watermark.getNewFilePath();
		// 水印占目标图片的比例
		float scale = watermark.getScale();
		//水印透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) (默认是0.8)
		float alpha = watermark.getAlpha();
		//水印文件相对于原文件左侧的偏移量，如果left<0, 则水印左右居中(默认-1)
		int left = watermark.getLeft();
		//水印文件相对于原文件上侧的偏移量，如果top<0, 则水印上下居中(默认-1)
		int top = watermark.getTop();
		
		Image image = ImageIO.read(targetImgFile);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		Image waterImage = ImageIO.read(waterImgIS); // 水印文件
		int width_1 = waterImage.getWidth(null);
		int height_1 = waterImage.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 水印宽高比
		float waterPre = width_1 / (float) height_1;
		// 目标文件宽高比
		float targetPre = width / (float) height;
		
		// 计算等比例压缩后的水印宽高
		float width_2 = width_1;
		float height_2 = height_1;
		
		//按比例缩放水印
		if(autoScale){
			//计算水印文件的自适应宽高-根据水印和目标文件的宽高比自适应
			if (targetPre > waterPre) {
				height_2 = height * scale;
				width_2 = height_2 * waterPre;
			} else {
				width_2 = width * scale;
				height_2 = width_2 / waterPre;
			}
		}
		
		//计算水印的位置
		int widthDiff = width - (int) width_2;
		widthDiff = widthDiff<0?0:widthDiff;
		int heightDiff = height - (int) height_2;
		heightDiff = heightDiff<0?0:heightDiff;
		if (left < 0) {
			left = widthDiff / 2;
		} else if (left > widthDiff) {
			left = widthDiff;
		}
		if (top < 0) {
			top = heightDiff / 2;
		} else if (top > heightDiff) {
			top = heightDiff;
		}
		
		g.drawImage(waterImage, left, top, (int) width_2, (int) height_2, null); // 水印文件结束
		g.dispose();
		//如果此属性为空的话就在原图片加水印，如果非空则按照此路径新生成一个加水印的文件
		if(null==newFilePath){
			ImageIO.write(bufferedImage, prefix, targetImgFile);
		}else{
			ImageIO.write(bufferedImage, prefix, new File(newFilePath));
		}
		
	}
	/**
	 * 传入图片流生成一个带水印的图片文件（必需设置水印配置类(watermark)中的newFilePath属性）
	 * @author wangzz-a
	 * @param targetImgIS 需要加水印的图片输入流
	 * @param waterImg 水印图片路径，如：D://test//shuiyin.png
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(InputStream targetImgIS, String waterImg, String prefix, Watermark watermark) throws IOException{
		InputStream waterImgIS = null;
		try {
			waterImgIS = new FileInputStream(new File(waterImg));
			pressImage(targetImgIS, waterImgIS, prefix, watermark);
		}finally{
			if(waterImgIS != null){
				waterImgIS.close();
			}
		}
	}
	/**
	 * 传入图片流生成一个带水印的图片文件（必需设置水印配置类(watermark)中的newFilePath属性）
	 * @author wangzz-a
	 * @param targetImgIS 需要加水印的图片输入流
	 * @param waterImgFile 水印图片文件
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(InputStream targetImgIS, File waterImgFile, String prefix, Watermark watermark) throws IOException {
		InputStream waterImgIS = null;
		try {
			waterImgIS = new FileInputStream(waterImgFile);
			pressImage(targetImgIS, waterImgIS, prefix, watermark);
		}finally{
			if(waterImgIS != null){
				waterImgIS.close();
			}
		}
	}
	/**
	 * 传入图片流生成一个带水印的图片文件（必需设置水印配置类(watermark)中的newFilePath属性）
	 * @author wangzz-a
	 * @param targetImgIS 需要加水印的图片输入流
	 * @param waterImgIS 水印图片流
	 * @param prefix 图片格式 如："jpg"
	 * @param watermark 水印配置类
	 * @throws IOException 
	 * @date 2015年1月19日 下午4:42:57
	 */
	public static void pressImage(InputStream targetImgIS, InputStream waterImgIS, String prefix, Watermark watermark) throws IOException {
		if(watermark==null){
			throw new IllegalArgumentException("The watermark must not be null");
		}
		if(watermark.getNewFilePath()==null){
			throw new IllegalArgumentException("The watermark.newFilePath must not be null");
		}
		// 是否按比例缩放水印
		boolean autoScale = watermark.isAutoScale();
		//如果此属性为空的话就在原图片加水印，如果非空则按照此路径新生成一个加水印的文件
		String newFilePath = watermark.getNewFilePath();
		// 水印占目标图片的比例
		float scale = watermark.getScale();
		//水印透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) (默认是0.8)
		float alpha = watermark.getAlpha();
		//水印文件相对于原文件左侧的偏移量，如果left<0, 则水印左右居中(默认-1)
		int left = watermark.getLeft();
		//水印文件相对于原文件上侧的偏移量，如果top<0, 则水印上下居中(默认-1)
		int top = watermark.getTop();
		
		Image image = ImageIO.read(targetImgIS);
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		Image waterImage = ImageIO.read(waterImgIS); // 水印文件
		int width_1 = waterImage.getWidth(null);
		int height_1 = waterImage.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 水印宽高比
		float waterPre = width_1 / (float) height_1;
		// 目标文件宽高比
		float targetPre = width / (float) height;
		
		// 计算等比例压缩后的水印宽高
		float width_2 = width_1;
		float height_2 = height_1;
		
		//按比例缩放水印
		if(autoScale){
			//计算水印文件的自适应宽高-根据水印和目标文件的宽高比自适应
			if (targetPre > waterPre) {
				height_2 = height * scale;
				width_2 = height_2 * waterPre;
			} else {
				width_2 = width * scale;
				height_2 = width_2 / waterPre;
			}
		}
		
		//计算水印的位置
		int widthDiff = width - (int) width_2;
		widthDiff = widthDiff<0?0:widthDiff;
		int heightDiff = height - (int) height_2;
		heightDiff = heightDiff<0?0:heightDiff;
		if (left < 0) {
			left = widthDiff / 2;
		} else if (left > widthDiff) {
			left = widthDiff;
		}
		if (top < 0) {
			top = heightDiff / 2;
		} else if (top > heightDiff) {
			top = heightDiff;
		}
		
		g.drawImage(waterImage, left, top, (int) width_2, (int) height_2, null); // 水印文件结束
		g.dispose();
		//如果此属性为空的话就在原图片加水印，如果非空则按照此路径新生成一个加水印的文件
		ImageIO.write(bufferedImage, prefix, new File(newFilePath));

	}
	
	public static void main(String[] args) throws Exception {
		String img = "";
		String shuijin = "D:\\shuiyin.png";
		Watermark watermark = new Watermark();
		pressImage(new File(img), new File(shuijin),"jpg",watermark);
		System.out.println("success");
	}
	
}
