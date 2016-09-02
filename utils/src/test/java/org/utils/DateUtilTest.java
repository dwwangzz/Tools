/**
 * glodon.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 * org.utils
 */
package org.utils;

import com.jngld.utils.DateUtil;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil测试类
 * @author wangzz-a
 * @version $Id: DateUtilTest.java, v 0.1 2015年12月2日 上午10:05:42 wangzz-a Exp $
 */
public class DateUtilTest extends TestCase {

	@Before
	public void setUp() {
		//System.out.println("=======@before=======");
	}

	@After
	public void tearDown() {
		//System.out.println("=======@after=======");
	}
	
	public void testDateToStr() {
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 12, 12, 12);
		Date date = cld.getTime();
		assertEquals(DateUtil.dateToStr(date), "2012-12-12 12:12:12");
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtSimpleChineseWithTime), "2012年12月12日 12:12:12");
	}
	
	public void testStrToDate(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 12, 12, 12);
		Date date = cld.getTime();
		try {
			Date date2 = DateUtil.strToUtilDate("2012-12-12 12:12:12",DateUtil.simple);
			assertEquals(DateUtil.dateToStr(date,DateUtil.simple), DateUtil.dateToStr(date2,DateUtil.simple));
		} catch (ParseException e) {
			assertTrue(false);
		}
	}
	
	public void testIsLeapYear(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 12, 12, 12);
		Date date = cld.getTime();
		assertTrue(DateUtil.isLeapYear(date));
		assertTrue(DateUtil.isLeapYear(2012));
	}
	
	public void testGetOffsetDays(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 12, 12, 12);
		Date date = cld.getTime();
		cld.add(Calendar.DAY_OF_MONTH, 5);
		Date date2 = cld.getTime();
		assertEquals(DateUtil.getOffsetDays(date,date2), 5l);
	}
	
	public void testAddField(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		date = DateUtil.addField(date, DateUtil.DAY, -2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20121210");
		date = DateUtil.addField(date, DateUtil.MONTH, -2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20121010");
		date = DateUtil.addField(date, DateUtil.YEAR, -2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20101010");
	}
	
	public void testAddYear(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		
		date = DateUtil.addYear(date, 2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20141212");
	}
	
	public void testAddMonth(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		
		date = DateUtil.addMonth(date, 2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20130212");
	}
	
	public void testAddDay(){
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		
		date = DateUtil.addDay(date, 2);
		assertEquals(DateUtil.dateToStr(date,DateUtil.dtShort), "20121214");
	}

	public void testGetField(){
		//无法测试
		int quarter = DateUtil.getField(DateUtil.QUARTER);
		int year = DateUtil.getField(DateUtil.YEAR);
		int month = DateUtil.getField(DateUtil.MONTH);
		int day = DateUtil.getField(DateUtil.DAY);
		//System.out.println("当前时间是:"+year+"年，第"+quarter+"季度，"+month+"月" + day +"号");
		
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		quarter = DateUtil.getField(date,DateUtil.QUARTER);
		year = DateUtil.getField(date,DateUtil.YEAR);
		month = DateUtil.getField(date,DateUtil.MONTH);
		day = DateUtil.getField(date,DateUtil.DAY);
		assertEquals(quarter, 4);
		assertEquals(year, 2012);
		assertEquals(month, 12);
		assertEquals(day, 12);
	}
	
	public void testGetMaxDay(){
		//本月最大天数 - 无法测试
		int max = DateUtil.getMaxDay();
		Calendar cld = Calendar.getInstance();
		cld.set(2012, 11, 12, 0, 0, 0);
		Date date = cld.getTime();
		max = DateUtil.getMaxDay(date);
		assertEquals(max, 31);
		max = DateUtil.getMaxDay(2020, 2);
		assertEquals(max, 29);
	}
	
	public void testGetDuration() throws InterruptedException{
		//无法测试
		long c1 = System.currentTimeMillis();
		Thread.sleep(123);
		long c2 = System.currentTimeMillis();
		String time = DateUtil.getDuration(Math.abs(c1-c2));
		System.out.println(time);
		
	}

}
