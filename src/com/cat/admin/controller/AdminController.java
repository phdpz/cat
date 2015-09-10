package com.cat.admin.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.json.JSONArray;
import org.json.JSONObject;
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

import com.cat.admin.service.AdminService;
import com.cat.admin.service.AdminServiceImpl;
import com.cat.base.controller.BaseController;
import com.cat.orderManage.model.GoodsStatus;
import com.cat.orderManage.service.OrderinfoService;
import com.cat.pojo.Admin;
import com.cat.pojo.Fruitgoods;
import com.cat.pojo.Orderinfo;
import com.cat.util.PageModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
	@Autowired
	protected AdminService adminService;
	@Autowired
	private OrderinfoService orderinfoService;
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(@RequestParam("userName") String userName,
			               @RequestParam("password") String password,Model model,HttpSession session) throws Exception{
		List<String> list=adminService.login(userName, password);
		if(list.size()==0){
			return "login";
		}else{ 
			session.setAttribute("admin", adminService.findById(userName));
			if(list.get(0)==null){
				return "redirect:/fruit/manageFruit";
			}else{
			    return getOrder(list.get(0),model);
			}
		}
	}
	
	private String getOrder(String school,Model model) throws Exception{
		java.util.Date date=new java.util.Date();
		Date bDate= new Date(date.getTime()-24*3600*1000);
		String nowTime1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(bDate);
		String nowTime2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
		List<Orderinfo> list=orderinfoService.getOrder(school,"全部",0,nowTime1,nowTime2);
		List<GoodsStatus> list1=getFruitList(school,nowTime1,nowTime2);
		JSONArray arr = new JSONArray(); 	
		for(int i=0;i<list.size();i++)
		{
			JSONObject json = new JSONObject();
			json.put("code",list.get(i).getCode());
			json.put("orderNum",list.get(i).getOrderNum());
			arr.put(json);
		}
		JSONObject json = new JSONObject();
		model.addAttribute("school",school);
		model.addAttribute("list", list);
		model.addAttribute("list1",list1);
		model.addAttribute("json", arr.toString());
		return "admin/schoolOrder";
	}
	
	@RequestMapping(value="/toLogin", method = RequestMethod.GET)
	public String toLoginPage(){
		return "login";
	}
	
	@RequestMapping("/sureOrder/{orderNum}")
	public ResponseEntity<Orderinfo> sureOrder(@PathVariable("orderNum") String orderNum) throws Exception{
		orderinfoService.sureOrder(orderNum);
		Orderinfo orderinfo=new Orderinfo();
		return new ResponseEntity<Orderinfo>(orderinfo, HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchOrder/{school}/{area}/{orderType}/{beginDate}/{endDate}", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> searchOrder(@PathVariable("school") String school,@PathVariable("area") String area, 
			@PathVariable("orderType") String orderType,@PathVariable("beginDate") String beginDate, 
			@PathVariable("endDate") String endDate,Model model) throws Exception{
		int type=Integer.valueOf(orderType);
		beginDate=beginDate+" 00:00:00";
		Date d=new java.sql.Date(java.sql.Date.valueOf(endDate).getTime()+24*60*60*1000);
		String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		
		List<Orderinfo> list=orderinfoService.getOrder(school, area, type, beginDate, d1);
		JSONArray arr = new JSONArray(); 	
		for(int i=0;i<list.size();i++)
		{
			JSONObject json = new JSONObject();
			json.put("code",list.get(i).getCode());
			json.put("orderNum",list.get(i).getOrderNum());
			arr.put(json);
		}
		JSONObject json = new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		map.put("json", arr.toString());
		
		return map;
	}
	
	@RequestMapping(value="/searchOrder1/{school}/{beginDate}/{endDate}", method = RequestMethod.GET)
	public @ResponseBody List<GoodsStatus> searchOrder1(@PathVariable("school") String school, 
			@PathVariable("beginDate") String beginDate, @PathVariable("endDate") String endDate
			,Model model) throws Exception{
		beginDate=beginDate+" 00:00:00";
		Date d=new java.sql.Date(java.sql.Date.valueOf(endDate).getTime()+24*60*60*1000);
		String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		return getFruitList(school,beginDate,d1);
	}
	
	@RequestMapping("/code/{code}")
	public @ResponseBody Orderinfo findCode(@PathVariable("code") String code) throws Exception{
		return adminService.findOrderByCode(code);
	}
	
	@RequestMapping(value="/toCode", method = RequestMethod.GET)
	public String toCode(){
		return "admin/showOneOrder";
	}
	
	@RequestMapping("/getAreas/{school}")
	public @ResponseBody Map<Object, Object> findSchoolArea(@PathVariable("school") String school) throws Exception{
		Map<Object, Object> map =new HashMap<Object, Object>();
		map.put("areaList", adminService.findSchoolArea(school));
		map.put("size", adminService.countNoGetOrder(school));
		return map;
	}
	
	@RequestMapping("/getAreas1/{school}")
	public @ResponseBody List<String> findSchoolArea1(@PathVariable("school") String school) throws Exception{
		return adminService.findSchoolArea(school);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/print/{school}/{area}/{orderType}/{beginDate}/{endDate}")
	public @ResponseBody String print(@PathVariable("school") String school,@PathVariable("area") String area, 
		   @PathVariable("orderType") String orderType,@PathVariable("beginDate") String beginDate, 
		   @PathVariable("endDate") String endDate,HttpServletRequest request) throws Exception{
		
		int type=Integer.valueOf(orderType);
		beginDate=beginDate+" 00:00:00";
		Date d=new java.sql.Date(java.sql.Date.valueOf(endDate).getTime()+24*60*60*1000);
		String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		List<Orderinfo> list=orderinfoService.getOrder(school, area, type, beginDate, d1);
		String strTime=null;
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单表");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0); 
        //设置第0列的宽度为6000
        sheet.setColumnWidth(0, 6000);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        
        // 创建一个水平垂直居中格式  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        
        HSSFFont font = wb.createFont();    
        font.setFontName("仿宋_GB2312"); //设置字体样式
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示   
        font.setFontHeightInPoints((short) 16);//设置字体大小   
        style.setFont(font);
        
        //style.setFillForegroundColor((short) 13);// 设置背景色    
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);    
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
  
        HSSFRow row1=sheet.createRow((int)1);
        HSSFRow row2=sheet.createRow((int)2);
        HSSFRow row3=sheet.createRow((int)3);
        
        //new Region(int rowFrom, short colFrom, int rowTo, short colTo)
        sheet.addMergedRegion(new Region(0,(short)0,3,(short)4));
        HSSFCell cellT = row.createCell((short) 0);  
        cellT.setCellValue("订单标题"); 
        cellT.setCellStyle(style); 
        
        row=sheet.createRow((int)4);
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("下单时间");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 1);  
        cell.setCellValue("状态");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 2);  
        cell.setCellValue("验证码");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 3);  
        cell.setCellValue("地区");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 4);  
        cell.setCellValue("签名");  
        cell.setCellStyle(style);   
        
        HSSFCellStyle style1 = wb.createCellStyle();  
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
        HSSFFont font1 = wb.createFont();    
        font1.setFontName("仿宋_GB2312"); //设置字体样式
        font1.setFontHeightInPoints((short) 13);//设置字体大小   
        style1.setFont(font1);
  
        for (int i = 0; i < list.size(); i++)  
        {  
            row = sheet.createRow((int) i + 5);  
            Orderinfo order = (Orderinfo) list.get(i);  
            // 第四步，创建单元格，并设置值  
            cell = row.createCell((short) 0);  
            cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(order.getOrderTime()));  
            cell.setCellStyle(style1); 
            
            cell = row.createCell((short) 1);  
            cell.setCellValue(order.getStatus()); 
            cell.setCellStyle(style1); 
            
            cell=row.createCell((short) 2);
            cell.setCellValue(order.getCode());
            cell.setCellStyle(style1);
            
            cell=row.createCell((short) 3);
            cell.setCellValue(order.getArea());
            cell.setCellStyle(style1);
            
            cell=row.createCell((short) 4);
            cell.setCellValue("");
            cell.setCellStyle(style1);
        }  
        // 第六步，将文件存到指定位置  
        try  
        {
        	Long time=new java.util.Date().getTime();
        	strTime=time.toString();
            FileOutputStream fout = new FileOutputStream(request.getServletContext().getRealPath("/resources/upload/excel")+"/order"+time+".xls");  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
		return "order"+strTime+".xls";	
	}
	
	@RequestMapping("/manageAdmin")
	public String manageAdmin(Model model,@ModelAttribute PageModel pageModel) throws Exception{
		pageModel.setRecordCount(adminService.getTotalRecords());
		List<Admin> list = adminService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize()).getResultList();
		model.addAttribute("list", list);
		model.addAttribute("page",pageModel);
		return "admin/manageAdmin";
	}
	
	@RequestMapping(value="/addAdmin",method=RequestMethod.POST)
	public String addAdmin(@ModelAttribute Admin admin,RedirectAttributes attr) throws Exception{
		
		adminService.save(admin);
		return "redirect:/admin/manageAdmin";
	}
	
	@RequestMapping(value="/delete/{userName}",method=RequestMethod.GET)
	public String deleteAdmin(@PathVariable("userName")String userName) throws Exception{
		adminService.delete(adminService.findById(userName));
		return "redirect:/admin/manageAdmin";
	}
	
	@RequestMapping(value="/updateAdmin/{userName}/{password}/{name}/{phone}/{email}/{school}")
	public ResponseEntity<String> updateAdmin(@PathVariable("userName")String userName,
			@PathVariable("password")String password,@PathVariable("name")String name,
			@PathVariable("phone")String phone,@PathVariable("email")String email,
			@PathVariable("school")String school) throws Exception{
		List<Admin> list = adminService.findAdminBySchool(school);
		if(list.size()==0){
			Admin admin = new Admin();
			admin.setAUserName(userName);
			admin.setAPassword(password);
			admin.setAStuName(name);
			admin.setAStuPhone(phone);
			admin.setAStuMail(email);
			adminService.save(admin);
		}else{
			Admin admin = list.get(0);
			adminService.delete(list.get(0));
			admin.setAUserName(userName);
			admin.setAPassword(password);
			admin.setAStuName(name);
			admin.setAStuPhone(phone);
			admin.setAStuMail(email);
			adminService.save(admin);
		}
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	@RequestMapping(value="/check/{userName}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> getCheckUserName(@PathVariable("userName")String userName) throws Exception{
		String result ="";
		if(adminService.hasSameUser(userName))
			result="该用户名已存在";
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	private List<GoodsStatus> getFruitList(String school,String from,String to) throws Exception{
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String,String>();
		List<Orderinfo> list;
		
		
		
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
		return gList;
	}
}
