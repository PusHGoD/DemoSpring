package com.spring.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updateInfo(Account account) {
		if (account != null) {
			// Inject hidden info (password, active) to arg account
			Account hiddenInfo = this.findById(account.getId());
			account.setPassword(hiddenInfo.getPassword());
			account.setRole(hiddenInfo.getRole());
			// Update account and return result
			return this.update(account);
		} else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#findAll()
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Account> findAll() {
		return this.find();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.impl.GenericDAOImpl#add(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean addAccount(Account account) {
		if (account != null) {
			return this.add(account);
		} else
			return false;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updatePassword(Account account, String password) {
		if (account != null && password != null) {
			Account hiddenInfo = this.findById(account.getId());
			hiddenInfo.setPassword(password);
			return this.update(hiddenInfo);
		} else
			return false;
	}
}
