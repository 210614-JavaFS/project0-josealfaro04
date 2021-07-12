package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Profile;
import com.revature.utils.ConnectionUtil;

public class ProfileDAOImpl implements ProfileDAO{

	@Override
	public List<Profile> findAllProfiles() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM profile";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Profile> list = new ArrayList<>();
			
			while(result.next()) {
				Profile profile = new Profile();
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setEmail(result.getString("email"));
				profile.setUsername(result.getString("username"));
				profile.setAddress(result.getString("address"));
				profile.setCity(result.getString("city"));
				profile.setState(result.getString("state"));
				profile.setZipcode(result.getString("zipcode"));
				list.add(profile);	

			}
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Profile findByProfile(String name) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM profile WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//this is where sql injection is checked for
			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			Profile profile = new Profile();
			
			while(result.next()) {
				profile.setFirstName(result.getString("first_name"));
				profile.setLastName(result.getString("last_name"));
				profile.setEmail(result.getString("email"));
				profile.setUsername(result.getString("username"));
				profile.setAddress(result.getString("address"));
				profile.setCity(result.getString("city"));
				profile.setState(result.getString("state"));
				profile.setZipcode(result.getString("zipcode"));

			}
			
			return profile;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateProfile(Profile profile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProfile(Profile profile) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO profile (username, first_name, last_name, email, address, city, state, zipcode)"
					+ " VALUES (?,?,?,?,?,?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, profile.getUsername());
			statement.setString(++index, profile.getFirstName());
			statement.setString(++index, profile.getLastName());
			statement.setString(++index, profile.getEmail());
			statement.setString(++index, profile.getAddress());
			statement.setString(++index, profile.getCity());
			statement.setString(++index, profile.getState());
			statement.setString(++index, profile.getZipcode());
			
			statement.execute();
			
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
