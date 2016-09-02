package com.jngld.text.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jngld.textutil.Filter;
import com.jngld.textutil.WordFilter;

import junit.framework.TestCase;

public class WordFilterTest extends TestCase{
	
	//容差字符集
    private static List<Character> MARK = null;
    
    static {
    	MARK = new ArrayList<Character>();
    	for(int i = 0; i < 128; i++) {
    		MARK.add((char)i);
    	}
        MARK.add('？');
        MARK.add('。');
        MARK.add('！');
        MARK.add('，');
        MARK.add('、');
        MARK.add('；');
        MARK.add('：');
        MARK.add('「');
        MARK.add('」');
        MARK.add('『');
        MARK.add('』');
        MARK.add('‘');
        MARK.add('’');
        MARK.add('“');
        MARK.add('”');
        MARK.add('（');
        MARK.add('）');
        MARK.add('〔');
        MARK.add('〕');
        MARK.add('【');
        MARK.add('】');
        MARK.add('—');
        MARK.add('…');
        MARK.add('–');
        MARK.add('．');
        MARK.add('《');
        MARK.add('》');
        MARK.add('〈');
        MARK.add('〉');
    }
	
	/**
	 * @Description 测试过滤功能
	 * @author liuy-8
	 * @date 2015年12月1日 上午11:14:20 
	 */
	public void testFilterText() {
		Filter wordFilter = new WordFilter();
		try {
			wordFilter.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = wordFilter.filterText("鹏生和子正都在包二奶");
		assertTrue(text.indexOf("包二奶") < 0);
	}
	
	/**
	 * @Description 测试允许出现非法词的最大次数
	 * @author liuy-8
	 * @date 2015年12月1日 上午11:14:30 
	 */
	public void testMaxIllegalNum() {
		Filter wordFilter = new WordFilter();
		try {
			wordFilter.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = wordFilter.filterText("鹏生和子正都在包二奶", 0);
		assertEquals("内容涉及非法词汇！", text);
	}
	
	/**
	 * @Description 测试容差位
	 * @author liuy-8
	 * @date 2015年12月1日 上午11:14:55 
	 */
	public void testSetAlw() {
		Filter wordFilter = new WordFilter();
		try {
			wordFilter.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wordFilter.setAlw(1);
		for(char c : MARK) {
			String text = wordFilter.filterText("鹏生和子正都在包二" + c + "奶");
			assertTrue(text.indexOf("*") > 0);
		}
	}
	
	/**
	 * @Description 测试词库扩展
	 * @author liuy-8
	 * @date 2015年12月1日 上午11:15:03 
	 */
	public void testAddThesaurus() {
		Filter wordFilter = new WordFilter();
		wordFilter.addThesaurus(WordFilterTest.class.getResource("/words_test.txt").getPath());
		try {
			wordFilter.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = wordFilter.filterText("尤鹏生和子正都在包二奶");
		assertTrue(text.indexOf("尤鹏生") < 0);
	}
	
}
