package com.cat.orderManage.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.service.BaseServiceImpl;
import com.cat.orderManage.dao.OrderinfoDao;
import com.cat.orderManage.model.Clientaddr;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("orderinfoService")
public class OrderinfoServiceImpl extends BaseServiceImpl<Orderinfo> implements OrderinfoService{
	

	@Autowired
	protected OrderinfoDao orderinfoDao;
	
	public OrderinfoServiceImpl(){
		super();
	}
	
	@Override
	public List<Orderinfo> getOrder(String school, String area,
			int orderType, String beginDate, String endDate)
			throws Exception {
		String hql = "from Orderinfo o where o.school='"+school+"'";
		if(!area.equals("全部"))
			hql=hql+" and o.area='"+area+"'";
		switch(orderType){
		   case 0:
			   hql=hql+" and o.status='未提货'";
			   break;
		   case 2:
			   hql=hql+" and o.status='已提货'";
			   break;
		   case 1:
			   hql=hql+" and (o.status='已提货' or o.status='未提货')";
			   break;   
		}
		hql=hql+" and o.orderTime between '"+beginDate+"' and '"+endDate+"'";
		return orderinfoDao.findAllByHql(hql);
	}
	
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Orderinfo> findAll(String school) throws Exception {
		if(school==null)
			return orderinfoDao.findAll();
		else
			return orderinfoDao.findByProperty("school", school);
	}
	
	//根据时间和学校查询订单，include代表是否包含已提货的情况
	public List<Orderinfo> findAllByTimeAndSchool(String school,String area,String from,String to,boolean include) throws Exception {
		if(school==null&&area==null)
			return orderinfoDao.findAllByTimeAndSchool(null,null,null,from,to,include);
		else{
			if(school!=null&&area==null)
				return orderinfoDao.findAllByTimeAndSchool(null,school,null,from,to,include);
			else{
				if(school!=null&&area!=null)
					return orderinfoDao.findAllByTimeAndSchool(null, school,area, from, to,include);
			}
		}	
		return null;	
	}
	
	public LinkedHashMap<String,Double> analyseMoney(String region,String from,String to) throws Exception{
		LinkedHashMap<String,Double> map = new LinkedHashMap<String,Double>();
		int regionDate = Integer.valueOf(region)*24*60*60*1000;
		Date from1 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(from).getTime()
				+regionDate);
		double money;
		to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(to).getTime()
				+24*60*60*1000)); 
		
		while(from1.before(new SimpleDateFormat("yyyy-MM-dd").parse(to))){
			List<Orderinfo> list1 = orderinfoDao.findAllByTime(from+" 00:00:00", new SimpleDateFormat("yyyy-MM-dd").format(from1)+" 00:00:00");
			
			money = 0.0;
			for(int i=0;i<list1.size();i++){
				money+=list1.get(i).getTotalFee();
			}
			map.put(from+"<-->"+new SimpleDateFormat("yyyy-MM-dd").format(from1), money);
			from = new SimpleDateFormat("yyyy-MM-dd").format(from1);
			from1 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(from).getTime()
					+regionDate);
		}
		
		List<Orderinfo> list1 = orderinfoDao.findAllByTime(from+" 00:00:00", to+" 00:00:00");
		money = 0.0;
		for(int i=0;i<list1.size();i++){
			money+=list1.get(i).getTotalFee();
		}
		map.put(from+"<-->"+to,money);
		return map;
	}
	
	public List<Clientaddr> findClientaddr(String openId){
		List<Object[]> list = orderinfoDao.findDistinctByOpenId(openId);
		List<Clientaddr> aList = new ArrayList<Clientaddr>();
		Clientaddr clientaddr;
		for(Object[] object:list){
			clientaddr = new Clientaddr();
			clientaddr.setName((String)object[0]);
			clientaddr.setPhone((String)object[1]);
			clientaddr.setSchool((String)object[2]);
			clientaddr.setArea((String)object[3]);
			aList.add(clientaddr);
			
		}
		return aList;
	}
	
	public QueryResult<Orderinfo>	findAllByPage(int page,int size){
		return orderinfoDao.listByPage(page, size);
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Orderinfo findById(Serializable entityId) throws Exception {
		return orderinfoDao.findById(entityId);
	}
	
	public int getTotalRecords() throws Exception{
		return orderinfoDao.getTotalRecords();
	}
	
	@Override
	public void sureOrder(String orderNum) throws Exception {
		Orderinfo oderInfo=(Orderinfo)this.findById(orderNum);
		oderInfo.setStatus("已提货");
		orderinfoDao.update(oderInfo);
	}
	
}
