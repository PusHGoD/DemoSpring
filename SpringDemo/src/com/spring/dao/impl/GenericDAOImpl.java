package com.spring.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import com.spring.dao.GenericDAO;

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
		Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(this.getClass(), GenericDAO.class);
		return (Class<E>) typeArgs[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#find()
	 */
	public List<E> find() {
		List<E> list = null;
		Session session = sessionFactory.getCurrentSession();
		Query<E> query = session.createQuery("FROM " + this.getGenericType().getName());
		list = query.getResultList();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#findById(java.io.Serializable)
	 */
	public E findById(K k) {
		Session session = sessionFactory.getCurrentSession();
		E result = session.get(this.getGenericType(), k);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#add(java.lang.Object)
	 */
	public K add(E e) {
		Session session = sessionFactory.getCurrentSession();
		K k = (K) session.save(e);
		return k;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.GenericDAO#update(java.lang.Object)
	 */
	public boolean update(E e) {
		if (e != null) {
			Session session = sessionFactory.getCurrentSession();
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
	public boolean delete(E e) {
		if (e != null) {
			Session session = sessionFactory.getCurrentSession();
			session.delete(e);
			return true;
		}
		return false;
	}
}
