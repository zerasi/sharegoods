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

import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.share.entity.Category;
import com.so.demosboot.modules.share.service.CategoryService;


/**
 * 图书种类Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/share/category")
public class CategoryController{

	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Category get(@RequestParam(required=false) String id) {
		Category entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = categoryService.getById(id);
		}else{
			entity = new Category();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Category category, Model model) {
		PageHelper.startPage(category.getPageNo(),10);
		List<Category> list = categoryService.findList(category);
		PageInfo<Category> pageInfo = new PageInfo<Category>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "share/categoryList";
	}

	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (StringUtils.isNotEmpty(category.getId())){
			category = categoryService.getById(category.getId());
			model.addAttribute("category",category);
		}
		return "share/categoryForm";
	}

	@RequestMapping(value = "save")
	public String save(Category category,RedirectAttributes redirectAttributes) {
		categoryService.save(category);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		return "redirect:"+"/share/category";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		categoryService.delete(category.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/share/category";
	}

}