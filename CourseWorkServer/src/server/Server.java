package server;

import sql.Lane;
import sql.SqlConnection;

import java.io.File;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Server 
{
	public void main(String[] args) throws ParseException
	{
		String filename = args[0];
		File checkExist = new File(filename);
		if (checkExist.exists())
		{
			Lane[] InsertDB = parseLog(filename);
			new SqlConnection().insert(InsertDB);
		}
		else
		{
			System.out.println("File does not exist");
		}
	}
	Lane[] parseLog(String filename) throws ParseException
	{
		Lane[] lanes = null;
		String temp = null;
		Pattern p = Pattern.compile("\\s+");
		String[] laneInfo = null;
		Scanner scan = new Scanner(filename);
		for (int i=0; scan.hasNext(); i++)
		{
			temp = scan.nextLine();		
			laneInfo = p.split(temp);
			lanes[i] = new Lane(laneInfo);
		}	
		return lanes;
	}
}
