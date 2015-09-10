package com.cat.orderManage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cat.base.controller.BaseController;
import com.cat.pojo.Admin;
import com.cat.pojo.Station;
import com.cat.orderManage.service.StationService;
import com.cat.orderManage.service.StationServiceImpl;
import com.cat.util.PageModel;


@Controller
@RequestMapping("/station")
public class StationController extends BaseController{
	@Autowired
	protected StationService stationService;
	@Autowired
	protected AdminService adminService;
	
	@RequestMapping("/manageStation")
	public String manageStation(Model model,@RequestParam(value="school",required=false)String school) throws Exception{
		if(school==null)
			school="华南农业大学";
		List<Station> list = stationService.findBySchool("华南农业大学");
		List<String> schoolList = stationService.findAllSchool();
		Admin admin = adminService.findAdminBySchool(school).get(0);
		model.addAttribute("stationList",list);
		model.addAttribute("schoolList",schoolList);	
		model.addAttribute("school", school);
		model.addAttribute("admin", admin);
		return "station/manageStation";	
	}
	
	@RequestMapping("/toAddStation")
	public String toAddStation(){
		return "station/addStation";
	}
	
	@RequestMapping("/addStation")
	public String addStation(@ModelAttribute Station station) throws Exception{
		stationService.save(station);
		return "redirect:/station/manageStation";
	}
	
	@RequestMapping("delete/{SId}")
	public String deleteStation(@PathVariable Integer SId) throws Exception{
		stationService.delete(stationService.findById(SId));
		return "redirect:/station/manageStation";
	}
	
	@RequestMapping(value="queryArea/{school}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> queryArea(@PathVariable String school){
		List<Station> list = stationService.findBySchool(school);
		String result = "";
		for(int i=0;i<list.size();i++){
			result += list.get(i).getSArea()+":";
		}
		
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value="addArea/{school}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> addArea(@PathVariable("school")String school) throws
	  Exception{
		Station station = new Station();
		station.setSSchool(school);
		stationService.save(station);
		return new ResponseEntity<String>(stationService.findMaxId()+"",HttpStatus.OK);
	}
	
	@RequestMapping(value="updateArea/{id}/{area}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> updateArea(@PathVariable("id") Integer SId,@PathVariable("area") String area) throws
	  Exception{
		Station station = stationService.findById(SId);
		station.setSArea(area);
		stationService.update(station);
		
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	@RequestMapping(value="deleteArea/{id}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> deleteArea(@PathVariable("id") Integer SId) throws
	  Exception{
		stationService.delete(stationService.findById(SId));
		
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
}
