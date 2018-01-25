package com.feicuiedu.atm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.feicuiedu.atm.controller.FunctionController;
import com.feicuiedu.atm.controller.ServiceMain;
import com.feicuiedu.atm.entity.User;

/**
 * 普通用户登录判断
 * @author 王
 */
public class UserService {
	
	private FunctionController fc;
	private ServiceMain sm;
	
	List<String> list = new ArrayList<>();//存放文本账户信息
    List<Object> listObj = new ArrayList<>();//存放user信息(当前登录账户)

	/**
	 * 选择登录方式
	 */
	public void judgment(Scanner scan) {
		
		System.out.println("请选择您的登录方式1.银行卡账号 2.身份证号");
		String select = scan.next();
		
			//银行卡账号登录
		if("1".equals(select)) {
			accountLogin(scan);
			
			//身份证号登录
		} else if("2".equals(select)) {
			IdLogin(scan);
			
		}
	}
	
	/**
	 * 银行卡账号登录
	 */
	public void accountLogin(Scanner scan) {
		
		try{
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
//			List<String> list = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("没有任何账户,请先开户!");
				//返回登录界面
				sm = new ServiceMain();
				sm.runService();
			}
			
			System.out.println("请输入您的银行卡账号");
			String account = scan.next();
			
			if (!account.matches("[0-9]{21}")) {
				System.out.println("账号格式输入错误,请重新登录");
				accountLogin(scan);
			}
			
			System.out.println("请输入您的密码");
			String password = scan.next();
			
			if (!password.matches("^(?=.*[\\x00-\\xff].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}$")) {
				System.out.println("密码格式输入错误,请重新登录");
				accountLogin(scan);
			}
			
			//将字符串的内容写入到交易记录文本里
			BufferedWriter bwRecord = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"trade.data"),true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time = sdf.format(new Date());
			
	        User user = new User();
	        
			//遍历获取每一个用户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				
				//分割一个用户信息
				String[] aArray = str1.split("\\, ");
				
				//账号
				String zh = aArray[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user.setAccountNumber(zhs[1]);//"232"
				
				//密码
				String mm = aArray[1];
				String[] mms = mm.split("=");
				user.setPassword(mms[1]);
				
				//姓名
				String xm = aArray[2];
				String[] xms = xm.split("=");
				user.setUserName(xms[1]);
				
				//性别
				String xb = aArray[3];
				String[] xbs = xb.split("=");
				user.setGender(xbs[1]);
				
				//身份证号
				String sfzh = aArray[4];
				String[] sfzhs = sfzh.split("=");
				user.setIdNumber(sfzhs[1]);
				
				//学历
				String xl = aArray[5];
				String[] xls = xl.split("=");
				user.setEducation(xls[1]);
				
				//余额
				String ye = aArray[6];
				String[] yes = ye.split("=");
				user.setBalance(Double.parseDouble(yes[1]));
				
				//地址
				String dz = aArray[7];
				String[] dzs = dz.split("=");
				user.setAddress(dzs[1]);
				
				//判断用户输入的账号和密码
				if(zh.contains(account) && mm.contains(password)) {
					System.out.println("登录成功!");
					
					String record = "登录记录     账户:"+user.getAccountNumber()+"于"+time+"登录";
					bwRecord.write(record, 0, record.length());
					bwRecord.write("\r");
					
					//将该用户信息添加到list2中
					listObj.add(list.get(i));
					//删除list中登录的用户信息
					list.remove(i);
					
					//将listObj里的所有用户信息写入到文本中
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"loginUser.data")));
					for (int j = 0; j < listObj.size(); j++) {
						bw.write(listObj.get(j).toString());
						bw.write("\r");
					}
					
					//将list里的所有用户信息(不包含已登录用户信息)写入到文本中
					BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"anotherUser.data")));
					for (int j = 0; j < list.size(); j++) {
						bw2.write(list.get(j).toString());
						bw2.write("\r");
					}
					
					br.close();
					bwRecord.close();
					bw.close();
					bw2.close();
				
				//进行功能选择
				fc = new FunctionController();
				fc.userFunction(scan);
				} 
			}
			
			//判断用户输入的账号和身份证号是否同时存在
			System.out.println("银行账号和密码不匹配,请重新登录");
			accountLogin(scan);
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 身份证号登录
	 */
	public void IdLogin(Scanner scan) {
		
		try{
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
//			List<String> list = new ArrayList<>();
			
			//判断文本内容是否为空
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("没有任何账户,请先开户!");
				//返回登录界面
				sm = new ServiceMain();
				sm.runService();
			}
			
			System.out.println("请输入您的身份证号(8位数字):");
			String ID = scan.next();
			
			if (!ID.matches("[0-9]{8}")) {
				System.out.println("身份证号输入错误,请重新登录");
				IdLogin(scan);
			}
			System.out.println("请输入您的密码");
			String password = scan.next();
			
			if (!password.matches("^(?=.*[\\x00-\\xff].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}$")) {
				System.out.println("密码输入错误,请重新登录");
				IdLogin(scan);
			}
			
			//将字符串的内容写入到交易记录文本里
			BufferedWriter bwRecord = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"trade.data"),true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time = sdf.format(new Date());
	        
	        User user = new User();
	        
			//遍历获取每一个用户信息
			for(int i = 0; i < list.size(); i++) {
				
				String str1 = list.get(i);
				//分割一个用户信息
				String[] aArray = str1.split("\\, ");
				
				//账号
				String zh = aArray[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user.setAccountNumber(zhs[1]);//"232"
				
				//密码
				String mm = aArray[1];
				String[] mms = mm.split("=");
				user.setPassword(mms[1]);
				
				//姓名
				String xm = aArray[2];
				String[] xms = xm.split("=");
				user.setUserName(xms[1]);
				
				//性别
				String xb = aArray[3];
				String[] xbs = xb.split("=");
				user.setGender(xbs[1]);
				
				//身份证号
				String sfzh = aArray[4];
				String[] sfzhs = sfzh.split("=");
				user.setIdNumber(sfzhs[1]);
				
				//学历
				String xl = aArray[5];
				String[] xls = xl.split("=");
				user.setEducation(xls[1]);
				
				//余额
				String ye = aArray[6];
				String[] yes = ye.split("=");
				user.setBalance(Double.parseDouble(yes[1]));
				
				//地址
				String dz = aArray[7];
				String[] dzs = dz.split("=");
				user.setAddress(dzs[1]);
				
				//判断用户输入身份账户和密码
				if(mm.contains(password) && sfzh.contains(ID)) {
					System.out.println("登录成功!");
					
					String record = "登录记录     账户:"+user.getAccountNumber()+"于"+time+"登录";
					bwRecord.write(record, 0, record.length());
					bwRecord.write("\r");
					
					//将该用户信息添加到list2中
					listObj.add(list.get(i));
					//删除list中登录的用户信息
					list.remove(i);
					
					//将listObj里的所有用户信息写入到文本中
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"loginUser.data")));
					for (int j = 0; j < listObj.size(); j++) {
						bw.write(listObj.get(j).toString());
						bw.write("\r");
					}
					
					//将list里的所有用户信息写入到文本中
					BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"anotherUser.data")));
					for (int j = 0; j < list.size(); j++) {
						bw2.write(list.get(j).toString());
						bw2.write("\r");
					}
					
					bwRecord.close();
					bw.close();
					bw2.close();
					br.close();
					
					//进行功能选择
					fc = new FunctionController();
					fc.userFunction(scan);
				} 
			}
			
			//判断用户输入的账号和身份证号是否同时存在
			System.out.println("身份证号和密码不匹配,请重新登录");
			IdLogin(scan);
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
