package com.devjp.basic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APIRequest {
	
	public static JSONObject request(String url, String method) throws ParseException, IOException{
		JSONObject returnObj = new JSONObject();
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection http = (HttpURLConnection) requestUrl.openConnection();
		http.setRequestMethod(method);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setDoOutput(false);
		
		InputStreamReader input = new InputStreamReader(http.getInputStream());
		BufferedReader reader = new BufferedReader(input);
		
		StringBuffer sb = new StringBuffer();
		String inputLine;
		while((inputLine = reader.readLine()) != null) {
			sb.append(inputLine+"\n");
		}
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(sb.toString());
			
		returnObj = (JSONObject) obj;
		
		return returnObj;
	}
	
	public static JSONObject request(String url, String method, Map<String, Object> params) throws IOException, ParseException {
		JSONObject returnObj = new JSONObject();
		
		StringBuilder postData = new StringBuilder();
		
		for(Map.Entry<String, Object> param : params.entrySet()) {
			if(postData.length() != 0) postData.append("&");
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append("=");
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection http = (HttpURLConnection) requestUrl.openConnection();
		http.setRequestMethod(method);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		http.setDoOutput(true);
		http.getOutputStream().write(postDataBytes);
		
		InputStreamReader input = new InputStreamReader(http.getInputStream());
		BufferedReader reader = new BufferedReader(input);
		
		StringBuffer sb = new StringBuffer();
		String inputLine;
		while((inputLine = reader.readLine()) != null) {
			sb.append(inputLine+"\n");
		}
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(sb.toString());
			
		returnObj = (JSONObject) obj;
		
		return returnObj;
	}
}
