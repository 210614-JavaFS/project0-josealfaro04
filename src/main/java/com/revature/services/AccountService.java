package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.repos.AccountDAO;
import com.revature.repos.AccountDAOImpl;

public class AccountService {

	public static AccountDAO accountDao = new AccountDAOImpl();
	
	public List<Account> getAllAccounts(){
		return accountDao.findAllAccounts();
	}
	
	public Account getAccount(String name) {
		return accountDao.findAccount(name);
	}
	
	public boolean addAccount(Account account) {
		return accountDao.addAccount(account);
	}
}
