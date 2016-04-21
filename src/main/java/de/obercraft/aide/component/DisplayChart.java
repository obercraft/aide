package de.obercraft.aide.component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.obercraft.aide.dto.Chit;
import de.obercraft.aide.dto.Denizen;
import de.obercraft.aide.dto.DisplayEntry;

@Component
public class DisplayChart {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${application.version}")
	private String version;
	
	
	@Value("${aide.config.display}")
	private String displayFile;

	@Value("${aide.config.chits}")
	private String chitsFile;
	
	@Value("${aide.config.denizens}")
	private String denizensFile;
	
	List<List<DisplayEntry>> chart;
	
	List<Chit> chits;
	
	Map<String,List<Denizen>> denizens;

	@PostConstruct
	public void init() {
		initDenizens();
		initHirelings();
		initChits();
		initDisplay();
	}
	
	private void initChits() {	
		Gson gson = new Gson();			
		Type listOfTestObject = new TypeToken<List<Chit>>(){}.getType();
		String json = loadJson(chitsFile);
		chits = gson.fromJson(json, listOfTestObject);		
	}
	
	private void initDenizens() {
		Gson gson = new Gson();			
		Type listOfTestObject = new TypeToken<Map<String,List<Denizen>>>(){}.getType();
		String json = loadJson(denizensFile);
		denizens = gson.fromJson(json, listOfTestObject);		
		
	}
	
	private void initHirelings() {
		
	}

	private void initDisplay() {
		Gson gson = new Gson();			
		Type listOfTestObject = new TypeToken<List<List<DisplayEntry>>>(){}.getType();
		chart = gson.fromJson(loadJson(displayFile), listOfTestObject);
	}

	private String loadJson(String fileName) {
		try {
			ClassPathResource classPathResource = new ClassPathResource(
					fileName);
			InputStream inputStream = classPathResource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));				
			String json = "";
			String line;

			while ((line = reader.readLine()) != null) {
				json += line;
			}
			return json;
		} catch (Exception e) {
			logger.error("DisplayChart." + fileName, e);
		}		
		return null;
	}
	

	public Object getData() {
		Map<String, Object> data = new HashMap<String, Object>();
			data.put("chart", chart);
			data.put("chits", chits);
			data.put("denizens", denizens);
		return data;
		
	}
		
}
