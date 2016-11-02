package com.spring.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;

/**
 * @author HuanPM Implementation of account DAO
 */
public class AccountDAOImpl extends GenericDAOImpl<Integer, Account> implements AccountDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#checkLogin(java.lang.String,
	 * java.lang.String)
	 */
	public Account findByUserNameAndPassword(String username, String password) {
		// Get current session
		Session session = sessionFactory.getCurrentSession();
		// Query with criteria object
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("userName", username));
		criteria.add(Restrictions.eq("password", password));
		// Get result
		Account account = (Account) criteria.uniqueResult();
		// Return null if none of above conditions are met
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#updateInfo(com.spring.model.Account)
	 */
	public boolean updateInfo(Account account) {
		// Inject sensitive info (password, active) to arg account
		Account sensitiveInfo = this.findById(account.getId());
		account.setPassword(sensitiveInfo.getPassword());
		account.setActive(sensitiveInfo.isActive());
		return this.update(account);
	}

}
