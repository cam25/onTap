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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.cmozie.ontap.Favorites;
import com.cmozie.ontap.MoreDetails;
import com.cmozie.ontap.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class WhatToDrink.
 */
public class WhatToDrink extends Fragment  {

	public static TextView beerName;
	public static String beerNam;
	public static TextView beerDescription;
	public static String description;
	public static String style;
	public static String description2;
	public static ImageView beerImg;
	public static URL url;
	public static HashMap<String, String> map;

	
	
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

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		beerName = (TextView)getActivity().findViewById(R.id.beerTitle);
		beerDescription = (TextView)getActivity().findViewById(R.id.descript);
		beerImg = (ImageView)getActivity().findViewById(R.id.imageView1);
		getApiResults();
		
		//Fragment fm = getChildFragmentManager().findFragmentByTag("What To Drink");
		
		//Log.i("fragment?", fm.toString());
		
		Log.i("Started", "activit");
	}
	


	public void getApiResults(){
		
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
			 WhatToDrinkRequest wtdRequest = new WhatToDrinkRequest();
			 wtdRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
	
	private class WhatToDrinkRequest extends AsyncTask<URL, Void, String>{
		ProgressDialog progressIndicator;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressIndicator = new ProgressDialog(getActivity());
			progressIndicator.setMessage("Image Loading...");
			progressIndicator.setIndeterminate(false);
			progressIndicator.setCancelable(true);
			progressIndicator.show();
		}

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
				
				//JSONObject details = data.getJSONObject("style");
				
				JSONObject labels = data.getJSONObject("labels");
				 url = new URL(labels.getString("large"));
				Log.i("WTDURL", result);
				
				if (data.has("name")) {
					beerNam = data.getString("name");
					beerName.setText(beerNam);
				}
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				
				if (data.has("description")) {
					description = data.getString("description");
					beerDescription.setText(description);
				}else {
					beerDescription.setText("No Description Available");
				}
				
				
				
				if (labels.has("large")) {
					ImageRequest image = new ImageRequest();
					image.execute(url);
					
				}
				
				
				
				
				
				//beerDescription.setText(description2);
				Log.i("beer", beerNam);
			} catch (JSONException e) {
				// TODO: handle exception
				
				e.printStackTrace();
				beerName.setText(beerNam);
				beerDescription.setText("No description");
				
			}
			
			catch (Exception e) {
				// TODO: handle exception
				beerName.setText("No Beer Title");
			}
			
			
		}
		public class ImageRequest extends AsyncTask<URL, Void, Drawable>
        {

                /* (non-Javadoc)
                 * @see android.os.AsyncTask#doInBackground(Params[])
                 */
                @Override
                protected Drawable doInBackground(URL... urls)
                {
                        
            Drawable draw = null;
          
                    draw = Network.LoadImageFromWebOperations(url.toString());
          
            return draw;
                }
                
                /* (non-Javadoc)
                 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
                 */
                @Override
                protected void onPostExecute(Drawable result) 
                {
                	progressIndicator.dismiss();
                        beerImg.setImageDrawable(result);
            }
        }
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		
	//favorites icon
		case R.id.add:
			
			map = new HashMap<String, String>();
			String nameOfBeer = beerName.getText().toString();
			String descriptionOfbeer = beerDescription.getText().toString();
		
			
			map.put("beerName", nameOfBeer);
			map.put("description", descriptionOfbeer);
			
			storeFile(getActivity(), "favorite", map, true);
			

			break;
			//share icon
		case R.id.share:
			AlertDialog.Builder alert2 = new AlertDialog.Builder(getActivity());
			alert2.setTitle("Share Feature");
			alert2.setMessage("Feature Coming Soon...");
			alert2.setCancelable(false);
			alert2.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert2.show();
			  break;
			  
			  //favorites activity
		case R.id.favorites:
			
			

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	@SuppressWarnings("resource")
	public static Boolean storeFile(Context context, String favorite, Object favs, Boolean external){
		try {
			File file;
			FileOutputStream fos;
			ObjectOutputStream oos;
			if (external) {
				file = new File(context.getExternalFilesDir(null),favorite);
				
				
				fos = new FileOutputStream(file);
				Log.i("file", file.toString());
			}else {
				
				fos = context.openFileOutput(favorite, Context.MODE_PRIVATE);
				Log.i("fos", fos.toString());
			}
			oos = new ObjectOutputStream(fos);
			oos.writeObject(favs);
			oos.close();
			
			fos.close();
			
			Log.i("File ","Saved");
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle("Saved Files");
			alert.setMessage("File Saved!");
			alert.setCancelable(false);
			alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert.show();
		} catch (IOException e) {
			
			Log.i("WRITE ERROR",favorite);
		}
		return true;
	}
	
}
