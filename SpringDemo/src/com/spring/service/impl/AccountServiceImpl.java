package com.spring.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.service.AccountService;

@Service("accountSerivce")
@Transactional
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
	public Account checkLogin(String username, String password) {
		Account result = dao.findByUserNameAndPassword(username, password);
		if (result != null) {
			if (!result.isActive()) {
				return new Account();
			}
			return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.spring.service.AccountService#updateAccountInfo(com.spring.entity.
	 * Account, java.lang.String)
	 */
	public boolean updateAccountInfo(Account account, String strDOB) throws ParseException {
		if (account != null) {
			// Convert string date of birth to Date object
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dob = new Date();
			dob = sdf.parse(strDOB);
			// Set date into account info
			account.setDateOfBirth(dob);
			// Update and return result
			return dao.updateInfo(account);
		}
		return false;
	}
}
