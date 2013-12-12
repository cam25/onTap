/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 4, 2013
 */
package com.cmozie.ontap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class Favorites.
 */
public class Favorites extends Activity {
	HashMap<String, String> _history;
	public static List<Map<String,String>> favs;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		favs = new ArrayList<Map<String,String>>();
		TextView favBeerName = (TextView)findViewById(R.id.favBeerName);
		TextView favBeerDescription = (TextView)findViewById(R.id.favDescription);
		TextView favBeerType = (TextView)findViewById(R.id.typeOfBeer);
		TextView favBeerABV = (TextView)findViewById(R.id.aBV);
		TextView favBeerAvailability = (TextView)findViewById(R.id.availbleBeer);

		_history = getHistory();
		
		favs.add(_history);
		
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
		
		Log.i("HISTORY READ", _history.toString());
	      
	}
private HashMap<String, String> getHistory(){
		
		//creates an object named stored that reads the object that is stored in local storage
		Object  saved = readObjectFile(this, "favorites", true);
		
		
		//declares the hashmap history variable
		HashMap<String, String> history;
		
		//if theres an error fire alert
		if (saved == null) {
			Log.i("HISTORY","NO HISTORY FILE FOUND");
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Saved Files");
			alert.setMessage("There are no saved zipcodes in local storage. Once a search is made the zipcode will be saved.");
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
			
			history = (HashMap<String, String>) saved;
		}
		return history;
		
	}

@SuppressWarnings("resource")
public static Object readObjectFile(Context context, String filename, Boolean external){
		
		Object content = new Object();
		try {
			File file;
			FileInputStream fin;
			if (external) {
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			}else{
				file = new File(filename);
				fin = context.openFileInput(filename);
				
			}
			ObjectInputStream ois = new ObjectInputStream(fin);
			try {
				content = (Object) ois.readObject();
			} catch (Exception e) {
				Log.e("READ ERROR","INVALID JAVA OBJECT FILE");
			}
			ois.close();
			fin.close();
		} catch (FileNotFoundException e) {
			Log.e("READ ERROR","FILE NOT FOUND" + filename);
			return null;
		}catch (IOException e) {
			Log.e("READ ERROR","I/O ERROR");
			
		}
		return content;
		
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

}
