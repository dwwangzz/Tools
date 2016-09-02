package com.jngld.textutil;

import java.util.List;

public interface Search extends Trie{
	
	/**
	 * 匹配到的词的详细信息
	 * @author liuy-8
	 * @date 2015年11月30日 下午3:30:52 
	 * @return 词的详细信息
	 */
	public List<Word> matchedWordsInfo(String text);
	
	/**
	 * 只得到匹配到的词
	 * @author liuy-8
	 * @date 2015年11月30日 下午3:31:18 
	 * @return 匹配到的词
	 */
	public List<String> getOnlyWord(String text);

}
