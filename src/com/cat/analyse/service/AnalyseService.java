package com.cat.analyse.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cat.pojo.Orderinfo;
@Service
public interface AnalyseService {
	
   public abstract List<String> getSchools() throws Exception;
   public abstract Map<String,Object> getOrderSexNum(String school,String area,String beginDate,String endDate) throws Exception;
   public abstract Map<String,Object> getOrderStationNum(String school,String beginDate,String endDate) throws Exception;
   public abstract Map<String,Object> getOrderSchoolNum(String beginDate,String endDate) throws Exception;
   public List<Orderinfo> findAllByTimeAndSchool(String school,String area,String from,String to,boolean include) throws Exception;
   public LinkedHashMap<String,Double> analyseMoney(String region,String from,String to) throws Exception;
}
