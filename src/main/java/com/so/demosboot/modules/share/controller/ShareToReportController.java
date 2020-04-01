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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.so.demosboot.common.utils.DateUtils;
import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.share.entity.ShareOrderInfo;
import com.so.demosboot.modules.share.entity.ShareToReport;
import com.so.demosboot.modules.share.service.ShareOrderInfoService;
import com.so.demosboot.modules.share.service.ShareToReportService;
import com.so.demosboot.modules.sys.utils.UserUtil;


/**
 * 举报信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/share/shareToReport")
public class ShareToReportController{

	@Autowired
	private ShareToReportService shareToReportService;
	@Autowired
	private ShareOrderInfoService shareOrderInfoService;
	
	@ModelAttribute
	public ShareToReport get(@RequestParam(required=false) String id) {
		ShareToReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareToReportService.getById(id);
		}else{
			entity = new ShareToReport();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ShareToReport shareToReport, Model model) {
		PageHelper.startPage(shareToReport.getPageNo(),10);
		List<ShareToReport> list = shareToReportService.findList(shareToReport);
		PageInfo<ShareToReport> pageInfo = new PageInfo<ShareToReport>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "share/shareToReportList";
	}

	@RequestMapping(value = "form")
	public String form(ShareToReport shareToReport, Model model) {
		if (StringUtils.isNotEmpty(shareToReport.getId())){
			shareToReport = shareToReportService.getById(shareToReport.getId());
			model.addAttribute("shareToReport",shareToReport);
		}
		return "share/shareToReportForm";
	}

	@RequestMapping(value = "save")
	public String save(ShareToReport shareToReport,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		ShareOrderInfo byId = shareOrderInfoService.getById(shareToReport.getOrderId());
		shareToReport.setRepotUser(byId.getUserId());
		shareToReport.setReportTime(DateUtils.getDateTime());
		shareToReport.setReporter(UserUtil.currentUser(request).getUsername());
		shareToReportService.save(shareToReport);
		byId.setIsReport("1");
		shareOrderInfoService.save(byId);
		redirectAttributes.addFlashAttribute("msg", "举报成功！");
		return "redirect:"+"/share/shareOrderInfo/myRent";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ShareToReport shareToReport, RedirectAttributes redirectAttributes) {
		shareToReportService.delete(shareToReport.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/share/shareToReport";
	}

}