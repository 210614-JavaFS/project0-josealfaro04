package com.revature;

import java.security.AccessController;
import java.util.Scanner;

import com.revature.controllers.AccountController;
import com.revature.controllers.ProfileController;
import com.revature.models.Profile;

public class Driver {
	
	private static ProfileController profileController = new ProfileController();
	private static AccountController accountController = new AccountController();

	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome! \n"
				+ "What type of level entry are you? \n"
				+ "1) Customer \n"
				+ "2) Employee \n"
				+ "3) Admin \n"
				+ "4) Quit");
		String response = scan.nextLine();
		
		switch(response) {
			case "1":
				accountController.accountMenu();
				profileController.profileMenu();
				break;
			case "2":
				//TODO
				break;
			case "3":
				//TODO
				break;
			case "4":
				System.out.println("thank you so much!");
				break;
			default:
				System.out.println("Not a valid input, please try again.");
				
		}
		
		
		//profileController.showAllProfiles();

	}

}
