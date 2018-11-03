package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class SqlConnection 
{
	 String url; //database's url
	 String user; //Login from database
	 String password; //Password from database
	 Connection connect; 
	 Statement stmt;
	 
	 public SqlConnection()
	 {
		 url = "jdbc:mysql://localhost:3306/coursework?serverTimezone=Europe/Moscow";
		 user = "root";
		 password = "root123"; 
	 }
	 
	 // Get the insert query to database
	 public void insert(ArrayList<Lane> lane) throws ClassNotFoundException, SQLException
	 {	 
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 //Query text
		 String insertQuery = "INSERT INTO coursework.lane VALUES";
		 boolean Continue = false;
		 try 
		 {
			connect = DriverManager.getConnection(url,user,password);
			stmt = connect.createStatement();
			Continue = needToInsert(lane.get(0),lane.get(lane.size()-1));
		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 if(Continue)
		 {
			 System.out.println("Insertion...");
			 for(int i=0;i<lane.size();i++)
			 {
				 insertQuery += lane.get(i).getInsertQuery();
				 if(i==lane.size()-1)
				 {
					 insertQuery += ";";
				 }
				 else 
				 {
					 insertQuery += ",";
				 }			 
			 }
			 try 
			 {
				stmt.executeUpdate(insertQuery);
				System.out.println("Insertion completed");
			 } 
			 catch (SQLException e) 
			 {
				e.printStackTrace();
			 }
		 }
		 stmt.close();
		 connect.close();		 
	 }
	 
	 // Check for re-insertion source log file by date on the first line
	 boolean needToInsert(Lane first,Lane last) throws SQLException
	 {
		 ResultSet rs = stmt.executeQuery("SELECT date FROM coursework.lane where date="
				 						 + "'" + first.date +"'"+";");
		 if (rs.isBeforeFirst())
		 {
			 rs.close();
			 if(getChoice(first.date.substring(0, 10)))
			 { 
				 System.out.println("Removing old data...");
				 stmt.executeUpdate("DELETE FROM coursework.lane WHERE date BETWEEN"
					 			+ "'" + first.date +"'" 
					 			+ "AND" + "'" + last.date +"'" + ";");
				 System.out.println("Removing completed");
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 }
		 rs.close();
		 return true;
	 }
	 
	 boolean getChoice(String str)
	 {
		 System.out.println("Would you kindly give permission to update following data:" + str + "? (Y/N)");
		 Scanner scan = new Scanner(System.in);
		 if (scan.next().equals("Y"))
		 {
			 return true;
		 }
		 else
		 {
			 System.out.println("Data of the " + str + " day was not inserted by user's choice" );
			 return false;
		}
	 }
}
