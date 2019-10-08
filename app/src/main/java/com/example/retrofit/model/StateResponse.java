package com.example.retrofit.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StateResponse{

	@SerializedName("cities")
	private List<String> cities;

	@SerializedName("state")
	private String state;

	public void setCities(List<String> cities){
		this.cities = cities;
	}

	public List<String> getCities(){
		return cities;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"StateResponse{" + 
			"cities = '" + cities + '\'' + 
			",state = '" + state + '\'' + 
			"}";
		}
}