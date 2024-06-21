package com.jdbc.work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class WelderStudents2 {
	
	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("working ");

		WelderStudents2 welderStudent = new WelderStudents2();

		try {
			
			// load and register driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			String dbUrl = "jdbc:mysql://localhost:3306/jdbcnsti";
			String username = "root";
			String password = "";

			//
			connection = DriverManager.getConnection(dbUrl, username, password);

			System.out.println("Enter choice\n");
			System.out.println("1. Insert Record");
			System.out.println("2. Delete Record");

			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				welderStudent.insertRecord();
				break;

			default:
				break;
			}

		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}finally {
			
			try {
				connection.close();
				System.out.println("\n Connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	private void insertRecord() throws SQLException {
		System.out.println("Insert your Record");
		System.out.println("\n");
		String sql = "insert into student(name,percentage,address) values (?,?,?)";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1,"Kamal");
		preparedStatement.setDouble(2,45);
		preparedStatement.setString(3, "pune");
				
		int rows = preparedStatement.executeUpdate();

		if (rows > 0) {
			System.out.println("Record inserted successfully");
		}
	}

}
