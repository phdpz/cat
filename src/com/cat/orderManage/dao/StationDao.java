package com.cat.orderManage.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.cat.base.dao.BaseDao;
import com.cat.pojo.Station;
import com.cat.util.QueryResult;

public interface StationDao extends BaseDao<Station>{	
	public int getTotalRecords() throws Exception;
	
	/**
	 * 根据属性查询
	 * 
	 * @param propertyName 	属性名
	 * @param value 		属性值
	 * @return　				查询结果集
	 */
	@SuppressWarnings("unchecked")
	public List<Station> findByProperty(String propertyName, Object value) throws HibernateException;
	/**
	 * 获取所有实体对象集合（没有排序，没有筛选，没有分页）
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Station> findAll() throws Exception;
	
	public List<String> getAllSchool();
	
	public List<Integer> findByHql(String hql);
	
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
	public List<Station> findAll(final LinkedHashMap<String, String> orderBy) throws Exception;
	/**
	 * @Description 		获取分页数据（没有排序，没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Station> listByPage(final int page,final int size);
	/**
	 * @Description 		获取分页数据（没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Station> listByPage(final int page,final int size,	final LinkedHashMap<String, String> orderBy);
	

	/**
	 * @Description 获取分页数据（没有排序）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Station> listByPage(final int page, final int size, final String where, final List<Object> queryParams);
	/**
	 * @Description 获取分页数据没有（有排序和筛选）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Station> listByPage(final int page, final int size, final String where, final List<Object> queryParams, final LinkedHashMap<String, String> orderBy);
}
