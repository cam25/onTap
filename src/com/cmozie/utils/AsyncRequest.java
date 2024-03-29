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

import org.json.JSONObject;

import com.cmozie.fragclasses.WhatToDrink;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

	// TODO: Auto-generated Javadoc
/**
	 * The Class AsyncRequest.
	 */
	@SuppressLint("NewApi")
	public class AsyncRequest extends AsyncTask<URL, Void, String>{
		 TextView beerName;
		 String beerNam;
	
		 String description;
		
		Context context;	
		
		ProgressDialog progressIndicator;
		
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
				

				JSONObject labels = data.getJSONObject("labels");
				 WhatToDrink.url = new URL(labels.getString("large"));
				 
				 
				if (data.has("name")) {
					WhatToDrink.beerNam = data.getString("name");
					WhatToDrink.beerName.setText(WhatToDrink.beerNam);
				}
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				
				if (data.has("description")) {
					WhatToDrink.description = data.getString("description");
					
					WhatToDrink.beerDescription.setText(WhatToDrink.description);
				}else if (data.has("style")) {
					
				JSONObject style = data.getJSONObject("style");
				
				String styleDescription = style.getString("description");
				
				
				Log.i("Style", styleDescription);
				WhatToDrink.beerDescription.setText(styleDescription);
					
				}else{
				
					WhatToDrink.beerDescription.setText("Description Not Available");
				
			}
				
				if (labels.has("large")) {
					ImageRequest image = new ImageRequest();
					image.execute(WhatToDrink.url);
					
				}
				
				// TODO Auto-generated method stub
				Log.i("ASYNC", result);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}
		
		/**
		 * The Class ImageRequest.
		 */
		public class ImageRequest extends AsyncTask<URL, Void, Drawable>
        {
			
			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			WhatToDrink.loading.show();
				
				
			}

                /* (non-Javadoc)
                 * @see android.os.AsyncTask#doInBackground(Params[])
                 */
                @Override
                protected Drawable doInBackground(URL... urls)
                {
                        
            Drawable draw = null;
          
                    draw = Network.LoadImageFromWebOperations(WhatToDrink.url.toString());
          
            return draw;
                }
                
                /* (non-Javadoc)
                 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
                 */
                @Override
                protected void onPostExecute(Drawable result) 
                {
                	WhatToDrink.loading.dismiss();
                	//set background
                	WhatToDrink.beerImg.setBackground(result);
                        
            }
        }
		
	}

