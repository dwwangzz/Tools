package org.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.jngld.utils.EmptyUtil;

import junit.framework.TestCase;

public class EmptyUtilTest extends TestCase{
	
	public void testIsNotEmpty() { 
		
		assertTrue(EmptyUtil.isEmpty(""));
		assertTrue(EmptyUtil.isEmpty(new byte[0]));
		assertTrue(EmptyUtil.isEmpty(new HashMap()));
		assertTrue(EmptyUtil.isEmpty(new ArrayList<String>()));
	}
	
	public void testIsNotNull() {
		assertTrue(EmptyUtil.isNull(null));
		assertFalse(EmptyUtil.isNull(""));
	}

}
