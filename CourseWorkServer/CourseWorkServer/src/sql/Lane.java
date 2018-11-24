package sql;

import java.text.ParseException;

public class Lane 
{
	String name; // Lane's name
	float volume; // p
	float occupancy; // Q
	float speed; // V
	float distance; // d (headway*speed*1000/3600)
	String date; // date
	
	// Parse into new Lane object
	public Lane(String[] str) throws ParseException
	{
		name = str[1];
		volume = Float.parseFloat(str[2].replace(',', '.'));
		occupancy = (float) (1000*Float.parseFloat(str[3].replace(',', '.'))/5.7);
		if(occupancy != 0)
		{	
			speed = Float.parseFloat(str[5].replace(',', '.'));	
			distance = Float.parseFloat(str[14].replace(',', '.')) * speed * 1000/3600; 
		}
		else
		{
			speed = 0;
			distance = 0;
		}	
		date = str[16] +' '+ str[17];	
	}

	// return the InsertQuery for this Lane
	public String getInsertQuery()
	{
		return "(" + 
				"'" + this.name 		+ "'" + "," + 
				"'" + this.volume 		+ "'" + "," + 
				"'" + this.occupancy	+ "'" + "," + 
				"'" + this.speed 		+ "'" + "," +
				"'" + this.distance		+ "'" + "," +				
				"'" + this.date         + "'" +")";
	}
}