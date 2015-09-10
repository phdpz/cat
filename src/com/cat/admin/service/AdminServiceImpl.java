package com.cat.admin.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.admin.dao.*;
import com.cat.base.service.BaseServiceImpl;
import com.cat.orderManage.dao.OrderinfoDao;
import com.cat.pojo.Admin;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

/**
 * @Desecration
 * @author 王亚南
 * @date 2014年12月2日 下午5:35:10
 */
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
	@Autowired
	protected AdminDao adminDao;
	
	
	public AdminServiceImpl(){
		super();
	}
	
	//登录
	public boolean login(Admin admin) throws Exception{
		Admin admin1 = adminDao.findById(admin.getAUserName());
		if(admin1==null)
			return false;
		else{
			if(admin1.getAPassword().equals(admin.getAPassword()))
				return true;
			else
				return false;
		}
	}
	
	public boolean hasSameUser(String userName) throws Exception{
		if(adminDao.findById(userName)==null)
			return false;
		else
			return true;
	}
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Admin> findAll() throws Exception {
		return adminDao.findAll();
	}

	
	public QueryResult<Admin>	findAllByPage(int page,int size){
		return adminDao.listByPage(page, size);
	}
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Admin findById(Serializable entityId) throws Exception {
		return adminDao.findById(entityId);
	}
	
	public int getTotalRecords() throws Exception{
		return adminDao.getTotalRecords();
	}
	
	String hql;
	@Override
	public List<String> login(String userName, String password) throws Exception {
		hql="select a.ASchool from Admin a where a.AUserName='"+userName+"' and a.APassword='"+password+"'";
		return adminDao.findSomeByHql(hql);
	}


	@Override
	public Orderinfo findOrderByCode(String code) throws Exception {
		hql="from Orderinfo o where o.code="+code;
		List list=this.adminDao.findAllByHql(hql);
		if(list.size()!=0)	
			return (Orderinfo)list.get(0);
		return null;
	}

	@Override
	public List<String> findSchoolArea(String school) throws Exception {
		hql="select o.SArea from Station o where o.SSchool='"+school+"'";
		return adminDao.findSomeByHql(hql);
	}
	
	public int countNoGetOrder(String school) throws Exception{
		Date date=new Date();
		String nowTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
		hql="select count(*) from Orderinfo o where o.status='未提货' and o.school='"+school+"' and o.orderTime<'"+nowTime+"'";
		return adminDao.count(hql).intValue();
	}
	
	public List<Admin> findAdminBySchool(String school){
		List<Admin> list = adminDao.findByProperty("ASchool", school);
		return list;
	}
	
}
