package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI 
{
	String sUrl;
	String keyAPI;
	String latitude;
	String longtitude;
	URL url;
	HttpsURLConnection connection;
	
	public WeatherAPI(String day) throws IOException
	{
		sUrl = "https://api.darksky.net/forecast/";
		keyAPI = ""; // Get your key here
		latitude = "55.7522"; //Europe/Moscow
		longtitude = "37.6156"; //Europe/Moscow
		
		url = new URL(sUrl + keyAPI + "/" + latitude + "," 
					 + longtitude +"," + day+"T"+"12:00:00"
					 + "?exclude=flags");
		connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
	}
	
	public ArrayList<Weather> getWeatherInfo() throws IOException, JSONException
	{
		System.out.println("Connection to darksky...");
		connection.connect();
		if(HttpsURLConnection.HTTP_OK == connection.getResponseCode())
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			JSONObject answ = new JSONObject(br.readLine());
			JSONArray hours =  answ.getJSONObject("hourly").getJSONArray("data");
			JSONArray day   =  answ.getJSONObject("daily").getJSONArray("data");
			ArrayList<Weather> weathers = new ArrayList<Weather>();
			
			System.out.println("Parsing the answer from darksky...");
			for (int i=0;i<hours.length();i++)
			{
				weathers.add(new Weather((JSONObject)hours.get(i),(JSONObject)day.get(0)));
			}
			System.out.println("Parsing completed");
			return weathers;
		}
		return null;
	}	
}
