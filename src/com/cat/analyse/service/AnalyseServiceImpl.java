package com.cat.analyse.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.orderManage.dao.OrderinfoDao;
import com.cat.pojo.Orderinfo;
@Service("analyseService")
public class AnalyseServiceImpl implements AnalyseService{

	@Autowired
	private OrderinfoDao orderinfoDao;
	private String hql;
	
	@Override
	public List<String> getSchools() throws Exception {
		hql="select distinct s.SSchool from Station s";
		return orderinfoDao.findSomeByHql(hql);
	}

	@Override
	public Map<String,Object> getOrderSexNum(String school, String area,
			String beginDate, String endDate) throws Exception {
		hql="select o.openId,o.totalFee from Orderinfo o where (o.status='未提货' or o.status='已提货') and ";
		if(!school.equals("全部"))
			hql+=" o.school='"+school+"' and ";
		if(!area.equals("全部"))
			hql+="o.area='"+area+"' and ";
		hql+="o.orderTime between '"+beginDate+"' and '"+endDate+"'";
		List<Object[]> openList=orderinfoDao.findSomeByHql1(hql);
		String openId=null,sex=null;
		int boyNum=0,girlNum=0;
		Double boyMoney=0.0,girlMoney=0.0;
		for(int i=0;i<openList.size();i++){
			openId=openList.get(i)[0].toString();
			hql="select c.sex from Clientinfo c where c.openId='"+openId+"'";
			sex=orderinfoDao.findSomeByHql(hql).get(0);
			if(sex.equals("男"))
				{boyNum++; boyMoney+=(Double)openList.get(i)[1];}
			else
				{girlNum++; girlMoney+=(Double)openList.get(i)[1];}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("boy", boyNum);
		map.put("girl", girlNum);
		map.put("boyMoney", boyMoney);
		map.put("girlMoney", girlMoney);
		return map;
	}

	@Override
	public Map<String, Object> getOrderStationNum(String school,
			String beginDate, String endDate) throws Exception {
		hql="select o.area,count(o.area),SUM(o.totalFee) from Orderinfo o where o.school='"+school+"'";
		hql+=" and (o.status='未提货' or o.status='已提货') and o.orderTime between '"+beginDate+"' and '"+endDate+"' group by o.area";
		List<Object[]> areaList=orderinfoDao.findSomeByHql1(hql);

		hql="select s.SArea from Station s where s.SSchool='"+school+"'";
		List<String> stationList=orderinfoDao.findSomeByHql(hql);
		
		Map<String,Object> map=new HashMap<String,Object>();
		List<Integer> list1=new ArrayList<Integer>();
		List<Double> list2=new ArrayList<Double>();
		List<String> list3=new ArrayList<String>();
		
		for(int i=0;i<areaList.size();i++){
			list1.add(Integer.valueOf(areaList.get(i)[1].toString()));
			list2.add(Double.valueOf(areaList.get(i)[2].toString()));
			list3.add(areaList.get(i)[0].toString());
		}
		
		String area=null;
		for(int i=0;i<stationList.size();i++){
			area=stationList.get(i);
			if(!list3.contains(area)){
				list1.add(0);
				list2.add(0.0);
				list3.add(area);
			}
		}
		map.put("num", list1);
		map.put("money", list2);
		map.put("area", list3);
		return map;
	}
	
	@Override
	public Map<String, Object> getOrderSchoolNum(String beginDate,
			    String endDate) throws Exception {
		hql="select o.school,count(o.school),SUM(o.totalFee) from Orderinfo o where";
		hql+=" (o.status='未提货' or o.status='已提货') and o.orderTime between '"+beginDate+"' and '"+endDate+"' group by o.school";
		List<Object[]> orderList=orderinfoDao.findSomeByHql1(hql);

		hql="select distinct s.SSchool from Station s";
		List<String> schoolList=orderinfoDao.findSomeByHql(hql);
		
		Map<String,Object> map=new HashMap<String,Object>();
		List<Integer> list1=new ArrayList<Integer>();
		List<Double> list2=new ArrayList<Double>();
		List<String> list3=new ArrayList<String>();
		
		for(int i=0;i<orderList.size();i++){
			list1.add(Integer.valueOf(orderList.get(i)[1].toString()));
			list2.add(Double.valueOf(orderList.get(i)[2].toString()));
			list3.add(orderList.get(i)[0].toString());
		}
		
		String school=null;
		for(int i=0;i<schoolList.size();i++){
			school=schoolList.get(i);
			if(!list3.contains(school)){
				list1.add(0);
				list2.add(0.0);
				list3.add(school);
			}
		}
		map.put("num", list1);
		map.put("money", list2);
		map.put("school", list3);
		return map;
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

}
