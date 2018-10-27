package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	 
	 public void insert(Lane[] lane) throws ClassNotFoundException
	 {	 
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
		 for(Lane ln: lane)
		 {
			 try 
			 {
				stmt.executeUpdate(ln.getInsertQuery());
			 } 
			 catch (SQLException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 }
	 }
}
