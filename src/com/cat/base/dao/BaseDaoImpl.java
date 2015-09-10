package com.cat.base.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cat.util.GenericsUtils;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T>{
	
	/*************** 注入sessionFactory，以获取当前数据库操作session**************************/
	@Autowired
	private SessionFactory sessionFactory;
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/*******************************对于泛型实体操作的具体实现*******************************/
	
	/******************增加相关********************/
	/**
	 * 保存一个对象
	 * @param entity
	 */
	public void save(T entity){
		this.getCurrentSession().save(entity);
	};

	/******************删除相关********************/
	/**
	 * 删除一个对象
	 * 
	 * @param entity 待删除的实体
	 * @throws Exception
	 */
	public void delete(T entity){
		this.getCurrentSession().delete(entity);
	};
	/**
	 * 删除实体集
	 * @param entites 待删除的实体集
	 * @throws Exception
	 */
	public void deleteEntities(List<T> entites) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Iterator<T> iter = entites.iterator();
		while(iter.hasNext()){
			session.delete(iter.next());
		}
		session.flush();
	}

	/******************修改相关********************/
	/**
	 * 更新一个对象
	 * 
	 * @param entity
	 */
	public void update(T entity){
		this.getCurrentSession().update(entity);
	};

	/******************查询相关********************/	
	// 当前泛型实体类类型
		@SuppressWarnings("unchecked")
		protected Class<T> entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());
	/**
	 * @Description 根据Id查询单个对象
	 * @param e
	 * @param id
	 * @return Entity
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T findById(Serializable entityId) throws Exception {
		return (T) this.sessionFactory.getCurrentSession().get(entityClass, entityId);
	};
	
	@SuppressWarnings("unchecked")
	public List findAllByHql(String hql) throws Exception {
		System.out.println(hql);
		return (List) this.sessionFactory.getCurrentSession().createQuery(hql).list();
	};

	@SuppressWarnings("unchecked")
	public List<String> findSomeByHql(String hql) throws Exception {
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
	};	
	
	@SuppressWarnings("unchecked")
	public Long count(String hql) throws Exception {
		List<Long> list=this.sessionFactory.getCurrentSession().createQuery(hql).list();
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findSomeByHql1(String hql) throws Exception {
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
	};
}
