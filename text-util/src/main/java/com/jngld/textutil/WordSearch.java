package com.jngld.textutil;

import java.util.ArrayList;
import java.util.List;

public class WordSearch extends AbstractTrie implements Search{
	
	public WordSearch() {
		super();
	}

	public List<Word> matchedWordsInfo(String text) {
		List<Word> words = search(text);
		return words;
	}

	public List<String> getOnlyWord(String text) {
		List<Word> words = matchedWordsInfo(text);
		List<String> onlyWords = new ArrayList<String>();
		for(Word word : words) {
			onlyWords.add(word.getWord());
		}
		return onlyWords;
	}

}
