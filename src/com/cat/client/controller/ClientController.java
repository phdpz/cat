package com.cat.client.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cat.admin.service.AdminService;
import com.cat.admin.service.AdminServiceImpl;
import com.cat.base.controller.BaseController;
import com.cat.client.service.ClientService;
import com.cat.pojo.Admin;
import com.cat.pojo.Orderinfo;
import com.cat.util.PageModel;


@Controller
@RequestMapping("/client")
public class ClientController extends BaseController{
	@Autowired
	protected AdminService adminService;
	
	@Autowired
	protected ClientService clientService;
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute Admin admin, HttpSession session) throws Exception{
		if(adminService.login(admin)){
			session.setAttribute("admin", admin);
			return "redirect:/admin/manageAdmin";
		}	
		else
			return "index";
	}
	
	@RequestMapping("/toPerCenter/{openId}")
	public String toPerCenter(@PathVariable("openId") String openId,Model model) throws Exception{
		List<Object[]> list=clientService.getPerInfo(openId);
		model.addAttribute("headimgurl",list.get(0)[0]);
		model.addAttribute("nickName",list.get(0)[1]);
		model.addAttribute("scroe",list.get(0)[2]);
		return "perCenter/perCenter";
	}
	
	@RequestMapping("/getOrder/{openId}")
	public String getOrder(@PathVariable("openId") String openId,Model model) throws Exception{
		List<Orderinfo> list=clientService.getOrderByOpenId(openId);
		model.addAttribute("list",list);
		return "perCenter/perOrder";
	}
}
