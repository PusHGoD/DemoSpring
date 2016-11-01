package com.spring.controller;

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
import com.spring.entity.Account;

/**
 * @author HuanPM Controller of account
 */
@Controller
@RequestMapping("")
public class AccountController {

	/**
	 * Log4j logger
	 */
	private static Logger logger = Logger.getLogger(AccountController.class.getName());

	/**
	 * Autowired account DAO
	 */
	@Autowired
	private AccountDAO dao;

	/**
	 * @param account
	 * @return logic name of login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@ModelAttribute("user") Account account) {
		return "login";
	}

	/**
	 * @param model
	 * @param account
	 * @param session
	 * @return logic name of login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("user") Account account, HttpSession session) {
		// Log in and get account information
		Account result = dao.checkLogin(account.getUserName(), account.getPassword());
		// Check if user logged in successfully
		if (result != null) {
			// Set session attribute to account information
			session.setAttribute("accountInfo", result);
			// Redirect to home page
			return "redirect:/home.htm";
		} else {
			// Put error message to login page
			model.put("errorMessage", "Invalid username or password");
			return "login";
		}
	}

	/**
	 * @param account
	 * @return logic name of home page
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(@ModelAttribute("user") Account account) {
		return "home";
	}

	/**
	 * @param model
	 * @param account
	 * @param dateOfBirth
	 * @param session
	 * @return logic name of home page
	 */
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String updateInfo(ModelMap model, @ModelAttribute("user") Account account,
			@RequestParam("dateOfBirth") String dateOfBirth, HttpSession session) {
		// Convert string dateOfBirth to Date object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dob = new Date();
		try {
			dob = sdf.parse(dateOfBirth);
		} catch (ParseException e) {
			// Log parse error message
			logger.error("Inputted date cannot be parsed : " + dateOfBirth);
		}
		account.setDateOfBirth(dob);
		// Update account and get result
		boolean result = dao.updateInfo(account);
		// Check if update operation is successful
		if (result) {
			// Update session account attribute
			session.setAttribute("accountInfo", account);
			// Put success message to home page
			model.put("successMessage", "Your information has been updated!");
			// Redirect to home page
			return "home";
		} else {
			// Put error message to home page
			model.put("errorMessage", "Error in updating account! No update processed");
			// Redirect to home page
			return "home";
		}
	}

	/**
	 * @param session
	 * @return redirection to login page
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// Remove current session from the registry
		session.invalidate();
		// Redirect to login page
		return "redirect:/login.htm";
	}

}
