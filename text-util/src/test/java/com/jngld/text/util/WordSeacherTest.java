package com.jngld.text.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jngld.textutil.Search;
import com.jngld.textutil.Word;
import com.jngld.textutil.WordSearch;

import junit.framework.TestCase;

public class WordSeacherTest extends TestCase{
	
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
     * @Description 方法描述说明
     * @author liuy-8
     * @date 2015年12月1日 下午12:13:26 
     */
    public void testMatchedWordsInfo() {
    	Search search = new WordSearch();
    	search.addThesaurus(WordFilterTest.class.getResource("/words.txt").getPath());
    	try {
			search.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	List<Word> words = search.matchedWordsInfo("鹏生在包二奶，子正也在包二奶");
    	assertNotNull(words);
    	assertTrue(words.size() == 1);
    	assertTrue(words.get(0).getWord().equals("包二奶"));
    	assertTrue(words.get(0).getNum() == 2);
    }
    
    /**
     * @Description 测试得到关键词
     * @author liuy-8
     * @date 2015年12月1日 下午12:13:13 
     */
    public void testGetOnlyWord() {
    	Search search = new WordSearch();
    	search.addThesaurus(WordFilterTest.class.getResource("/words.txt").getPath());
    	try {
			search.createTrie();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	List<String> words = search.getOnlyWord("鹏生在包二奶，子正也在包二奶");
    	assertNotNull(words);
    	assertTrue(words.size() == 1);
    	assertTrue(words.get(0).equals("包二奶"));
    }
    
    public void testRuntimeException() {
    	Search search = new WordSearch();
		try {
			search.createTrie();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		} 
	}

}
