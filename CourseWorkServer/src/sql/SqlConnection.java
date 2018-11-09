package sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

import weather.Weather;
import weather.WeatherAPI;

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
	 public void insert(ArrayList<Lane> lanes) throws ClassNotFoundException, SQLException, IOException, JSONException
	 {	 
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 //Query text
		 String insertQuery = "INSERT INTO coursework.lane VALUES";
		 boolean Continue = false;
		 try 
		 {
			connect = DriverManager.getConnection(url,user,password);
			stmt = connect.createStatement();
			Continue = needToInsertLane(lanes.get(0),lanes.get(lanes.size()-1));
			needToInsertWeather(lanes.get(0));
		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 if(Continue)
		 {
			 System.out.println("Inserting the lane data...");
			 for(int i=0;i<lanes.size();i++)
			 {
				 insertQuery += lanes.get(i).getInsertQuery();
				 if(i==lanes.size()-1)
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
				System.out.println("Insertion of the lane data completed");
			 } 
			 catch (SQLException e) 
			 {
				e.printStackTrace();
			 }
		 }
		 stmt.close();
		 connect.close();		 
	 }
	 
	 // 
	 public void needToInsertWeather(Lane ln) throws SQLException, IOException, JSONException
	 {
		 System.out.println("Checking the weather data...");
		 ResultSet rs = stmt.executeQuery("SELECT date FROM coursework.weather where date="
					 + "'" + ln.date +"'"+";");
		 if(!rs.isBeforeFirst()) 
		 {
			String insertQuery = "INSERT INTO coursework.weather VALUES";
			System.out.println("No weather data for the " + ln.date.substring(0,10)  +" day");
			ArrayList<Weather> weathers = new WeatherAPI(ln.date.substring(0,10)).getWeatherInfo();
			System.out.println("Inserting the weather data...");
			for(int i=0;i<weathers.size();i++)
			{
				 insertQuery += weathers.get(i).getInsertQuery();
				 if(i==weathers.size()-1)
				 {
					 insertQuery += ";";
				 }
				 else 
				 {
					 insertQuery += ",";
				 }			 
			}
			stmt.executeUpdate(insertQuery);
			System.out.println("Insertion of the weather data completed");
			return;
		 }
		 System.out.println("Checking the weather data completed");		 
	 }
	 
	 
	 // Check for re-insertion source log file by date on the first line
	 boolean needToInsertLane(Lane first,Lane last) throws SQLException
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
