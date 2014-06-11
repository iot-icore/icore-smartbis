package com.siemens.ct.ro.transportation.entities;


/**
 * Order entity.
 * 
 * @author Anca Petrescu
 * 
 */
public class Order {

	private long id;
	private String orderId;

	public Order() {
		super();
	}

	public Order(String orderId) {
		super();
		this.orderId = orderId;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
