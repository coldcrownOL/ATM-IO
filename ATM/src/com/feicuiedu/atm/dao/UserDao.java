package com.feicuiedu.atm.dao;

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
import com.feicuiedu.atm.entity.User;

/**
 * 普通用户功能
 * @author 王
 */
public class UserDao {
	
	private User user;
	FunctionController fc = new FunctionController();
	
	/**
	 * 存款
	 */
	public void deposit(Scanner scan) {
		
		user = new User();
		
		try {
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"loginUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			//接收用户修改后的信息
			List<Object> list3 = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			//获取当前账户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				//分割用户信息
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
			}
			
			double money;
			while(true){
				System.out.println("您的余额为:"+user.getBalance());
				System.out.println("请输入存款金额(100元的倍数，不得为0):");
				
				double selectMoney = scan.nextDouble();

				//100整数，且不为0
				if(selectMoney%100 == 0 && selectMoney>0){
					money = selectMoney;
					break;	
					
				} else{
					System.out.println("请按照提示重新输入!");
					continue;
				}			
			}	
			
			//将字符串的内容写入到交易记录文本里
			BufferedWriter bwRecord = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"trade.data"),true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time = sdf.format(new Date());
	        
			while(true){
				
				System.out.println("1.确认");		
				System.out.println("2.重新输入");
				System.out.println("3.返回功能选择");
				
				String select=scan.next();
				
				//为1.确认存款
				if("1".equals(select)){
					
					String recordB = "存款记录     账户:"+user.getAccountNumber()+"存款前余额为:"+user.getBalance();
					bwRecord.write(recordB, 0, recordB.length());
					bwRecord.write("\r\n");
					
					user.setBalance(user.getBalance()+money);
					System.out.println("存款成功，现在的余额为:"+user.getBalance());
					
					String recordA = "存款记录     账户:"+user.getAccountNumber()+"存款后余额为:"+user.getBalance()+",存款时间:"+time;
					bwRecord.write(recordA, 0, recordA.length());
					bwRecord.write("\r\n");
					
					break;
					
				//为2.重新输入
				} else if("2".equals(select)){
					//存款方法
					deposit(scan);
					
				//为3.返回功能选择
				} else if("3".equals(select)){	
					fc.userFunction(scan);
								
				//其他重新输入
				} else {
					System.out.println("请按照提示请重新输入！");
					continue;
				}	
			}
			
			list3.add(user);
			//将集合的内容写入到文本loginUser.txt里,修改后的账户,方便查询
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"loginUser.data")));
			for (int i = 0; i < list3.size(); i++) {
				bw.write(list3.get(i).toString());
				bw.write("\r");
			}
			
			//将文本内容输出到list集合中
			BufferedReader br1 = new BufferedReader(new FileReader("resources"+File.separator+"anotherUser.data"));
			String str1 = null;
			
			//读取文本文件并放入list里
			List<String> list1 = new ArrayList<>();
			
			//判断文本内容不为空,就将文本内容写入到集合
			while((str1 = br1.readLine()) != null) {
				list1.add(str1);
			}
			
			for (int i = 0; i < list1.size(); i++) {
				list3.add(list1.get(i));
			}
			
			//将集合的内容写入到文本里
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data")));
			for (int i = 0; i < list3.size(); i++) {
				bw2.write(list3.get(i).toString());
				bw2.write("\r");
			}
			br.close();
			br1.close();
			bw.close();
			bw2.close();
			bwRecord.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回菜单
        fc.userFunction(scan);
		
	}
	
	/**
	 * 取款
	 */
	public void Withdrawal(Scanner scan) {
		
		user = new User();
		
		try {
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"loginUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			//接收用户修改后的信息
			List<Object> list3 = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			//获取当前账户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				//分割用户信息
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
			}

			double money;
			
			while(true){
				System.out.println("您的余额为:"+user.getBalance());
				System.out.println("请输入取款金额(100元的倍数，不得为0):");
				
				double selectMoney = scan.nextDouble();

				//取款金额不得大于余额
				if(selectMoney>user.getBalance()){
						System.out.println("取款金额不得大于余额，请重新输入");
						continue;	
						
				//100整数，且不为0
				} else if(selectMoney%100 == 0 && selectMoney>0){
					money = selectMoney;
					break;
					
				} else {
					System.out.println("请按照提示重新输入!");
					continue;
				}		
			}	
			
			//将字符串的内容写入到交易记录文本里
			BufferedWriter bwRecord = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"trade.data"),true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time = sdf.format(new Date());
	        
			while(true){
				
				System.out.println("1.确认");		
				System.out.println("2.重新输入");
				System.out.println("3.返回功能选择");
				
				String select=scan.next();
				
				//为1.确认取款
				if("1".equals(select)){
					
					String recordB = "取款记录     账户:"+user.getAccountNumber()+"取款前余额为:"+user.getBalance();
					bwRecord.write(recordB, 0, recordB.length());
					bwRecord.write("\r\n");
					
					user.setBalance(user.getBalance()-money);
					System.out.println("取款成功，现在的余额为:"+user.getBalance());
					
					String recordA = "取款记录     账户:"+user.getAccountNumber()+"取款后余额为:"+user.getBalance()+",取款时间:"+time;
					bwRecord.write(recordA, 0, recordA.length());
					bwRecord.write("\r\n");
					
					break;
					
				//为2.重新输入
				} else if("2".equals(select)){
					//取款方法
					Withdrawal(scan);
					
				//为3.返回功能选择
				} else if("3".equals(select)){	
					fc.userFunction(scan);
								
				//其他重新输入
				} else {
					System.out.println("请按照提示请重新输入！");
					continue;
				}	
			}
			
			list3.add(user);
			//将集合的内容写入到文本loginUser.txt里,修改后的账户,方便查询
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"loginUser.data")));
			for (int i = 0; i < list3.size(); i++) {
				bw.write(list3.get(i).toString());
				bw.write("\r");
			}
			
			//将文本内容输出到list集合中
			BufferedReader br1 = new BufferedReader(new FileReader("resources"+File.separator+"anotherUser.data"));
			String str1 = null;
			
			//读取文本文件并放入list里
			List<String> list1 = new ArrayList<>();
			
			//判断文本内容不为空,就将文本内容写入到集合
			while((str1 = br1.readLine()) != null) {
				list1.add(str1);
			}
			
			for (int i = 0; i < list1.size(); i++) {
				list3.add(list1.get(i));
			}
			
			//将集合的内容写入到文本里
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data")));
			for (int i = 0; i < list3.size(); i++) {
				bw2.write(list3.get(i).toString());
				bw2.write("\r");
			}
			br.close();
			br1.close();
			bw.close();
			bw2.close();
			bwRecord.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回菜单
        fc.userFunction(scan);
        
	}
	
	/**
	 * 转账
	 */
	public void transfer(Scanner scan) {
		String account;
		while(true) {
			System.out.println("请输入对方账户");
			account = scan.next();
			
			//判断账户格式
			if (account.matches("[0-9]{21}")) {
				break;
			} else {
				System.out.println("账户格式错误,请重新输入");
				continue;
			}
			
		}
		try {
			//判断账户是否存在
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"allUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			
			//如果list为空,先开户
			if(list.isEmpty()) {
				System.out.println("账户不存在,请重新输入!");
				transfer(scan);
			}
			
			//将文本内容输出到list2集合中
			BufferedReader br2 = new BufferedReader(new FileReader("resources"+File.separator+"loginUser.data"));
			String str2 = null;
			
			//读取文本文件并放入list里
			List<String> list2 = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str2 = br2.readLine()) != null) {
				list2.add(str2);
			}
			
			//存放被转账对象的信息
			List<String> listUser = new ArrayList<>();
			
			//获取当前账户信息
			for(int i = 0; i < list.size(); i++) {
				
				String str1 = list.get(i);
				//分割用户信息
				String[] aArray = str1.split("\\, ");
				String a = aArray[0];
				//判断用户输入的账号是否存在,如果存在放到集合里,继续判断是否为自己登录的账户
				if(a.contains(account)) {
					listUser.add(list.get(i));
					list.remove(i);
				}
				
			}
			
			//如果为空,账户不存在
			if(listUser.isEmpty()){
				System.out.println("您要转账的账户不存在,请重新输入!");
				transfer(scan);
			}
			
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list2.size(); j++) {
					//如果list集合包含list2(登录用户信息),就删除
					if(list.get(i).equals(list2.get(j))){
						list.remove(i);
					}
				}
			}
			
			if(listUser.equals(list2)){
				System.out.println("不能向自己转账,请重新输入!");
				transfer(scan);
			}
			
			User user1 = new User();
			List<Object> listUser1 = new ArrayList<>();
			
			//遍历获取被转账用户的信息
			for (int i = 0; i < listUser.size(); i++) {
				String incomeUser = listUser.get(i);
				//分割一个用户信息
				String[] aArray = incomeUser.split("\\, ");
				//账号
				String zh = aArray[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user1.setAccountNumber(zhs[1]);//"232"
				
				//密码
				String mm = aArray[1];
				String[] mms = mm.split("=");
				user1.setPassword(mms[1]);
				
				//姓名
				String xm = aArray[2];
				String[] xms = xm.split("=");
				user1.setUserName(xms[1]);
				
				//性别
				String xb = aArray[3];
				String[] xbs = xb.split("=");
				user1.setGender(xbs[1]);
				
				//身份证号
				String sfzh = aArray[4];
				String[] sfzhs = sfzh.split("=");
				user1.setIdNumber(sfzhs[1]);
				
				//学历
				String xl = aArray[5];
				String[] xls = xl.split("=");
				user1.setEducation(xls[1]);
				
				//余额
				String ye = aArray[6];
				String[] yes = ye.split("=");
				user1.setBalance(Double.parseDouble(yes[1]));
				
				//地址
				String dz = aArray[7];
				String[] dzs = dz.split("=");
				user1.setAddress(dzs[1]);
			}
			
			User user2 = new User();
			
			//遍历获取自己登录的用户信息
			for(int i = 0; i < list2.size(); i++) {
				String loginUser = list2.get(i);
				
				//分割一个用户信息
				String[] aArray2 = loginUser.split("\\, ");
				
				//账号
				String zh = aArray2[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user2.setAccountNumber(zhs[1]);//"232"
				
				//密码
				String mm = aArray2[1];
				String[] mms = mm.split("=");
				user2.setPassword(mms[1]);
				
				//姓名
				String xm = aArray2[2];
				String[] xms = xm.split("=");
				user2.setUserName(xms[1]);
				
				//性别
				String xb = aArray2[3];
				String[] xbs = xb.split("=");
				user2.setGender(xbs[1]);
				
				//身份证号
				String sfzh = aArray2[4];
				String[] sfzhs = sfzh.split("=");
				user2.setIdNumber(sfzhs[1]);
				
				//学历
				String xl = aArray2[5];
				String[] xls = xl.split("=");
				user2.setEducation(xls[1]);
				
				//余额
				String ye = aArray2[6];
				String[] yes = ye.split("=");
				user2.setBalance(Double.parseDouble(yes[1]));
				
				//地址
				String dz = aArray2[7];
				String[] dzs = dz.split("=");
				user2.setAddress(dzs[1]);
			}
			
			double money;
			while(true){
				
				System.out.println("您的余额为:"+user2.getBalance());
				System.out.println("请输入转账金额(100元的倍数，不得为0,输入0直接返回功能界面):");
				
				double selectMoney = scan.nextDouble();

				//转账金额不得大于余额
				if(selectMoney>user2.getBalance()){
						System.out.println("转账金额不得大于余额，请重新输入");
						continue;	
						
				//100整数，且不为0
				} else if(selectMoney%100 == 0 && selectMoney>0){
					money = selectMoney;
					break;
					
				} else if(selectMoney == 0) {
					//返回菜单
			        fc.userFunction(scan);
					
				} else {
					System.out.println("请按照提示重新输入!");
					continue;
				}		
			}	
			
			//将字符串的内容写入到交易记录文本里
			BufferedWriter bwRecord = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"trade.data"),true));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time = sdf.format(new Date());
	        
			while(true){
				
				System.out.println("1.确认");		
				System.out.println("2.重新输入");
				System.out.println("3.返回功能选择");
				
				String select=scan.next();
				
				//为1.确认转账
				if("1".equals(select)){
					
					String recordB = "转账记录     账户:"+user2.getAccountNumber()+"转账前余额为:"+user2.getBalance();
					bwRecord.write(recordB, 0, recordB.length());
					bwRecord.write("\r\n");
					
					user2.setBalance(user2.getBalance()-money);//转账后自己的余额
					user1.setBalance(user1.getBalance()+money);//转账后对方的余额
					
					System.out.println("转账成功，现在的余额为:"+user2.getBalance());
					
					String recordA = "转账记录     账户:"+user2.getAccountNumber()+"转账后余额为:"+user2.getBalance();
					bwRecord.write(recordA, 0, recordA.length());
					bwRecord.write("\r\n");
					
					String tradeU = "转账记录     账户:"+user2.getAccountNumber()+" 向 账户:"+user1.getAccountNumber()+"转账"+money+"元,转账时间:"+time;
					bwRecord.write(tradeU, 0, tradeU.length());
					bwRecord.write("\r\n");
					
					String tradeR = "转账记录     账户:"+user1.getAccountNumber()+"收到 账户:"+user2.getAccountNumber()+"的转账"+money+"元,收账时间:"+time;
					bwRecord.write(tradeR, 0, tradeR.length());
					bwRecord.write("\r\n");
					
					break;
					
				//为2.重新输入
				} else if("2".equals(select)){
					//转账方法
					transfer(scan);
					
				//为3.返回功能选择
				} else if("3".equals(select)){	
					fc.userFunction(scan);
								
				//其他重新输入
				} else {
					System.out.println("请按照提示请重新输入！");
					continue;
				}	
			}
			
			listUser1.add(user2);//转出的用户
			
			//将集合的内容写入到文本loginUser.txt里,修改后的账户,方便查询
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"loginUser.data")));
			for (int i = 0; i < listUser1.size(); i++) {
				bw.write(listUser1.get(i).toString());
				bw.write("\r");
			}
			
			listUser1.add(user1);//接收转账的用户
			
			//将list里的内容写入到listUser1
			for (int i = 0; i < list.size(); i++) {
				listUser1.add(list.get(i));
			}
			
			//将集合的内容写入到文本里
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("resources"+File.separator+"allUser.data")));
			for (int i = 0; i < listUser1.size(); i++) {
				bw2.write(listUser1.get(i).toString());
				bw2.write("\r");
			}
			
			br.close();
			br2.close();
			bw.close();
			bw2.close();
			bwRecord.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回菜单
        fc.userFunction(scan);
        
	}
	
	/**
	 * 查询
	 */
	public void inquire(Scanner scan) {
		
		user = new User();
		
		try {
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"loginUser.data"));
			String str = null;
			
			//读取文本文件并放入list里
			List<String> list = new ArrayList<>();
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				list.add(str);
			}
			//获取当前账户信息
			for(int i = 0; i < list.size(); i++) {
				String str1 = list.get(i);
				//分割用户信息
				String[] aArray = str1.split("\\, ");
				
				//账号
				String zh = aArray[0];//"账号=23232"
				String[] zhs = zh.split("=");//"账号   23232"
				user.setAccountNumber(zhs[1]);//"232"
				
				//姓名
				String xm = aArray[2];
				String[] xms = xm.split("=");
				user.setUserName(xms[1]);
				
				//余额
				String ye = aArray[6];
				String[] yes = ye.split("=");
				user.setBalance(Double.parseDouble(yes[1]));
			}
			br.close();
			
			System.out.println("账户卡号:"+user.getAccountNumber());
			System.out.println("账户所有人:"+user.getUserName());
			System.out.println("余额:"+user.getBalance());
			System.out.println("查询成功!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回菜单
        fc.userFunction(scan);
        
	}
	
	/**
	 * 流水交易
	 */
	public void recording(Scanner scan) {
		
		try {
			//将文本内容输出到list集合中
			BufferedReader br = new BufferedReader(new FileReader("resources"+File.separator+"trade.data"));
			String str = null;
			System.out.println("流水记录:");
			
			//判断文本内容不为空.写入到集合
			while((str = br.readLine()) != null) {
				System.out.println(str);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回菜单
        fc.userFunction(scan);
	}
}
