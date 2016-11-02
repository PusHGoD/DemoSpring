package com.spring.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.entity.Account;
import com.spring.service.AccountService;

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
	 * Autowired account service
	 */
	@Autowired
	private AccountService service;

	/**
	 * @param account
	 * @return logic name of login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@ModelAttribute("user") Account account) {
		// Redirect to login page
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
		try {
			// Log in and get account information
			Account result = service.checkLogin(account.getUserName(), account.getPassword());
			// Check if user logged in successfully
			if (result != null) {
				if (result.getFirstName() == null && result.getLastName() == null && result.getDateOfBirth() == null) {
					session.setAttribute("inactiveAccountName", account.getUserName());
					return "redirect:/inactive.htm";
				} else {
					// Set session attribute to account information
					session.setAttribute("accountInfo", result);
					// Redirect to home page
					return "redirect:/home.htm";
				}
			} else {
				// Put error message to login page
				model.put("errorMessage", "Invalid username or password");
				// Redirect to login page
				return "login";
			}
		} catch (HibernateException e) {
			// Put error message to login page
			model.put("errorMessage", "Error occurred in processing request!");
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		return "login";
	}

	/**
	 * @param account
	 * @return logic name of home page
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(@ModelAttribute("user") Account account) {
		// Redirect to home page
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
			@RequestParam("dateOfBirth") String strDOB, HttpSession session) {
		try {
			// Update account and get result
			boolean result = service.updateAccountInfo(account, strDOB);
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
		} catch (ParseException e) {
			// Put error message to home page
			model.put("errorMessage", "Error occurred in parsing date!");
			// Log parse error message
			logger.error("Inputted date cannot be parsed : " + strDOB);
		} catch (HibernateException e) {
			// Put error message to home page
			model.put("errorMessage", "Error occurred in processing request!");
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		return "home";
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

	/**
	 * @return redirection to inactive page
	 */
	@RequestMapping(value = "/inactive")
	public String getInactivePage() {
		// Redirect to inactive page
		return "inactive";
	}
}
