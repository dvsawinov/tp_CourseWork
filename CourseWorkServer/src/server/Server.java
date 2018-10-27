package server;

import sql.Lane;
import sql.SqlConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Server 
{
	public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException
	{
		String filename = "C:\\Users\\Дмитрий\\Desktop\\Новая папка\\logdet07-11-11.log";
		File logFile = new File(filename);
		if (logFile.exists())
		{
			Lane[] InsertDB = parseLog(logFile, getLinesCount(logFile));
			new SqlConnection().insert(InsertDB);
		}
		else
		{
			System.out.println("File does not exist");
		}
	}
	static int getLinesCount(File file) throws IOException
	{
		LineNumberReader lnr = new LineNumberReader(new FileReader(file));
		int linesCount = 0;
		while (lnr.readLine() != null)
		{
			linesCount++;
		}
		lnr.close();
		return linesCount;
	}
	static Lane[] parseLog(File file, int linesCount) throws ParseException, FileNotFoundException
	{
		Lane[] lanes = new Lane[linesCount];
		String temp = null;
		Pattern p = Pattern.compile("\\s+");
		String[] laneInfo = null;
		Scanner scan = new Scanner(file);
		for (int i=0; scan.hasNext(); i++)
		{
			temp = scan.nextLine();
			if (temp.trim().length() == 0)
			{
				i--;
			}
			else 
			{
				laneInfo = p.split(temp);
				lanes[i] = new Lane(laneInfo);
			}
		}
		scan.close();
		return lanes;
	}
}
