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

import org.json.JSONException;

public class Server 
{
	public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException, SQLException, JSONException
	{
		System.out.println("Program is running");
		System.out.println("\n" + "1:Import file to database" + 
						   "\n" + "2:Import directory to database" +
						   "\n" + "3:Export to file");
		Scanner scan = new Scanner(System.in);
		int key = scan.nextInt();
		switch(key)
		{
		case 1:
			importFileToDB();
			break;
		case 2:
			importDirToDB();
			break;
		case 3:
			new SqlConnection().exportToFile();
			break;
		default:
			break;
		}	
		System.out.println("\n"+"Program completed");
	}
	
	static void importFileToDB() throws ClassNotFoundException, SQLException, IOException, JSONException, ParseException
	{
		System.out.println("Please enter the path to the file with 'log' as extension");
		Scanner scan = new Scanner(System.in);
		File file = new File(scan.next());
		if(file.isFile() && getFileExtension(file).equals("log"))
		{
			System.out.println("\n" + file.getName() + " Parsing in progress...");
			//Get all lanes information from source file
			ArrayList<Lane> LanesInfo = parseFile(file);
			//Import the lanes to database
			System.out.println(file.getName() + " Importing in progress...");
			new SqlConnection().importLanes(LanesInfo);
		}
		else
		{
			System.out.println("No such file or file not with 'log' as extension. Please check input data");
		}
	}

	static void importDirToDB() throws ClassNotFoundException, SQLException, IOException, JSONException, ParseException
	{
		System.out.println("Please enter the path to the folder with files with 'log' as extension");
		Scanner scan = new Scanner(System.in);
		File dir = new File(scan.next());
		if(dir.isDirectory())
		{
			for(File file: dir.listFiles())
			{
				if(getFileExtension(file).equals("log"))
				{
					System.out.println("\n" + file.getName() + " Parsing in progress...");
					//Get all lanes information from source file
					ArrayList<Lane> LanesInfo = parseFile(file);
					//Insert the lanes into database
					System.out.println(file.getName() + " Importing in progress...");
					new SqlConnection().importLanes(LanesInfo);
				}
			}
		}
		else
		{
			System.out.println("No such directory. Please check input data");
		}
	}
	
	//Read from file and add to ArrayList new Lane object
	static ArrayList<Lane> parseFile(File file) throws ParseException, FileNotFoundException
	{
		ArrayList<Lane> lanes = new ArrayList<Lane>();
		String temp = null;
		Pattern p = Pattern.compile("\\s+");
		Scanner scan = new Scanner(file);
		while(scan.hasNext())
		{
			temp = scan.nextLine();
			if (temp.trim().length() != 0)
			{	
				lanes.add(new Lane(p.split(temp)));
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