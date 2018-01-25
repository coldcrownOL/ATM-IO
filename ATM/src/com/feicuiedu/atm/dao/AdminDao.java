package com.feicuiedu.atm.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.feicuiedu.atm.controller.FunctionController;
import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.service.AdminDaoService;

/**
 * 管理员功能
 * @author 王
 */
public class AdminDao{
	
	private User user;
	private AdminDaoService ads;

	FunctionController fc = new FunctionController();
	
	/**
	 * 开户
	 */
	public void openAccount(Scanner scan) {
		
		ads = new AdminDaoService();
		
		ads.nameService();//接收用户姓名
		ads.passwordService();//接收用户密码
		ads.idService();//接收用户身份证号
		ads.eduService();//接收用户学历
		ads.addressService();//接收用户地址
		ads.balanceService();//接收用户余额
		ads.accountService();//接收用户账户+接收用户性别
		ads.writeToTest();//将list写入到文本中
		
        System.out.println("账号开户成功!");
        
        //返回菜单
        fc.adminFunction(scan);
	}
	/**
	 * 销户
	 */
	public void salesAccount(Scanner scan) {

		try{
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			
			//判断文本内容不为空,就添加到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("没有任何账户,请先开户!");
				//返回管理员菜单
				fc = new FunctionController();
				fc.adminFunction(scan);
			}
			
			System.out.println("请输入您要删除的账号:");
			String account = scan.next();
			
			if (!(account.matches("[0-9]{21}"))) {
				System.out.println("账号格式输入错误,请重新开始输入");
				salesAccount(scan);
			}
			
			System.out.println("请输入您要删除的身份证号:");
			String ID = scan.next();
			
			if (!(ID.matches("[0-9]{8}"))) {
				System.out.println("身份证号格式输入错误,请重新开始输入");
				salesAccount(scan);
			}
			
			//将被删除的用户信息放到list2里
			List<String> list2 = new ArrayList<>();
			
			//遍历获取每一个用户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				
				//分割一个用户信息
				String[] aArray = str1.split("\\, ");
				String a = aArray[0];//账号
				String b = aArray[4];//身份证号
				
				//判断用户输入的账号和身份证号是否同时存在,存在则删除该用户信息
				if(a.contains(account) && b.contains(ID)) {
					list2.add(list.get(i));
					System.out.println("删除账户:"+list.get(i));
					list.remove(i);
				}

			}
			
			if(list2.isEmpty()) {
				System.out.println("账户不存在.请重新开始");
				salesAccount(scan);
			}
			
			//将删除后的集合写入到文本中
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data")));	
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i));
				bw.write("\r");
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("删除成功!");
		
		//返回菜单
		fc.adminFunction(scan);
		//删除该账户信息   同时删除账号的金钱交易信息
	}
	
	/**
	 * 显示所有普通用户信息
	 */
	public void showAll(Scanner scan) {

		try{
			//读取文本内容并打印出来
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			
			//判断文本内容是否为空
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("没有任何账户,请先开户!");
				//返回管理员菜单
				fc = new FunctionController();
				fc.adminFunction(scan);
				
			//如果list不为空,遍历集合,获取所有信息
			} else {
				//循环打印所有用户信息
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
				}
			}
			
			br.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("查询成功!");
		
		//返回菜单
        fc.adminFunction(scan);
	}
	/**
	 * 修改普通用户信息
	 */
	public void modify(Scanner scan) {
		
		user = new User();
		
		try{
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			
			//判断文本内容是否为空
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("没有任何账户,请先开户!");
				//返回管理员菜单
				fc = new FunctionController();
				fc.adminFunction(scan);
			}
			
			System.out.println("请输入您要修改的账号");
			String account = scan.next();
			
			if (!account.matches("[0-9]{21}")) {
				System.out.println("账号格式输入错误,请重新开始输入");
				modify(scan);
			}
			
			//获取要修改的账户信息
			List<String> list2 = new ArrayList<>();
			
			//遍历获取每一个用户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				
				//分割一个用户信息
				String[] aArray = str1.split("\\, ");
				String a = aArray[0];//账号
				
				//判断用户输入的账号是否存在.存在就将该信息存放到list2中,并删除list里面的该用户信息
				if(a.contains(account)) {
					System.out.println("该账户信息为:"+list.get(i));
					list2.add(list.get(i));
					list.remove(i);
				}
			}
			//存放修改后的所有信息
			List<Object> list3 = new ArrayList<>();
			
			//账户,性别,身份证号,余额 不可修改
			for(int j = 0; j < list2.size(); j++) {
				String str2 = list2.get(j);
				//分割一个用户信息
				String[] aArray = str2.split("\\, ");
				
				//账号
				String zh = aArray[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user.setAccountNumber(zhs[1]);//"232"
				
				//性别
				String xb = aArray[3];
				String[] xbs = xb.split("=");
				user.setGender(xbs[1]);
				
				//身份证号
				String sfzh = aArray[4];
				String[] sfzhs = sfzh.split("=");
				user.setIdNumber(sfzhs[1]);
				
				//余额
				String ye = aArray[6];
				String[] yes = ye.split("=");
				user.setBalance(Double.parseDouble(yes[1]));
				
				//接收用户姓名
//				String name;
				while(true){
					System.out.println("请输入您的姓名(长度不得大于10):");
					String name = scan.next();
					//长度小于10
					if(name.length()<10 && name.length()>0){
						user.setUserName(name);
					} else {
						System.out.println("请按照提示重新输入!");
						continue;
					}
					break;
				}
				
				//接收用户密码
//				String password2;
				while(true){
					System.out.println("请输入密码(至少8位,数字和字母必须同时有,至少一个大写字母):");
					String password1 = scan.next();
					//判断 至少8位   数字和字母   至少一个大写字母
					if (!password1.matches("^(?=.*[\\x00-\\xff].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}$")) {
						continue;
					} 
					System.out.println("请再次输入您的密码:");
					String password2 = scan.next();
					//判断 至少8位   数字和字母   至少一个大写字母
					if (!password1.matches("^(?=.*[\\x00-\\xff].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}$")) {
						continue;
					} 
					//判断是否相同
					if(password2.equals(password1)){
						user.setPassword(password2);
					} 
					else {
						System.out.println("密码不相同，请重新输入密码");
						continue;
					}
					break;
				}
				//接收用户学历
//				String edu;
				while(true){
					System.out.println("请输入您的学历:(1为小学  2为中学  3为大学  4为其他)");
					String edu = scan.next();
					// 1为小学  2为中学  3为大学  4为其他
					if("1".equals(edu)){
						edu = "小学";
						user.setEducation(edu);
					} else if("2".equals(edu)){
						edu = "中学";
						user.setEducation(edu);
					} else if("3".equals(edu)){
						edu = "大学";
						user.setEducation(edu);
					} else if("4".equals(edu)){
						edu = "其它";
						user.setEducation(edu);
					} else {
						System.out.println("请按照提示重新输入");
						continue;
					}
					break;
				}
				//接收用户联系地址
//				String address;
				while(true){
					System.out.println("请输入您的联系地址(不得超过50):");
					String address = scan.next();
					//长度小于50
					if(address.length()<50 && address.length()>0){
						user.setAddress(address);
					} else {
						System.out.println("请按照提示重新输入");
						continue;
					}
					break;
				}
				
				//将修改后的用户信息添加到list3中
				list3.add(user);
			}
			
			//遍历list,将list里剩余的信息全部写入到list3中
			for (int i = 0; i < list.size(); i++) {
				list3.add(list.get(i));
			}
			
			//将list3里的所有用户信息写入到文本中
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data")));	
			for (int i = 0; i < list3.size(); i++) {
				bw.write(list3.get(i).toString());
				bw.write("\r");
				
			}
			System.out.println("修改成功!");
			br.close();
			bw.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//返回菜单
        fc.adminFunction(scan);
	}

}
