package com.so.demosboot.modules.sys.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.so.demosboot.common.utils.DateUtils;
import com.so.demosboot.common.utils.StringUtils;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.sys.entity.User;
import com.so.demosboot.modules.sys.entity.UserEx;
import com.so.demosboot.modules.sys.entity.UserLink;
import com.so.demosboot.modules.sys.service.UserService;
import com.so.demosboot.modules.sys.utils.Result;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys/user")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 列表查询
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String findList(User user, Model model){
		PageHelper.startPage(user.getPageNo(),10);
		List<User> list = userService.findList(user);
		PageInfo<User> pageInfo = new PageInfo<User>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "sys/userList";
	}
	
	/**
	 * 列表查询
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/userlistLink")
	public String userlistLink(UserLink userLink, Model model){
		PageHelper.startPage(userLink.getPageNo(),10);
		List<UserLink> list = userService.findListLink(userLink);
		PageInfo<UserLink> pageInfo = new PageInfo<UserLink>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "sys/userListLink";
	}
	
	/**
	 * 列表查询
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/userlistExMsg")
	public String userlistExMsg(UserEx userEx, Model model){
		PageHelper.startPage(userEx.getPageNo(),10);
		List<UserEx> list = userService.findListExMsg(userEx);
		PageInfo<UserEx> pageInfo = new PageInfo<UserEx>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "sys/userListLinkExMsg";
	}

	/**
	 * 表单跳转
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/form")
	public String form(User user, Model model){
		if (StringUtils.isNotEmpty(user.getId())){
			user = userService.getById(user.getId());
			model.addAttribute("user",user);
		}
		return "sys/userForm";
	}

	/**
	 * 新增修改
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(User user,RedirectAttributes redirectAttributes) {
		userService.save(user);
		redirectAttributes.addFlashAttribute("msg", "保存成功！");
		return "redirect:"+"/sys/user/list";
	}

	/**
	 * 删除
	 * @param user
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(User user,RedirectAttributes redirectAttributes){
		userService.delete(user.getId());
		redirectAttributes.addFlashAttribute("msg", "删除成功！");
		return "redirect:"+"/sys/user/list";
	}
	@RequestMapping("/deleteUserLink")
	@ResponseBody
	public Result deleteUserLink(UserLink userLink,RedirectAttributes redirectAttributes,HttpServletRequest request){
		try{
			userService.deleteUserLink(userLink);
			return new Result(true, "操作成功");
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}
	
	@RequestMapping("/deleteUserEx")
	@ResponseBody
	public Result deleteUserEx(UserEx userEx,RedirectAttributes redirectAttributes,HttpServletRequest request){
		
		try{
			userService.deleteUserEx(userEx);
			return new Result(true, "操作成功");
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false, "操作失败");
		}
	}

	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request,Model model){
		User login = userService.login(user);
		if (login!=null){
			if(login.getIsBlock() !=null && "1".equals(login.getIsBlock())){
				model.addAttribute("msg","您的账号已经被锁定，请联系管理员！");
				return "sys/login";
			}
			request.getSession().setAttribute("login",login);
			return "redirect:"+"/index";
		}else{
			model.addAttribute("msg","用户名或密码错误！");
			return "sys/login";
		}
	}
	
	@RequestMapping("/update_lendPoint")
	public String update_lendPoint(User user, HttpServletRequest request,Model model){
		User user_update = userService.update_lendPoint(user);
		request.getSession().setAttribute("login",user_update);
		return "redirect:"+"/index";
	}
	
	@RequestMapping("/fresh_lendPoint")
	@ResponseBody
	public String fresh_lendPoint(User user, HttpServletRequest request,Model model){
		User login_user = (User) request.getSession().getAttribute("login");
		if(login_user != null){
			login_user = userService.getById(login_user.getId());
		}
		return login_user.getLendPoint().toString();
	}

	@GetMapping("/regit")
	public String regit(){
		return "sys/register";
	}
	
	
	@PostMapping("/regit")
	public String regit(User user, HttpServletRequest request,Model model,RedirectAttributes redirectAttributes){
		User query = new User();
		query.setUsername(user.getUsername());
		List<User> findList = userService.findList(query);
		if (findList!=null && findList.size()>0) {
			model.addAttribute("msg", "注册失败,用户名已存在");
			return "sys/register";
		}else{
			try {
				user.setRole("2");
				userService.save(user);
				redirectAttributes.addFlashAttribute("msg", "注册成功！");
				return "redirect:"+"/login";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "注册失败，服务异常！");
				return "sys/register";
			}
		}
	}
	
	/**
	 * 安全退出
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:"+"/login";
	}
	
	@RequestMapping("/addUserLink")
	@ResponseBody
	public Result addUserLink(UserLink userLink){
		try{
			userLink.setCreattime(DateUtils.getDate());
			userService.addUserLink(userLink);
			return new Result(true, "添加成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, "已存在好友");
		}
	}
	
	@RequestMapping("/sendMsg")
	@ResponseBody
	public Result sendMsg(UserEx userEx){
		try{
			userEx.setCreatedate(DateUtils.getDate());
			userService.sendMsg(userEx);
			return new Result(true, "发送成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, "发送失败");
		}
	}
	
	
}
