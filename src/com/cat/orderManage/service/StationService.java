package com.cat.orderManage.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.pojo.Station;
import com.cat.util.QueryResult;

@Service
public interface StationService extends BaseService<Station>{
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Station> findAll() throws Exception;
	
	public QueryResult<Station>	findAllByPage(int page,int size);
	
	public List<String> findAllSchool();
	
	public List<Station> findBySchool(String school);
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Station findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords() throws Exception;
	
	public Integer findMaxId() throws Exception;
}
