package com.jngld.textutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WordFilter extends AbstractTrie implements Filter {
	
	private static String defaultWordsPath = WordFilter.class.getResource("/words.txt").getPath();
	
	private static List<String> asterisk = null;
	
	static {
        asterisk = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++){
            sb.append("*");
            asterisk.add(sb.toString());
        }
    }
	
	/**
	 * 创建非法词过滤器，非法词库为默认词库，非法词容差为0
	 */
	public WordFilter() {
		super();
		//加入非法词默认词典
		addThesaurus(defaultWordsPath);
	}
	
	public String filterText(String text) {
		List<Word> words = search(text);
		return filterWords(text, words);
	}
	
	public String filterText(String text, int MaxIllegalNum) {
		List<Word> words = search(text);
		if(words.size() <= MaxIllegalNum) {
			return filterText(text);
		} else {
			return "内容涉及非法词汇！";
		}
	}
	
	private String filterWords(String text, List<Word> words) {
		for(Word word : words) {
			Set<String> strs = word.getWordForm();
			for(String str : strs) {
				text = replaceAsterisk(text, str);
			}
		}
		return text;
	}
	
	private String replaceAsterisk(String text, String word) {
		int length = word.length();
		length = length > 4 ? 4 : length;
		String asteriskStr = asterisk.get(length - 1);
		return text.replace(word, asteriskStr);
	} 

}
