package com.cat.admin.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.base.dao.BaseDao;
import com.cat.base.service.BaseService;
import com.cat.pojo.Admin;
import com.cat.pojo.Orderinfo;
import com.cat.util.QueryResult;

@Service
public interface AdminService extends BaseService<Admin>{
	public boolean login(Admin admin) throws Exception;
	
	public boolean hasSameUser(String userName) throws Exception;
	
	/**
	 * 查看实体结果集
	 * @return
	 * @throws Exception
	 */
	public List<Admin> findAll() throws Exception;
	
	public QueryResult<Admin>	findAllByPage(int page,int size);
	
	/**
	 * 查看实体结果
	 * @return
	 * @throws Exception
	 */
	public Admin findById(Serializable entityId) throws Exception ;
	
	public int getTotalRecords() throws Exception;
	
	public abstract List<String> login(String userName,String password) throws Exception;
    public abstract Orderinfo findOrderByCode(String code) throws Exception;
    public abstract List<String> findSchoolArea(String school) throws Exception;
    public abstract int countNoGetOrder(String school) throws Exception;
    public List<Admin> findAdminBySchool(String school);
}
