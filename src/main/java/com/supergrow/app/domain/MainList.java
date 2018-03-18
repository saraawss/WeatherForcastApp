package com.supergrow.app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainList {
	
	private String cod;
	private double message;
	private int cnt;
	private City city;
		
	@JsonProperty("list")
	private List<Forcast> listItem;
	
	

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public double getMessage() {
		return message;
	}

	public void setMessage(double message) {
		this.message = message;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Forcast> getListItem() {
		return listItem;
	}

	public void setListItem(List<Forcast> listItem) {
		this.listItem = listItem;
	}

	
	

	
	

}
