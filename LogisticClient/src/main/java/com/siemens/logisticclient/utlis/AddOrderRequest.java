package com.siemens.logisticclient.utlis;

/**
 * The class is used to send the request from the javascriptr page in a more
 * friendly format.
 * 
 * @author Anca Petrescu
 * 
 */
public class AddOrderRequest {

	private String orderId;
	private int nr_pack;
	private String productType;
	private String sender;
	private String receiver;
	private long arrivalTime;
	private String jobType;

	public AddOrderRequest() {
		super();
	}

	public AddOrderRequest(String orderId, int nr_pack, String productType,
			String sender, String receiver, long arrivalTime) {
		super();
		this.orderId = orderId;
		this.nr_pack = nr_pack;
		this.productType = productType;
		this.sender = sender;
		this.receiver = receiver;
		this.arrivalTime = arrivalTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getNr_pack() {
		return nr_pack;
	}

	public void setNr_pack(int nr_pack) {
		this.nr_pack = nr_pack;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
