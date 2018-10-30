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
		String filename = "C:\\Users\\Дмитрий\\Desktop\\Новая папка\\logdet07-11-11.log";
		File logFile = new File(filename);
		if (logFile.exists())
		{
			ArrayList<Lane> InsertDB = parseLog(logFile);
			new SqlConnection().insert(InsertDB);
		}
		else
		{
			System.out.println("File does not exist");
		}
	}
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
}
