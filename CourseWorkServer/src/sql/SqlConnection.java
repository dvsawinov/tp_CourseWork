package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlConnection 
{
	 String url; //адрес БД
	 String user; //Логин БД
	 String password; // Пароль к БД
	 Connection connect; 
	 Statement stmt;
	 
	 public SqlConnection()
	 {
		 url = "jdbc:mysql://localhost:3306/coursework?serverTimezone=Europe/Moscow";
		 user = "root";
		 password = "root123"; 
	 }
	 
	 public void insert(ArrayList<Lane> lane) throws ClassNotFoundException, SQLException
	 {	 
		 String insertQuery = "INSERT INTO coursework.lane" + 
				 "(name,volume,occupancy,speed,headway,gap,date)VALUES";
		 try 
		 {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url,user,password);
			stmt = connect.createStatement();
		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }	
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
		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 connect.close();
	 }
}
