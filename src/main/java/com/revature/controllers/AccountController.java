package com.revature.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.Profile;
import com.revature.services.AccountService;
import com.revature.utils.ConnectionUtil;
import com.revature.controllers.ProfileController;

public class AccountController {

	private static AccountService accountService = new AccountService();
	private static Account account = new Account();
	private static ProfileController profileController = new ProfileController();
	//private static CustomerMenu customerMenu = new CustomerMenu();
	private static AccountController accountController = new AccountController();
	private static Scanner scan = new Scanner(System.in);
	
	private static Logger log = LoggerFactory.getLogger(AccountController.class);
	
	public Account startUpMenu() {
		System.out.println("Welcome in! Are you a new or returning user?\n"
			+ "1) Sign up here! \n"
			+ "2) Returning user. \n"
			+ "3) Employee\n"
			+ "4) Admin\n"
			+ "5) Exit");
		String response = scan.nextLine();
		switch(response) {
			case "1":
				try {
					addAccount();
					profileController.addProfile();
					} catch (SQLException e) {
						e.printStackTrace();
				}
				break;
			case "2":
				try {
					accountController.mainMenu(account);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return null;
			case "3":
				try {
					accountController.employeeMenu();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "4":
				try {
					accountController.adminMenu();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case "5":
				System.out.println("Thank you for stopping by!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option, try again.");
				account = startUpMenu();
				break;
			}
		return account;

	}
		
		
	public void employeeMenu() throws SQLException {
		
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result= null;
		
		System.out.println("Welcome back, you're right on time for your shift.");
		while(true) {
			System.out.println("Would you like to do: \n"
					+ "1. View account information. \n"
					+ "2. View account balance \n"
					+ "3. View user information \n"
					+ "4. Activate an account\n"
					+ "5. Deactivate an account\n"
					+ "6. Exit\n");
			String response = scan.nextLine();
			
			switch(response) {
				case "1":
					showOneAccount();
					break;
				case "2":
					System.out.println("What user's balance would you want to see?");
					String username = scan.nextLine();
					try(Connection connection = ConnectionUtil.getConnection()){
						ResultSet balance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
						while(balance.next()) {
							double newBalance = balance.getDouble(1);
							System.out.println("They have $"+balance+" available in their account");
							
						}
					}catch(SQLException e) {
						e.printStackTrace();
						log.debug("Not able to view account balance");
					}
					break;
				case "3":
					profileController.showOneProfile();
					break;
				case "4":
					System.out.println("What is the user whose account you want to activate?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT * FROM account WHERE username = '" + username + "';");
					if(result.next()) {
						System.out.println("Understood, give it a moment.");
						
					} else {
						System.out.println("That user does not exist. Try again");
						username = scan.nextLine();
								
					}
					try(Connection connection = ConnectionUtil.getConnection()){
						statement.executeUpdate("UPDATE account SET does_exist = TRUE WHERE username = '" + username +"';");
						}catch(SQLException e) {
							e.printStackTrace();
							log.debug("Activation did not go through");
						}
					break;
				case "5":
					System.out.println("What is the user whose account you want to deactivate?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT * FROM account WHERE username = '" + username + "';");
					if(result.next()) {
						System.out.println("Understood, give it a moment.");
						
					} else {
						System.out.println("That user does not exist. Try again");
						username = scan.nextLine();
								
					}
					try(Connection connection = ConnectionUtil.getConnection()){
						statement.executeUpdate("UPDATE account SET does_exist = FALSE WHERE username = '" + username +"';");
						}catch(SQLException e) {
							e.printStackTrace();
							log.debug("Deactivation did not go through");
						}
					break;
				case "6":
					System.out.println("See you again on your next shift!");
					System.exit(0);
				default:
					System.out.println("Invalid, try again.");
					break;
					
			}
		}
	}
	

	public void adminMenu() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result= null;
		
		System.out.println("Hello Admin!");
		while(true) {
			System.out.println("What are you looking to do today? \n"
					+ "1. Deposit for a user\n"
					+ "2. Withdraw for a user\n"
					+ "3. View user information\n"
					+ "4. View account information\n"
					+ "5. Edit user information\n"
					+ "6. View an accounts balance\n"
					+ "7. Activate a customers account\n"
					+ "8. Deactivate a customers account\n"
					+ "9. Cancel a customers account\n"
					+ "10. Exit");
			String response = scan.nextLine();
			
			switch(response) {
				case "1": 
					System.out.println("To what user do you want to deposit to?");
					String username = scan.nextLine();
					result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
					if(result.next()) {
						double balance = result.getDouble(1);
						System.out.println("How much do you want to deposit?\n"
								+ "They have $" + balance + " available.");
						
						double cash = scan.nextDouble();
						scan.nextLine();
						
						while(cash < 0) {
							System.out.println("Negative numbers are not allowed. Try again.");
							cash = scan.nextDouble();
						}
						
						statement.executeUpdate("UPDATE account SET balance = " +(balance + cash)+ " WHERE username = '"+username+"';");
						ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
						while(newBalance.next()) {
							double finalBalance = newBalance.getDouble(1);
							System.out.println("They now have a new balance of $" +finalBalance);
						}
					}
					break;
				case "2":
					System.out.println("What user will you withdraw from?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
					if(result.next()) {
						double balance = result.getDouble(1);
						System.out.println("How much do you want to withdraw?\n"
								+ "They have $" + balance + " available.");
						
						double cash = scan.nextDouble();
						scan.nextLine();
						
						while(cash > balance) {
							System.out.println("You do not have enough funds to take that much out. Try again.");
							cash = scan.nextDouble();
						}
						
						statement.executeUpdate("UPDATE account SET balance = " +(balance - cash)+ " WHERE username = '"+username+"';");
						ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
						while(newBalance.next()) {
							double finalBalance = newBalance.getDouble(1);
							System.out.println("They now have a balance of $" +finalBalance);
						}
					}
					break;
				case "3":
					profileController.showOneProfile();
					break;
				case "4":
					showOneAccount();
					break;
				case "5":
					profileController.updateProfileInfo();
					break;
				case "6":
					System.out.println("Who's account balance do you want to see?");
					username = scan.nextLine();
					try(Connection connection = ConnectionUtil.getConnection()){
						result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
						while(result.next()) {
							double balance = result.getDouble(1);
							System.out.println("They have $" + balance + " available in the account.");
						}
					}catch(SQLException e) {
						e.printStackTrace();
						log.debug("Error, account not found.");
					}
					break;
				case "7":
					System.out.println("What is the user whose account you want to activate?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT * FROM account WHERE username = '" + username + "';");
					if(result.next()) {
						System.out.println("Understood, give it a moment.");
						
					} else {
						System.out.println("That user does not exist. Try again");
						username = scan.nextLine();
								
					}
					try(Connection connection = ConnectionUtil.getConnection()){
						statement.executeUpdate("UPDATE account SET does_exist = TRUE WHERE username = '" + username +"';");
						}catch(SQLException e) {
							e.printStackTrace();
							log.debug("Activation did not go through");
						}
					break;
				case "8":
					System.out.println("What is the user whose account you want to deactivate?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT * FROM account WHERE username = '" + username + "';");
					if(result.next()) {
						System.out.println("Understood, give it a moment.");
						
					} else {
						System.out.println("That user does not exist. Try again");
						username = scan.nextLine();
								
					}
					try(Connection connection = ConnectionUtil.getConnection()){
						statement.executeUpdate("UPDATE account SET does_exist = FALSE WHERE username = '" + username +"';");
						}catch(SQLException e) {
							e.printStackTrace();
							log.debug("Deactivation did not go through");
						}
					break;
				case "9":
					System.out.println("Who's account do you want to delete?");
					username = scan.nextLine();
					result = statement.executeQuery("SELECT * FROM account WHERE username = '" + username +"';");
					
					if(result.next()) {
						System.out.println("Account deleted!");
						
					} else {
						System.out.println("That username was not in our system");
						username = scan.nextLine();
					}
					
					statement.executeUpdate("DELETE FROM account WHERE username = '" + username + "';");
					statement.executeUpdate("DELETE FROM profile WHERE username = '" + username + "';");
					break;
				case "10":
					System.out.println("Have a nice day Admin");
					System.exit(0);
				default:
					System.out.println("Invalid input");
					break;
			}
			
		}
		
	}
	
	public void mainMenu(Account account) throws SQLException{
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result= null;
		
		while(true) {
			System.out.println("Enter credentials to continue:");
			System.out.println("Enter your username: ");
			String username = scan.nextLine();
			System.out.println("Enter your password: ");
			String password = scan.nextLine();
			boolean accountExist = AccountController.userExists(username, password);
			int accLevel = AccountController.accountPass(username, password);
			
			System.out.println("Credentials were successful!");
			while(accLevel > 0) {
				
				//customer
				if(accLevel == 1) {
					System.out.println("Welcome! How may I assist you?");
					
					System.out.println("1. Deposit\n"
							+ "2. Withdraw\n"
							+ "3. View Balance\n"
							+ "4. View personal information\n"
							+ "5. Update personal information\n"
							+ "6. Exit");
					
					String response = scan.nextLine();
					switch(response) {
						case "1":
							try(Connection connection = ConnectionUtil.getConnection()){
								result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
								if(result.next()) {
									double balance = result.getDouble(1);
									System.out.println("How much do you want to deposit?\n"
											+ "You have $" + balance + " available.");
									
									double cash = scan.nextDouble();
									scan.nextLine();
									
									while(cash < 0) {
										System.out.println("Negative numbers are not allowed. Try again.");
										cash = scan.nextDouble();
									}
									
									statement.executeUpdate("UPDATE account SET balance = " +(balance + cash)+ " WHERE username = '"+username+"';");
									ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
									while(newBalance.next()) {
										double finalBalance = newBalance.getDouble(1);
										System.out.println("Your new balance is $" +finalBalance);
									}
								}
							}catch(SQLException e) {
								e.printStackTrace();
								log.debug("Error, deposit did not go through.");
							}
							break;
							
						case "2":
							try(Connection connection = ConnectionUtil.getConnection()){
								result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
								if(result.next()) {
									double balance = result.getDouble(1);
									System.out.println("How much do you want to withdraw?\n"
											+ "You have $" + balance + " available.");
									
									double cash = scan.nextDouble();
									scan.nextLine();
									
									while(cash > balance) {
										System.out.println("You do not have enough funds to take that much out. Try again.");
										cash = scan.nextDouble();
									}
									
									statement.executeUpdate("UPDATE account SET balance = " +(balance - cash)+ " WHERE username = '"+username+"';");
									ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
									while(newBalance.next()) {
										double finalBalance = newBalance.getDouble(1);
										System.out.println("Your new balance is $" +finalBalance);
									}
								}
							}catch(SQLException e){
								e.printStackTrace();
								log.debug("Error, withdraw did not work.");
							}
		
							break;
						case "3":
							try(Connection connection = ConnectionUtil.getConnection()){
								result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
								while(result.next()) {
									double balance = result.getDouble(1);
									System.out.println("You have $" + balance + " available in your account.");
								}
							}catch(SQLException e) {
								e.printStackTrace();
								log.debug("Error, account not found.");
							}
							break;
						case "4":
							try(Connection connection = ConnectionUtil.getConnection()){
								profileController.showOneProfile();
							}catch(SQLException e) {
								e.printStackTrace();
								log.debug("Error, personal information not found.");
							}
							break;
						case "5":
							try(Connection connection = ConnectionUtil.getConnection()){
								profileController.updateProfileInfo();
							}catch(SQLException e) {
								e.printStackTrace();
								log.debug("Error, personal information was not updated.");
							}
							break;
						case "6":
							System.out.println("Thank you for your visit. Farewell.");
							System.exit(0);
						default:
							System.out.print("invalid input, try again");
							break;
					}
				
				}else {
					System.out.println("Account does not exist. Try making one.");
					System.exit(0);		
				}
				
				//employee
				//while(accLevel == 2) {
//					
//					
//				}
				
				//admin
//				while(accLevel ==3) {
//					
//				}
				
			}
		
		}		
	}
	
	
	public static boolean userExists(String username, String password) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery("SELECT does_exist FROM account WHERE username = '"+username+"' AND pass_word = '"+password+"';");
		boolean active = false;
		Account exists = new Account();
		
		if(result.next()) {
			username = exists.getUsername();
			password = exists.getPassword();
			active = result.getBoolean(1);
		}
		return active;
		
	}
	
	public static int accountPass(String username, String password) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery("SELECT acc_level FROM account WHERE username = '"+username+"';");
//		int level = 0;
		Account newAccount = new Account();
		int level = 0;
		if(result.next()) {
			username = newAccount.getUsername();
			password = newAccount.getPassword();
			level = result.getInt(1);
		}
		
		conn.close();
		return level;
	}

	private void addAccount() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		Statement statement = conn.createStatement();
		
		ResultSet result = null;
		
		String username = "";
		
		System.out.println("What is your first name?");
		String firstName = scan.nextLine();
		System.out.println("What is your last name?");
		String lastName = scan.nextLine();
		
		while(true) {
			System.out.println("What will your username be? Must be atleast 6 characters");
			username = scan.nextLine();
			
			if(username.length() < 6) {
				while(username.length() < 6) {
					System.out.println("Try again. Not enough characters");
					username = scan.nextLine();
				}
			}
			
			result = statement.executeQuery("SELECT * FROM account WHERE username = '"+username+"'");
			if(result.next()) {
				System.out.println("Sorry, this username is being used already. Try another one");
			} else {
				break;
			}
		}
		
		System.out.println("What will your password be? Must be atleast 6 characters");
		String password = scan.nextLine();
		if(password.length() < 6) {
			while(password.length() < 6) {
				System.out.println("Try again. Not enough characters");
				password = scan.nextLine();
			}
		}
		
		Account account = new Account(username, password, firstName, lastName, 1.0, 1, true);
		
		if(accountService.addAccount(account)) {
			System.out.println("Account was successfully created!");
		}else {
			 System.out.println("Something went wrong, please try again.");
		}
	}
	
	public void showAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		
		for(Account a:accounts) {
			System.out.println(a);
		}
	}
	
	public void showOneAccount() {
		System.out.println("What account would you want to see?");
		 String respone = scan.nextLine();
		 Account account = accountService.getAccount(respone);
		  
		 if(account!=null) {
			 System.out.println(account);
			 
		 }else {
			 System.out.println("That is not a valid name, try again.");
			 showOneAccount();
		 }
	}
}
