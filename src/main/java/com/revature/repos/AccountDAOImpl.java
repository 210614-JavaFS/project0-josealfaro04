package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	//private static ProfileDAO profileDao = new ProfileDAOImpl();
	
	@Override
	public List<Account> findAllAccounts() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Account> list = new ArrayList<>();
			
			while(result.next()) {
				Account account = new Account();
				account.setId(result.getInt("id"));
				account.setUsername(result.getString("username"));
				account.setPassword(result.getString("pass_word"));
				account.setFirstName(result.getString("first_name"));
				account.setLastName(result.getString("last_name"));
				account.setBalance(result.getDouble("balance"));
				
				list.add(account);	

			}
			
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Account findAccount(String name) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			Account account = new Account();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				account.setId(result.getInt("id"));
				account.setUsername(result.getString("username"));
				account.setPassword(result.getString("pass_word"));
				account.setFirstName(result.getString("first_name"));
				account.setLastName(result.getString("last_name"));
				account.setBalance(result.getDouble("balance"));
			}
			
			return account;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAccount(Account account) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO account (username, pass_word, first_name, last_name, balance)"
					+ " VALUES (?,?,?,?,?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setString(++index, account.getUsername());
			statement.setString(++index, account.getPassword());
			statement.setString(++index, account.getFirstName());
			statement.setString(++index, account.getLastName());
			statement.setDouble(++index, account.getBalance());

			statement.execute();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}


