package com.jngld.textutil;

public interface Filter extends Trie{
	
	/**
	 * 过滤文本
	 * @author liuy-8
	 * @date 2015年11月30日 下午2:36:55 
	 * @param text 待过滤文本
	 * @return 过滤后的文本
	 */
	public String filterText(String text);
	
	/**
	 * 过滤文本
	 * @author liuy-8
	 * @date 2015年11月30日 下午2:36:53 
	 * @param text 待过滤文本
	 * @param MaxIllegalNum 允许出现非法词的最大次数
	 * @return 过滤后的文本
	 */
	public String filterText(String text, int MaxIllegalNum);
	
}
