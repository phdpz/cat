package com.cat.friut.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.pojo.Fruitgoods;
import com.cat.util.QueryResult;

@Service
public interface FruitService extends BaseService<Fruitgoods>{
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Fruitgoods> findAll() throws Exception;
	
	public List<Fruitgoods> findAll(LinkedHashMap<String, String> orderBy,String native1,String fruitType) throws Exception;
	
	public QueryResult<Fruitgoods>	findAllByPage(int page,int size,String fruitType,String native1,LinkedHashMap<String, String> orderBy);
	
	public QueryResult<Fruitgoods>	findAllByPage1(int page,int size,String fruitType,String native1,LinkedHashMap<String, String> orderBy);
	
	public List<String> findAllType();
	
	public List<String> findLocationByType(String fruitType);
	
	public void test() throws Exception;
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Fruitgoods findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords(String fruitType,String native1) throws Exception;
}
