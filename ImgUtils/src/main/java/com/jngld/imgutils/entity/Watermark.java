package com.jngld.imgutils.entity;

/**
 * 水印配置文件
 * @author wangzz-a
 * @version $Id: Watermark.java, v 0.1 2015年11月26日 下午5:09:52 wangzz-a Exp $
 */
public class Watermark {

	//水印文件相对于原文件左侧的偏移量，如果left<0, 则水印左右居中(默认-1)
	private int left = -1;
	//水印文件相对于原文件上侧的偏移量，如果top<0, 则水印上下居中(默认-1)
	private int top = -1;
	//水印图片相对于原图的比例 (默认是1/3)
	private float scale = (1 / 3f);
	//水印透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) (默认是0.8)
	private float alpha = 0.8f;
	//是否按比例缩放水印(默认 true)
	private boolean autoScale = true;
	//新图片文件绝对路径 如：D:\\img.jpg
	//如果需要新生成一个带水印的图片文件则此字段非空，如果需要在原图片上加水印则此字段为空
	private String newFilePath = null;
	
	/**水印文件相对于原文件左侧的偏移量，如果left<0, 则水印左右居中(默认-1)*/
	public int getLeft() {
		return left;
	}
	/**水印文件相对于原文件左侧的偏移量，如果left<0, 则水印左右居中(默认-1)*/
	public void setLeft(int left) {
		this.left = left;
	}
	/**水印文件相对于原文件上侧的偏移量，如果top<0, 则水印上下居中(默认-1)*/
	public int getTop() {
		return top;
	}
	/**水印文件相对于原文件上侧的偏移量，如果top<0, 则水印上下居中(默认-1)*/
	public void setTop(int top) {
		this.top = top;
	}
	/**水印图片相对于原图的比例 (默认是1/3)*/
	public float getScale() {
		return scale;
	}
	/**水印图片相对于原图的比例 (默认是1/3)*/
	public void setScale(float scale) {
		this.scale = scale;
	}
	/**水印透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) (默认是0.8)*/
	public float getAlpha() {
		return alpha;
	}
	/**水印透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) (默认是0.8)*/
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	/**是否按比例缩放水印 (默认 true)*/
	public boolean isAutoScale() {
		return autoScale;
	}
	/**是否按比例缩放水印 (默认 true)*/
	public void setAutoScale(boolean autoScale) {
		this.autoScale = autoScale;
	}
	/**新图片文件绝对路径<b> 如：D:\\img.jpg</b><br>如果需要新生成一个带水印的图片文件则此字段非空，如果需要在原图片上加水印则此字段为空*/
	public String getNewFilePath() {
		return newFilePath;
	}
	/**新图片文件绝对路径<b> 如：D:\\img.jpg</b><br>如果需要新生成一个带水印的图片文件则此字段非空，如果需要在原图片上加水印则此字段为空*/
	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}
	
}
