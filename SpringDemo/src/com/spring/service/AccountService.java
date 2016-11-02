package com.spring.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Account;

/**
 * @author HuanPM Interface of account service
 */
@Service("accountSerivce")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface AccountService {

	/**
	 * @param username
	 * @param password
	 * @return account information
	 */
	public Account checkLogin(String username, String password);

	/**
	 * @param account
	 * @param strDOB
	 *            strDOB: a string following dd/mm/yyyy format
	 * @return update result
	 * @throws ParseException
	 */
	public boolean updateAccountInfo(Account account, String strDOB) throws ParseException;
}
