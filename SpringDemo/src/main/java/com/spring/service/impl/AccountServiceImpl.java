package com.spring.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.service.AccountService;

/**
 * @author HuanPM Implementation of account service
 */
@Service("accountSerivce")
public class AccountServiceImpl implements AccountService {

	/**
	 * Autowired account DAO
	 */
	@Autowired
	private AccountDAO dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.service.AccountService#checkLogin(java.lang.String,
	 * java.lang.String)
	 */
	public Entry<Integer, Account> checkLogin(String username, String password) {
		Entry<Integer, Account> result = null;
		// Call to DAO and get account information
		Account accountInfo = dao.findByUserNameAndPassword(username, password);
		// If result is null, return null
		if (accountInfo != null) {
			// If account is inactive, return empty result
			if (!accountInfo.isActive()) {
				result = new AbstractMap.SimpleEntry<Integer, Account>(2, null);
			} else {
				// Return account info
				result = new AbstractMap.SimpleEntry<Integer, Account>(1, accountInfo);
			}
		} else {
			result = new AbstractMap.SimpleEntry<Integer, Account>(0, null);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.spring.service.AccountService#updateAccountInfo(com.spring.entity.
	 * Account, java.lang.String)
	 */
	public boolean updateAccountInfo(Account input) {
		if (input != null) {
			// Update and return result
			return dao.updateInfo(input);
		}
		return false;
	}
}
