package com.so.demosboot.modules.cms.controller;

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
import com.so.demosboot.modules.cms.entity.Comments;
import com.so.demosboot.modules.cms.service.CommentsService;
import com.so.demosboot.modules.share.entity.ShareOrderInfo;
import com.so.demosboot.modules.share.service.ItemsService;
import com.so.demosboot.modules.share.service.ShareOrderInfoService;
import com.so.demosboot.modules.sys.utils.UserUtil;


/**
 * 评论信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/cms/comments")
public class CommentsController{

	@Autowired
	private CommentsService commentsService;
	@Autowired
	private ShareOrderInfoService shareOrderInfoService;
	@Autowired
	private ItemsService itemsService;
	
	@ModelAttribute
	public Comments get(@RequestParam(required=false) String id) {
		Comments entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commentsService.getById(id);
		}else{
			entity = new Comments();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Comments comments, Model model) {
		PageHelper.startPage(comments.getPageNo(),10);
		List<Comments> list = commentsService.findList(comments);
		PageInfo<Comments> pageInfo = new PageInfo<Comments>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "cms/commentsList";
	}

	@RequestMapping(value = "form")
	public String form(Comments comments, Model model) {
		if (StringUtils.isNotEmpty(comments.getId())){
			comments = commentsService.getById(comments.getId());
			model.addAttribute("comments",comments);
		}
		return "cms/commentsForm";
	}

	@RequestMapping(value = "save")
	public String save(Comments comments,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		ShareOrderInfo byId = shareOrderInfoService.getById(comments.getTableId());
		comments.setTableId(byId.getItemId());
		comments.setCommentTime(DateUtils.getDateTime());
		comments.setCommentUser(UserUtil.currentUser(request).getUsername());
		commentsService.save(comments);
		byId.setIsComment("1");
		shareOrderInfoService.save(byId);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		return "redirect:"+"/share/shareOrderInfo/mine";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Comments comments, RedirectAttributes redirectAttributes) {
		commentsService.delete(comments.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/cms/comments";
	}

}