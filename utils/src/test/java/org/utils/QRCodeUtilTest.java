package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.jngld.utils.QRCodeUtil;

import junit.framework.TestCase;

public class QRCodeUtilTest extends TestCase{
	
	private String basePath = FileUtilTest.class.getResource("/").getPath();
	
	public void testCreateImage() {
		File file = new File("c:\\1\\7ab02402-215f-444e-bca2-46fc28cde783.jpg");
		try {
			InputStream in = new FileInputStream(file);
			try {
				String s = QRCodeUtil.decode(in);
				System.out.println(s);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
