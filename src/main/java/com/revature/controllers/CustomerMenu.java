//package com.revature.controllers;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Scanner;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.revature.models.Account;
//import com.revature.utils.ConnectionUtil;
//
//public class CustomerMenu {
//	private static Scanner scan = new Scanner(System.in);
//	private static Logger log = LoggerFactory.getLogger(CustomerMenu.class);
//	private static ProfileController profileController = new ProfileController();
//	
//	
//	public void customMenu(Account account) throws SQLException {
//		Connection conn = ConnectionUtil.getConnection();
//		Statement statement = conn.createStatement();
//		ResultSet result= null;
//		
//		while(true) {
//			System.out.println("Enter your username: ");
//			String username = scan.nextLine();
//			System.out.println("Enter your password: ");
//			String password = scan.nextLine();
//			
//			boolean accountExist = AccountController.userExists(username, password);
//			if(accountExist) {
//				System.out.println("Credentials were valid. How may I assist you?");
//				
//				System.out.println("1. Deposit\n"
//						+ "2. Withdraw\n"
//						+ "3. View Balance\n"
//						+ "4. View personal information\n"
//						+ "5. Update personal information\n"
//						+ "6. Exit");
//				
//				String response = scan.nextLine();
//				switch(response) {
//					case "1":
//						try(Connection connection = ConnectionUtil.getConnection()){
//							result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
//							if(result.next()) {
//								double balance = result.getDouble(1);
//								System.out.println("How much do you want to deposit?\n"
//										+ "You have $" + balance + " available.");
//								
//								double cash = scan.nextDouble();
//								scan.nextLine();
//								
//								while(cash < 0) {
//									System.out.println("Negative numbers are not allowed. Try again.");
//									cash = scan.nextDouble();
//								}
//								
//								statement.executeUpdate("UPDATE account SET balance = " +(balance + cash)+ " WHERE username = '"+username+"';");
//								ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
//								while(newBalance.next()) {
//									double finalBalance = newBalance.getDouble(1);
//									System.out.println("Your new balance is $" +finalBalance);
//								}
//							}
//						}catch(SQLException e) {
//							e.printStackTrace();
//							log.debug("Error, deposit did not go through.");
//						}
//						break;
//						
//					case "2":
//						try(Connection connection = ConnectionUtil.getConnection()){
//							result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
//							if(result.next()) {
//								double balance = result.getDouble(1);
//								System.out.println("How much do you want to withdraw?\n"
//										+ "You have $" + balance + " available.");
//								
//								double cash = scan.nextDouble();
//								scan.nextLine();
//								
//								while(cash > balance) {
//									System.out.println("You do not have enough funds to take that much out. Try again.");
//									cash = scan.nextDouble();
//								}
//								
//								statement.executeUpdate("UPDATE account SET balance = " +(balance - cash)+ " WHERE username = '"+username+"';");
//								ResultSet newBalance = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
//								while(newBalance.next()) {
//									double finalBalance = newBalance.getDouble(1);
//									System.out.println("Your new balance is $" +finalBalance);
//								}
//							}
//						}catch(SQLException e){
//							e.printStackTrace();
//							log.debug("Error, withdraw did not work.");
//						}
//	
//						break;
//					case "3":
//						try(Connection connection = ConnectionUtil.getConnection()){
//							result = statement.executeQuery("SELECT balance FROM account WHERE username = '"+username+"';");
//							while(result.next()) {
//								double balance = result.getDouble(1);
//								System.out.println("You have $" + balance + " available in your account.");
//							}
//						}catch(SQLException e) {
//							e.printStackTrace();
//							log.debug("Error, account not found.");
//						}
//						break;
//					case "4":
//						try(Connection connection = ConnectionUtil.getConnection()){
//							profileController.showOneProfile();
//						}catch(SQLException e) {
//							e.printStackTrace();
//							log.debug("Error, personal information not found.");
//						}
//						break;
//					case "5":
//						try(Connection connection = ConnectionUtil.getConnection()){
//							profileController.updateProfileInfo();
//						}catch(SQLException e) {
//							e.printStackTrace();
//							log.debug("Error, personal information was not updated.");
//						}
//					case "6":
//						System.out.println("Thank you for your visit. Farewell.");
//						System.exit(0);
//					default:
//						System.out.print("invalid input, try again");
//						break;
//				}
//			
//			}else {
//				System.out.println("Account does not exist. Try making one.");
//				System.exit(0);		
//			}	
//		} 
//	}
//}