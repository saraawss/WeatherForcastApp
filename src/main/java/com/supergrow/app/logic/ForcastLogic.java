package com.supergrow.app.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.supergrow.app.domain.Forcast;
import com.supergrow.app.domain.MainList;

public class ForcastLogic {
	private MainList mainList;
	
	public ForcastLogic(MainList mainList) {
		this.mainList = mainList;
	}
	
	public ForcastLogic() {
		
	}
	
	//To get the summary forcast of 5 days
	public MainList getSummaryForcast() {
		
		MainList mainLists = new MainList();
		List<Forcast> forcasts = new ArrayList<>();
		List<Forcast> forcastLists = this.mainList.getListItem();
		
		forcasts = forcastLists.stream().filter(p -> this.getFilterDuration(p.getDt_txt(), new Date()) == true).collect(Collectors.toList());
		
		mainLists.setListItem(forcasts);
		mainLists.setCity(this.mainList.getCity());
		
		return mainLists;
	}
	
	//To get the detail forcast of 5 days
		public MainList getDetailForcast(String date) {
			
			MainList mainLists = new MainList();
			List<Forcast> forcasts = new ArrayList<>();
			List<Forcast> forcastLists = this.mainList.getListItem();
			
			System.out.println("date>> "+date);
			
			forcasts = forcastLists.stream().filter(p ->  (formatData(p.getDt_txt())).equals(formatData(date))).collect(Collectors.toList());
			
			mainLists.setListItem(forcasts);
			mainLists.setCity(this.mainList.getCity());
			
			return mainLists;
		}
	
	//To pick the closest time
	public boolean getFilterDuration(String dateTime, Date currentDateTime) {
		boolean isValid = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			
			String forcastTime = (dateTime).split("\\s")[1].split("\\.")[0];
			String currentTime = getFormatNumber(currentDateTime.getHours()) +":"+ getFormatNumber(currentDateTime.getMinutes()) +":" + getFormatNumber(currentDateTime.getSeconds());
						
			String currentDateTimeStrFormat = sdf.format(currentDateTime);
			
			String forcastDateTimeStr = currentDateTimeStrFormat + " " + forcastTime;
			String currentDateTimeStr = currentDateTimeStrFormat + " " + currentTime;
			
			Date forcastDateTime = new Date(forcastDateTimeStr);
			currentDateTime = new Date(currentDateTimeStr.replaceAll("-", "/"));
			
						
			if(currentDateTime.after(forcastDateTime) || currentDateTime.equals(forcastDateTime)) {
				long diff = currentDateTime.getTime() - forcastDateTime.getTime();
				long diffHours = diff / (60 * 60 * 1000);  
				System.out.println("diffHours>> "+diffHours);
				if(diffHours < 3) {
					isValid = true;
				}
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return isValid;
	}
	
	private String getFormatNumber(int num) {
		String numStr = "";
		
		if(num < 10) {
			numStr = "0" + num;
		}else {
			numStr = num+"";
		}
		return numStr;
	}
	
	private String formatData(String date) {
		
		String formatDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			Date formatDate_ = sdf.parse(date);
			formatDate = sdf.format(formatDate_);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("formatDate>> "+formatDate);
		return formatDate;
		
	}
	
}
