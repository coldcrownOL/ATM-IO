package com.feicuiedu.atm.entity;

/**
 * 管理员属性
 * @author 王
 */
public class Admin {

	private String accountNumber = "123456";
	private String password = "123456";
	private String userName = "翡翠";
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}
	
	public Admin(String accountNumber, String password, String userName) {
		super();
		this.accountNumber = accountNumber;
		this.password = password;
		this.userName = userName;
	}

	public Admin() {
		super();
	}
}
