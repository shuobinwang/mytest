package com.itheima.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

//连接solr服务，进行索引的增删改查
//添加索引   也可以根据id修改索引
public class SolrDemo {
	@Test
	public void insertIndex() throws Exception{
//		1、	创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		String baseURL = "http://localhost:8080/solr";
		SolrServer hss = new HttpSolrServer(baseURL);
//		2、	创建SolrInputDocument对象，然后通过它来添加域。
		SolrInputDocument document = new SolrInputDocument();
		//id域是必须要有的
		document.addField("id","006");
		document.addField("name", "《计算机科学丛书：Java编程思想（第4版）》赢得了全球程序员的广泛赞誉。");
		document.addField("price", 55f);
		document.addField("author", "11");
		
		
//		3、	通过HttpSolrServer对象将SolrInputDocument添加到索引库。  有时间会自动提交
		hss.add(document, 1000);
//		4 、	提交。
		//hss.commit();
	}
	
	//删除索引
	@Test
	public void deleteIndex() throws Exception{
//		1、	创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		String baseURL = "http://localhost:8080/solr";
		SolrServer hss = new HttpSolrServer(baseURL);
//		2.根据id进行删除, 有时间会自动提交
		//hss.deleteById("002",1000);
//		3根据条件进行删除, 有时间会自动提交
		hss.deleteByQuery("name:曾",1000);
	}
	
	//查询索引  //简单查询
	@Test
	public void testIndex() throws Exception{
		//1、创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		String baseURL = "http://localhost:8080/solr";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		
		//2.创建solrquery查询对象
		SolrQuery solrQuery = new SolrQuery();
		
		//3/设置查询条件
		//如果用set，则前边必须加上quer对象q
		//solrQuery.set("q","*:*");
		//查询所有
		//solrQuery.setQuery("*:*");
		//按条件查询
		solrQuery.setQuery("name:计算");
		
		//4.发起搜索请求,返回搜索请求对象,传入要查询的对象
		QueryResponse qr = solrServer.query(solrQuery);
		
		//5.通过搜索请求对象，获取结果集
		SolrDocumentList results = qr.getResults();
		System.out.println("搜索到的结果总数为："+ results.getNumFound());
		
		//输出结果
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument);
			System.out.println("=========================");
			System.out.println("id"+":"+solrDocument.get("id"));
			System.out.println("name"+":"+solrDocument.get("name"));
			System.out.println("--------------------------------------");
		}
	}
}
