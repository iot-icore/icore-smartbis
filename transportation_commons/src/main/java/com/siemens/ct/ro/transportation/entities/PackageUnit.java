package com.siemens.ct.ro.transportation.entities;

/**
 * Package unit Entity.
 * 
 * @author Anca Petrescu
 * 
 */
public class PackageUnit {

	private long id;
	private String packageId;
	private String productType;
	private String sender;
	private String receiver;
	private long arrivalTime;
	private long startTime;
	// specifies if the packageUnit is bound or not.
	private boolean bound;
	private String orderId;

	public PackageUnit() {
		super();
	}

	
	public PackageUnit(String packageId, String productType, String sender,
			String receiver, long arrivalTime, long startTime, boolean bound,
			String orderId) {
		super();
		this.packageId = packageId;
		this.productType = productType;
		this.sender = sender;
		this.receiver = receiver;
		this.arrivalTime = arrivalTime;
		this.startTime = startTime;
		this.bound = bound;
		this.orderId = orderId;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
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

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isBound() {
		return bound;
	}

	public void setBound(boolean bound) {
		this.bound = bound;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	


}
