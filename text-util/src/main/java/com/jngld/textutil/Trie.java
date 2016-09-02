package com.jngld.textutil;

import java.io.IOException;

interface Trie {
	
	/**
	 * 创建字典树
	 * @author liuy-8
	 * @date 2015年12月1日 下午1:53:50 
	 * @throws IOException
	 */
	public void createTrie() throws IOException;
	
	/**
	 * 添加词库文件
	 * @author liuy-8
	 * @date 2015年12月1日 下午1:54:08 
	 * @param thesaurusPath 词库文件物理路径
	 */
	public void addThesaurus(String thesaurusPath);
	
	/**
	 * 设置容差位
	 * @author liuy-8
	 * @date 2015年12月1日 下午1:54:17 
	 * @param alw 容差位
	 */
	public void setAlw(int alw);
	
	/**
	 * 获取当前容差位
	 * @author liuy-8
	 * @date 2015年12月1日 下午1:54:38 
	 * @return 当前容差位
	 */
	public int getAlw();

}
