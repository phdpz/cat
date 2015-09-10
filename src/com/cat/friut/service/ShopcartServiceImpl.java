package com.cat.friut.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.service.BaseServiceImpl;
import com.cat.friut.dao.ShopcartDao;
import com.cat.pojo.Shopcart;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("shopcartService")
public class ShopcartServiceImpl extends BaseServiceImpl<Shopcart> implements ShopcartService{
	
	@Autowired
	protected ShopcartDao shopcartDao;
	
	public ShopcartServiceImpl(){
		super();
	}
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Shopcart> findAll() throws Exception {
		return shopcartDao.findAll();
	}

	
	public QueryResult<Shopcart>	findAllByPage(int page,int size){
		return shopcartDao.listByPage(page, size);
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Shopcart findById(Serializable entityId) throws Exception {
		return shopcartDao.findById(entityId);
	}
	
	public int getTotalRecords() throws Exception{
		return shopcartDao.getTotalRecords();
	}
	
	//根据用户名和水果id查找购物车记录
	public Shopcart findByUserAndFruit(String openId,Integer fruitId){
		String where = "o.openId=? and o.fruitId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		params.add(fruitId);
		List<Shopcart> list =shopcartDao.listByPage(1, 1,where,params).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);	
	}
	
	//找出某个用户下购物车内所有的东西
	public List<Shopcart> findByUser(String openId){
		return shopcartDao.getListByOpenId(openId);
	}
	
	//找出某个用户下购物车内所有的东西,按水果类型分组,轻重object数组中
	//有六个元素，分别代表商品id，商品名，商品类型，数量，价格，重量
	public List<Object[]> findByUserGroupByType(String openId){
		return shopcartDao.getListByOpenIdGroupByType(openId);
	}
	
	//结算时把购物车清空
	public void deleteShopcarts(String openId) throws Exception{
		List<Shopcart> list = shopcartDao.getListByOpenId(openId);
		shopcartDao.deleteEntities(list);
		return;
	}
		
	//计算某用户购物车中货物数量
	public Integer countShopcartByUser(String openId){
		Integer num=0;
		List<Shopcart> list = shopcartDao.getListByOpenId(openId);
		for(int i=0;i<list.size();i++){
			num += list.get(i).getSNum();
		}
		return num;
	}
	
	//统计购物车里所有商品的价格
	public Double countTotalPrice(String openId) {
		List<Object[]> list = shopcartDao.getPrices(openId);
		double count = 0.0;
		for(int i=0;i<list.size();i++){
			Object[] s = list.get(i);
			count += (Integer)s[0]*(Double)s[1];
		}
		
		return count;
	}
}
