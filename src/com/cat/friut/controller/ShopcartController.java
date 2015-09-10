package com.cat.friut.controller;

import java.io.File;
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
import com.cat.friut.service.FruitService;
import com.cat.friut.service.ShopcartService;
import com.cat.pojo.*;
import com.cat.util.PageModel;

@Controller
@RequestMapping("/shopcart")
public class ShopcartController extends BaseController{
	@Autowired
	private ShopcartService shopcartService;
	@Autowired
	private FruitService fruitService;
	
	//删除购物车
	@RequestMapping(value="/deleteCart/{SId}/{num}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> deleteCart(Shopcart shopcart,@PathVariable("SId")Integer SId,
			@PathVariable("num")Integer num,HttpSession session) throws Exception{
		shopcart = shopcartService.findById(SId);
		int oldNum=shopcart.getSNum();
		String result="";
		shopcartService.delete(shopcart);
		result="delete:"+shopcart.getSId();
		double totalPrices = shopcartService.countTotalPrice(((Admin)session.getAttribute("admin")).getAUserName());
		result+=":"+totalPrices;
		if(shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName())==0)
			result+="^-^null";
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	//把货物放到购物车
	@RequestMapping(value="/putToCart/{fruitId}/{num}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> puToCart(Shopcart shopcart,@PathVariable("fruitId")Integer fruitId,
			@PathVariable("num")Integer num,HttpSession session) throws Exception{
		shopcart.setFruitId(fruitId);
		shopcart.setSNum(num);
		shopcart.setOpenId(((Admin)session.getAttribute("admin")).getAUserName());
		Shopcart s = shopcartService.findByUserAndFruit(shopcart.getOpenId(), shopcart.getFruitId());
		if(s==null){
			shopcartService.save(shopcart);
		}else{
			s.setSNum(s.getSNum()+shopcart.getSNum());
			shopcartService.update(s);
		}
		Integer cartNum = shopcartService.countShopcartByUser(shopcart.getOpenId());
		
		return new ResponseEntity<String>(cartNum.toString(),HttpStatus.OK);
	}
	
	//改变购物车商品数量
	@RequestMapping(value="/changeCart/{SId}/{num}/{sign}",produces = "text/html;charset=UTF-8")
	public ResponseEntity<String> changeCart(Shopcart shopcart,@PathVariable("SId")Integer SId,
			@PathVariable("num")Integer num,@PathVariable("sign")String sign,HttpSession session) throws Exception{
		shopcart = shopcartService.findById(SId);
		int oldNum=shopcart.getSNum();
		String result="";
		
		if(sign.equals("+")){
			shopcart.setSNum(oldNum+1);
			shopcartService.update(shopcart);
			result=shopcart.getSId()+":"+shopcart.getSNum();
		}else{
			if(oldNum==1){	
				result="doNothing";
			}else{
				result+=shopcart.getSId()+":";
				shopcart.setSNum(oldNum-1);
				shopcartService.update(shopcart);
				result+=oldNum-1;
			}
		}
		double totalPrices = shopcartService.countTotalPrice(((Admin)session.getAttribute("admin")).getAUserName());
		result+=":"+totalPrices;
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@RequestMapping("/toShopcart")
	public String goToShopcart(Model model,HttpSession session) throws Exception{
		String openId = ((Admin)session.getAttribute("admin")).getAUserName();
		List<Shopcart> list=shopcartService.findByUser(openId);
		model.addAttribute("shopList",list);
		List<Fruitgoods> list1 = fruitService.findAll();
		model.addAttribute("fruitList", list1);
		model.addAttribute("totalPrice",shopcartService.countTotalPrice(openId));
		Integer cartNum = shopcartService.countShopcartByUser(((Admin)session.getAttribute("admin")).getAUserName());
		model.addAttribute("cartNum",cartNum);
		return "fruit/shopcart";
	}
}
