package com.so.demosboot.modules.share.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.so.demosboot.common.utils.DateUtils;
import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.share.entity.ShareOrderInfo;
import com.so.demosboot.modules.share.service.ItemsService;
import com.so.demosboot.modules.share.service.ShareOrderInfoService;
import com.so.demosboot.modules.sys.entity.User;
import com.so.demosboot.modules.sys.service.UserService;
import com.so.demosboot.modules.sys.utils.UserUtil;


/**
 * 订单信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/share/shareOrderInfo")
public class ShareOrderInfoController{

	@Autowired
	private ShareOrderInfoService shareOrderInfoService;
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public ShareOrderInfo get(@RequestParam(required=false) String id) {
		ShareOrderInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareOrderInfoService.getById(id);
		}else{
			entity = new ShareOrderInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ShareOrderInfo shareOrderInfo, Model model) {
		PageHelper.startPage(shareOrderInfo.getPageNo(),10);
		List<ShareOrderInfo> list = shareOrderInfoService.findList(shareOrderInfo);
		PageInfo<ShareOrderInfo> pageInfo = new PageInfo<ShareOrderInfo>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "share/shareOrderInfoList";
	}

	@RequestMapping(value = "form")
	public String form(ShareOrderInfo shareOrderInfo, Model model) {
		if (StringUtils.isNotEmpty(shareOrderInfo.getId())){
			shareOrderInfo = shareOrderInfoService.getById(shareOrderInfo.getId());
			model.addAttribute("shareOrderInfo",shareOrderInfo);
		}
		return "share/shareOrderInfoForm";
	}

	@RequestMapping(value = "save")
	public String save(ShareOrderInfo shareOrderInfo,RedirectAttributes redirectAttributes) {
		shareOrderInfoService.save(shareOrderInfo);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		return "redirect:"+"/share/shareOrderInfo";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ShareOrderInfo shareOrderInfo, RedirectAttributes redirectAttributes) {
		shareOrderInfoService.delete(shareOrderInfo.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/share/shareOrderInfo";
	}
	
	@RequestMapping(value = "rent")
	public String rent(ShareOrderInfo shareOrderInfo,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		shareOrderInfo.setUserId(UserUtil.currentUser(request).getUsername());
		shareOrderInfo.setOrderTime(DateUtils.getDateTime());
		shareOrderInfo.setIsBack("0");
		shareOrderInfoService.save(shareOrderInfo);
		//更新
		Items byId = itemsService.getById(shareOrderInfo.getItemId());
		byId.setRentType("1");
		itemsService.save(byId);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		return "redirect:"+"/share/shareOrderInfo/mine";
	}
	
	@RequestMapping(value = "mine")
	public String mine(ShareOrderInfo shareOrderInfo, Model model,HttpServletRequest request) {
		PageHelper.startPage(shareOrderInfo.getPageNo(),10);
		shareOrderInfo.setUserId(UserUtil.currentUser(request).getUsername());
		List<ShareOrderInfo> list = shareOrderInfoService.findList(shareOrderInfo);
		PageInfo<ShareOrderInfo> pageInfo = new PageInfo<ShareOrderInfo>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "share/shareOrderInfoMine";
	}
	
	@RequestMapping(value = "back")
	public String back(ShareOrderInfo shareOrderInfo,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		ShareOrderInfo byId = shareOrderInfoService.getById(shareOrderInfo.getId());
		byId.setIsBack("1");
		byId.setBackTime(DateUtils.getDateTime());
		byId.setRentTimes(com.so.demosboot.modules.sys.utils.DateUtils.between_days(byId.getOrderTime(), byId.getBackTime()).intValue());
		shareOrderInfoService.save(byId);
		//更新
		Items byId1 = itemsService.getById(byId.getItemId());
		byId1.setRentType("0");
		itemsService.save(byId1);
		
		BigDecimal rentPrice = new BigDecimal(byId1.getRentPrice());
		BigDecimal tetweenDays = new BigDecimal(byId.getRentTimes());
		User user = userService.getByUserName(byId.getUserId());
		BigDecimal total = rentPrice.divide(new BigDecimal(50), 2).multiply(tetweenDays);
		total = total.setScale(2, RoundingMode.HALF_UP);
		user.setLendPoint(user.getLendPoint().subtract(total));
		
		userService.update_len(user);
		
		User user1 =userService.getByUserName(byId1.getBelongUser()); 
		user1.setLendPoint(user1.getLendPoint().add(total));
		userService.update_len(user1);
		
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！结算金额："+total);
		return "redirect:"+"/share/shareOrderInfo/myRent";
	}
	
	@RequestMapping(value = "myRent")
	public String myRent(ShareOrderInfo shareOrderInfo, Model model,HttpServletRequest request) {
		PageHelper.startPage(shareOrderInfo.getPageNo(),10);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" and a.item_id in (");
		Items query = new Items();
		query.setBelongUser(UserUtil.currentUser(request).getUsername());
		List<Items> findList = itemsService.findList(query);
		for (Items items : findList) {
			stringBuffer.append("'"+items.getId()+"',");
		}
		stringBuffer.append(" '1') ");
		shareOrderInfo.setSqlStr(stringBuffer.toString());
		List<ShareOrderInfo> list = shareOrderInfoService.findList(shareOrderInfo);
		PageInfo<ShareOrderInfo> pageInfo = new PageInfo<ShareOrderInfo>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "share/shareOrderInfoMineRent";
	}

}