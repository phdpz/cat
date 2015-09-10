package com.cat.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.dao.BaseDao;
import com.cat.base.dao.BaseDaoImpl;


@Service
public class BaseServiceImpl<T> implements BaseService<T>{

	@Autowired
	protected BaseDao<T> baseDao;
	/**
	 * 保存实体
	 * 
	 * @param t
	 *            待保存的实体
	 * @throws Exception
	 */
	public void save(T t) throws Exception {
		baseDao.save(t);
	}
	/**
	 * 修改实体
	 * 
	 * @param t
	 *            待修改的实体
	 * @throws Exception
	 */
	public void update(T t) throws Exception {
		baseDao.update(t);
	}
	
	/**
	 * 删除实体
	 * 
	 * @param t
	 *            待修改的实体
	 * @throws Exception
	 */
	public void delete(T t) throws Exception {
		baseDao.delete(t);
	}
}
