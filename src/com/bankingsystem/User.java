package com.bankingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	private Connection connection;
	private Scanner scanner;

	User(Connection connection, Scanner scanner){
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void register() {
		
		scanner.nextLine();
		System.out.println("Full Name: ");
		String full_name = scanner.nextLine();
		System.out.println("Email: ");
		String email= scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		
		if(user_exist(email)) {
			System.out.println("User already exist with given mail id");
			return; 
		}
		String register_Query = "insert into user(full_name, email, password) values(?,?,?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(register_Query);
			preparedStatement.setString(1, full_name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
		int affectedRows=	preparedStatement.executeUpdate();
		
		if(affectedRows>0) {
			System.out.println("Registration Succcessful");
		}else {
			System.out.println("Registration Failed");
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
			
		}
			
		}
	
	public String login() {
		scanner.nextLine();
		System.out.println("Enter Email:");
		String email = scanner.nextLine();
		System.out.println("Enter Passsword:");
		String password = scanner.nextLine();
		
		String login_Query = "Select * from user where email = ? and password = ?"; 
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(login_Query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return email;
			}else {
				return null;
			}
			
		}
		catch( SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private boolean user_exist(String email) {
		String query = "Select * from user where email =?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
			
		}
		catch( SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
