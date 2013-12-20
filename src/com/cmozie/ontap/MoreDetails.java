/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 19, 2013
 */
package com.cmozie.ontap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmozie.fragclasses.WhatToDrink;
import com.cmozie.fragclasses.FindABrew.SearchAsyncTask;
import com.cmozie.utils.BreweryNameJSON;
import com.cmozie.utils.Network;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

// TODO: Auto-generated Javadoc
/**
 * The Class MoreDetails.
 */
public class MoreDetails extends Activity {

	
	public static String beerId;
	public static TextView descriptionTitle;
	public static ImageView beerImage;
	public static URL url;
	public static String imagURL;
	public TextView type;
	public TextView availble;
	public TextView beersName;
	public TextView  abv;
    //public static final String filename = "favorites";
    public static HashMap<String, String> map;
	public static List<Map<String,String>> favs;
	 public static final String filename = "storedContents";
	public static Context context;
	public static AlertDialog.Builder alert;
	public static TextView breweryDetails;
	ProgressDialog progressIndicator;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_details);
		
		//actionbar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    //ui elements
		 breweryDetails = (TextView)findViewById(R.id.breweryDetails);	
		beersName = (TextView) findViewById(R.id.beerName);
		 abv = (TextView) findViewById(R.id.abv);
		type = (TextView)findViewById(R.id.beertype);
		 availble = (TextView)findViewById(R.id.available);
		descriptionTitle = (TextView) findViewById(R.id.description);
		beerImage = (ImageView)findViewById(R.id.beerLogo);
		
		
		//getting intents
		 if ( getIntent() != null )
		 {
		 Bundle extras = getIntent().getExtras();
		  if (extras != null)
		 {
		  imagURL = extras.getString("styleDescription");
		  beersName.setText(extras.getString("name"));
		  abv.setText(extras.getString("abv") + "%");
		  descriptionTitle.setText(extras.getString("description"));
		  beerId = extras.getString("id");
		  type.setText(extras.getString("style"));
		  availble.setText(extras.getString("available"));
		 //loadDoc();
		 }

		 }if (getIntent() == null) {
			Log.i("Intent", "Is Null");
		}
		 
		BreweryNameJSON.getBreweryDetails(beerId);
		 progressIndicator = new ProgressDialog(this);
			progressIndicator.setMessage("Getting Info...");
			progressIndicator.setIndeterminate(false);
			progressIndicator.setCancelable(true);
			progressIndicator.show();
		 //parsing image
		 try {
			//setting shareprefrences equal to my static string filename	
		
				
				if (url != null) {
					url = new URL(imagURL);
					
				}

				getImage imageRecieve = new getImage(); 
				imageRecieve.execute(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		breweryDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sets textview to
				breweryDetails.setClickable(false);
				Log.i("beerid", beerId);
				getBreweryDetails(beerId);
				
			}
		});
	}
	

	/**
	 * Gets the brewery details.
	 *
	 * @param beerId the beer id
	 * @return the brewery details
	 */
	public  void getBreweryDetails(String beerId){

		
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
				 brewDetails breweryDetailRequest = new brewDetails();
				
				 breweryDetailRequest.execute(finalURL2);
				
				
					
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("BAD URL", "URL MALFORMED");
			}
			
			
		}
	
	/**
	 * The Class getImage.
	 */
	@SuppressLint("NewApi")
	public class getImage extends AsyncTask<URL, Void, Drawable>
    {

            /* (non-Javadoc)
             * @see android.os.AsyncTask#doInBackground(Params[])
             */
            @Override
            protected Drawable doInBackground(URL... urls)
            {
                    
        Drawable draw = null;
      
                draw = Network.LoadImageFromWebOperations(imagURL);
      
        return draw;
            }
            
            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
           
            protected void onPostExecute(Drawable result) 
            {
            	progressIndicator.dismiss();
            	
            	beerImage.setBackground(result);
           

            	if (result == null) {
					Log.i("Results", "Null");
					
					beerImage.setImageDrawable(getResources().getDrawable(R.drawable.loadbeerimage));
				}
            
                   // beerImage.setImageDrawable(result);
        }
    }
	
	/**
	 * The Class brewDetails.
	 */
	public class brewDetails extends AsyncTask<URL, Void, String>{
		
		

		
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
					JSONArray locations = locationDetails.getJSONArray("locations");
					
					
					Log.i("two", locations.toString());
					Intent intent = new Intent(MoreDetails.this, BreweryDetails.class);

					if (one.has("name")) {
						String name = one.getString("name");
						intent.putExtra("name", name);
						
					}
					if (one.has("images")) {
						JSONObject image = one.getJSONObject("images");
						intent.putExtra("images", image.getString("large"));
						Log.i("intent", intent.toString());
					}
					for (int j = 0; j < locations.length(); j++) {
						JSONObject locationInfo = locations.getJSONObject(0);
						
						JSONObject country = locationInfo.getJSONObject("country");
						
						Log.i("country", country.toString());
						
						//address
						if (locationInfo.has("streetAddress")) {
							String address = locationInfo.getString("streetAddress");
							intent.putExtra("streetAddress", address);
						}else {
							intent.putExtra("streetAddress", "N/A");
						}
						
						//locality
						if (locationInfo.has("locality")) {
							String city = locationInfo.getString("locality");
							intent.putExtra("locality", city);
						}else{
							intent.putExtra("locality", "N/A");
						}
						
						
						//region
						if (locationInfo.has("region")) {
							String state = locationInfo.getString("region");
							intent.putExtra("region", state);
						}else{
							intent.putExtra("region", "N/A");
						}
						
						//postal code
						if (locationInfo.has("postalCode")) {
							String zipcode = locationInfo.getString("postalCode");
							intent.putExtra("postalCode", zipcode);
							
						}else{
							intent.putExtra("postalCode", "N/A");
						}
						
						//country
						if (country.has("name")) {
							
							
							String countryName = country.getString("name");
							intent.putExtra("countryName", countryName);
						}
						
						
						//foreign zipcode
						if (country.has("numberCode")) {
							
							
							String numberCode = country.getString("numberCode");
							intent.putExtra("countryName", numberCode);
						}else {
							intent.putExtra("countryName", "N/A");
						}
						
						//open
						if (locationInfo.has("openToPublic")) {
							String open = locationInfo.getString("openToPublic");
							intent.putExtra("openToPublic", open);
							
						}else{
							intent.putExtra("openToPublic", "N/A");
						}
						
						//phone
						if (locationInfo.has("phone")) {
							String phone = locationInfo.getString("phone");
							intent.putExtra("phone", phone);
							
						}else{
							intent.putExtra("phone", "N/A");
						}
						
						//website
						if (locationInfo.has("website")) {
							String website = locationInfo.getString("website");
							intent.putExtra("website", website);
							
						}else{
							intent.putExtra("website", "N/A");
						}

					}
					
				
					startActivityForResult(intent, 0);
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				AlertDialog.Builder alert = new AlertDialog.Builder(MoreDetails.this);
				alert.setTitle("Not Available");
				alert.setMessage("Brewery details not available for this beer.");
				alert.setCancelable(false);
				alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});
				alert.show();
				e.printStackTrace();
				
				
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_details, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
	
		
		

		case R.id.add:
			//storage 
			map = new HashMap<String, String>();
			String nameOfBeer = beersName.getText().toString();
			String descriptionOfbeer = descriptionTitle.getText().toString();
			String typeOfBeer = type.getText().toString();
			String abvOfBeer = abv.getText().toString();
			String availablilityOfBeer = availble.getText().toString();
			
			map.put("beerName", nameOfBeer);
			map.put("description", descriptionOfbeer);
			map.put("type", typeOfBeer);
			map.put("abv", abvOfBeer);
			map.put("available", availablilityOfBeer);
			storeFile(this, "favorites", map, true);
			

			break;
			//share icon
		case R.id.shareDetails:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_TEXT,"Beer's Name:" + beersName.getText().toString() + "\n" + "Beer Description" + "\n " + descriptionTitle.getText().toString());
			startActivity(intent); 
			  break;
			  
			  //favorites activity
		case R.id.favorites:
			
			startActivity(new Intent(MoreDetails.this, Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	/**
	 * Store file.
	 *
	 * @param context the context
	 * @param favorite the favorite
	 * @param favs the favs
	 * @param external the external
	 * @return the boolean
	 */
	@SuppressWarnings("resource")
	public static Boolean storeFile(Context context, String favorite, Object favs, Boolean external){
		try {
			File favBeerFile;
			FileOutputStream outputFile;
			ObjectOutputStream favsObjectOutputStream;
			if (external) {
				favBeerFile = new File(context.getExternalFilesDir(null),favorite);
				
				
				outputFile = new FileOutputStream(favBeerFile);
				Log.i("file", favBeerFile.toString());
			}else {
				
				outputFile = context.openFileOutput(favorite, Context.MODE_PRIVATE);
				Log.i("fos", outputFile.toString());
			}
			favsObjectOutputStream = new ObjectOutputStream(outputFile);
			favsObjectOutputStream.writeObject(favs);
			favsObjectOutputStream.close();
			
			outputFile.close();
			
			Log.i("File ","Saved");
			AlertDialog.Builder alert2 = new AlertDialog.Builder(context);
			alert2.setTitle("Favorites");
			alert2.setMessage("Beer Saved To Favorites!");
			alert2.setCancelable(false);
			alert2.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert2.show();
		} catch (IOException e) {
			
			Log.i("WRITE ERROR",favorite);
		}
		return true;
	}

}
