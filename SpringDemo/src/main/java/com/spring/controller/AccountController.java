package com.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	 * A thread-safe method to store SimpleDateFormat
	 */
	private static final ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf;
		}
	};

	/**
	 * register property editors
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = tl.get();
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
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
			Entry<Integer, Account> result = service.checkLogin(account.getUserName(), account.getPassword());
			switch (result.getKey()) {
			case 0: {
				// Put error message to login page
				model.put("errorMessage", "Invalid username or password");
				// Redirect to login page
				return "wrong";
			}
			case 1: {
				Account info = result.getValue();
				// Set session attribute to account information
				session.setAttribute("accountInfo", info);
				switch (info.getRole().getId()) {
				case 1:
					return "redirect:/manager.htm";
				default:
					// Redirect to home page
					return "redirect:/home.htm";
				}
			}
			case 2: {
				// Put inactive account message to login page
				model.put("errorMessage",
						"Your account is currently inactive. Please contact support for more information.");
				// Redirect to login page
				return "login";
			}
			default: {
				// Put error message to login page
				model.put("errorMessage", "Unknown error has occurred.");
				// Log Hibernate error message
				logger.error("Unknown return code has returned" + result.getKey());
				break;
			}
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
	public String updateInfo(ModelMap model, @ModelAttribute("user") Account account, HttpSession session) {
		try {
			// Update account and get result
			boolean result = service.updateAccountInfo(account);
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
		} catch (HibernateException e) {
			// Put error message to home page
			model.put("errorMessage", "Error occurred in processing request!");
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		return "home";
	}

	/**
	 * @param account
	 * @return logic name of home page
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String getManagerPage(ModelMap model, @ModelAttribute("account") Account account) {
		// Redirect to home page
		return "manager";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody String loadAccountList() throws JsonProcessingException {
		// Get list
		List<Account> list = service.getAccountList();
		// Convert list into JSON type
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String addAccount(@RequestBody Account account) {
		try {
			// Add account and get result
			boolean result = service.addNewAccount(account, "minhhuan@test.com", account.getEmail(), 9);
			// Check if the operation is successful
			if (result) {
				// Return message
				return "You have added new user!";
			} else {
				// Return message
				return "Error in adding user!";
			}
		} catch (HibernateException e) {
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		// Return message
		return "Error occurred in DB processing";
	}

	/**
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public @ResponseBody String editAccount(@RequestBody Account account) {
		try {
			// Update account and get result
			boolean result = service.updateAccountInfo(account);
			// Check if update operation is successful
			if (result) {
				// Return message
				return "You have updated user " + account.getUserName() + "!";
			} else {
				// Return message
				return "Error in updating user!";
			}
		} catch (HibernateException e) {
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		// Return message
		return "Error occurred in DB processing";
	}

	/**
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public @ResponseBody String resetPassword(@RequestBody Account account) {
		try {
			// Reset password and get result
			boolean result = service.resetPassword(account, "minhhuan@test.com", account.getEmail());
			// Check if update operation is successful
			if (result) {
				// Return message
				return "You have resetted password of " + account.getUserName() + "!";
			} else {
				// Return message
				return "Error in resetting password!";
			}
		} catch (HibernateException e) {
			// Log Hibernate error message
			logger.error("Error in processing request in DB :" + e.getMessage());
		}
		// Return message
		return "Error occurred in DB processing";
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
