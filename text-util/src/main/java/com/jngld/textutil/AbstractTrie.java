package com.jngld.textutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

abstract class AbstractTrie implements Trie{
	
	private int ALW = 0;
	
	//字典树的根节点
    private Map<Object, Object> rootMap = new HashMap<Object, Object>();
	
    private List<String> wordsPaths = null;
    
    //容差字符集
    private static Map<Character, Boolean> MARK = null;
    
    static {
    	MARK = new HashMap<Character, Boolean>();
    	for(int i = 0; i < 128; i++) {
    		MARK.put((char)i, true);
    	}
        MARK.put('？', true);
        MARK.put('。', true);
        MARK.put('！', true);
        MARK.put('，', true);
        MARK.put('、', true);
        MARK.put('；', true);
        MARK.put('：', true);
        MARK.put('「', true);
        MARK.put('」', true);
        MARK.put('『', true);
        MARK.put('』', true);
        MARK.put('‘', true);
        MARK.put('’', true);
        MARK.put('“', true);
        MARK.put('”', true);
        MARK.put('（', true);
        MARK.put('）', true);
        MARK.put('〔', true);
        MARK.put('〕', true);
        MARK.put('【', true);
        MARK.put('】', true);
        MARK.put('—', true);
        MARK.put('…', true);
        MARK.put('–', true);
        MARK.put('．', true);
        MARK.put('《', true);
        MARK.put('》', true);
        MARK.put('〈', true);
        MARK.put('〉', true);
    }
	
	public AbstractTrie() {
		this.wordsPaths = new ArrayList<String>();
	}
	
	
	
	/**
	 * 读取所有词库文件
	 * @author liuy-8
	 * @date 2015年11月30日 下午4:06:58 
	 * @param wordsPaths 词库文件路径集合
	 * @return 所有的词
	 * @throws IOException
	 */
	protected List<String> readAllWordsFile(List<String> wordsPaths) throws IOException {
		List<String> words = new ArrayList<String>();
		if(wordsPaths != null) {
			for(String wordsPath : wordsPaths) {
				words.addAll(readWordsFile(wordsPath));
			}
		}
		return words;
	}
	
	/**
	 * 读取词库文件
	 * @author liuy-8
	 * @date 2015年11月30日 下午4:03:19 
	 * @param path 词库文件路径
	 * @return 所有的词
	 * @throws IOException
	 */
	protected List<String> readWordsFile(String path) throws IOException {
        List<String> words = new ArrayList<String>();
        
        FileReader file = null;
        BufferedReader br = null;
		try {
			file = new FileReader(path);
			br = new BufferedReader(file);
			 String s;
            while ((s = br.readLine()) != null) {
                words.add(s);
            }
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(file);
		} 
        return words;
    }
	
	/**
	 * 方法描述说明
	 * @author liuy-8
	 * @date 2015年11月30日 下午4:21:18 
	 * @param words
	 * @throws IOException 
	 */
	public void createTrie() throws IOException {
		if(wordsPaths.size() == 0) {
			throw new RuntimeException("没有任何词典文件");
		}
		List<String> words = readAllWordsFile(wordsPaths);
		for (String word : words) {
			Map<Object, Object> parentMap = rootMap;
			char[] letters = word.toCharArray();
			// Console.WriteLine(word.Length);
			// 将每个字节插入字典树
			for (int i = 0; i < letters.length; i++) {
				Map<Object, Object> map = (Map) parentMap.get(letters[i]);

				// 该字符对应的节点不存在创建对应节点
				if (map == null) {
					map = new HashMap<Object, Object>();
					parentMap.put(letters[i], map);
				}
				// 将该节点设置为父节点
				parentMap = map;

				// 非法词结束，设置结束标志
				if (i == word.length() - 1) {
					if (parentMap.get("end") == null)
						parentMap.put("end", word);
				}
			}// end for bytes
		}// end for words
        words = null;
    }
	
	protected List<Word> search(String content) {
		//词的缓存
	    StringBuilder wordBuffer = new StringBuilder();
	    Map<String, Word> wordMap = new HashMap<String, Word>();
	    
        int rollback = 0;		//回滚数
        int pos = 0;			//当前比较的位置
        int alw = 0;			//容差位

        Map parentMap = rootMap;
        char[] letters = content.toCharArray();
        for (; pos < content.length(); pos++) {
            char c = letters[pos];
            //将词暂时缓存
            wordBuffer.append(c);
            Map<Object, Object> map = (Map)parentMap.get(c);

            if (map == null) {
                //全角字符的第一个字节总是被置为163
            	//若进入非法词且碰到特殊字符，容差位加一
                if (rollback > 0 && alw < ALW && MARK.containsKey(c)) {
                    alw++;	//容差加一
                }
                else {
                    //回滚
                    pos -= (rollback + alw);
                    //回滚数清零
                    rollback = 0;
                    //返回字典树根节点
                    parentMap = rootMap;
                    wordBuffer.setLength(0);;
                    //确定不是违禁词，容差清零
                    alw = 0;
                }
            } else {
                if (map.containsKey("end")) {
                	String theWord = (String) map.get("end");
                    //回滚数清零
                    rollback = 0;
                    //返回字典树根节点
                    parentMap = rootMap;
                    //记录该非法词
                    String illegalWord = wordBuffer.toString();
                    wordBuffer.setLength(0);
                    if(!wordMap.containsKey(theWord)) {
                    	Word word = new Word();
                    	word.setWord(theWord);
                    	word.addNum();
                    	word.addWordForm(illegalWord);
                    	wordMap.put(theWord, word);
                    } else {
                    	Word word = wordMap.get(theWord);
                    	word.addNum();
                    	word.addWordForm(illegalWord);
                    }
                } else {
                    rollback++;
                    parentMap = map;
                    alw = 0;//是违禁词的一部分，容差清零
                }//end if node.isWordEnd()
            }//end if node == null
        }//end for bytes
        return new ArrayList<Word>(wordMap.values());
    }
	
	/**
	 * 设置容差位，默认容差位为0，建议最大设置为1
	 * 1代表色_情这类中间有一个字符的也可以被过滤掉
	 * @author liuy-8
	 * @date 2015年11月30日 下午4:32:59 
	 * @param alw
	 */
	public void setAlw(int alw) {
		this.ALW = alw;
	}
	
	/**
	 * 得到当前容差位
	 * @author liuy-8
	 * @date 2015年11月30日 下午4:35:57 
	 * @return 当前容差位
	 */
	public int getAlw() {
		return ALW;
	}
	
	/**
	 * 增加词库文件
	 * @author liuy-8
	 * @date 2015年12月1日 上午11:11:26 
	 * @param thesaurusPath
	 */
	public void addThesaurus(String thesaurusPath) {
		wordsPaths.add(thesaurusPath);
	}

}
