package org.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

import com.jngld.utils.Base64Util;
import com.jngld.utils.ConvertUtil;
import com.jngld.utils.FileUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class base64Test {
   
	public static void main(String[] args) {
		try {
			FileImageInputStream fis = new FileImageInputStream(new File("c:\\2.jpg"));
			String s = Base64Util.encodeImgBase64(fis);
			Base64Util.decodeImgBase64(s, new FileOutputStream(new File("C:\\3.jpg")));
			//FileUtil.saveFile("c:\\img2.txt", s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
