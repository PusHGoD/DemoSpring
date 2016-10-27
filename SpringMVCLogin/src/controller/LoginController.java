package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import DAO.UserDAO;
import entities.*;

@Controller
@RequestMapping(value = "/user")
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap mm){
		mm.put("acc", new User());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute(value = "acc") User acc, ModelMap mm, HttpSession session){
		UserDAO dao = new UserDAO();
		boolean check = false;
		check = dao.login(acc.getUsername(), acc.getPassword());
		if(check == true){
			User user = dao.getUser();
			session.setAttribute("user", user);
			return "welcome";
		}
		mm.put("err", "Username or password is not correct!");
		return "login";
	}
	
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String update(ModelMap mm){		
		mm.put("acc", new User());
		return "welcome";		
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public String update(@ModelAttribute(value = "acc") User acc, ModelMap mm, HttpSession session){
		UserDAO dao = new UserDAO();
		dao.update(acc.getId(), acc.getFname(), acc.getLname());
		session.setAttribute("user", acc);
		return "welcome";
	}
}
