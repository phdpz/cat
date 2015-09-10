package com.cat.analyse.controller;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.chart.servlet.ServletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cat.analyse.service.AnalyseService;
import com.cat.jfreechart.LineChartUtil;
import com.cat.jfreechart.TypeChartUtil;
import com.cat.jfreechart.schoolChartUtil;
import com.cat.jfreechart.sexChartUtil;
import com.cat.jfreechart.stationChartUtil;
import com.cat.orderManage.model.GoodsStatus;
import com.cat.orderManage.service.StationService;
import com.cat.pojo.Fruitgoods;
import com.cat.pojo.Orderinfo;
import com.cat.pojo.Station;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/analyse")
public class AnalyseController {
	
	@Autowired
	private AnalyseService analyseService;
	@Autowired
	private StationService stationService;
	
	@RequestMapping("/getSchools")
	public @ResponseBody List<String> getSchool() throws Exception{
		return analyseService.getSchools();
	}
	
	@RequestMapping("/sexAnalyse/{school}/{area}/{beginDate}/{endDate}")
	public String sexAnalyse(@PathVariable("school") String school,
			@PathVariable("area") String area,@PathVariable("beginDate") String beginDate,
			@PathVariable("endDate") String endDate,Model model,HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,	Object> map=analyseService.getOrderSexNum(school, area, beginDate, end);
		model.addAttribute("boy", (Integer)map.get("boy"));
		model.addAttribute("girl", (Integer)map.get("girl"));
		model.addAttribute("boyMoney", (Double)map.get("boyMoney"));
		model.addAttribute("girlMoney", (Double)map.get("girlMoney"));
		
		Map<String,	Object> map1=new HashMap<String,Object>();
		HttpSession session=req.getSession();
		
		map1.put("boy", map.get("boy"));
		map1.put("girl", map.get("girl"));
		String fileName=ServletUtilities.saveChartAsJPEG(sexChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName", fileName);
		
		map1.clear();
		map1.put("boyMoney", map.get("boyMoney"));
		map1.put("girlMoney", map.get("girlMoney"));
		String fileName1=ServletUtilities.saveChartAsJPEG(sexChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName1", fileName1);
		
		return "analyse/sexAnalyse";
	}
	
	@RequestMapping("/sexAnalyseAjax/{school}/{area}/{beginDate}/{endDate}")
	public @ResponseBody Map<String,Object> sexAnalyseAjax(@PathVariable("school") String school,
			@PathVariable("area") String area,@PathVariable("beginDate") String beginDate,
			@PathVariable("endDate") String endDate,Model model,HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,Object> map=analyseService.getOrderSexNum(school, area, beginDate, end);
		double d1=0;
		NumberFormat num = NumberFormat.getPercentInstance();  
		num.setMaximumFractionDigits(2); 
		if(((Integer)map.get("boy")+(Integer)map.get("girl"))!=0){
			d1=(double) ((Integer)map.get("boy")*1.0/((Integer)map.get("boy")+(Integer)map.get("girl")));
			map.put("boyRadio", num.format(d1));
			d1=(double) ((Integer)map.get("girl")*1.0/((Integer)map.get("boy")+(Integer)map.get("girl")));
			map.put("girlRadio", num.format(d1));
		}
		else{
			map.put("boyRadio", "0%");
			map.put("girlRadio","0%");
		}
		
		if(((Double)map.get("boyMoney")+(Double)map.get("girlMoney"))!=0){
			d1=(Double)map.get("boyMoney")/((Double)map.get("boyMoney")+(Double)map.get("girlMoney"));
			map.put("boyMoneyRadio", num.format(d1));
			d1=(Double)map.get("girlMoney")/((Double)map.get("boyMoney")+(Double)map.get("girlMoney"));
			map.put("girlMoneyRadio", num.format(d1));
		}
		else{
			map.put("boyMoneyRadio", "0%");
			map.put("girlMoneyRadio", "0%");
		}
		
		Map<String,	Object> map1=new HashMap<String,Object>();
		HttpSession session=req.getSession();
		
		map1.put("boy", map.get("boy"));
		map1.put("girl", map.get("girl"));
		String fileName=ServletUtilities.saveChartAsJPEG(sexChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName", fileName);
		
		map1.clear();
		map1.put("boyMoney", map.get("boyMoney"));
		map1.put("girlMoney", map.get("girlMoney"));
		String fileName1=ServletUtilities.saveChartAsJPEG(sexChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName1", fileName1);
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stationAnalyse/{school}/{beginDate}/{endDate}")
	public String stationAnalyse(@PathVariable("school") String school,
			@PathVariable("beginDate") String beginDate,@PathVariable("endDate") String endDate,
			Model model,HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,Object> map=analyseService.getOrderStationNum(school, beginDate, end);
		model.addAttribute("map", map);
		int all=0;
		double allMoney=0;
		for(int i=0;i<((List<Integer>)map.get("num")).size();i++){
			allMoney+=((List<Double>)map.get("money")).get(i);
		    all+=((List<Integer>)map.get("num")).get(i);	
		}
		model.addAttribute("all",all);
		model.addAttribute("allMoney",allMoney);
		
		Map<String,Object> map1=new HashMap<String,Object>();
		HttpSession session=req.getSession();
		
		map1.put("num", (List<Integer>)map.get("num"));
		map1.put("area", (List<String>)map.get("area"));
		String fileName=ServletUtilities.saveChartAsJPEG(stationChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName", fileName);
		
		map1.clear();
		map1.put("money", (List<Double>)map.get("money"));
		map1.put("area", (List<String>)map.get("area"));
		String fileName1=ServletUtilities.saveChartAsJPEG(stationChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName1", fileName1);
		
		return "analyse/stationAnalyse";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stationAnalyseAjax/{school}/{beginDate}/{endDate}")
	public @ResponseBody Map<String,Object> stationAnalyseAjax(@PathVariable("school") String school,
			@PathVariable("beginDate") String beginDate,@PathVariable("endDate") String endDate,
			Model model,HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,Object> map=analyseService.getOrderStationNum(school, beginDate, end);
		
		List<String> radio=new ArrayList<String>();
		List<String> radioMoney=new ArrayList<String>();
		
		int all=0;
		double allMoney=0;
		for(int i=0;i<((List<Integer>)map.get("num")).size();i++){
			allMoney+=((List<Double>)map.get("money")).get(i);
			all+=((List<Integer>)map.get("num")).get(i);
		}
		
		double d1=0;
		NumberFormat num = NumberFormat.getPercentInstance();  
		num.setMaximumFractionDigits(2); 
		for (int i=0;i<((List<Integer>)map.get("num")).size();i++) {
			if(all!=0)
			  d1=((List<Integer>)map.get("num")).get(i)*1.0/all;
			else
			  d1=0;
			radio.add(num.format(d1));
			
			if(allMoney!=0)
			  d1=((List<Double>)map.get("money")).get(i)/allMoney;
			else
			  d1=0;
			radioMoney.add(num.format(d1));
	    }
		
		map.put("radio", radio);
		map.put("radioMoney", radioMoney);
		
		HttpSession session=req.getSession();
		Map<String,Object> map1=new HashMap<String,Object>();
		
		map1.put("num", (List<Integer>)map.get("num"));
		map1.put("area", (List<String>)map.get("area"));
		String fileName=ServletUtilities.saveChartAsJPEG(stationChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName", fileName);
		
		map1.clear();
		map1.put("money", (List<Integer>)map.get("money"));
		map1.put("area", (List<String>)map.get("area"));
		String fileName1=ServletUtilities.saveChartAsJPEG(stationChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName1", fileName1);
		
		return map;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/schoolAnalyse/{beginDate}/{endDate}")
	public String schoolAnalyse(@PathVariable("beginDate") String beginDate,
			@PathVariable("endDate") String endDate,Model model,
			HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,Object> map=analyseService.getOrderSchoolNum(beginDate, end);
		model.addAttribute("map", map);
		int all=0;
		double allMoney=0;
		for(int i=0;i<((List<Integer>)map.get("num")).size();i++){
			allMoney+=((List<Double>)map.get("money")).get(i);
		    all+=((List<Integer>)map.get("num")).get(i);	
		}
		model.addAttribute("all",all);
		model.addAttribute("allMoney",allMoney);
		
		Map<String,Object> map1=new HashMap<String,Object>();
		HttpSession session=req.getSession();
		
		map1.put("num", (List<Integer>)map.get("num"));
		map1.put("school", (List<String>)map.get("school"));
		String fileName=ServletUtilities.saveChartAsJPEG(schoolChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName", fileName);
		
		map1.clear();
		map1.put("money", (List<Double>)map.get("money"));
		map1.put("school", (List<String>)map.get("school"));
		String fileName1=ServletUtilities.saveChartAsJPEG(schoolChartUtil.getJFreeChart(map1),400,400,session);
		model.addAttribute("fileName1", fileName1);
		
		return "analyse/schoolAnalyse";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/schoolAnalyseAjax/{beginDate}/{endDate}")
	public @ResponseBody Map<String,Object> schoolAnalyseAjax(@PathVariable("beginDate") String beginDate,
			@PathVariable("endDate") String endDate,Model model,
			HttpServletRequest req) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new Date(Date.valueOf(endDate).getTime()+24*60*60*1000);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		Map<String,Object> map=analyseService.getOrderSchoolNum(beginDate, end);
		
		List<String> radio=new ArrayList<String>();
		List<String> radioMoney=new ArrayList<String>();
		
		int all=0;
		double allMoney=0;
		for(int i=0;i<((List<Integer>)map.get("num")).size();i++){
			allMoney+=((List<Double>)map.get("money")).get(i);
			all+=((List<Integer>)map.get("num")).get(i);
		}
		
		double d1=0;
		NumberFormat num = NumberFormat.getPercentInstance();  
		num.setMaximumFractionDigits(2); 
		for (int i=0;i<((List<Integer>)map.get("num")).size();i++) {
			if(all!=0)
			  d1=((List<Integer>)map.get("num")).get(i)*1.0/all;
			else
			  d1=0;
			radio.add(num.format(d1));
			
			if(allMoney!=0)
			  d1=((List<Double>)map.get("money")).get(i)/allMoney;
			else
			  d1=0;
			radioMoney.add(num.format(d1));
	    }
		
		map.put("radio", radio);
		map.put("radioMoney", radioMoney);
		
		HttpSession session=req.getSession();
		Map<String,Object> map1=new HashMap<String,Object>();
		
		map1.put("num", (List<Integer>)map.get("num"));
		map1.put("school", (List<String>)map.get("school"));
		String fileName=ServletUtilities.saveChartAsJPEG(schoolChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName", fileName);
		
		map1.clear();
		map1.put("money", (List<Integer>)map.get("money"));
		map1.put("school", (List<String>)map.get("school"));
		String fileName1=ServletUtilities.saveChartAsJPEG(schoolChartUtil.getJFreeChart(map1),400,400,session);
		map.put("fileName1", fileName1);
		
		return map;
	}	
	
	@RequestMapping("/analyse")
	public String analyse(Model model,@RequestParam(value="school",required=false)String school
			,@RequestParam(value="area",required=false)String area
			,@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,HttpServletRequest request) throws Exception{
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String,String>();
		List<Orderinfo> list;
		
		if(from == null){
			from = "2015-08-01";
			to = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		}
		model.addAttribute("from", from);
		model.addAttribute("to",to);
		to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(to).getTime()+24*60*60*1000));
		from = from+" 00:00:00";  to = to+" 00:00:00";
		
		if(school==null || school.equals("全部")){
			list=analyseService.findAllByTimeAndSchool(null,null,from,to,true);
		}	
		else{
			if(area==null || area.equals("全部")){
				list = analyseService.findAllByTimeAndSchool(school,null,from,to,true);
			}else{
				list = analyseService.findAllByTimeAndSchool(school, area, from, to,true);
			}
		}
		 Map<String,GoodsStatus> map = new LinkedHashMap<String,GoodsStatus>();  //用来保存商品号和商品状态
		 ObjectMapper mapper = new ObjectMapper();          
		 HashMap<String,List<GoodsStatus>> modelMap;
		 Set<Entry<String, List<GoodsStatus>>> set;
		 
		 
		 Iterator<Entry<String, List<GoodsStatus>>> s;
		 //遍历订单列表
		 for(int i=0;i< list.size();i++)
		 {
			 //获取每张订单所保存的json
			 String json = list.get(i).getFruit();
			 //把json转换成HashMap
			 modelMap = mapper.readValue(json,
					 new TypeReference<HashMap<String,List<GoodsStatus>>>(){});
			 
			 //遍历由json转化来的HashMap
			 set = modelMap.entrySet();  
			 s = set.iterator(); 
			 GoodsStatus tempG;
			 while (s.hasNext()) {  
			     Entry<String, List<GoodsStatus>> en = s.next();  
			     //key保存fruitType
			     String key = en.getKey();  
			     List<GoodsStatus> val = en.getValue();
			     //遍历hashmap里存放的List，并把其中的信息保存到map中
			     for(int j=0;j<val.size();j++){
			    	 if(map.get(key+val.get(j).getLocation())==null){
			    		 val.get(j).setFruitType(key);
			    		 val.get(j).setTotalFee(val.get(j).getRealFee()*val.get(j).getNum());
			    		 map.put(key+val.get(j).getLocation(), val.get(j));
			    	 }else{
			    		 tempG = map.get(key+val.get(j).getLocation());
			    		 tempG.setFruitType(key);
			    		 tempG.setNum(tempG.getNum()+val.get(j).getNum());
			    		 tempG.setTotalFee(tempG.getTotalFee()+val.get(j).getRealFee()*val.get(j).getNum());
			    		 map.put(key+tempG.getLocation(), tempG);
			    	 }
			     }	 
			 }  
		 }
		 //gList保存显示到页面中的订货需求
		double totalFee=0.0;
		int totalWeight=0;
		List<GoodsStatus> gList = new ArrayList<GoodsStatus>();
		Set<Entry<String, GoodsStatus>> set1 = map.entrySet();  
		Iterator<Entry<String,  GoodsStatus>> s1=set1.iterator();
		Fruitgoods f;
		while (s1.hasNext()) {  
		    Entry<String, GoodsStatus> en = s1.next();  
		    String key = en.getKey();  
		    GoodsStatus goodStatus = en.getValue();
		    goodStatus.setTotalWeight(Integer.valueOf(goodStatus.getWeight())*goodStatus.getNum());
		    totalFee+=goodStatus.getTotalFee();
		    totalWeight+=goodStatus.getTotalWeight();
		    gList.add(goodStatus);
		}  
		//计算百分比
		for(int i=0;i<gList.size();i++){
			gList.get(i).setRadio(gList.get(i).getTotalWeight()/(double)totalWeight);
			gList.get(i).setRadio1(gList.get(i).getTotalFee()/(double)totalFee);
		}
		
		String fileName=ServletUtilities.saveChartAsJPEG(TypeChartUtil.getJFreeChart(gList,"weight"),1000,800,request.getSession());
    	String chartUrl = request.getContextPath()+"/DisplayChart?filename="+fileName;
    	String fileName1=ServletUtilities.saveChartAsJPEG(TypeChartUtil.getJFreeChart(gList,"money"),1000,800,request.getSession());
    	String chartUrl1= request.getContextPath()+"/DisplayChart?filename="+fileName1;
    	
		//查询现有的学校
		List<String> stationList = stationService.findAllSchool();
		//根据学校查询区域
		List<Station> list1 = stationService.findBySchool(school);
		
		model.addAttribute("goodsList", gList);
		model.addAttribute("school",school);
		model.addAttribute("stationList", stationList);
		model.addAttribute("typeChart",chartUrl);
		model.addAttribute("typeChart1",chartUrl1);
		model.addAttribute("areaList",list1);
		model.addAttribute("area",area);
		return "analyse/analyse";
	}
	//String region,String from ,String to
	@RequestMapping("/analyseBussiness")
	public String analyseBussiness(@RequestParam(value="region",required=false)String region,@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,Model model,HttpServletRequest request) throws Exception{
		if(region==null) region="1";
		if(from==null) from=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(new java.util.Date().getTime()-6*24*60*60*1000));
		if(to==null) to=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		
		LinkedHashMap<String,Double> map = analyseService.analyseMoney(region, from, to);
		
		String fileName=ServletUtilities.saveChartAsJPEG(LineChartUtil.getJFreeChart(map,region,from,to),1000,800,request.getSession());
    	String chartUrl = request.getContextPath()+"/DisplayChart?filename="+fileName;
    	
		model.addAttribute("charturl", chartUrl);
		model.addAttribute("region",region);
		model.addAttribute("from",from);
		model.addAttribute("to",to);
		model.addAttribute("map",map);
		return "analyse/anaylseBussiness";
	}

}
