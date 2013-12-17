package com.cmozie.utils;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.cmozie.ontap.MoreDetails;

public class BreweryNameAsyncTask  extends AsyncTask<URL, Void, String> {
	
		
		
Context context;
		

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(URL... urls) {
			// TODO Auto-generated method stub
			String reply = "";
			for (URL url : urls) {
			reply = Network.URLStringResponse(url);	
			}
			return reply;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");
				
				JSONArray breweryDetails = data.getJSONArray("breweries");
				JSONObject locationDetails = breweryDetails.getJSONObject(0);
				
				
			
				for (int i = 0; i < breweryDetails.length(); i++) {
					JSONObject one = breweryDetails.getJSONObject(0);
					JSONArray two = locationDetails.getJSONArray("locations");
					
					
					Log.i("two", two.toString());
					
					
					
					String name = one.getString("name");
					
					if (one.has("name")) {
						
						MoreDetails.breweryDetails.setText(name);
					}else {
						MoreDetails.breweryDetails.setText("N/A");
					}
					
					
					
				
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
				MoreDetails.breweryDetails.setText("N/A");
				
				
			}
			}
	}


