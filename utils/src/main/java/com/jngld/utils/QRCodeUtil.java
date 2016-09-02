package com.jngld.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jngld.utils.zxing.BufferedImageLuminanceSource;

/**
 * 
 * 二维码辅助工具类
 * 
 * @author (作者) xus-a
 * @date (开发日期) 2015年11月26日 下午2:42:50
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */

public class QRCodeUtil {

  private static final String CHARSET = "utf-8";
  private static final String FORMAT_NAME = "JPG";
  // 二维码尺寸
  private static final int QRCODE_SIZE = 300;
  // LOGO宽度
  private static final int WIDTH = 60;
  // LOGO高度
  private static final int HEIGHT = 60;

  /**
   * 
   * @Description 生成image
   * @author xus-a
   * @date 2015年12月2日 下午3:36:19
   * @param content
   *          二维码内容
   * @param logoPicPath
   *          logo图片地址
   * @param needCompress
   *          是否需要压缩
   * @return
   * @throws WriterException
   * @throws IOException
   */
  private static BufferedImage createImage(String content, String logoPicPath,
      boolean needCompress) throws WriterException, IOException {
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
    hints.put(EncodeHintType.MARGIN, 1);
    BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
        BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
    int width = bitMatrix.getWidth();
    int height = bitMatrix.getHeight();
    BufferedImage image = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
      }
    }
    if (logoPicPath == null || "".equals(logoPicPath)) {
      return image;
    }
    // 插入Logo
    QRCodeUtil.insertImage(image, logoPicPath, needCompress);
    return image;
  }

  /**
   * 插入LOGO
   * 
   * @param source
   *          二维码图片
   * @param logoPicPath
   *          LOGO图片地址
   * @param needCompress
   *          是否压缩
   * @throws IOException
   */
  private static void insertImage(BufferedImage source, String logoPicPath,
      boolean needCompress) throws IOException {
    File file = new File(logoPicPath);
    if (!file.exists()) {
      throw new FileNotFoundException("file not found!");
    }
    Image src = ImageIO.read(new File(logoPicPath));
    int width = src.getWidth(null);
    int height = src.getHeight(null);
    if (needCompress) { // 压缩LOGO
      if (width > WIDTH) {
        width = WIDTH;
      }
      if (height > HEIGHT) {
        height = HEIGHT;
      }
      Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage tag = new BufferedImage(width, height,
          BufferedImage.TYPE_INT_RGB);
      Graphics g = tag.getGraphics();
      g.drawImage(image, 0, 0, null); // 绘制缩小后的图
      g.dispose();
      src = image;
    }
    // 插入LOGO
    Graphics2D graph = source.createGraphics();
    int x = (QRCODE_SIZE - width) / 2;
    int y = (QRCODE_SIZE - height) / 2;
    graph.drawImage(src, x, y, width, height, null);
    Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
    graph.setStroke(new BasicStroke(3f));
    graph.draw(shape);
    graph.dispose();
  }

  /**
   * 
   * 根据内容生成二维码的base64编码
   * @author xus-a
   * @date 2015年12月2日 下午4:06:05 
   * @param content 二维码内容
   * @return 二维码的base64编码(data:image/JPG;base64............)
   * @throws WriterException
   * @throws IOException
   */
  public static String base64Encode(String content) throws WriterException, IOException{
    byte[] imageByte = getImageByteArray(content, null, false);
    return Base64Util.encodeImgBase64(imageByte,FORMAT_NAME);
  }
  /**
   * 
   * 根据内容生成二维码的base64编码
   * @author xus-a
   * @date 2015年12月2日 下午5:10:17 
   * @param content 二维码内容
   * @param logoPath logo地址
   * @return 二维码的base64编码(data:image/JPG;base64............)
   * @throws WriterException
   * @throws IOException
   */
  public static String base64Encode(String content, String logoPath) throws WriterException, IOException{
    byte[] imageByte = getImageByteArray(content, logoPath, true);
    return Base64Util.encodeImgBase64(imageByte,FORMAT_NAME);
  }
  /**
   * 
   * 根据内容生成二维码的base64编码
   * @author xus-a
   * @date 2015年12月2日 下午5:10:41 
   * @param content 二维码内容
   * @param logoPath logo地址
   * @param needCompress 是否压缩logo
   * @return 二维码的base64编码(data:image/JPG;base64............)
   * @throws WriterException
   * @throws IOException
   */
  public static String base64Encode(String content, String logoPath,
      boolean needCompress) throws WriterException, IOException {
    byte[] imageByte = getImageByteArray(content, logoPath, needCompress);
    return Base64Util.encodeImgBase64(imageByte,FORMAT_NAME);
  }

  /**
   * 生成二维码(内嵌LOGO)
   * 
   * @param content
   *          内容
   * @param imgPath
   *          LOGO地址
   * @param output
   *          输出流
   * @param needCompress
   *          是否压缩LOGO
   * @throws WriterException
   * @throws IOException
   */
  public static void encode(String content, String imgPath, OutputStream output,
      boolean needCompress) throws WriterException, IOException {
    BufferedImage image = QRCodeUtil.createImage(content, imgPath,
        needCompress);
    ImageIO.write(image, FORMAT_NAME, output);
  }

  /**
   * 只生成二维码
   * 
   * @param content
   *          内容
   * @param output
   *          输出流
   * @throws IOException
   * @throws WriterException
   */
  public static void encode(String content, OutputStream output)
      throws WriterException, IOException {
    QRCodeUtil.encode(content, null, output, false);
  }

  /**
   * 解析二维码
   * 
   * @param file
   *          二维码图片
   * @return
   * @throws IOException
   * @throws NotFoundException
   */
  public static String decode(InputStream in)
      throws IOException, NotFoundException {
    BufferedImage image;
    image = ImageIO.read(in);
    if (image == null) {
      return null;
    }
    BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
        image);
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    Result result;
    Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
    hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
    result = new MultiFormatReader().decode(bitmap, hints);
    String resultStr = result.getText();
    return resultStr;
  }
  /**
   * 
   * @Description 返回二维码的byte[]
   * @author xus-a
   * @date 2015年12月2日 下午4:17:12 
   * @param content
   * @param imgPath
   * @param needCompress
   * @return
   * @throws WriterException
   * @throws IOException
   */
  private static byte[] getImageByteArray(String content, String imgPath,
      boolean needCompress) throws WriterException, IOException {
    BufferedImage image;
    ByteArrayOutputStream bs = null;
    ImageOutputStream imOut = null;
    try {
      image = QRCodeUtil.createImage(content, imgPath, needCompress);
      bs = new ByteArrayOutputStream();
      imOut = ImageIO.createImageOutputStream(bs);
      ImageIO.write(image, FORMAT_NAME, imOut);
    } finally {
      if (bs != null) {
        bs.close();
      }
      if (imOut != null) {
        imOut.close();
      }
    }
    return bs.toByteArray();
  }

  public static void main(String[] args) throws Exception {

    String text = "哈哈对萨达萨达!@#";
    QRCodeUtil.encode(text, null, null, true);
    /*
     * File file = new File(
     * "C:\\Users\\Administrator\\Desktop\\temp\\erweima\\a976d9b4-432b-40b2-a392-531c7db45d0a.jpg"
     * ); InputStream in = new FileInputStream(file); String cs = decode(in);
     * System.out.println(cs);
     */
  }
}
