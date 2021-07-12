package com.revature.repos;

import java.util.List;

import com.revature.models.Profile;

public interface ProfileDAO {
	
	public List<Profile> findAllProfiles();
	public Profile findByProfile(String name);
	public boolean updateProfile(Profile profile);
	public boolean addProfile(Profile profile);
	
	
}
