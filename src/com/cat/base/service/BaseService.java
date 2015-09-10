package com.cat.base.service;

public interface BaseService<T> {
	public void save(T t) throws Exception;
	/**
	 * 修改实体
	 * 
	 * @param t
	 *            待修改的实体
	 * @throws Exception
	 */
	public void update(T t) throws Exception;
	
	/**
	 * 删除实体
	 * 
	 * @param t
	 *            待修改的实体
	 * @throws Exception
	 */
	public void delete(T t) throws Exception;
}
