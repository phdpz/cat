package com.cat.orderManage.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.orderManage.model.Clientaddr;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

@Service
public interface OrderinfoService extends BaseService<Orderinfo>{
	
	public List<Orderinfo> getOrder(String school, String area,
			int orderType, String beginDate, String endDate)
			throws Exception ;
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Clientaddr> findClientaddr(String openId);
	
	public List<Orderinfo> findAll(String school) throws Exception;
	
	public List<Orderinfo> findAllByTimeAndSchool(String school,String area,String from,String to,boolean include) throws Exception;
	
	public LinkedHashMap<String,Double> analyseMoney(String region,String from,String to) throws Exception;
	
	public QueryResult<Orderinfo>	findAllByPage(int page,int size);
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Orderinfo findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords() throws Exception;
	
	public void sureOrder(String orderNum) throws Exception;
}
