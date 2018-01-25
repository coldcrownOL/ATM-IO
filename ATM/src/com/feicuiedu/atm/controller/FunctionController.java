package com.feicuiedu.atm.controller;

import java.util.Scanner;

import com.feicuiedu.atm.dao.AdminDao;
import com.feicuiedu.atm.dao.UserDao;
import com.feicuiedu.atm.view.AdminView;
import com.feicuiedu.atm.view.UserView;

/**
 * 功能选择
 * @author 王
 */
public class FunctionController {

	private AdminView adminView;
	private UserView userView;
	private UserDao userDao;
	private AdminDao adminDao;
	
	ServiceMain service = new ServiceMain();
	
	/**
	 * 管理员功能选择
	 */
	public void adminFunction(Scanner scan) {
		
		adminDao = new AdminDao();
		
			//显示功能界面
		adminView = new AdminView();
		adminView.showView();
		
			//接收用户输入
		String select = scan.next();
			
			//开户
		if("1".equals(select)) {
			adminDao.openAccount(scan);
			
			//销户
		} else if("2".equals(select)) {
			adminDao.salesAccount(scan);
			
			//显示所有账户信息
		} else if("3".equals(select)) {
			adminDao.showAll(scan);
			
			//修改账户信息
		} else if("4".equals(select)) {
			adminDao.modify(scan);
			
			//返回上一级
		}else if("5".equals(select)) {
			service.runService();
			
			//退卡
		} else if("6".equals(select)) {
			System.exit(0);
			
			//重新输入
		} else {
			System.out.println("请按照提示重新输入!");
			adminFunction(scan);
		}
	}
	
	/**
	 * 普通用户功能选择
	 */
	public void userFunction(Scanner scan) {
		
		userDao = new UserDao();
		
			//显示功能界面
		userView = new UserView();
		userView.showView();
		
			//接收用户输入
		String select = scan.next();
			
			//存款
		if("1".equals(select)) {
			userDao.deposit(scan);
			
			//取款
		} else if("2".equals(select)) {
			userDao.Withdrawal(scan);
			
			//转账
		} else if("3".equals(select)) {
			userDao.transfer(scan);
			
			//查询
		} else if("4".equals(select)) {
			userDao.inquire(scan);
			
			//交易流水
		}else if("5".equals(select)) {
			userDao.recording(scan);
			
			//返回上一级
		}else if("6".equals(select)) {
			service.runService();
			
			//退卡
		} else if("7".equals(select)) {
			System.exit(0);
			
			//重新输入
		} else {
			System.out.println("请按照提示重新输入!");
			userFunction(scan);
		}
	}
}
