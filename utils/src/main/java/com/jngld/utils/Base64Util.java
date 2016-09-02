package com.jngld.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

import com.jngld.utils.base64.BASE64Decoder;
import com.jngld.utils.base64.BASE64Encoder;
import com.jngld.utils.exception.ParameterException;

public class Base64Util {

  /**
   * 
   * 对字符串进行base64编码
   * 
   * @author xus-a
   * @date 2015年11月25日 下午3:08:20
   * @param str
   *          需要编码的字符串
   * @return 编码后的base64码
   */
  public static String encode(String str) {
    if (EmptyUtil.isEmpty(str)) {
      throw new ParameterException("参数不能为空");
    }
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(str.getBytes());
  }

  /**
   * 
   * 对base64进行解码
   * 
   * @author xus-a
   * @date 2015年11月25日 下午3:08:36
   * @param str
   *          base64加密后的字符串
   * @return 解码后得到的字符串
   * @throws IOException
   */
  public static String decode(String str) throws IOException {
    if (EmptyUtil.isEmpty(str)) {
      throw new ParameterException("参数不能为空");
    }
    BASE64Decoder decoder = new BASE64Decoder();
    return new String(decoder.decodeBuffer(str));
  }

  /**
   * 
   * @Description 对base64进行解码
   * @author youps-a
   * @date 2015年12月1日 下午4:38:44
   * @param data
   *          需加密的 byte[]
   * @return
   */
  public static String encode(byte[] data) throws IOException {
    if (EmptyUtil.isEmpty(data)) {
      throw new ParameterException("参数不能为空");
    }
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(data);
  }

  /**
   * 
   * 图片(输入流)编码为base64字符串
   * 
   * @author xus-a
   * @date 2015年11月25日 下午3:25:44
   * @param in
   *          图片的输入流
   * @return 图片base64编码
   * @throws IOException
   */
  public static String encodeImgBase64(FileImageInputStream in)
      throws IOException {
    Iterator<ImageReader> iter = ImageIO.getImageReaders(in);
    byte[] data = null;
    data = new byte[(int) in.length()];
    in.read(data);

    String formatName = "";
    if (iter.hasNext()) {
      ImageReader reader = iter.next();
      formatName = reader.getFormatName();
    }
    return encodeImgBase64(data, formatName);
  }

  /**
   * 
   * @Description 图片（byte[]） 编码为base64编码
   * @author youps-a
   * @date 2015年12月1日 下午4:39:45
   * @param data
   * @param formatName
   *          图片类型名
   * @return
   * @throws IOException
   */
  public static String encodeImgBase64(byte[] data, String formatName)
      throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("data:image/");
    sb.append(formatName);
    sb.append(";base64,");
    sb.append(encode(data));
    return sb.toString();
  }

  /**
   * 
   * 图片(图片路径)编码为base64字符串
   * 
   * @author xus-a
   * @date 2015年11月26日 上午9:34:19
   * @param filePath
   *          图片路径
   * @return 图片base64编码
   * @throws IOException
   */
  public static String encodeImgBase64(String filePath) throws IOException {
    if(EmptyUtil.isEmpty(filePath)){
      throw new ParameterException("图片路径不能为空");
    }
    File file = new File(filePath);
    FileImageInputStream in = null;
    String result = "";
    try {
      in = new FileImageInputStream(file);
      result = encodeImgBase64(in);
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return result;
  }

  /**
   * 
   * 图片(File)编码为base64字符串
   * 
   * @author xus-a
   * @date 2015年12月1日 下午4:35:45
   * @param file
   *          图片文件
   * @return
   * @throws IOException
   */
  public static String encodeImgBase64(File file) throws IOException {
    if(EmptyUtil.isEmpty(file)){
      throw new ParameterException("File不能为空");
    }
    FileImageInputStream in = new FileImageInputStream(file);
    String resCode = encodeImgBase64(in);
    return resCode;
  }

  /**
   * 
   * 图片base64解码
   * 
   * @author xus-a
   * @date 2015年11月25日 下午4:06:07
   * @param imgBase64Code
   *          图片的base64编码
   * @param filePath
   *          图片存储路径
   * @return
   * @throws IOException
   */
  public static void decodeImgBase64(String imgBase64Code, OutputStream out)
      throws IOException {
    if(EmptyUtil.isEmpty(imgBase64Code)){
      throw new ParameterException("base64编码不能为空");
    }
    if(EmptyUtil.isEmpty(out)){
      throw new ParameterException("输出流不能为空");
    }
    byte[] base64Code = decodeBase64(imgBase64Code);
    out.write(base64Code);
    out.flush();
  }
  /**
   * 
   * 图片base64解码
   * 
   * @author xus-a
   * @date 2015年11月25日 下午4:06:07
   * @param imgBase64Code
   *          图片的base64编码
   * @param File
   *          文件
   * @return
   * @throws IOException
   */
  public static void decodeImgBase64(String imgBase64Code, File file) throws IOException
      {
    if(EmptyUtil.isEmpty(imgBase64Code)){
      throw new ParameterException("base64编码不能为空");
    }
    if(EmptyUtil.isEmpty(file)){
      throw new ParameterException("File不能为空");
    }
    byte[] base64Code = decodeBase64(imgBase64Code);
    FileOutputStream out=null;
    try {
      out = new FileOutputStream(file);
      out.write(base64Code);
    } finally{
      if(out!=null){
        out.flush();
        out.close();
      }
    }
  }

  /**
   * 
   * @Description 解码图片到指定路径
   * @author xus-a
   * @date 2015年12月2日 上午10:57:24 
   * @param imgBase64Code base64编码
   * @param filePath 文件路径
   * @param fileName 文件名
   * @throws IOException
   */
  public static void decodeImgBase64(String imgBase64Code, String filePath,
      String fileName) throws IOException {
    if(EmptyUtil.isEmpty(imgBase64Code)){
      throw new ParameterException("base64编码不能为空");
    }
    if(EmptyUtil.isEmpty(filePath)){
      throw new ParameterException("filePath不能为空");
    }
    if(EmptyUtil.isEmpty(fileName)){
      throw new ParameterException("fileName不能为空");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    byte[] base64Code = decodeBase64(imgBase64Code);
    FileOutputStream out = new FileOutputStream(
        filePath + "\\" + fileName + ".jpg");
    out.write(base64Code);
    out.flush();
    out.close();
  }

  /**
   * 
   * 解码base64为byte[]
   * 
   * @author xus-a
   * @date 2015年12月2日 上午10:31:47
   * @param imgBase64Code
   * @return
   * @throws IOException
   */
  private static byte[] decodeBase64(String imgBase64Code) throws IOException {
    if (imgBase64Code == null || "".equals(imgBase64Code)) {// 图像数据为空
      throw new ParameterException("图像数据不能为空");
    }
    BASE64Decoder decoder = new BASE64Decoder();

    if (imgBase64Code.indexOf(",") != -1) {
      imgBase64Code = imgBase64Code.substring(imgBase64Code.indexOf(",") + 1,
          imgBase64Code.length());
    }

    // Base64解码
    byte[] bytes = decoder.decodeBuffer(imgBase64Code);
    return bytes;
  }
}
