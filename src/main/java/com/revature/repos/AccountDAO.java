package com.revature.repos;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	
	public List<Account> findAllAccounts();
	public Account findAccount(String name);
	public boolean updateAccount(Account account);
	public boolean addAccount(Account account);

}
