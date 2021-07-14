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
	
	
	
//	  public void profileMenu() { System.out.println("You were accepted ! \n" +
//	  "1) Finish setting up account \n" + "2)  \n" + "3) return");
//	  
//	  String response = scan.nextLine();
//	  
//	  switch(response) {
//	  	case "1": 
//	  		try {
//	  			a
//	  		}
//	 
	
	public void addProfile() throws SQLException {
		System.out.println("What is your username?");
		String username = scan.nextLine();
		System.out.print("What is your password?");
		String password = scan.nextLine();
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
		
		
		Profile profile = new Profile(username, password, firstName, lastName, email, address, city, state, zipcode);
		
		if(profileService.addProfile(profile)) {
			System.out.println("Profile was successfully created!");
		}else {
			 System.out.println("Something went wrong, please try again");
			 //addProfile();
		}
	}
	
	public void updateProfileInfo() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = null;
		
		String username = "";
		String password = "";
		
		while(true) {
			System.out.println("Enter your username: \n");
			username = scan.nextLine();
			System.out.println("Enter your password: \n");
			password = scan.nextLine();
			
			result = statement.executeQuery("SELECT * FROM account WHERE username ='"+username+"' AND pass_word = '"+password+"';");
			
			if(result.next()) {
				System.out.println("Process Complete, thank you");
				break;
			} else {
				System.out.println("Sorry, there was an error. Try again.");
				System.out.println("Enter your username: \n");
				username = scan.nextLine();
				System.out.println("Enter your password: \n");
				password = scan.nextLine();
			}
		}
		
		System.out.println("What is your current email?");
		String email = scan.nextLine();
		System.out.println("What is your current address?");
		String address = scan.nextLine();
		System.out.println("What is your current city?");
		String city = scan.nextLine();
		System.out.println("What is your current state?");
		String state = scan.nextLine();
		System.out.println("What is your current zipcode?");
		String zipcode = scan.nextLine();
		
		statement.executeUpdate("UPDATE profile SET email = '"+email+"', address = '"+address+"', city = '"+city+"', state = '"+state+"', zipcode = '"+zipcode+"' WHERE username = '"+username+"';");
		System.out.println("Update Successful");
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
		String password ="";
		
		while(true) {
			System.out.println("What user profile would you want to see?");
			username = scan.nextLine();
			
			result = statement.executeQuery("SELECT * FROM account WHERE username ='"+username+"' AND pass_word = '"+password+"';");
			
			if(result.next()) {
				System.out.println("Give it a minute please.");
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
