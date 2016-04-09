package com.meyao.thingmarket.model;

import cn.bmob.v3.BmobObject;

public class Account extends BmobObject {

	private String name;
	private Double money;
	private String uid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
