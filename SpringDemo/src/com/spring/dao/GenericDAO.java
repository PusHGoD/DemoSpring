package com.spring.dao;

import java.io.Serializable;
import java.util.List;

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
	 * @return id K
	 */
	public K add(E e);

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
