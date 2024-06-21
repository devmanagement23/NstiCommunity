package com.jdbc.work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WelderStudents3 {

	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("working ");

		WelderStudents3 welderStudent = new WelderStudents3();

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
			System.out.println("2. Select Record");
			System.out.println("3. Delete Record");

			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				welderStudent.insertRecord();
				break;
			case 2:
				welderStudent.selectRecord();
				break;
			default:
				break;
			}

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

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

		System.out.println("\nEnter Name: ");
		preparedStatement.setString(1, scanner.nextLine());

		System.out.println("\nEnter Percentage: ");
		preparedStatement.setDouble(2, Double.parseDouble(scanner.nextLine()));

		System.out.println("\nEnter Address: ");
		preparedStatement.setString(3, scanner.nextLine());

		int rows = preparedStatement.executeUpdate();

		if (rows > 0) {
			System.out.println("Record inserted successfully");
		}
	}

	public void selectRecord() throws SQLException {
		// System.out.println("Select Method is called.");

		// String sql = "select * from student";
		// String sql = "select * from student where roll_number = 2";

		System.out.println("Enter roll number to find result : ");
		int rollNumber = Integer.parseInt(scanner.nextLine());

		String sql = "select * from student where roll_number = " + rollNumber;

		Statement statement = connection.createStatement();

		ResultSet result = statement.executeQuery(sql);

		if (result.next()) {

			int sRollNumber = result.getInt("roll_number");
			String sName = result.getString("name");
			double sPercentage = result.getDouble("percentage");
			String sAddress = result.getString("address");

			System.out.println("-"+sRollNumber+"-"+sName+"-"+sPercentage+"-"+sAddress);
		}
		else {
			System.out.println("No Records Found");
		}
	}
}