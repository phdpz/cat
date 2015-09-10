package com.cat.client.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.service.BaseServiceImpl;
import com.cat.client.dao.ClientDao;
import com.cat.pojo.Clientinfo;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("clientService")
public class ClientServiceImpl extends BaseServiceImpl<Clientinfo> implements ClientService{
	

	@Autowired
	protected ClientDao clientDao;
	private String hql;
	public ClientServiceImpl(){
		super();
	}
	
	//登录
	public Clientinfo login(String openId,String nickName,String sex,String city,String province,
			String country,String headimgurl) throws Exception{
		Clientinfo client = hasUser(openId);
		if(client == null){
			client = new Clientinfo();
			client.setOpenId(openId);
			client.setNickName(nickName);
			client.setSex(sex);
			client.setCity(city);
			client.setProvince(province);
			client.setCountry(country);
			client.setHeadimgurl(headimgurl);
			client.setScroe(0.0);
		}
		return client;
	}
	
	public Clientinfo hasUser(String openId) throws Exception{
		return clientDao.findById(openId);
	}
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Clientinfo> findAll() throws Exception {
		return clientDao.findAll();
	}

	
	public QueryResult<Clientinfo>	findAllByPage(int page,int size){
		return clientDao.listByPage(page, size);
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Clientinfo findById(Serializable entityId) throws Exception {
		return clientDao.findById(entityId);
	}
	
	public int getTotalRecords() throws Exception{
		return clientDao.getTotalRecords();
	}
	
	@Override
	public List<Object[]> getPerInfo(String openId) throws Exception {
		hql="select c.headimgurl,c.nickName,c.scroe from Clientinfo c where c.openId="+openId;
		List<Object[]> list=clientDao.findPerInfo(hql);
		return list;
	}
	@Override
	public List<Orderinfo> getOrderByOpenId(String openId) throws Exception {
		hql="from Orderinfo o where o.openId="+openId;
		return clientDao.findOrderByOpenId(hql);
	}
	
}
