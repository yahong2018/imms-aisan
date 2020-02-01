package com.zhxh.imms.general;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonTest {
	
	@org.junit.Test
	public void testReadJson() {
		String str = "{\"StationID\":\"001\",\"StationName\":\"刘永红\"}";
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		Station station = gson.fromJson(str, Station.class);
		
		System.out.println("StationID:"+station.getStationID()+",StationName:"+station.getStationName());
	}
	
	@org.junit.Test
	public void testCreateJson() {
		Station station = new Station();
		station.setStationID("001");
		station.setStationName("刘永红");
		station.setGID(1L);
		station.setStartDID(1);
		station.setEndDID(30);
		
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		String jsonString = gson.toJson(station);
		System.out.println(jsonString);
	}
}

class Station{
	private String stationID;
	private String stationName;
	private long GID;	
	private int startDID;
	private int endDID;
	
	public String getStationID() {
		return stationID;
	}
	public void setStationID(String stationID) {
		this.stationID = stationID;
	}
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public long getGID() {
		return GID;
	}
	public void setGID(long gID) {
		this.GID = gID;
	}

	public int getStartDID() {
		return startDID;
	}
	public void setStartDID(int startDID) {
		this.startDID = startDID;
	}
	public int getEndDID() {
		return endDID;
	}
	public void setEndDID(int endDID) {
		this.endDID = endDID;
	}
}
