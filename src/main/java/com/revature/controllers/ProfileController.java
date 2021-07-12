package com.revature.controllers;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Profile;
import com.revature.services.ProfileService;

public class ProfileController {
	
	private static ProfileService profileService = new ProfileService();
	private static Scanner scan = new Scanner(System.in);
	
	public void profileMenu() {
		System.out.println("Are you a returning or new user? \n"
				+ "1) Sign up here! \n"
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
		System.out.println("What will your username be?");
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
	
	public void showOneProfile() {
		System.out.println("What user would you want to see?");
		 String respone = scan.nextLine();
		 Profile profile = profileService.getProfile(respone);
		  
		 if(profile!=null) {
			 System.out.println(profile);
			 
		 }else {
			 System.out.println("That is not a valid name, try again");
			 showOneProfile();
		 }
	}
	
	
			
}
