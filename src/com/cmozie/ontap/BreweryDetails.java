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

import java.net.MalformedURLException;
import java.net.URL;

import com.cmozie.fragclasses.FindABrew;
import com.cmozie.ontap.MoreDetails.getImage;
import com.cmozie.utils.Network;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class BreweryDetails.
 */
@SuppressLint("NewApi")
public class BreweryDetails extends Activity {

	
	public static String imageURL;
	public static ImageView brewImg;
	public static URL url;
	public static TextView zipcode;
	public static TextView addy;
	public TextView city;
	 public static final String filename = "alert";
	 public static SharedPreferences prefs;
	 public static Boolean isOkay;
	 public TextView phone;
	 public TextView website;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brewery_details);
		
		
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
		
		actionBar.setDisplayShowTitleEnabled(false);
		TextView breweryName = (TextView)findViewById(R.id.breweryName);
		TextView state = (TextView)findViewById(R.id.state);
		TextView open = (TextView)findViewById(R.id.open);
		addy = (TextView)findViewById(R.id.address);
		city = (TextView)findViewById(R.id.city);
		zipcode = (TextView)findViewById(R.id.zipcode);
		phone = (TextView)findViewById(R.id.phone);
		website = (TextView)findViewById(R.id.website);
		brewImg = (ImageView)findViewById(R.id.brewImage);
		
		
		 imageURL = getIntent().getExtras().getString("images");
		 
	
		 
		 try {
				url = new URL(imageURL);
				Log.i("URL", url.toString());

				getBreweryImage al = new getBreweryImage(); 
				al.execute(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				brewImg.setImageDrawable(getResources().getDrawable(R.drawable.brewery));
				e.printStackTrace();
			}
		 
		breweryName.setText(getIntent().getExtras().getString("name"));
		addy.setText(getIntent().getExtras().getString("streetAddress"));
		city.setText(getIntent().getExtras().getString("locality"));
		state.setText(getIntent().getExtras().getString("region"));
		
		zipcode.setText(getIntent().getExtras().getString("postalCode"));
		if (city.getText().toString() == "N/A") {
			city.setText(getIntent().getExtras().getString("countryName"));
		}
		open.setText(getIntent().getExtras().getString("openToPublic"));
		phone.setText(getIntent().getExtras().getString("phone"));
		website.setText(getIntent().getExtras().getString("website"));
		
		
		addy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Log.i("textView", zipcode.getText().toString());
			try {
				
				getGPS(addy.getText().toString(), city.getText().toString(),zipcode.getText().toString());
			} catch (ActivityNotFoundException e) {
				// TODO: handle exception
				
				addy.setClickable(false);
			}
				
			}
		});
		
		website.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		        try {
		        	String web =  website.getText().toString();
			        Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web)); 
			        startActivity(callIntent);
				} catch (ActivityNotFoundException e) {
					// TODO: handle exception
					
					website.setClickable(false);
				}
			}
		});
		
		
	}
	@Override
	   public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    onBackPressed();

	}

	return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
	MoreDetails.breweryDetails.setClickable(true);
	Log.i("back", "press");
	finish();
	return;
	    }
	
	public void getGPS(String address,String locality,String locationCode){
		Intent mapIntent = new Intent(Intent.ACTION_VIEW,
    			
    			
				//updated map action. Removed gps and implemented map to show location via passed zipcode from intent
				Uri.parse("http://maps.google.com/maps?q="+ address + " " + locality +  " " + locationCode +"&zoom=14&size=512x512&maptype=roadmap&sensor=false"));
		
		Log.i("URI", Uri.parse("http://maps.google.com/maps?q="+ address + " " + locality +  " " + locationCode +"&zoom=14&size=512x512&maptype=roadmap&sensor=false").toString());

		startActivity(mapIntent);
	}
	public class getBreweryImage extends AsyncTask<URL, Void, Drawable>
    {

            /* (non-Javadoc)
             * @see android.os.AsyncTask#doInBackground(Params[])
             */
            @Override
            protected Drawable doInBackground(URL... urls)
            {
                    
        Drawable draw = null;
      
                draw = Network.LoadImageFromWebOperations(imageURL);
      
        return draw;
            }
            
            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(Drawable result) 
            {
            	if (result == null) {
					Log.i("Results", "Null");
					
					brewImg.setImageDrawable(getResources().getDrawable(R.drawable.brewery));
				}
            	
            	//progressIndicator.dismiss();
            	brewImg.setBackground(result);
            	
                   // brewImg.setImageDrawable(result);
        }
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.brewery_details, menu);
		return true;
	}
	 @Override
		public boolean onOptionsItemSelected(MenuItem item){
			switch (item.getItemId()) {
			
			case R.id.favorites:
				
				startActivity(new Intent(BreweryDetails.this, Favorites.class));

				break;
			default:
				break;
			}
			return super.onOptionsItemSelected(item);
			
		}
}
