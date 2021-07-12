package com.revature.services;

import java.util.List;

import com.revature.models.Profile;
import com.revature.repos.ProfileDAO;
import com.revature.repos.ProfileDAOImpl;

public class ProfileService {
	
	private static ProfileDAO profileDao = new ProfileDAOImpl();
	
	public List<Profile> getAllProfiles() {
		return profileDao.findAllProfiles();
	}
	
	public Profile getProfile(String name) {
		return profileDao.findByProfile(name);
		
	}
	
	public boolean addProfile(Profile profile) {
		return profileDao.addProfile(profile);
	}

}
