package com.feicuiedu.atm.controller;

import java.util.Scanner;

import com.feicuiedu.atm.service.AdminService;
import com.feicuiedu.atm.service.UserService;

/**
 * 登录控制
 * @author 王
 */
public class LoginController {
	
	private AdminService as;
	private UserService us;
	private FunctionController fc;
	
	public void login(Scanner scan) {
		
		//接收用户输入的信息
		String select = scan.next();
		
		while(true) {
			
				//管理员
			if("1".equals(select)) {
				
				//登录判断
				as = new AdminService();
				as.judgment(scan);
				
				//进行功能选择
				fc = new FunctionController();
				fc.adminFunction(scan);
			
				//普通用户
			} else if("2".equals(select)) {
				
				//登录判断
				us = new UserService();
				us.judgment(scan);
				
				//进行功能选择
				fc = new FunctionController();
				fc.userFunction(scan);
				
				//退卡
			} else if("3".equals(select)) {
				System.exit(0);
				
				//其他重新输入
			} else {
				System.out.println("请按照提示重新输入!");
				continue;
			}
		}
	}
}
