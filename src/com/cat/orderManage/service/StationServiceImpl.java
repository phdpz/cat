package com.cat.orderManage.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.base.service.BaseServiceImpl;
import com.cat.orderManage.dao.StationDao;
import com.cat.pojo.Station;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("stationService")
public class StationServiceImpl extends BaseServiceImpl<Station> implements StationService{
	@Autowired
	protected StationDao stationDao;
	
	public StationServiceImpl(){
		super();
	}
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Station> findAll() throws Exception {
		return stationDao.findAll();
	}

	
	public QueryResult<Station>	findAllByPage(int page,int size){
		return stationDao.listByPage(page, size);
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Station findById(Serializable entityId) throws Exception {
		return stationDao.findById(entityId);
	}
	
	public int getTotalRecords() throws Exception{
		return stationDao.getTotalRecords();
	}
	
	public List<String> findAllSchool(){
		return stationDao.getAllSchool();
	}
	
	public List<Station> findBySchool(String school){
		return stationDao.findByProperty("SSchool", school);
	}
	
	public Integer findMaxId() throws Exception{
		String hql = "select max(s.SId) from Station s";
		return stationDao.findByHql(hql).get(0);
	}
}
