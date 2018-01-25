package com.feicuiedu.atm.service;

import java.util.Scanner;

import com.feicuiedu.atm.controller.FunctionController;
import com.feicuiedu.atm.entity.Admin;

/**
 * 管理员登录判断
 * @author 王
 */
public class AdminService {
	
	private Admin admin;
	private FunctionController fc;
	
	/**
	 * 管理员登录判断
	 */
	public void judgment(Scanner scan) {
		while(true) {
			System.out.println("请输入管理员账户(123456):");
			String str = scan.next();
			System.out.println("请输入账户密码(123456):");
			String str1 = scan.next();
			
			admin = new Admin();
			//判断账号和密码
			if(str.equals(admin.getAccountNumber())&&str1.equals(admin.getPassword())) {
				
				//进行功能选择
				fc = new FunctionController();
				fc.adminFunction(scan);
				
			} else {
				System.out.println("用户错误,请重新输入");
				continue;
			}
		}
	}
}
