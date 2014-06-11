package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.Order;

/**
 * Order DAO interface.
 * 
 * @author anca.petrescu
 * 
 */
public interface OrderDao {
	public Order add(Order order);

	public Order get(String id);

	public List<Order> getAll();

	public boolean delete(String id);
	

}
