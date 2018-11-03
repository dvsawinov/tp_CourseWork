package server;

import sql.Lane;
import sql.SqlConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Server 
{
	public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException, SQLException
	{
		System.out.println("Program is running");
		// Source directory
		File dir = new File("D://CourseWork//Logs");
		if(dir.isDirectory())
		{
			for(File item: dir.listFiles())
			{
				if(getFileExtension(item).equals("log"))
				{
					System.out.println("\n" + item.getName() + "Parsing in progress...");
					//Get all lanes information from source file
					ArrayList<Lane> InsertDB = parseLog(item);
					//Insert the lanes into database
					System.out.println(item.getName() + " Insertion in progress...");
					new SqlConnection().insert(InsertDB);
				}
			}
		}
		System.out.println("\n"+"Program completed");
	}
	
	//Read from file and add to ArrayList new Lane object
	static ArrayList<Lane> parseLog(File file) throws ParseException, FileNotFoundException
	{
		ArrayList<Lane> lanes = new ArrayList<Lane>();
		String temp = null;
		Pattern p = Pattern.compile("\\s+");
		String[] laneInfo = null;
		Scanner scan = new Scanner(file);
		while(scan.hasNext())
		{
			temp = scan.nextLine();
			if (temp.trim().length() != 0)
			{	
				laneInfo = p.split(temp);
				lanes.add(new Lane(laneInfo));
			}
		}
		scan.close();
		return lanes;
	}
	
	//get the file extension :D 
	static String getFileExtension(File file)
	{
		String fname = file.getName();
		try
		{
			return fname.substring(fname.lastIndexOf(".") +1);
		}
		catch (Exception e)
		{
			return "";
		}
	}
}
