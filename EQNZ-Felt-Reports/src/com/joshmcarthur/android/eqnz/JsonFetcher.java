package com.joshmcarthur.android.eqnz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public class JsonFetcher {
	private static HttpClient _client = new DefaultHttpClient();
	
	public static String fetch_raw(String uri) {
		//The string builder will be used to marshall the raw JSON response
		StringBuilder builder = new StringBuilder();
		
		//Assemble the get request
		HttpGet request = new HttpGet(uri);
		
		try {
			//Fetch the response (note: this could take some time)
			HttpResponse response = _client.execute(request);
			StatusLine status = response.getStatusLine();
			
			//Is the response ok?
			if(status.getStatusCode() == 200) {
				HttpEntity body = response.getEntity();
				InputStream content = body.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				
				//A cache for the reader
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}
			else {
				Log.e(JsonFetcher.class.toString(), "Failed to download " + uri);
			}
		} catch(Exception e) {
			Log.e(JsonFetcher.class.toString(), e.getMessage());
		}
		return builder.toString();
	}
	
	//This will return an array of HashMaps representing the retrieved JSON object
	public static JSONObject fetch(String uri) {
		try {
			String json_string = JsonFetcher.fetch_raw(uri);
			return new JSONObject(json_string);
			
		} catch(Exception e) {
			Log.e(JsonFetcher.class.toString(), e.getMessage());
			return null;
		}
	}
}
