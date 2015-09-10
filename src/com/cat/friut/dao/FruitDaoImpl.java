package com.cat.friut.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.cat.pojo.Fruitgoods;

@Repository("fruitDao")
public class FruitDaoImpl extends BaseDaoImpl<Fruitgoods> implements FruitDao{
	public FruitDaoImpl(){
		super();
	}
	/*************** 注入sessionFactory，以获取当前数据库操作session**************************/
	@Autowired
	private SessionFactory sessionFactory;
	
	/******************************* 解决泛型精确匹配，提高通用性************************************/
	// 当前泛型实体类类型
	@SuppressWarnings("unchecked")
	protected Class<Fruitgoods> entityClass = (Class<Fruitgoods>) GenericsUtils.getSuperClassGenricType(this.getClass());
	//
	protected static String tableName = "Fruitgoods";
	// 配置组件，方便方法的使用
	/**
	 * @Description 获取记录总数
	 * @return int
	 * @throws Exception
	 */
	
	public int getTotalRecords(String hql) throws Exception{
		return ((Long)this.sessionFactory.getCurrentSession().createQuery(hql).uniqueResult()).intValue();
	}
	
	public void testHql() throws Exception{
		String hql="from Orderinfo where orderTime between ? and ?";
		Date date=new Date();
		
		Date date1 = new Date(date.getTime()-3*24*60*60*1000);
		String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
		Timestamp s1 = Timestamp.valueOf(d1);
		
		Date date2 = new Date(date.getTime()+3*24*60*60*1000);
		String d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2);
		Timestamp s2 = Timestamp.valueOf(d2);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		
		query.setDate(0, date1);
		query.setDate(1, date2);
		query.list();
	}
	
	public List<String> findFruitList(){
		String hql = "select distinct f.FFruitType from Fruitgoods as f";
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	public List<String> findFruitLocation(String fruitType){
		String hql = "select distinct f.FLocation from Fruitgoods as f where f.FFruitType = '"+fruitType+"'";
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
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
	public List<Fruitgoods> findByProperty(String propertyName, Object value) throws HibernateException{
		String hql = "from " + tableName + " o where o." + propertyName + "= ?";
		return (List<Fruitgoods>) this.sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, value).list();
	}
	/**
	 * 获取所有实体对象集合（没有排序，没有筛选，没有分页）
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Fruitgoods> findAll() throws Exception{
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
	public List<Fruitgoods> findAll(final LinkedHashMap<String, String> orderBy) throws Exception{
		
		return (List<Fruitgoods>) this.sessionFactory.getCurrentSession().createQuery("from " + tableName +" o " + buildOrderby(orderBy)).list();
	}
	
	public List<Fruitgoods> findAll(final LinkedHashMap<String,String> orderBy, final String where, final List<Object> queryParams) throws Exception{
		String hql = "from " + tableName + " o " + " where " + where+buildOrderby(orderBy);
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
		setQueryParams(query, queryParams);
		return query.list();
	}
	/**
	 * @Description 		获取分页数据（没有排序，没有筛选）
	 * @param page 			当前页面号
	 * @param size
	 * @param entityName
	 * @return QueryResult
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Fruitgoods> listByPage(final int page,final int size){ 
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery("from " + tableName); 
        final QueryResult<Fruitgoods> queryResult = new QueryResult<Fruitgoods>();
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
	public QueryResult<Fruitgoods> listByPage(final int page,final int size,final LinkedHashMap<String, String> orderBy){ 
		//拼接Hql查询语句
		String hql = "from " + tableName + " o " + buildOrderby(orderBy);
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
        //获取总记录数
        final QueryResult<Fruitgoods> queryResult = new QueryResult<Fruitgoods>();
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
	public QueryResult<Fruitgoods> listByPage(final int page, final int size, final String where, final List<Object> queryParams){ 
		//拼接HQL查询语句
		String hql = "from " + tableName + " o " + " where " + where;
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
		//将过滤条件配置到查询中
        setQueryParams(query, queryParams);
        //获取总记录数
        final QueryResult<Fruitgoods> queryResult = new QueryResult<Fruitgoods>();
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
	public QueryResult<Fruitgoods> listByPage(final int page, final int size, final String where, final List<Object> queryParams, final LinkedHashMap<String, String> orderBy){ 
		//拼接HQL查询语句
		String hql = "from " + tableName + " o " + " where " + where + buildOrderby(orderBy);
		//获取查询数据
        Query query=sessionFactory.getCurrentSession().createQuery(hql); 
		//将过滤条件配置到查询中
        setQueryParams(query, queryParams);
        //获取总记录数
        final QueryResult<Fruitgoods> queryResult = new QueryResult<Fruitgoods>();
        queryResult.setTotalRecords(query.list().size());
        //获取分页数
        query.setFirstResult((page-1)*size).setMaxResults(size);
        queryResult.setResultList(query.list());
        return queryResult; 
	}
}
