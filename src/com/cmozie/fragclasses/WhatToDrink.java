/*
 * project 			onTap
 * 
 * package			com.cmozie.fragclasses
 * 
 * name				cameronmozie
 * 
 * date				Dec 5, 2013
 */
package com.cmozie.fragclasses;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.cmozie.ontap.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class WhatToDrink.
 */
public class WhatToDrink extends Fragment {

	public static TextView beerName;
	public static String beerNam;
	public static TextView beerDescription;
	public static String description;
	public static String style;
	public static String description2;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.whattodrink, container,false);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		beerName = (TextView)getActivity().findViewById(R.id.beerTitle);
		beerDescription = (TextView)getActivity().findViewById(R.id.descript);
		
		getApiResults();
		
		Log.i("Started", "activit");
	}
	


	

	public void getApiResults(){
		
		String baseUrl = "http://api.brewerydb.com/v2/beer/random?key=4b77a2665f85f929d4a87d30bbeae67b";
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
			 WhatToDrinkRequest wtdRequest = new WhatToDrinkRequest();
			 wtdRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
	
	private class WhatToDrinkRequest extends AsyncTask<URL, Void, String>{

		@Override
		protected String doInBackground(URL... urls) {
			// TODO Auto-generated method stub
			String reply = "";
			for (URL url : urls) {
			reply = Network.URLStringResponse(url);	
			}
			return reply;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");
				JSONObject style = data.getJSONObject("style");
				Log.i("WTDURL", result);
				
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				beerNam = data.getString("name");
				
				description2 = style.getString("description");
				
				
				beerName.setText(beerNam);
				beerDescription.setText(description);
				beerDescription.setText(description2);
				Log.i("beer", beerNam);
			} catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
				
			}
		}
		
	}
	
}
