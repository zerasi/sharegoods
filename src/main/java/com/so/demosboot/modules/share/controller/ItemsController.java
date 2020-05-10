package com.so.demosboot.modules.share.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.so.demosboot.common.utils.DateUtils;
import com.so.demosboot.common.utils.FileUploadAndDowload;
import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.share.entity.Category;
import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.share.service.CategoryService;
import com.so.demosboot.modules.share.service.ItemsService;
import com.so.demosboot.modules.sys.utils.Result;
import com.so.demosboot.modules.sys.utils.UserUtil;


/**
 * 图书信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/share/items")
public class ItemsController{

	@Autowired
	private ItemsService itemsService;
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Items get(@RequestParam(required=false) String id) {
		Items entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemsService.getById(id);
		}else{
			entity = new Items();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Items items, Model model) {
		PageHelper.startPage(items.getPageNo(),10);
		items.setAuditStatue("1");
		List<Items> list = itemsService.findList(items);
		PageInfo<Items> pageInfo = new PageInfo<Items>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		//查出所有的类型
		List<Category> findList = categoryService.findList(new Category());
		model.addAttribute("types", findList);
		return "share/itemsList";
	}

	@RequestMapping(value = "ue")
	public String ue(Items items, Model model) {
		return "index";
	}
	
	@RequestMapping(value = "form")
	public String form(Items items, Model model) {
		if (StringUtils.isNotEmpty(items.getId())){
			items = itemsService.getById(items.getId());
			model.addAttribute("items",items);
		}
		//查出所有的类型
		List<Category> findList = categoryService.findList(new Category());
		model.addAttribute("types", findList);
		return "share/itemsForm";
	}

	@RequestMapping(value = "save")
	public String save(Items items,@RequestParam(value = "file",required=false) MultipartFile file,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String upload = FileUploadAndDowload.upload(file, "itemsImage");
		if (StringUtils.isNotEmpty(upload)) {
			items.setImage(upload);
		}
		if (StringUtils.isEmpty(items.getId())) {
			if(StringUtils.isNotEmpty(items.getItems_isbn_flag()) && "1".equals(items.getItems_isbn_flag())){
				items.setAuditStatue("1");//已经审核
				items.setOnsell("1");
			}else{
				items.setAuditStatue("0");//待审核
				items.setOnsell("0");
			}
			items.setBelongUser(UserUtil.currentUser(request).getUsername());
			items.setCreatetime(DateUtils.getDateTime());
		}
		itemsService.save(items);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		String role = UserUtil.currentUser(request).getRole();
		if ("1".equals(role)) {
			return "redirect:"+"/share/items";
		}else {
			return "redirect:"+"/share/items/mine";
		}
		
	}
	
	@RequestMapping(value = "delete")
	public String delete(Items items, RedirectAttributes redirectAttributes) {
		itemsService.delete(items.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/share/items";
	}
	
	@RequestMapping(value = "onsell")
	public String onsell(Items items, RedirectAttributes redirectAttributes) {
		Items byId = itemsService.getById(items.getId());
		byId.setOnsell(items.getOnsell());
		itemsService.save(byId);
		redirectAttributes.addFlashAttribute("msg", "操作成功！");
		return "redirect:"+"/share/items/mine";
	}
	
	@RequestMapping(value = "mine")
	public String mine(Items items, Model model,HttpServletRequest request) {
		PageHelper.startPage(items.getPageNo(),10);
		items.setBelongUser(UserUtil.currentUser(request).getUsername());
		List<Items> list = itemsService.findList(items);
		PageInfo<Items> pageInfo = new PageInfo<Items>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		//查出所有的类型
		List<Category> findList = categoryService.findList(new Category());
		model.addAttribute("types", findList);
		return "share/itemsMine";
	}
	
	/**
	 * 图书审核
	 * @param items
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "audit")
	public String audit(Items items, Model model,HttpServletRequest request) {
		PageHelper.startPage(items.getPageNo(),10);
		items.setAuditStatue("0");
		List<Items> list = itemsService.findList(items);
		PageInfo<Items> pageInfo = new PageInfo<Items>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		//查出所有的类型
		List<Category> findList = categoryService.findList(new Category());
		model.addAttribute("types", findList);
		return "share/itemsAudit";
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

	
	@RequestMapping(value = "mineCount")
	public String mineCount(Items items, Model model,HttpServletRequest request) {
		List<Category> findList = categoryService.findList(new Category());
		List<Category> categories = new ArrayList<Category>();
		for (Category category : findList) {
			Items query = new Items();query.setBelongUser(UserUtil.currentUser(request).getUsername());query.setItemType(category.getId());
			List<Items> findList2 = itemsService.findList(query);
			category.setItems(findList2);
			categories.add(category);
		}
		int size = categories.size();
		String name [] = new String[size];
		Integer total [] = new Integer[size];
		int i = 0;
		for (Category category  : categories) {
			name[i] = category.getName();
			total[i] = category.getItems().size();
			i++;
		}
		System.out.println( Arrays.toString(name));
		System.out.println( Arrays.toString(total));
		model.addAttribute("names", name);
		model.addAttribute("totals", total);
		return "share/echartjs";
	}
	
	@GetMapping(value = "isbn_get")
	public String isbn_get(Items items,Model model){
		if (StringUtils.isNotEmpty(items.getIsbn())){

	        String result = "";
	        BufferedReader in = null;
	        try {
	        	String urlNameString = "https://api.douban.com/v2/book/isbn/:"+items.getIsbn()+"?apikey=0df993c66c0c636e29ecbb5344252a4a";
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	 
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	            
	            JSONObject json = JSON.parseObject(result);
		        String title = json.getString("title");
		        String summary = json.getString("summary");
		        String price = json.getString("price");
		        items.setName(title);
		        items.setRentPrice(Double.parseDouble(price.replace("元","")));
		        items.setContent(summary);
		        model.addAttribute("items",items);
		        model.addAttribute("items_isbn_flag","1");
		        model.addAttribute("items_isbn","通过isbn获取成功");
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	            model.addAttribute("items_isbn","通过isbn获取失败");
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
		
			
		}
		//查出所有的类型
		List<Category> findList = categoryService.findList(new Category());
		model.addAttribute("types", findList);
		return "share/itemsForm";
		
	}
}