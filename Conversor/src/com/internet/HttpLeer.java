package com.internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpLeer {
    
	private HttpURLConnection conn;

    private final String USER_AGENT = "Mozilla/5.0";
    
    public String GetPageContent(String url)  {

    	try {
			URL obj = new URL(url);
			conn = (HttpURLConnection) obj.openConnection();
			
			// default is GET
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			
			// act like a browser
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Accept",
			    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
			
			int responseCode = conn.getResponseCode();

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = 
			        new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
			    response.append(inputLine);
			}
			in.close();
			
			return response.toString();
    	} catch(Exception e) {
    		System.out.print(e.getMessage());
    		return null;
    	}

    }
}
