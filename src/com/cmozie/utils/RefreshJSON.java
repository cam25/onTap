/*
 * project 			onTap
 * 
 * package			com.cmozie.utils
 * 
 * name				cameronmozie
 * 
 * date				Dec 19, 2013
 */
package com.cmozie.utils;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.cmozie.fragclasses.WhatToDrink;


// TODO: Auto-generated Javadoc
/**
 * The Class RefreshJSON.
 */
public class RefreshJSON {
	
	 TextView beerName;
	 String beerNam;
	TextView beerDescription;
	 String description;
	
 /**
  * Gets the api results.
  *
  * @return the api results
  */
 public static void getApiResults(){
	 
		String baseUrl = "http://api.brewerydb.com/v2/beer/random/?hasLabels=Y&key=4b77a2665f85f929d4a87d30bbeae67b";
		String queryString;
		try {
			queryString = URLEncoder.encode(baseUrl,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		URL finalURL;
		try {
			 finalURL = new URL(baseUrl);
			 AsyncRequest wtdRequest = new AsyncRequest();
			 wtdRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
	
	
		/**
		 * On post execute.
		 *
		 * @param result the result
		 */
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");
				
				Log.i("JSON", result);
				
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				if (data.has("name")) {
					WhatToDrink.beerNam = data.getString("name");
					WhatToDrink.beerName.setText(WhatToDrink.beerNam);
				}
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				
				if (data.has("description")) {
					WhatToDrink.description = data.getString("description");
					
					WhatToDrink.beerDescription.setText(WhatToDrink.description);
				}else {
					beerDescription.setText("No Description Available");
				}
				
			
				//beerName.setText(beerNam);
				Log.i("beer", beerNam);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}


