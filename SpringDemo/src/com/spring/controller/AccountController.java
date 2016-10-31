package com.spring.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.dao.AccountDAO;
import com.spring.model.Account;

@Controller
@RequestMapping("")
public class AccountController {

	private static Logger logger = Logger.getLogger(AccountController.class.getName());

	@Autowired
	private AccountDAO dao;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@ModelAttribute("user") Account account) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("user") Account account, HttpSession session) {
		try {
			Account result = dao.checkLogin(account.getUserName(), account.getPassword());
			if (result != null) {
				session.setAttribute("accountInfo", result);
				return "redirect:/home.htm";
			} else {
				model.put("errorMessage", "Invalid username or password");
				return "login";
			}
		} catch (SQLException e) {
			model.put("errorMessage", "Error occurred in processing login!");
			logger.error("Error occurred in processing login!");
		}
		return "login";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(@ModelAttribute("user") Account account) {
		return "home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String updateInfo(ModelMap model, @ModelAttribute("user") Account account,
			@RequestParam("dateOfBirth") String dateOfBirth, HttpSession session) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dob = new Date();
			try {
				dob = sdf.parse(dateOfBirth);
			} catch (ParseException e) {
				logger.error("Inputted date cannot be parsed : " + dateOfBirth);
			}
			account.setDateOfBirth(dob);
			boolean result = dao.updateInfo(account);
			if (result) {
				session.setAttribute("accountInfo", account);
				model.put("successMessage", "Your information has been updated!");
				return "home";
			} else {
				model.put("errorMessage", "Error in updating account! No update processed");
				return "home";
			}
		} catch (SQLException e) {
			model.put("errorMessage", "An Unknown error has occurred in updating account!");
			logger.error("Database has met error : " + e.getMessage());
		}
		return "home";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login.htm";
	}

}
