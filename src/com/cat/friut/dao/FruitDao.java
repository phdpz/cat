package com.cat.friut.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.cat.base.dao.BaseDao;
import com.cat.pojo.Fruitgoods;
import com.cat.util.QueryResult;

public interface FruitDao extends BaseDao<Fruitgoods>{	
	public int getTotalRecords(String hql) throws Exception;
	
	/**
	 * 根据属性查询
	 * 
	 * @param propertyName 	属性名
	 * @param value 		属性值
	 * @return　				查询结果集
	 */
	@SuppressWarnings("unchecked")
	public List<Fruitgoods> findByProperty(String propertyName, Object value) throws HibernateException;
	/**
	 * 获取所有实体对象集合（没有排序，没有筛选，没有分页）
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public void testHql() throws Exception;
	
	public List<String> findFruitList();
	
	public List<String> findFruitLocation(String fruitType);
	
	public List<Fruitgoods> findAll() throws Exception;
	
	/**
	 * 获取所有实体对象集合（没有分页，没有筛选）
	 * @param orderBy
	 *            排序子句，key为排序属性，value为升序（asc）或降序（desc），支持多排序，用法为：
	 *            LinkedHashMap<String, String> orderBy = new
	 *            LinkedHashMap<String, String>(); orderBy.put("属性名1","asc");
	 *            orderBy.put("属性名2","desc");....
	 * @return　所有实体对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<Fruitgoods> findAll(final LinkedHashMap<String, String> orderBy) throws Exception;
	
	public List<Fruitgoods> findAll(final LinkedHashMap<String,String> orderBy, final String where, final List<Object> queryParams) throws Exception;
	/**
	 * @Description 		获取分页数据（没有排序，没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Fruitgoods> listByPage(final int page,final int size);
	/**
	 * @Description 		获取分页数据（没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Fruitgoods> listByPage(final int page,final int size,	final LinkedHashMap<String, String> orderBy);
	

	/**
	 * @Description 获取分页数据（没有排序）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Fruitgoods> listByPage(final int page, final int size, final String where, final List<Object> queryParams);
	/**
	 * @Description 获取分页数据没有（有排序和筛选）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Fruitgoods> listByPage(final int page, final int size, final String where, final List<Object> queryParams, final LinkedHashMap<String, String> orderBy);
}
