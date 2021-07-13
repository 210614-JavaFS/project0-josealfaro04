 package com.revature.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.revature.models.Profile;
import com.revature.repos.ProfileDAOImpl;
import com.revature.services.ProfileService;
import com.revature.utils.ConnectionUtil;

public class ProfileController {
	
	private static ProfileService profileService = new ProfileService();
	private static ProfileDAOImpl profileDaoImpl = new ProfileDAOImpl();
	
	private static Scanner scan = new Scanner(System.in);
	
	public void profileMenu() {
		System.out.println("You were accepted ! \n"
				+ "1) Finish setting up account \n"
				+ "2) Returning user. \n"
				+ "3) return");
		
		String response = scan.nextLine();
		
		switch(response) {
			case "1":
				addProfile();
				break;
			case "2":
				//accountMenu(); this will go to the login
				break;
			case "3":
				//showOneProfile();
				break;
			default:
				return;
				
			
		}
	}
	
	private void addProfile() {
		System.out.println("What is your username?");
		String username = scan.nextLine();
		System.out.println("What is your first name?");
		String firstName = scan.nextLine();
		System.out.println("What is your last name?");
		String lastName = scan.nextLine();
		System.out.println("What is your email?");
		String email = scan.nextLine();
		System.out.println("What is your address?");
		String address = scan.nextLine();
		System.out.println("What is your city?");
		String city = scan.nextLine();
		System.out.println("What is your state?");
		String state = scan.nextLine();
		System.out.println("What is your zipcode?");
		String zipcode = scan.nextLine();
		
		//System.out.println("Successfully created!");
		
		Profile profile = new Profile(username, firstName, lastName, email, address, city, state, zipcode);
		
		if(profileService.addProfile(profile)) {
			System.out.println("Profile was successfully created!");
		}else {
			 System.out.println("Something went wrong, please try again");
			 //addProfile();
		}
	}

	public void showAllProfiles() {
		List<Profile> profiles = profileService.getAllProfiles();
		
		for(Profile p:profiles) {
			System.out.println(p);
		}
	}
	
	public void showOneProfile() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		
		ResultSet result = null;
		
		String username = "";
		
		while(true) {
			System.out.println("What user profile would you want to see?");
			username = scan.nextLine();
			
			result = statement.executeQuery("SELECT * FROM profile WHERE username = '"+username+"'");
			
			if(result.next()) {
				System.out.println("Thank you.");
				break;
			} else {
				System.out.println("Sorry that user does not exist. Try again.");
				username = scan.nextLine();
			}
		}
		
		 Profile profile = profileDaoImpl.findByProfile(username);
		 System.out.println(profile);
		
	}
	
	
			
}
