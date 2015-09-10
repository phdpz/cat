package com.cat.friut.controller;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.cat.base.controller.BaseController;
import com.cat.client.service.ClientService;
import com.cat.friut.service.FruitService;
import com.cat.friut.service.ShopcartService;
import com.cat.pojo.Admin;
import com.cat.pojo.Fruitgoods;
import com.cat.util.PageModel;

@Controller
@RequestMapping("/fruit")
public class FruitController extends BaseController{
	@Autowired
	private FruitService fruitService;
	@Autowired
	private ShopcartService shopcartService;
	@Autowired
	private ClientService clientService;
	
	
	@RequestMapping(value="/manageFruit",method=RequestMethod.GET)
	public String manageFruit(Model model,@ModelAttribute PageModel pageModel
			,@RequestParam(value="fruitType",required=false)String fruitType
			,@RequestParam(value="native",required=false)String native1,HttpSession session) throws Exception{
		pageModel.setRecordCount(fruitService.getTotalRecords(fruitType,native1));
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("FId","desc");
		if(fruitType==null)
			fruitType = "全部";
		if(native1==null)
			native1 = "全部";
		List<Fruitgoods> list = fruitService.findAllByPage1(pageModel.getCurrentPageNo(), pageModel.getPageSize(),fruitType,native1,orderBy).getResultList();
		model.addAttribute("fruitList", list);
		model.addAttribute("page",pageModel);
		Integer cartNum = shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("cartNum",cartNum);
		model.addAttribute("fruitTypeList",fruitService.findAllType());
		model.addAttribute("fruitType1", fruitType);
		model.addAttribute("native1",native1);
		model.addAttribute("active","fruitActive");
		return "fruit/manageFruit";
	}
	
	@RequestMapping(value="/manageFruit",method=RequestMethod.GET,params="add")
	public String toAddFruit(Model model) throws Exception{
		model.addAttribute("fruitTypeList",fruitService.findAllType());
		return "fruit/addFruit";
	}
	
	@RequestMapping(value="/addFruit",method=RequestMethod.POST)
	public String addFruit(@ModelAttribute Fruitgoods fruitgoods,@RequestParam("file")MultipartFile file,HttpServletRequest request) throws Exception{
		if(!file.isEmpty()){
			String s=System.currentTimeMillis()+file.getOriginalFilename();
			FileUtils.copyInputStreamToFile(file.getInputStream(),new File(request.getServletContext().getRealPath("/resources/upload"),s));
			fruitgoods.setFImg(s);
		} 
		System.out.println("ddddddddddddddddddddd");
		fruitService.save(fruitgoods);
		return "redirect:/fruit/manageFruit";
	}
	
	@RequestMapping(value="/delete/{fruitId}",method=RequestMethod.GET)
	public String deleteFruit(@PathVariable("fruitId")Integer fruitId) throws Exception{
		fruitService.delete(fruitService.findById(fruitId));
		return "redirect:/fruit/manageFruit";
	}
	
	@RequestMapping(value="/update/{fruitId}",method=RequestMethod.GET)
	public String toUpdateFruit(@PathVariable("fruitId")Integer fruitId,Model model) throws Exception{
		model.addAttribute("fruit", fruitService.findById(fruitId));
		return "/fruit/updateFruit";
	}
	
	@RequestMapping("/update")
	public String updateFruit(@ModelAttribute Fruitgoods fruitgoods,@RequestParam("file")MultipartFile file,HttpServletRequest request) throws Exception{
		if(!file.isEmpty()){
			String s=System.currentTimeMillis()+file.getOriginalFilename();
			FileUtils.copyInputStreamToFile(file.getInputStream(),new File(request.getServletContext().getRealPath("/resources/upload"),s));
			fruitgoods.setFImg(s);
		}
		fruitService.update(fruitgoods);
		return "redirect:/fruit/manageFruit";
	}
	
	@RequestMapping(value="/showFruit/{fruitId}",method=RequestMethod.GET)
	public String showFruit(@PathVariable("fruitId")Integer fruitId,Model model,HttpSession session) throws Exception{
		model.addAttribute("fruit", fruitService.findById(fruitId));
		//暂时为adminid
		Integer cartNum = shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("cartNum",cartNum);
		return "fruit/showFruit";
	}
	
	@RequestMapping(value="/findLocation/{fruitType}")
	public ResponseEntity<List<String>> findLocation(@PathVariable("fruitType")String fruitType){
		List<String> list = fruitService.findLocationByType(fruitType);
		return new ResponseEntity(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/toIndex")
	public String toIndex(@ModelAttribute PageModel pageModel,@RequestParam(value="native",required=false)String native1,
			@RequestParam(value="fruitType",required=false)String fruitType,Model model,HttpSession session) throws Exception{
		pageModel.setRecordCount(fruitService.getTotalRecords(fruitType,native1));
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("FId","desc");
		
		if(fruitType==null)
			fruitType = "全部";
		if(native1==null)
			native1 = "全部";
		
		List<Fruitgoods> list = fruitService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize(),fruitType,native1,orderBy).getResultList();
		List<Fruitgoods> list1 = fruitService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize(), "精选果篮", "全部", orderBy).getResultList();
		
		//fruitService.findAll(orderBy,native1,fruitType);
		model.addAttribute("fruitList", list);
		model.addAttribute("choiceList",list1);
		model.addAttribute("page",pageModel);
		Integer cartNum = shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("cartNum",cartNum);
		session.setAttribute("client",clientService.login("1", "1", "1","1","1","1","1"));
		model.addAttribute("fruitType",fruitType);
		model.addAttribute("native1",native1);
		if(pageModel.getTotalPages()==1)
			model.addAttribute("last", true);
		return "index";
	}
	
	@RequestMapping(value="/index")
	public String index(@ModelAttribute PageModel pageModel,@RequestParam(value="native",required=false)String native1,
			@RequestParam(value="fruitType",required=false)String fruitType,Model model,HttpSession session) throws Exception{
		pageModel.setRecordCount(fruitService.getTotalRecords(fruitType,native1));
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("FId","desc");
		
		if(fruitType==null)
			fruitType = "全部";
		if(native1==null)
			native1 = "全部";
		
		List<Fruitgoods> list = fruitService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize(),fruitType,native1,orderBy).getResultList();
		List<Fruitgoods> list1 = fruitService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize(), "精选果篮", "全部", orderBy).getResultList();
		
		//fruitService.findAll(orderBy,native1,fruitType);
		model.addAttribute("fruitList", list);
		model.addAttribute("choiceList", list1);
		model.addAttribute("page",pageModel);
		Integer cartNum = shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("cartNum",cartNum);
		return "index";
	}
	
	@RequestMapping("/search")
	public String searchByKey(@RequestParam(value="key")String key){
		
		return "searchList";
	}
	
	@RequestMapping(value="/pageConsult")
	public ResponseEntity<List<Fruitgoods>> pageConsult(@ModelAttribute PageModel pageModel
			,@RequestParam(value="native",required=false)String native1,
			@RequestParam(value="fruitType",required=false)String fruitType) throws Exception{
		System.out.println("---------------->"+pageModel.getCurrentPageNo()+"---->"+fruitService.getTotalRecords(null,null));
		if((fruitType!=null && fruitType.equals("全部"))||(native1!=null && native1.equals("全部"))){
			if((fruitType!=null && fruitType.equals("全部"))&&!(native1!=null && native1.equals("全部"))){
				pageModel.setRecordCount(fruitService.getTotalRecords(null,native1));
			}
			if(!(fruitType!=null && fruitType.equals("全部"))&&(native1!=null && native1.equals("全部"))){
				pageModel.setRecordCount(fruitService.getTotalRecords(fruitType,null));
			}
			if((fruitType!=null && fruitType.equals("全部"))&&(native1!=null && native1.equals("全部"))){
				pageModel.setRecordCount(fruitService.getTotalRecords(null,null));
			}
		}else{
			pageModel.setRecordCount(fruitService.getTotalRecords(fruitType,native1));
		}
			
		if(fruitType==null)
			fruitType = "全部";
		if(native1==null)
			native1 = "全部";
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("FId","desc");
		List<Fruitgoods> list = fruitService.findAllByPage(pageModel.getCurrentPageNo(), pageModel.getPageSize(),fruitType,native1,orderBy).getResultList();
		int pageNo = pageModel.getCurrentPageNo()+1;  //页数增加一
		Fruitgoods fruitgoods = new Fruitgoods();
		fruitgoods.setFId(pageNo);
		
		if(pageNo==(pageModel.getTotalPages()+1))
			fruitgoods.setFStatus("last");
		list.add(fruitgoods);
		return new ResponseEntity<List<Fruitgoods>>(list,HttpStatus.OK);
	}
}
