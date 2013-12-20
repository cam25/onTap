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

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.cmozie.ontap.BreweryDetails;
import com.cmozie.ontap.MoreDetails;
import com.cmozie.ontap.MoreDetails.brewDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class BreweryNameJSON.
 */
public class BreweryNameJSON {

/**
 * Gets the brewery details.
 *
 * @param beerId the beer id
 * @return the brewery details
 */
public static  void getBreweryDetails(String beerId){

		
		String query = "http://api.brewerydb.com/v2/beer/"+beerId+"/?withBreweries=Y&key=4b77a2665f85f929d4a87d30bbeae67b";

			String queryString;
			String queryString2;
			try {
				

				queryString2 = URLEncoder.encode(query,"UTF-8");
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("ERROR-URL", "ENCODING ISSUE");
				queryString = "";
			}
			
			 query = "http://api.brewerydb.com/v2/beer/"+beerId+"/?withBreweries=Y&key=4b77a2665f85f929d4a87d30bbeae67b";
			URL finalURL2;
			try {
				
				 finalURL2 = new URL(query);
				 Log.i("finalurl", finalURL2.toString());

				 
				 	BreweryNameAsyncTask brewNameRequest = new BreweryNameAsyncTask();
					 brewNameRequest.execute(finalURL2);
				
				
					
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("BAD URL", "URL MALFORMED");
			}
			

			
		


	
}
}
