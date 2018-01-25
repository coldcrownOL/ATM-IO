package com.feicuiedu.atm.entity;

/**
 * 普通用户属性
 * @author 王
 */
public class User {

	private String accountNumber;//账户
	private String password;//账户密码
	private String userName;//姓名
	private String gender;//性别
	private String idNumber;//身份证号
	private String education;//学历
	private Double balance;//余额
	private String address;//地址
	
	public User() {
		super();
	}
	
//	//销户判断
//	public User(String accountNumber, String idNumber) {
//		super();
//		this.accountNumber = accountNumber;
//		this.idNumber = idNumber;
//	}
//	
//	//修改账号信息
//	public User(String accountNumber) {
//		super();
//		this.accountNumber = accountNumber;
//	}
	//账号注册
	public User(String accountNumber, String password, String userName, String gender, String idNumber, String education,
			Double balance, String address) {
		super();
		this.accountNumber = accountNumber;
		this.password = password;
		this.userName = userName;
		this.gender = gender;
		this.idNumber = idNumber;
		this.education = education;
		this.balance = balance;
		this.address = address;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "账号=" + accountNumber + ", 密码=" + password + ", 姓名=" + userName + ", 性别="
				+ gender + ", 身份证号=" + idNumber + ", 学历=" + education + ", 余额=" + balance + ", 地址="
				+ address;
	}
	
}
