package com.cat.orderManage.controller;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.jfree.chart.servlet.ServletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cat.base.controller.BaseController;
import com.cat.friut.service.FruitService;
import com.cat.friut.service.ShopcartService;
import com.cat.jfreechart.LineChartUtil;
import com.cat.jfreechart.TypeChartUtil;
import com.cat.orderManage.service.OrderinfoService;
import com.cat.orderManage.service.StationService;
import com.cat.pojo.Admin;
import com.cat.pojo.Fruitgoods;
import com.cat.pojo.Orderinfo;
import com.cat.pojo.Shopcart;
import com.cat.pojo.Station;
import com.cat.util.PageModel;
import com.cat.orderManage.model.Clientaddr;
import com.cat.orderManage.model.GoodsStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/order")
public class OrderinfoController extends BaseController{
	@Autowired
	protected OrderinfoService orderinfoService;
	@Autowired
	private ShopcartService shopcartService;
	@Autowired
	private FruitService fruitService;
	@Autowired
	private StationService stationService;

	
	private static List<GoodsStatus> goods = new ArrayList<GoodsStatus>();
	
	@RequestMapping("/manageOrder")
	public String manageOrder(Model model) throws Exception{
		List<Orderinfo> list = orderinfoService.findAll(null);
		model.addAttribute("orderList",list);
		return "order/manageOrder";
	}
	
	@RequestMapping("/toAddOrder")
	public String toAddOrder(Model model,HttpSession session){
		List<String> list = stationService.findAllSchool();
		model.addAttribute("schoolList",list);
		List<Clientaddr> aList = orderinfoService.findClientaddr(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("addrList", aList);
		return "order/addOrder";
	}

	//下单
	@RequestMapping(value="/addOrder",method=RequestMethod.POST)
	public String addOrder(@ModelAttribute Orderinfo orderinfo,HttpSession session) throws Exception{
		String s1 = System.currentTimeMillis()+"";
		orderinfo.setOrderNum(s1);
		orderinfo.setWeOrderNum(s1);
		orderinfo.setOpenId(((Admin)session.getAttribute("admin")).getAUserName());
		orderinfo.setOrderTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		orderinfo.setStatus("待付款");
		orderinfo.setCode(String.valueOf(new Random().nextInt(10000)));
		orderinfo.setTotalFee(shopcartService.countTotalPrice(((Admin)session.getAttribute("admin")).getAUserName()));
		List<Object[]> list = shopcartService.findByUserGroupByType(((Admin)session.getAttribute("admin")).getAUserName());
		String json = "{";
		Object[] s;
		String preS="";
		//把水果信息拼接成json
		for(int i=0;i<list.size();i++){
			
			s= list.get(i);
			if(!preS.equals((String)s[2]))
				json+="\""+s[2]+"\":[";
			
			if(list.size()==1 || i==list.size()-1){
				json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
						"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"}";
				json+="]}";
			}else{
				if(!((String)list.get(i+1)[2]).equals((String)s[2])){
					json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
							"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"}";
					json+="],";
				}else{
					json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
							"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"},";
				}
			}
			preS=(String)s[2];
		}
		orderinfo.setFruit(json);
		orderinfoService.save(orderinfo);
		shopcartService.deleteShopcarts(((Admin)session.getAttribute("admin")).getAUserName());
		
		
		return "redirect:/order/manageOrder";
	}
	
	//用已有地址下单
	@RequestMapping("/addOrder1")
	public String addOrder1(@RequestParam(value="CId")String CId,HttpSession session) throws Exception{
		String[] addr = CId.split("&&");
		String s1 = System.currentTimeMillis()+"";
		Orderinfo orderinfo = new Orderinfo();
		orderinfo.setClientName(addr[0]);
		orderinfo.setPhone(addr[1]);
		orderinfo.setSchool(addr[2]);
		orderinfo.setArea(addr[3]);
		orderinfo.setOrderNum(s1);
		orderinfo.setWeOrderNum(s1);
		orderinfo.setOpenId(((Admin)session.getAttribute("admin")).getAUserName());
		orderinfo.setOrderTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		orderinfo.setStatus("待付款");
		orderinfo.setCode(String.valueOf(new Random().nextInt(10000)));
		orderinfo.setTotalFee(shopcartService.countTotalPrice(((Admin)session.getAttribute("admin")).getAUserName()));
		List<Object[]> list = shopcartService.findByUserGroupByType(((Admin)session.getAttribute("admin")).getAUserName());
		String json = "{";
		Object[] s;
		String preS="";
		//把水果信息拼接成json
		for(int i=0;i<list.size();i++){
			
			s= list.get(i);
			if(!preS.equals((String)s[2]))
				json+="\""+s[2]+"\":[";
			
			if(list.size()==1 || i==list.size()-1){
				json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
						"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"}";
				json+="]}";
			}else{
				if(!((String)list.get(i+1)[2]).equals((String)s[2])){
					json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
							"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"}";
					json+="],";
				}else{
					json+="{\"fruitId\":\""+(Integer)s[0] +"\",\"fruitName\":\""+(String)s[1]+"\",\"num\":\""+(Integer)s[3]+
							"\",\"realFee\":\""+(Double)s[4]+"\",\"weight\":\""+(Integer)s[5]+"\",\"location\":\""+(String)s[6]+"\"},";
				}
			}
			preS=(String)s[2];
		}
		orderinfo.setFruit(json);
		orderinfoService.save(orderinfo);
		shopcartService.deleteShopcarts(((Admin)session.getAttribute("admin")).getAUserName());
		return "redirect:/order/manageOrder";
	}
	
	@RequestMapping("/orderStatus")
	public String orderStatus(Model model,@RequestParam(value="school",required=false)String school
			,@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to) throws Exception{
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String,String>();
		List<Orderinfo> list;
		
		if(from == null){
			from = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			to = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		model.addAttribute("from", from);
		model.addAttribute("to",to);
		to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(to).getTime()+24*60*60*1000));
		from = from+" 00:00:00";  to = to+" 00:00:00";
		
		
		if(school==null || school.equals("全部"))
			list=orderinfoService.findAllByTimeAndSchool(null,null,from,to,false);
		else
			list = orderinfoService.findAllByTimeAndSchool(school,null,from,to,false);
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
			    		 map.put(key+val.get(j).getLocation(), val.get(j));
			    	 }else{
			    		 tempG = val.get(j);
			    		 tempG.setFruitType(key);
			    		 tempG.setNum(tempG.getNum()+val.get(j).getNum());
			    		 map.put(key+tempG.getLocation(), tempG);
			    	 }
			     }	 
			 }  
		 }
		 //gList保存显示到页面中的订货需求
		List<GoodsStatus> gList = new ArrayList<GoodsStatus>();
		Set<Entry<String, GoodsStatus>> set1 = map.entrySet();  
		Iterator<Entry<String,  GoodsStatus>> s1=set1.iterator();
		Fruitgoods f;
		while (s1.hasNext()) {  
		    Entry<String, GoodsStatus> en = s1.next();  
		    String key = en.getKey();  
		    GoodsStatus goodStatus = en.getValue();
		    goodStatus.setTotalWeight(Integer.valueOf(goodStatus.getWeight())*goodStatus.getNum());
		    gList.add(goodStatus);
		}  
		
		goods.addAll(gList); //把gList中的数据放到静态成员goods中，在打印excel表时用到
		//查询现有的学校
		List<String> stationList = stationService.findAllSchool();
		
		model.addAttribute("goodsList", gList);
		model.addAttribute("school",school);
		model.addAttribute("stationList", stationList);
		
		return "order/orderStatus";
	}
	
	@RequestMapping("/toExcel")
	public ResponseEntity<String> toExcel(HttpServletRequest request) throws Exception{
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("Sheet1");
		 HSSFRow row1 = sheet.createRow(0);
		 row1.setHeight((short)300);
		 HSSFCell cell = row1.createCell(0);
		 cell.setCellValue("订单统计表");
		 sheet.addMergedRegion(new Region(0, (short) 0, 3, (short) 3));
		 HSSFCellStyle cellStyle = workbook.createCellStyle();  
		 cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		 cell.setCellStyle(cellStyle);
		 
		 HSSFRow row2 = sheet.createRow(4);
		 HSSFCell cell1 = row2.createCell((short)0);
		 HSSFCell cell2 = row2.createCell((short)1);
		 HSSFCell cell3 = row2.createCell((short)2);
		 HSSFCell cell4 = row2.createCell((short)3);
		 
		 cell1.setCellValue("商品号");
		 cell2.setCellValue("商品名");
		 cell3.setCellValue("数量");
		 cell4.setCellValue("重量");
		 
		 for(int i=0;i<goods.size();i++){
			 row2= sheet.createRow(i+5);
			 cell1 = row2.createCell((short)0);
			 cell2 = row2.createCell((short)1);
			 cell3 = row2.createCell((short)2);
			 cell4 = row2.createCell((short)3);
			 
			 cell1.setCellValue(goods.get(i).getFruitId());
			 cell2.setCellValue(goods.get(i).getFruitName());
			 cell3.setCellValue(goods.get(i).getNum());
			 cell4.setCellValue(goods.get(i).getTotalWeight());
		 }
		 
		 FileOutputStream fileOut = null;  
		 	String file = System.currentTimeMillis()+".xls";
	        try{              
	            fileOut = new FileOutputStream(request.getServletContext().getRealPath("/resources/upload/excel")+"/"+file);  
	            workbook.write(fileOut);  
	            //fileOut.close();  
	            System.out.print("OK");  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }finally{
	        	fileOut.close();
	        }
	        return new ResponseEntity<String>(file,HttpStatus.OK);
	}
	
	/*@RequestMapping("/analyse")
	public String analyse(Model model,@RequestParam(value="school",required=false)String school
			,@RequestParam(value="area",required=false)String area
			,@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,HttpServletRequest request) throws Exception{
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String,String>();
		List<Orderinfo> list;
		
		if(from == null){
			from = "2015-08-01";
			to = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		model.addAttribute("from", from);
		model.addAttribute("to",to);
		to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(to).getTime()+24*60*60*1000));
		from = from+" 00:00:00";  to = to+" 00:00:00";
		
		if(school==null || school.equals("全部")){
			list=orderinfoService.findAllByTimeAndSchool(null,null,from,to,true);
		}	
		else{
			if(area==null || area.equals("全部")){
				list = orderinfoService.findAllByTimeAndSchool(school,null,from,to,true);
			}else{
				list = orderinfoService.findAllByTimeAndSchool(school, area, from, to,true);
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
		
		String fileName=ServletUtilities.saveChartAsJPEG(TypeChartUtil.getJFreeChart(gList,"weight"),1200,800,request.getSession());
    	String chartUrl = request.getContextPath()+"/DisplayChart?filename="+fileName;
    	String fileName1=ServletUtilities.saveChartAsJPEG(TypeChartUtil.getJFreeChart(gList,"money"),1200,800,request.getSession());
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
		return "order/analyse";
	}
	//String region,String from ,String to
	@RequestMapping("/analyseBussiness")
	public String analyseBussiness(@RequestParam(value="region",required=false)String region,@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,Model model,HttpServletRequest request) throws Exception{
		if(region==null) region="1";
		if(from==null) from=new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime()-6*24*60*60*1000));
		if(to==null) to=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		LinkedHashMap<String,Double> map = orderinfoService.analyseMoney(region, from, to);
		
		String fileName=ServletUtilities.saveChartAsJPEG(LineChartUtil.getJFreeChart(map,region,from,to),1200,800,request.getSession());
    	String chartUrl = request.getContextPath()+"/DisplayChart?filename="+fileName;
    	
		model.addAttribute("charturl", chartUrl);
		model.addAttribute("region",region);
		model.addAttribute("from",from);
		model.addAttribute("to",to);
		model.addAttribute("map",map);
		return "order/anaylseBussiness";
	}*/
}  
