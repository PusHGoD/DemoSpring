package com.spring.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;
import com.spring.entity.Role;
import com.spring.mail.CustomMailHandler;
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

	private CustomMailHandler mail = new CustomMailHandler();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.service.AccountService#checkLogin(java.lang.String,
	 * java.lang.String)
	 */
	public Entry<Integer, Account> checkLogin(String username, String password) {
		Entry<Integer, Account> result = null;
		// Call to DAO and get account information
		Account accountInfo = dao.findByUserNameAndPassword(username, encryptMD5(password));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.service.AccountService#getAccountList()
	 */
	public List<Account> getAccountList() {
		return dao.findAll();
	}

	/**
	 * @param size
	 * @return randomized password
	 */
	public String randomPassword(int size) {
		Random random = new Random();
		String pass = "";
		String randomValue = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		for (int i = 0; i < size; i++) {
			int randomNum = random.nextInt(randomValue.length());
			pass += randomValue.charAt(randomNum);
		}
		return pass;
	}

	/**
	 * @param input
	 * @return MD5 encrypted string (32 characters)
	 */
	public String encryptMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.service.AccountService#addNewAccount(com.spring.entity.
	 * Account, java.lang.String)
	 */
	public boolean addNewAccount(Account input, String from, String to, int passSize) {
		if (input != null) {
			String enpass = randomPassword(passSize);
			input.setPassword(encryptMD5(enpass));
			Role role = new Role();
			role.setId(2);
			input.setRole(role);
			mail.sendAddMail(from, to, input.getUserName(), enpass);
			return dao.addAccount(input);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.service.AccountService#resetPassword(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public boolean resetPassword(Account input, String from, String to) {

		if (input != null && from != null && to != null) {
			String password = randomPassword(9);
			mail.sendResetMail(from, to, password);
			return dao.updatePassword(input, encryptMD5(password));
		}
		return false;
	}
}
