package com.supergrow.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
	private double speed;
	private double deg;
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDeg() {
		return deg;
	}
	public void setDeg(double deg) {
		this.deg = deg;
	}
	
}
