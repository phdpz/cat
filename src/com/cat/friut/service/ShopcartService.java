package com.cat.friut.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.pojo.Shopcart;
import com.cat.util.QueryResult;


public interface ShopcartService extends BaseService<Shopcart>{
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Shopcart> findAll() throws Exception;
	
	public QueryResult<Shopcart> findAllByPage(int page,int size);
	
	public Double countTotalPrice(String openId);
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Shopcart findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords() throws Exception;
	
	public Shopcart findByUserAndFruit(String openId,Integer fruitId);
	
	public void deleteShopcarts(String openId) throws Exception;
	
	public List<Shopcart> findByUser(String openId);
	
	public List<Object[]> findByUserGroupByType(String openId);
	
	public Integer countShopcartByUser(String openId);
}
