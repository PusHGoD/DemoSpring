package com.spring.dao.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.dao.AccountDAO;
import com.spring.entity.Account;

/**
 * @author HuanPM Implementation of account DAO
 */
@SuppressWarnings("serial")
public class AccountDAOImpl implements AccountDAO, Serializable {

	/**
	 * Autowired session factory
	 */
	@Autowired
	SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#checkLogin(java.lang.String,
	 * java.lang.String)
	 */
	public Account checkLogin(String username, String password) {
		// Account account = null;
		// try {
		// Session session = sessionFactory.openSession();
		// Query query = session.createQuery("FROM Account as a WHERE
		// a.userName = :userName and a.password = :password");
		// query.setParameter("userName", username);
		// query.setParameter("password", password);
		// account = (Account) query.getSingleResult();
		// } catch (NoResultException e) {
		// account = null;
		// }

		// Open session
		Session session = sessionFactory.openSession();
		// Query with criteria object
		Criteria criteria = session.createCriteria(Account.class.getName());
		criteria.add(Restrictions.eq("userName", username));
		criteria.add(Restrictions.eq("password", password));
		// Get result
		Account account = (Account) criteria.uniqueResult();
		// Close session
		session.close();
		// Return null if none of above conditions are met
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#updateInfo(com.spring.model.Account)
	 */
	public boolean updateInfo(Account account) {
		// Open session
		Session session = sessionFactory.openSession();
		// Begin transaction
		session.beginTransaction();
		// Set up HQL query
		Query query = session.createQuery(
				"UPDATE Account as a SET firstName=:firstName, lastName = :lastName, dateOfBirth=:dateOfBirth WHERE id=:id");
		query.setParameter("firstName", account.getFirstName());
		query.setParameter("lastName", account.getLastName());
		query.setParameter("dateOfBirth", account.getDateOfBirth());
		query.setParameter("id", account.getId());
		// Update account info
		int result = query.executeUpdate();
		// Commit to DB
		session.getTransaction().commit();
		// Close session
		session.close();
		// Return true if there is a affected row
		if (result > 0) {
			return true;
		}
		// Return false if none of above conditions are met
		return false;
	}
}
