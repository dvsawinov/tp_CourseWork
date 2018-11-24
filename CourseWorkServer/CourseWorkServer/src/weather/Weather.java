package weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather 
{
	Date time;
	String precipType;
	Date sunriseTime;
	Date sunsetTime;
	float temperature;
	String timeOfDay; 

	public Weather(JSONObject hour,JSONObject day) throws JSONException
	{
		time =  new Date(((long)(Long.parseLong(hour.optString("time"))*1000)));
		temperature = Float.parseFloat(hour.optString("temperature"));
		if(hour.optString("precipType")!="")
		{
			precipType = hour.optString("precipType");
		}
/*		else if(day.optString("precipType")!="")
		{
			precipType = day.optString("precipType");
		}*/
		// 23°F = -5°C; 35.6°F = 2°C
		else if(temperature >= 23 && temperature <= 35.6)
		{
			precipType = "icy";
		}
		else
		{
			precipType = "Clear";
		}
		sunriseTime = new Date(((long)(Long.parseLong(day.optString("sunriseTime"))*1000)));
		sunsetTime =  new Date(((long)(Long.parseLong(day.optString("sunsetTime"))*1000)));
		if (time.before(sunriseTime)||time.after(sunsetTime))
		{
			timeOfDay = "night";
		}
		else
		{
			timeOfDay = "day";
		}
		
	}

	public String getInsertQuery()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "(" + 
				"'" + df.format(time) 	+ "'" + "," + 
				"'" + this.precipType	+ "'" + "," +			
				"'" + this.timeOfDay 	+ "'" +")";
	}

}
