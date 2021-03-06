package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//Tarea en segundo plano para realizar la petici�n al servicio web
public class WeatherTask extends AsyncTask<Double, Void, Weather> {

	String response = null;
	
	double latitude;
	double longitude;
	
	Weather weather = null;
	// Keep a reference of the father activity to be able to change its interface
	private Context context = null;
	
	@Override
	protected void onPreExecute() {
		Log.d("app","onPreExecute");
	}
	
	
	protected Weather doInBackground(Double... params) {
		// Web Service
		HttpURLConnection con = null;
		Log.d("app","doInBackground");
		try {
			latitude = params[0];
			longitude = params[1];
			// Create a new list of name-pair values to use as parameters in the request
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			// Add the name and friend's name
			
			pairs.add(new BasicNameValuePair("APPID",   "<PUT YOUR API KEY HERE>"));
			pairs.add(new BasicNameValuePair("lat",  String.valueOf(latitude) ));
			pairs.add(new BasicNameValuePair("lon",   String.valueOf(longitude)));
			pairs.add(new BasicNameValuePair("lang",  "sp"));
			pairs.add(new BasicNameValuePair("units",  "metric"));
			
			
			
			// Create a new URL
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather" +"?"+ URLEncodedUtils.format(pairs,"utf-8"));
			// Get a new connection to the required resource
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			// Set the header to ask for JSON replies
			con.setRequestProperty("Accept", "application/json");
			
			// Process the response using a BufferedReader
			BufferedReader  reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			StringBuffer buffer = new StringBuffer();
			String s = null;
			while((s = reader.readLine()) != null) {
				buffer.append(s);
			}
			
			response = buffer.toString();
			Log.d("app",response);
		}
		catch (UnsupportedEncodingException uee) {
			Log.d("DEBUG", "UnsupportedEncodingException while processing the PUT friend's name");
		}
		catch (IOException ioe) {
			Log.d("DEBUG", "IOException while processing the PUT friend's email");
		} 
		finally {
			// Release connection
			con.disconnect();
		}
		
		
		// try parse the string to a JSON object
        try {
        	//Generate the jsonObject form respobnse
        	JSONObject jsonObject = new JSONObject(response);
        	
        	weather = new Weather();
        	
        	weather.setWName(jsonObject.getString("name"));
        	
        	JSONArray array = jsonObject.getJSONArray("weather");
        
        	JSONObject object = array.getJSONObject(0);

        	weather.setMain(object.getString("main"));
        	weather.setDescription(object.getString("description"));
        	weather.setIcon(object.getString("icon"));
        	
        	JSONObject object2 = jsonObject.getJSONObject("main");
        	weather.setTemp(object2.getString("temp"));
        	weather.setTempMin(object2.getString("temp_min"));
        	weather.setTempMax(object2.getString("temp_max"));
        	
        	weather.setPressure(object2.getString("pressure"));
        	weather.setHumidity(object2.getString("humidity"));
        	
        	JSONObject object3 = jsonObject.getJSONObject("wind");
        	weather.setSpeed(object3.getString("speed"));
        	weather.setDeg(object3.getString("deg"));
        	
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
		
		return weather;
	}
	
	public void onPostExecute(Weather weather) {
		super.onPostExecute(weather);
		//Paint the layout
		((MainActivity) context).updateMyLayout(weather);
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
}
