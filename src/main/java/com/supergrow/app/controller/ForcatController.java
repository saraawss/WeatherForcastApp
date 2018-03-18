package com.supergrow.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.supergrow.app.config.AppConfig;
import com.supergrow.app.domain.Forcast;
import com.supergrow.app.domain.MainList;
import com.supergrow.app.logic.ForcastLogic;

@RestController
public class ForcatController {
	
	@Autowired 
    private HttpSession session;
	
	//To get the forcast data from the api
	@RequestMapping( value = "/getForcast" , produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public MainList getForcast(@RequestParam("lat") String lat , @RequestParam("lon") String lon) {
		
			if(StringUtils.isEmpty(lat) || StringUtils.isEmpty(lon)) {
				lat = AppConfig.LAT;
				lon = AppConfig.LON;
			}
			
			String url = AppConfig.WEATHER_ENDPOINT+"?lat="+lat+"&lon="+lon+"&appid="+AppConfig.API_KEY;
			
			RestTemplate restTemplate = new RestTemplate();
			MainList mainList = restTemplate.getForObject(url, MainList.class);
		    
			ForcastLogic forcastLogic = new ForcastLogic(mainList);
			MainList mainListData = new MainList();
			mainListData = forcastLogic.getSummaryForcast();
			
			session.setAttribute("FullList", mainList.getListItem());
			
			return mainListData;
	}
	
		//To get the forcast detail from the api
		@RequestMapping( value = "/getForcastDetail/{date}" , produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
		public MainList getForcastDetail(@PathVariable("date") String date) {
			
			List<Forcast> forcastFullList = (List<Forcast>) session.getAttribute("FullList");
			
			MainList mainList = new MainList();
			mainList.setListItem(forcastFullList);
			ForcastLogic forcastLogic = new ForcastLogic(mainList);
			mainList = forcastLogic.getDetailForcast(date);
			return mainList;
		}
	
}
