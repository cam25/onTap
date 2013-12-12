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

import com.cmozie.fragclasses.Network;
import com.cmozie.ontap.MoreDetails.getImage;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class BreweryDetails.
 */
public class BreweryDetails extends Activity {

	
	public static String imageURL;
	public static ImageView brewImg;
	public static URL url;
	public static TextView zipcode;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brewery_details);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		zipcode = (TextView)findViewById(R.id.zipcode);
		actionBar.setDisplayShowTitleEnabled(false);
		TextView breweryName = (TextView)findViewById(R.id.breweryName);
		TextView addy = (TextView)findViewById(R.id.address);
		TextView city = (TextView)findViewById(R.id.city);
		TextView state = (TextView)findViewById(R.id.state);
		TextView open = (TextView)findViewById(R.id.open);
		TextView phone = (TextView)findViewById(R.id.phone);
		TextView website = (TextView)findViewById(R.id.website);
		 brewImg = (ImageView)findViewById(R.id.brewImage);
		
		
		 imageURL = getIntent().getExtras().getString("images");
		 
	
		 
		 try {
				url = new URL(imageURL);
				

				getBreweryImage al = new getBreweryImage(); 
				al.execute(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
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
		zipcode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Log.i("textView", zipcode.getText().toString());
				getGPS(zipcode.getText().toString());
			}
		});
	}
	
	public void getGPS(String locationCode){
		Intent mapIntent = new Intent(Intent.ACTION_VIEW,
    			
    			
				//updated map action. Removed gps and implemented map to show location via passed zipcode from intent
				Uri.parse("http://maps.google.com/maps?q="+ locationCode +"&zoom=14&size=512x512&maptype=roadmap&sensor=false"));
		
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
            
                    brewImg.setImageDrawable(result);
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
			
			case android.R.id.home:
				Intent homeIntent = new Intent(this, MoreDetails.class);
				  homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				  startActivity(homeIntent);
				break;
			case R.id.favorites:
				
				startActivity(new Intent(BreweryDetails.this, Favorites.class));

				break;
			default:
				break;
			}
			return super.onOptionsItemSelected(item);
			
		}
}
