package my_Package;

import java.sql.*;
import java.io.*;
//HELLO THERE! test
public class Lab5_oracle {
	public static void main(String[] args) throws Exception{
		
		//Variables
		String username="";
		String password="";
		
		//Obtaining the Username
		System.out.print("Username = ");
		username = insertString();
		if(username.compareTo("sys")==0)
			username = username.concat(" as sysdba"); //Concat only if sys, (allows other usernames to work)
		
		//Obtaining the Password
		System.out.print("Password = ");
		password = insertString();
		
		//System.out.println("Debug "+username+" "+password); //For Debugging
		
		//Connecting to the DB
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection
				("jdbc:oracle:thin:@localhost:1521:orcl", username, password);

		//Running the Menu
		boolean run = true;
		while(run) {
			//Printing the Menu
			System.out.println("---------Main Menu----------");
			System.out.println("(1) Find Employees with last name ending with E");
			System.out.println("(2) List employees and their departments");
			System.out.println("(3) Print average salary of all employees");
			System.out.println("(4) Quit");
			System.out.print("Please insert your choice: ");
			
			//Obtaining the Menu choice
			String choice = insertString();
			
			//Performing the chosen operation
			switch(choice.charAt(0)) {
			case '1':
				//Finding names of employees with the last name ending with e
				namesWithE(conn);
				break;
			case '2':
				//Listing employee names with their department names
				employeeDept(conn);
				break;	
			case '3':
				//Print the average salary of all employees
				avgSalary(conn);
				break;
			case '4':
				//Quit the program
				run = false;
				System.out.println("Exiting the program!");
				System.out.println("Thank you for choosing DB Systems by Spectre.");
				System.out.println("We know you got a lot of options but you decided to choose us");
				System.out.println("We'd ask you \"What on earth were you thinking?\" but then again you do pay our bills");
				System.out.println("Thank you again for going with DB Systems");
				break;
			default:
				//Specify if input was invalid
				System.out.println("Invalid option... Please choose from 1, 2, or 3.");
				break;
			}
		}
		conn.close();
		}
	
	private static void namesWithE(Connection conn) throws Exception{
		Statement myStatement = conn.createStatement();
		String query = "SELECT fname, lname FROM Employee WHERE lname LIKE '%e'";
		ResultSet myResult = myStatement.executeQuery(query);
		
		System.out.println("-----Names-----");
		while (myResult.next()) {
			System.out.println(myResult.getString(1)+ " " +myResult.getString(2));
		}
		
		myResult.close();
		myStatement.close();
	}
	
	private static void employeeDept(Connection conn) throws Exception{
		Statement myStatement = conn.createStatement();
		String query = "SELECT fname, lname, dname FROM Employee, Department WHERE Dno=Dnumber";
		ResultSet myResult = myStatement.executeQuery(query);
		
		System.out.println("Employee Name \t\tDepartment Name");
		while (myResult.next()) {
			System.out.println(myResult.getString(1)+" "+myResult.getString(2)+"\t\t"+myResult.getString(3));
		}
		
		myResult.close();
		myStatement.close();
	}
	
	private static void avgSalary(Connection conn) throws Exception{
		Statement myStatement = conn.createStatement();
		String query = "SELECT AVG(Salary) FROM Employee";
		ResultSet myResult = myStatement.executeQuery(query);
		
		System.out.println("---Average Salary---");
		while (myResult.next()) {
			System.out.println(myResult.getDouble(1));
		}
		
		myResult.close();
		myStatement.close();
	}
	
	private static String insertString() throws Exception {
		StringBuffer buffer = new StringBuffer();
		int ch = System.in.read();
		while(ch!='\n'&&ch!=-1)
		{
			buffer.append((char) ch);
			ch = System.in.read();
		}	
		return buffer.toString().trim();
	}
}
