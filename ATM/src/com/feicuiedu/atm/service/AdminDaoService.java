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

import com.feicuiedu.atm.entity.User;

/**
 * 管理员操作信息判断
 * @author 王
 */
public class AdminDaoService {
//	private User user;
	User user = new User();
	List<Object> list = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	/**
	 * 用户名输入判断
	 */
	public void nameService() {
		
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
	}
	/**
	 * 密码输入判断
	 */
	public void passwordService() {
		
		while(true){
			System.out.println("请输入密码(8-16位,数字和字母必须同时有,至少一个大写字母):");
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
	}

	/**
	 * 身份证号输入判断
	 */
	public void idService() {
		
		while(true){
			System.out.println("请输入您的身份证号(8位数字):");
			String ID = scan.next();
				//判断是否是8位数字
			if(ID.matches("[0-9]{8}")){
				//判断是否存在
				try{
					//将文本内容输出到list集合中
					BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
					String str = null;
					List<String> list = new ArrayList<>();
					
					while ((str = br.readLine()) != null) {
						list.add(str);
					}
					
					//遍历获取每一个用户信息
					for(int i = 0; i < list.size(); i++) {
						String str1 = list.get(i);
						
						//分割一个用户信息
						String[] aArray = str1.split("\\, ");
						String a = aArray[4];//身份证号
						
						//判断用户输入的账号是否存在.存在就将该信息存放到list2中
						if(a.contains(ID)) {
							System.out.println("该身份账号已存在,请重新输入");
							idService();
						}
					}
					user.setIdNumber(ID);
					br.close();
				}catch(FileNotFoundException e){
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}
			} else {
				System.out.println("身份证号不是8位,请重新输入");
				continue;
			}
			break;
		}
	}
	/**
	 * 学历输入判断
	 */
	public void eduService() {
		
		while(true){
			System.out.println("请输入您的学历:(1为小学  2为中学  3为大学  4为其他)");
			String edu = scan.next();
			// 1为小学  2为中学  3为大学  4为其他
			if(edu.equals("1")){
				edu = "小学";
				user.setEducation(edu);
			} else if(edu.equals("2")){
				edu = "中学";
				user.setEducation(edu);
			} else if(edu.equals("3")){
				edu = "大学";
				user.setEducation(edu);
			} else if(edu.equals("4")){
				edu = "其它";
				user.setEducation(edu);
			} else {
				System.out.println("请按照提示重新输入");
				continue;
			}
			break;
		}
	}
	/**
	 * 联系地址输入判断
	 */
	public void addressService() {
		
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
	}
	/**
	 * 初始金额
	 */
	public void balanceService() {
		Double balance = 0.0;
		user.setBalance(balance);
	}
	/**
	 * 性别+生成账户
	 */
	public void accountService() {
		
		String gender;
		while(true){
			System.out.println("请输入您的性别(男输入1，女输入2):");
			gender = scan.next();
			//1为男       2为女
			if (gender.equals("1")){
				String gender1 = "男";
				user.setGender(gender1);
			} else if (gender.equals("2")){
				String gender1 = "女";
				user.setGender(gender1);
			} else {
				System.out.println("请按照提示重新输入");
				continue;
			}
			break;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(new Date());//获取系统当前时间,用作生成账户
        String account = "370"+ gender +time;//将需要的参数连接起来,组成账户
		user.setAccountNumber(account);
		
		System.out.println(user+"\r");
		list.add(user);
	}
	/**
	 * 读取集合信息到文本
	 */
	public void writeToTest() {
		try {
			//将list1写入到test.txt
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data"),true));	
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i).toString());
			}
			bw.write("\r");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取文本信息到集合
	 */
	public void writeToList(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			List<String> list = new ArrayList<>();
			
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取文本内容并打印出来
	 */
	public void print(){

		try {
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
