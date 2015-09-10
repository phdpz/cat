package com.cat.admin.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cat.util.GenericsUtils;
import com.cat.util.QueryResult;
import com.cat.base.dao.BaseDaoImpl;
import com.cat.pojo.Admin;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{
	public AdminDaoImpl(){
		super();
	}
	/*************** 注入sessionFactory，以获取当前数据库操作session**************************/
	@Autowired
	private SessionFactory sessionFactory;
	
	/******************************* 解决泛型精确匹配，提高通用性************************************/
	// 当前泛型实体类类型
	@SuppressWarnings("unchecked")
	protected Class<Admin> entityClass = (Class<Admin>) GenericsUtils.getSuperClassGenricType(this.getClass());
	//
	protected static String tableName = "Admin";
	// 配置组件，方便方法的使用
	/**
	 * @Description 获取记录总数
	 * @return int
	 * @throws Exception
	 */
	
	public int getTotalRecords() throws Exception{
		return ((Long)this.sessionFactory.getCurrentSession().createQuery("select count(*) from "+tableName).uniqueResult()).intValue();
	}
	/**
	 * @Description			设置查询参数
	 * @param query 		查询对象
	 * @param queryParams 	参数值
	 */
	protected static void setQueryParams(Query query, List<Object> queryParams) {
		if (queryParams != null && queryParams.size() > 0) {
			for (int i = 0; i < queryParams.size(); i++) {
				query.setParameter(i, queryParams.get(i));// 从0开始
			}
		}
	}
	/**
	 * 组装order by子句
	 * 
	 * @param orderby
			由属性与asc/desc构成的Map，其中key为属性，value为asc/desc
	 * @return order by 子句
	 */
	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuilder orderbyql = new StringBuilder("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}
	/**
	 * 根据属性查询
	 * 
	 * @param propertyName 	属性名
	 * @param value 		属性值
	 * @return　				查询结果集
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> findByProperty(String propertyName, Object value) throws HibernateException{
		String hql = "from " + tableName + " o where o." + propertyName + "= ?";
		return (List<Admin>) this.sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, value).list();
	}
	/**
	 * 获取所有实体对象集合（没有排序，没有筛选，没有分页）
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Admin> findAll() throws Exception{
		return findAll(null);
	}
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
	public List<Admin> findAll(final LinkedHashMap<String, String> orderBy) throws Exception{
		
		return (List<Admin>) this.sessionFactory.getCurrentSession().createQuery("from " + tableName +" o " + buildOrderby(orderBy)).list();
	}
	/**
	 * @Description 		获取分页数据（没有排序，没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Admin> listByPage(final int page,final int size){ 
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery("from " + tableName); 
        final QueryResult<Admin> queryResult = new QueryResult<Admin>();
        //获取总记录数
        queryResult.setTotalRecords(query.list().size());
        //获取分页数
        query.setFirstResult((page-1)*size).setMaxResults(size);
        queryResult.setResultList(query.list());
        return queryResult; 
	}
	/**
	 * @Description 		获取分页数据（没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Admin> listByPage(final int page,final int size,	final LinkedHashMap<String, String> orderBy){ 
		//拼接Hql查询语句
		String hql = "from " + tableName + " o " + buildOrderby(orderBy);
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
        //获取总记录数
        final QueryResult<Admin> queryResult = new QueryResult<Admin>();
        queryResult.setTotalRecords(query.list().size());
        //获取分页数
        query.setFirstResult((page-1)*size).setMaxResults(size);
        queryResult.setResultList(query.list());
        return queryResult; 
	}
	/**
	 * @Description 获取分页数据（没有排序）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Admin> listByPage(final int page, final int size, final String where, final List<Object> queryParams){ 
		//拼接HQL查询语句
		String hql = "from " + tableName + " o " + " where " + where;
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
		//将过滤条件配置到查询中
        setQueryParams(query, queryParams);
        //获取总记录数
        final QueryResult<Admin> queryResult = new QueryResult<Admin>();
        queryResult.setTotalRecords(query.list().size());
        //获取分页数
        query.setFirstResult((page-1)*size).setMaxResults(size);
        queryResult.setResultList(query.list());
        return queryResult; 
	}
	/**
	 * @Description 获取分页数据没有（有排序和筛选）
	 * @param page 当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Admin> listByPage(final int page, final int size, final String where, final List<Object> queryParams, final LinkedHashMap<String, String> orderBy){ 
		//拼接HQL查询语句
		String hql = "from " + tableName + " o " + " where " + where + buildOrderby(orderBy);
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
		//将过滤条件配置到查询中
        setQueryParams(query, queryParams);
        //获取总记录数
        final QueryResult<Admin> queryResult = new QueryResult<Admin>();
        queryResult.setTotalRecords(query.list().size());
        //获取分页数
        query.setFirstResult((page-1)*size).setMaxResults(size);
        queryResult.setResultList(query.list());
        return queryResult; 
	}
}
