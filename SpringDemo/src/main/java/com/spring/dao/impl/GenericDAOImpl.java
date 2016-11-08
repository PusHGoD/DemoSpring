package com.spring.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.GenericDAO;

/**
 * @author HuanPM Implementation of generic DAO
 *
 * @param <K>
 *            key
 * @param <E>
 *            entity
 */
public class GenericDAOImpl<K extends Serializable, E> implements GenericDAO<K, E> {

	/**
	 * Autowired session factory
	 */
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * @return Class object of E
	 */
	private Class<E> getGenericType() {
		// Resolve type of arguments (K,E)
		Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(this.getClass(), GenericDAO.class);
		// Return Class object of E
		return (Class<E>) typeArgs[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#find()
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<E> find() {
		// Get current session
		Session session = sessionFactory.getCurrentSession();
		// Set up query
		Query<E> query = session.createQuery("FROM :tablename");
		query.setParameter("tablename", this.getGenericType().getName());
		// Get result from query
		List<E> list = query.getResultList();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#findById(java.io.Serializable)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public E findById(K k) {
		// Get current session
		Session session = sessionFactory.getCurrentSession();
		// Get result from session by key
		E result = session.get(this.getGenericType(), k);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#add(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public K add(E e) {
		// Get current session
		Session session = sessionFactory.getCurrentSession();
		// Save e to session
		K k = (K) session.save(e);
		return k;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#update(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(E e) {
		// If e is not null, continue updating e
		if (e != null) {
			// Get current session
			Session session = sessionFactory.getCurrentSession();
			// Update (or merge to existing) e to session
			session.merge(e);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean delete(E e) {
		// If e is not null, continue removing e
		if (e != null) {
			// Get current session
			Session session = sessionFactory.getCurrentSession();
			// Delete e from session
			session.delete(e);
			return true;
		}
		return false;
	}
}
