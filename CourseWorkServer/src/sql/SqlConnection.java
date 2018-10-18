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
		 url = "jdbs:mysql//localhost:3306/coursework";
		 user = "root";
		 password = "root123";
		 try 
		 {
			connect = DriverManager.getConnection(url,user,password);
			stmt = connect.createStatement();
		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }		 
	 }
	 
	 public void insert(Lane[] lane)
	 {	 
		 for(Lane ln: lane)
		 {
			 try 
			 {
				stmt.executeQuery(ln.getInsertQuery());
			 } 
			 catch (SQLException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 }
	 }
}
