package com.supergrow.app.WeatherForcastApp;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supergrow.app.controller.ForcatController;
import com.supergrow.app.domain.Forcast;
import com.supergrow.app.domain.MainList;
import com.supergrow.app.logic.ForcastLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


@SpringBootTest
public class AppTest {
    

	
//	@Test
//	public void getForcastData() {
//		double lat = 35;
//		double lon= 139;
//		ForcatController forcatcontroller = new ForcatController();
//		MainList mainList = forcatcontroller.getForcast(lat, lon);
//		
//		System.out.println("forcast>>"+mainList.getListItem().toString());
//		
//		
//		Assert.assertEquals(mainList.getCod(), "200");
//	}
	
//	@Test
//	public void getForcastDetailData() {
//		String lat = "35";
//		String lon= "139";
//		String date = "2018-03-19 09:00:00";
//		ForcatController forcatcontroller = new ForcatController();
//		MainList mainList = forcatcontroller.getForcastDetail(lat, lon, date);
//		
//		System.out.println("forcast>>"+mainList.getListItem().toString());
//		
//		
//		Assert.assertEquals(mainList.getCod(), "200");
//	}
	
	@Test
	public void getTimeDuration() {
		boolean isValid = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate = new Date("2018/03/16 09:00:00");
			String date_txt = "2018-03-17 09:00:00";
			
			ForcastLogic forcastLogic = new ForcastLogic();
			isValid = forcastLogic.getFilterDuration(date_txt,currentDate);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(isValid, true);
		
	}
}
