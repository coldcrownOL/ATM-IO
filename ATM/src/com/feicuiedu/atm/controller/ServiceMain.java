package com.feicuiedu.atm.controller;

import java.util.Scanner;

import com.feicuiedu.atm.view.LoginView;

/**
 * 运行
 * @author 王
 */
public class ServiceMain {
	
	public static void main(String[] args) {
		
		ServiceMain sm = new ServiceMain();
		sm.runService();
	}
	
	public void runService() {
		
		Scanner scan = new Scanner(System.in);
		
		//显示登录界面
		LoginView loginView = new LoginView();
		loginView.showView();
		
		//选择登录方式 1.管理员 2.普通用户
		LoginController lc = new LoginController();
		lc.login(scan);
		
	}
}
