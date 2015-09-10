package com.cat.friut.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.service.BaseServiceImpl;
import com.cat.friut.dao.FruitDao;
import com.cat.pojo.Fruitgoods;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("fruitService")
public class FruitServiceImpl extends BaseServiceImpl<Fruitgoods> implements FruitService{
	
	@Autowired
	protected FruitDao fruitDao;
	
	public FruitServiceImpl(){
		super();
	}
	
	public void test() throws Exception{
		fruitDao.testHql();
	}
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Fruitgoods> findAll() throws Exception {
		return fruitDao.findAll();
	}
	
	public List<Fruitgoods> findAll(LinkedHashMap<String, String> orderBy,String native1,String fruitType) throws Exception {
		if(native1==null&&fruitType==null){
			return fruitDao.findAll(orderBy);
		}else{
			if(native1==null && fruitType!=null){
				String where = "o.FFruitType = ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				return fruitDao.findAll(orderBy,where,list);
			}
			if(native1!=null && fruitType==null){
				String where = "o.FNative = ?";
				List<Object> list = new ArrayList<Object>();
				list.add(native1);
				return fruitDao.findAll(orderBy,where,list);
			}
			if(native1!=null && fruitType!=null){
				String where="o.FFruitType = ? and o.FNative = ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				list.add(native1);
				return fruitDao.findAll(orderBy,where,list);
			}
			return null;
		}
	}
	
	public List<Fruitgoods> searchByKey(String key,LinkedHashMap<String, String> orderBy) throws Exception{
		String where = "o.FGodsName like %?%";
		List<Object> list = new ArrayList<Object>();
		list.add(key);
		return fruitDao.findAll(orderBy, where, list);
	}
	
	public QueryResult<Fruitgoods>	findAllByPage(int page,int size,String fruitType,String native1,LinkedHashMap<String, String> orderBy){
		
		if(fruitType.equals("全部") && native1.equals("全部")){
			String where = "o.FFruitType != ?";
			List<Object> list = new ArrayList<Object>();
			list.add("精选果篮");
			return fruitDao.listByPage(page, size,where,list,orderBy);
		}else{
			if(!fruitType.equals("全部") && native1.equals("全部")){
				String where = "o.FFruitType = ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
			if(fruitType.equals("全部") && !native1.equals("全部")){
				String where = "o.FNative = ? && o.FFruitType != '精选果篮'";
				List<Object> list = new ArrayList<Object>();
				list.add(native1);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
			if(!fruitType.equals("全部") && !native1.equals("全部")){
				String where = "o.FFruitType = ? and o.FNative= ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				list.add(native1);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
		}
		return null;
	}
	
	public QueryResult<Fruitgoods>	findAllByPage1(int page,int size,String fruitType,String native1,LinkedHashMap<String, String> orderBy){
		
		if(fruitType.equals("全部") && native1.equals("全部")){
			return fruitDao.listByPage(page, size,orderBy);
		}else{
			if(!fruitType.equals("全部") && native1.equals("全部")){
				String where = "o.FFruitType = ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
			if(fruitType.equals("全部") && !native1.equals("全部")){
				String where = "o.FNative = ? ";
				List<Object> list = new ArrayList<Object>();
				list.add(native1);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
			if(!fruitType.equals("全部") && !native1.equals("全部")){
				String where = "o.FFruitType = ? and o.FNative= ?";
				List<Object> list = new ArrayList<Object>();
				list.add(fruitType);
				list.add(native1);
				return fruitDao.listByPage(page, size,where,list,orderBy);
			}
		}
		return null;
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Fruitgoods findById(Serializable entityId) throws Exception {
		return fruitDao.findById(entityId);
	}
	
	public int getTotalRecords(String fruitType,String native1) throws Exception{
		String hql="";
		if(fruitType==null && native1==null){
			hql="select count(*) from Fruitgoods f where f.FFruitType!='精选果篮'";
			
		}else{
			if(fruitType!=null)
				hql="select count(*) from Fruitgoods f where f.FFruitType = '"+fruitType+"'";
			if(native1!=null)
				hql="select count(*) from Fruitgoods f where f.FNative = '"+native1+"'";
		}
		return fruitDao.getTotalRecords(hql);
	}
	
	//找出所有水果类型
	public List<String> findAllType(){
		return fruitDao.findFruitList();
	}
	
	public List<String> findLocationByType(String fruitType){
		return fruitDao.findFruitLocation(fruitType);
	}
	
}
