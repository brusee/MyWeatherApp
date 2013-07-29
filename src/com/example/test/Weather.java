package com.example.test;

public class Weather {
	protected double log;
	protected double lat;
	//Main
	protected String temp = null;
	protected String pressure = null; 
	protected String humidity = null; 
	protected String temp_min = null; 
	protected String temp_max = null;
	//Wind
	protected String speed = null; 
	protected String deg = null;
	
	//Weather

	protected int id;
	protected String main = null;
	protected String description  = null;
	protected String icon = null;
	
	
	protected String wname = null;
	
	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getWName() {
		return wname;
	}

	public void setWName(String wname) {
		this.wname = wname;
	}
	
	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String getTempMin() {
		return temp_min;
	}

	public void setTempMin(String temp_min) {
		this.temp_min = temp_min;
	}
	public String getTempMax() {
		return temp_max;
	}

	public void setTempMax(String temp_max) {
		this.temp_max = temp_max;
	}
	
	
	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public String getDeg() {
		return deg;
	}

	public void setDeg(String deg) {
		this.deg = deg;
	}
	
	
	
	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
}
