package com.so.demosboot.modules.share.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.so.demosboot.common.utils.DateUtils;
import com.so.demosboot.common.utils.FileUploadAndDowload;
import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.cms.entity.Comments;
import com.so.demosboot.modules.cms.service.CommentsService;
import com.so.demosboot.modules.share.entity.Category;
import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.share.service.CategoryService;
import com.so.demosboot.modules.share.service.ItemsService;
import com.so.demosboot.modules.sys.utils.UserUtil;


/**
 * 图书信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/front")
public class FrontController{

	@Autowired
	private ItemsService itemsService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentsService commentsService;
	/**
	 * 图书审核
	 * @param items
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "index")
	public String audit(Items items, Model model,HttpServletRequest request) {
		List<Category> findList = categoryService.findList(new Category());
		List<Category> categories = new ArrayList<Category>();
		for (Category category : findList) {
			Items query = new Items();query.setItemType(category.getId());query.setOnsell("1");
			List<Items> findList2 = itemsService.findList(query);
			for (Items items2 : findList2) {
				System.out.println(items2.getName());
			}
			category.setItems(findList2);
			categories.add(category);
		}
		model.addAttribute("categories", categories);
		return "front/index";
	}
	
	@GetMapping(value = "detail")
	public String detail(Items items, Model model,HttpServletRequest request) {
		Items byid = itemsService.getById(items.getId());
		model.addAttribute("items", byid);
		//查出所有的评价
		Comments comments = new Comments();
		comments.setTableName("db_items");comments.setTableId(items.getId());
		List<Comments> findList = commentsService.findList(comments);
		model.addAttribute("comments", findList);
		return "front/detail";
	}
	
	@ResponseBody
	@PostMapping(value = "audit")
	public String audit(@RequestBody String requestStr){
		try {
			String id = requestStr.replace("\"", "");
			System.out.println(id);
			Items byId = itemsService.getById(id);
			byId.setAuditStatue("1");
			itemsService.save(byId);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}

}