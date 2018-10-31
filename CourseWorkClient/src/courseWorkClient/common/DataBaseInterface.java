package courseWorkClient.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseInterface {
	private static final String url = "jdbc:mysql://localhost:3306/coursework?useSSL=false&serverTimezone=Europe/Moscow";
	private static final String user = "root";
	private static final String password = "2hj7x0rHitman";
	static Connection connection;
	
	public Connection connect()
	{
		if (connection == null)
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url,user,password);
			}
			catch (ClassNotFoundException|SQLException e)
			{
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public void disconnect()
	{
		if (connection!=null)
		{
			try
			{
				connection.close();
				connection = null;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
