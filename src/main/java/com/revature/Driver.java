package com.revature;

import java.security.AccessController;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.controllers.AccountController;
import com.revature.controllers.ProfileController;
import com.revature.models.Account;
import com.revature.models.Profile;

public class Driver {
	
	private static ProfileController profileController = new ProfileController();
	private static AccountController accountController = new AccountController();
	private static Account account = new Account();
	
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		
		
		accountController.startUpMenu();
	
	}
}
