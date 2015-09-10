package com.cat.base.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cat.util.GenericsUtils;

public interface BaseDao<T> {
	/******************增加相关********************/
	/**
	 * 保存一个对象
	 * @param entity
	 */
	public void save(T entity);

	/******************删除相关********************/
	/**
	 * 删除一个对象
	 * 
	 * @param entity 待删除的实体
	 * @throws Exception
	 */
	public void delete(T entity);
	/**
	 * 删除实体集
	 * @param entites 待删除的实体集
	 * @throws Exception
	 */
	public void deleteEntities(List<T> entites) throws Exception;

	/******************修改相关********************/
	/**
	 * 更新一个对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * @Description 根据Id查询单个对象
	 * @param e
	 * @param id
	 * @return Entity
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T findById(Serializable entityId) throws Exception;
	
	public List<T> findAllByHql(String hql) throws Exception;
	
	public List<String> findSomeByHql(String hql) throws Exception ;
	
	public Long count(String hql) throws Exception;
	
	public List<Object[]> findSomeByHql1(String hql) throws Exception;
}
