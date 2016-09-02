package com.jngld.textutil;

import java.util.HashSet;
import java.util.Set;

public class Word {
	
	private String word;
	
	private int num = 0;
	
	Set<String> wordForms = new HashSet<String>();

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getNum() {
		return num;
	}

	void addNum() {
		this.num++;
	}
	
	void addWordForm(String wordForm) {
		wordForms.add(wordForm);
	}
	Set<String> getWordForm() {
		return wordForms;
	}

}
