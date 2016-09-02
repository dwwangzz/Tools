package com.jngld.solr.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

public class SolrUtil {
	
	private HttpSolrClient solrClient = null;
	
	private String collection = null;
	
	public SolrUtil(HttpSolrClient solrClient, String collection) {
		if(solrClient == null || collection == null) {
			throw new IllegalArgumentException("The solrClient or collection must not be null");
		}
		this.solrClient = solrClient;
		this.collection = collection;
	}

	
	
	/**
	 * 向solr服务器中添加数据
	 * @author liuy-8
	 * @date 2015年7月3日 下午3:04:40 
	 * @param solrClient solr http 客户端
	 * @param collection 查询的core名字
	 * @param objects 数据实体列表
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public UpdateResponse add(List<?> objects) throws SolrServerException, IOException {
		UpdateResponse updateResponse = solrClient.addBeans(collection, objects);
		solrClient.commit(collection);
		return updateResponse;
	}
	
	/**
	 * @Description 向solr服务器中添加数据
	 * @author liuy-8
	 * @date 2015年7月3日 下午3:04:40 
	 * @param solrClient solr http 客户端
	 * @param collection 查询的core名字
	 * @param objects 数据实体
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public UpdateResponse add(Object object) throws SolrServerException, IOException{
		UpdateResponse updateResponse = solrClient.addBean(collection, object);
		solrClient.commit(collection);
		return updateResponse;
	}
	
	
	/**
	 * 
	 * 根据唯一id：删除对象
	 * @author huangqw
	 * @param collection 全文检索对应的core名称
	 * @param solrClient solr http 客户端
	 * @param uniqueKeyId 全文检索的唯一id
	 * @return 
	 * @date 2015年11月27日上午9:20:08
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static UpdateResponse delete(HttpSolrClient solrClient,String collection,String uniqueKeyId) throws SolrServerException, IOException {
		UpdateResponse updateResponse = solrClient.deleteById(collection,uniqueKeyId);
		solrClient.commit(collection);
		return updateResponse;
	}
	
	/**
	 * 
	 * 根据唯一id：删除对象（确切的是删除对象的集合） 
	 * @author huangqw
	 * @param solrClient solr http 客户端
	 * @param collection 全文检索对应的core名称
	 * @param ids 要删除的唯一id集合
	 * @return 
	 * @date 2015年11月27日上午9:45:43
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static UpdateResponse delete(HttpSolrClient solrClient,String collection,List<String> ids) throws SolrServerException, IOException {
		UpdateResponse updateResponse = solrClient.deleteById(collection,ids);
		solrClient.commit(collection);
		return updateResponse;
	}
	
	/**
	 * @throws IOException 
	 * @throws SolrServerException 
	 * 
	 * 删除所有数据
	 * @author huangqw
	 * @param solrClient solr http 客户端
	 * @param collection 全文检索对应的core名称
	 * @date 2015年11月27日上午9:46:49
	 * @throws
	 */
	public void deleteAll() throws SolrServerException, IOException {
		solrClient.deleteByQuery(collection,"*:*");
		solrClient.commit(collection);
	}

	
	public void update(List<?> objects) throws SolrServerException, IOException {
		add(objects);
	}
	
	/**
	 * @throws IOException 
	 * @throws SolrServerException 
	 * 
	 * 编辑全文检索数据
	 * @author huangqw
	 * @param solrClient solr http 客户端
	 * @param collection 全文检索对应的core名称
	 * @param object 需要编辑的对象
	 * @date 2015年11月27日上午9:22:53
	 * @throws
	 */
	public void update(Object object) throws SolrServerException, IOException {
		add(object);
	}
	
	/**
	 * 
	 * 在solr服务器中查询数据
	 * @author huangqw
	 * @param solrClient
	 * @param collection 查询的core名字
	 * @param solrQuery 查询方案
	 * @param beanClass 返回实体的class，用于bean转换
	 * @return Map<String,Object> 结果集，包括查询到的对象列表，查询到的数据总数量以及高亮
	 * @date 2015年11月27日上午9:36:01
	 * @throws
	 */
	public Map<String, Object> query(SolrQuery solrQuery, Class<?> beanClass){
		//结果集
		Map<String, Object> result = new HashMap<String, Object>();
		List<?> list = null;
		try {
			//查询结果
			QueryResponse queryResponse = solrClient.query(collection, solrQuery);
			Map<String, Map<String, List<String>>> hightlights = queryResponse.getHighlighting();
			list = queryResponse.getBeans(beanClass);
			//查询出总数量
			result.put("numFound", queryResponse.getResults().getNumFound());
			//返回列表对象
			result.put("datas", list);
			//高亮
			result.put("hightlights", hightlights);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
