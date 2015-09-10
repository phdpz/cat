package com.cat.client.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.pojo.Clientinfo;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

@Service
public interface ClientService extends BaseService<Clientinfo>{
	public Clientinfo login(String openId,String nickName,String sex,String city,String province,
			String country,String headimgurl) throws Exception;
	
	public Clientinfo hasUser(String openId) throws Exception;
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Clientinfo> findAll() throws Exception;
	
	public QueryResult<Clientinfo>	findAllByPage(int page,int size);
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Clientinfo findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords() throws Exception;
	
	public List<Object[]> getPerInfo(String openId) throws Exception ;
	
	public List<Orderinfo> getOrderByOpenId(String openId) throws Exception ;
}
