package com.spring.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.spring.entity.Account;

/**
 * @author HuanPM Interface of account service
 */
@Service("accountSerivce")
public interface AccountService {

	/**
	 * @param username
	 * @param password
	 * @return key: return code and value: account information (if return code
	 *         is 1)
	 */
	public Entry<Integer, Account> checkLogin(String username, String password);

	/**
	 * @param input
	 * @param strDOB
	 *            strDOB: a string following dd/mm/yyyy format
	 * @return update result
	 * @throws ParseException
	 */
	public boolean updateAccountInfo(Account input);
	
	/**
	 * @return
	 */
	public List<Account> getAccountList();
	
	/**
	 * @param input
	 * @param from
	 * @param to
	 * @param passSize
	 * @return
	 */
	public boolean addNewAccount(Account input, String from, String to, int passSize);
	
	/**
	 * @param from
	 * @param to
	 * @param password
	 * @return
	 */
	public boolean resetPassword(Account input, String from, String to);
}
