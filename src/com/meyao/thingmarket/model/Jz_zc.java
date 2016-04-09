package com.meyao.thingmarket.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Jz_zc extends BmobObject {

	private Double money;
	private String type;
	private String account;
	private String account2;
	private String bus;
	private String time;
	private String bz;
	private String uid;

	private String temp;

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getAccount2() {
		return account2;
	}

	public void setAccount2(String account2) {
		this.account2 = account2;
	}

}
