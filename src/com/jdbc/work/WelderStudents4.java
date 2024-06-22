package com.jdbc.work;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WelderStudents4 {

	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("working ");

		WelderStudents4 welderStudent = new WelderStudents4();

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
			System.out.println("3. All Record : Callable Statement");
			System.out.println("4. Record By RollNo : Callable Statement");

			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				welderStudent.insertRecord();
				break;
			case 2:
				welderStudent.selectRecord();
				break;
			case 3:
				welderStudent.selectAllRecords();
				break;
			case 4:
				welderStudent.selectRecordByRollNo();
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

	//Stored procedure practice 
	
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
	
	//-------- using procedures------------
	
	private void selectAllRecords() throws SQLException {
		System.out.println("Showing all records ");
		//to use this first create PROCEDUR ( GET_ALL_NSTI() ) in mysql
		
		CallableStatement callableStatement =  connection.prepareCall("{ call GET_ALL_NSTI() }");
		
		ResultSet result = callableStatement.executeQuery();
		
		while(result.next()) {
			
			int sRollNumber = result.getInt("roll_number");
			String sName = result.getString("name");
			double sPercentage = result.getDouble("percentage");
			String sAddress = result.getString("address");

			System.out.println("-"+sRollNumber+"-"+sName+"-"+sPercentage+"-"+sAddress);

		}
	}
	
	private void selectRecordByRollNo() throws SQLException {
		
		System.out.println("Enter roll no to fetch details : ");
		int roll = Integer.parseInt(scanner.nextLine());
				
		CallableStatement callableStatement =  connection.prepareCall("{ call GET_RECORD(?) }");
		
		//callableStatement.setInt(1,5);
		callableStatement.setInt(1,roll);
		
		ResultSet result = callableStatement.executeQuery();
		
		while(result.next()) {
			
			int sRollNumber = result.getInt("roll_number");
			String sName = result.getString("name");
			double sPercentage = result.getDouble("percentage");
			String sAddress = result.getString("address");

			System.out.println("-"+sRollNumber+"-"+sName+"-"+sPercentage+"-"+sAddress);

		}
	}
}
