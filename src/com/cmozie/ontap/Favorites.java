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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class Favorites.
 */
public class Favorites extends Activity {
	HashMap<String, String> storedBeers;
	public static List<Map<String,String>> favs;
	static Context context;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		favs = new ArrayList<Map<String,String>>();
		TextView favBeerName = (TextView)findViewById(R.id.favBeerName);
		TextView favBeerDescription = (TextView)findViewById(R.id.favDescription);
		TextView favBeerType = (TextView)findViewById(R.id.typeOfBeer);
		TextView favBeerABV = (TextView)findViewById(R.id.aBV);
		TextView favBeerAvailability = (TextView)findViewById(R.id.availbleBeer);

		storedBeers = getFavorites();
		
		favs.add(storedBeers);
		//getString();
		String setBeerName = favs.get(0).get("beerName");
		String setBeerDescription = favs.get(0).get("description");
		String setBeerType = favs.get(0).get("type");
		String setBeerABV = favs.get(0).get("abv");
		String setBeerAvailablility = favs.get(0).get("available");
		favBeerType.setText(setBeerType);
		favBeerABV.setText(setBeerABV);
		favBeerAvailability.setText(setBeerAvailablility);
		favBeerName.setText(setBeerName);
		favBeerDescription.setText(setBeerDescription);
		
		Log.i("HISTORY READ", storedBeers.toString());
	      
	}

/**
 * Gets the favorites.
 *
 * @return the favorites
 */
private HashMap<String, String> getFavorites(){
		
		//creates an object named stored that reads the object that is stored in local storage
		Object  favoritedBrews = readStorageObjectFile(this, "favorites", true);
		
		
		//declares the hashmap history variable
		HashMap<String, String> history;
		
		//if theres an error fire alert
		if (favoritedBrews == null) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Saved Beers");
			alert.setMessage("No brews have been added to favorites.");
			alert.setCancelable(false);
			alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert.show();
			history = new HashMap<String, String>();
			
			//else store it into the history
		}	else {
			
			history = (HashMap<String, String>) favoritedBrews;
		}
		return history;
		
	}

/**
 * Read storage object file.
 *
 * @param context the context
 * @param filename the filename
 * @param external the external
 * @return the object
 */
@SuppressWarnings("resource")
public static Object readStorageObjectFile(Context context, String filename, Boolean external){
		
		Object brewContent = new Object();
		try {
			File actualFile;
			FileInputStream inputStream;
			if (external) {
				actualFile = new File(context.getExternalFilesDir(null), filename);
				inputStream = new FileInputStream(actualFile);
			}else{
				actualFile = new File(filename);
				inputStream = context.openFileInput(filename);
				
			}
			ObjectInputStream brewContentIS = new ObjectInputStream(inputStream);
			try {
				brewContent = (Object) brewContentIS.readObject();
			} catch (Exception e) {
				Log.e("READ ERROR","INVALID JAVA OBJECT FILE");
			}
			brewContentIS.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			Log.e("READ ERROR","FILE NOT FOUND" + filename);
			return null;
		}catch (IOException e) {
			Log.e("READ ERROR","I/O ERROR");
			
		}
		return brewContent;
		
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites, menu);
		return true;
	}
	
	 /* (non-Javadoc)
 	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
 	 */
 	@Override
		public boolean onOptionsItemSelected(MenuItem item){
			switch (item.getItemId()) {
			
			case R.id.favorites:
				
				startActivity(new Intent(Favorites.this, Favorites.class));

				break;
			default:
				break;
			}
			return super.onOptionsItemSelected(item);
			
		}

}
