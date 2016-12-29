package com.spring.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuanPM Interface of generic DAO (Data Access Object)
 *
 * @param <K>
 *            key
 * @param <E>
 *            entity
 */
public interface GenericDAO<K extends Serializable, E> {

	/**
	 * @return list of E
	 */
	public List<E> find();

	/**
	 * @param k
	 * @return object E
	 */
	public E findById(K k);

	/**
	 * @param e
	 * @return add result
	 */
	public boolean add(E e);

	/**
	 * @param e
	 * @return update result
	 */
	public boolean update(E e);

	/**
	 * @param e
	 * @return delete result
	 */
	public boolean delete(E e);
}
