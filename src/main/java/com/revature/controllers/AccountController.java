package com.revature.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.services.AccountService;
import com.revature.utils.ConnectionUtil;

public class AccountController {

	private static AccountService accountService = new AccountService();
	private static Scanner scan = new Scanner(System.in);
	
	public void accountMenu() {
		System.out.println("Are you a returning or new user? \n"
				+ "1) Sign up here! \n"
				+ "2) Returning user. \n"
				+ "3) return");
		
		String response = scan.nextLine();
		
		switch(response) {
			case "1": 
				try {
					addAccount();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				break;
			case "3":
				break;
		}
	}

	private void addAccount() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		
		ResultSet result = null;
		
		String username = "";
		
		System.out.println("What is your first name?");
		String firstName = scan.nextLine();
		System.out.println("What is your last name?");
		String lastName = scan.nextLine();
		
		while(true) {
			System.out.println("What will your username be? Must be atleast 6 characters");
			username = scan.nextLine();
			
			if(username.length() < 6) {
				while(username.length() < 6) {
					System.out.println("Try again. Not enough characters");
					username = scan.nextLine();
				}
			}
			
			result = statement.executeQuery("SELECT * FROM account WHERE username = '"+username+"'");
			if(result.next()) {
				System.out.println("Sorry, this username is being used already. Try another one");
			} else {
				break;
			}
		}
		
		System.out.println("What will your password be? Must be atleast 6 characters");
		String password = scan.nextLine();
		if(password.length() < 6) {
			while(password.length() < 6) {
				System.out.println("Try again. Not enough characters");
				password = scan.nextLine();
			}
		}
		
		Account account = new Account(username, password, firstName, lastName, 0.0);
		
		if(accountService.addAccount(account)) {
			System.out.println("Account was successfully created!");
		}else {
			 System.out.println("Something went wrong, please try again.");
			 //addProfile();
		}
	}
	
	public void showAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		
		for(Account a:accounts) {
			System.out.println(a);
		}
	}
	
	public void showOneAccount() {
		System.out.println("What account would you want to see?");
		 String respone = scan.nextLine();
		 Account account = accountService.getAccount(respone);
		  
		 if(account!=null) {
			 System.out.println(account);
			 
		 }else {
			 System.out.println("That is not a valid name, try again.");
			 showOneAccount();
		 }
	}
}
