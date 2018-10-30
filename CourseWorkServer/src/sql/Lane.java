package sql;

import java.text.ParseException;

public class Lane 
{
	String name; //Название полосы в формате LANE_[0-1][0-9]
	int volume; //
	float occupancy; // Плотность в %
	float speed; // Скорость
	float headway; // 
	float gap; // Расстояние между машинами
	String date; // Дата фиксации измерений
	
	public Lane(String[] str) throws ParseException
	{
		name = str[1];
		volume = Integer.parseInt(str[2]);
		occupancy = Float.parseFloat(str[3].replace(',', '.'));
		speed = Float.parseFloat(str[4].replace(',', '.'));
		headway = Float.parseFloat(str[14].replace(',', '.'));
		gap = Float.parseFloat(str[15].replace(',', '.'));
		date = str[16] +' '+ str[17];	
	}

	public String getInsertQuery()
	{
		return "(" + 
				"'" + this.name 		+ "'" + "," + 
				"'" + this.volume 		+ "'" + "," +
				"'" + this.occupancy 	+ "'" + "," + 
				"'" + this.speed 		+ "'" + "," +
				"'" + this.headway		+ "'" + "," +
				"'" + this.gap			+ "'" + "," + 
				"'" + this.date         + "'" +")";
	}
}
