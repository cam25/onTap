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

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmozie.fragclasses.Network;
import com.cmozie.fragclasses.FindABrew.SearchAsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_details);
		
		//actionbar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    actionBar.setDisplayShowTitleEnabled(false);
		TextView breweryDetails = (TextView)findViewById(R.id.breweryDetails);
		
		TextView beersName = (TextView) findViewById(R.id.beerName);
		TextView abv = (TextView) findViewById(R.id.abv);
		TextView descriptionTitle = (TextView) findViewById(R.id.description);
		
		beersName.setText(getIntent().getExtras().getString("name"));
		abv.setText(getIntent().getExtras().getString("abv"));
		descriptionTitle.setText(getIntent().getExtras().getString("description"));
		 beerId = getIntent().getExtras().getString("id");
		breweryDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Log.i("beerid", beerId);
				getBreweryDetails(beerId);
				
			}
		});
	}
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
				 brewDetails wtdRequest = new brewDetails();
				
					 wtdRequest.execute(finalURL2);
				
				
					
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("BAD URL", "URL MALFORMED");
			}
			
			
		}
	public class brewDetails extends AsyncTask<URL, Void, String>{
		
		

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
				
				JSONArray breweryDetails = data.getJSONArray("breweries");
				JSONObject locationDetails = breweryDetails.getJSONObject(0);
				for (int i = 0; i < breweryDetails.length(); i++) {
					JSONObject one = breweryDetails.getJSONObject(0);
					JSONArray two = locationDetails.getJSONArray("locations");
					
					Log.i("two", two.toString());
					Intent intent = new Intent(MoreDetails.this, BreweryDetails.class);
					
					
					String name = one.getString("name");
					if (one.has("name")) {
						intent.putExtra("name", name);
						
					}
					for (int j = 0; j < two.length(); j++) {
						JSONObject locationInfo = two.getJSONObject(0);
						
						String address = locationInfo.getString("streetAddress");
						if (locationInfo.has("streetAddress")) {
							intent.putExtra("streetAddress", address);
						}
						
						String city = locationInfo.getString("locality");
						if (locationInfo.has("locality")) {
							
							intent.putExtra("locality", city);
						}
						
						String state = locationInfo.getString("region");
						
						if (locationInfo.has("region")) {
							intent.putExtra("region", state);
						}
						
						String zipcode = locationInfo.getString("postalCode");
						if (locationInfo.has("postalCode")) {
							
							intent.putExtra("postalCode", zipcode);
							
						}
						String open = locationInfo.getString("openToPublic");
						if (locationInfo.has("openToPublic")) {
							
							intent.putExtra("openToPublic", open);
							
						}
						
						String phone = locationInfo.getString("phone");
						if (locationInfo.has("phone")) {
							
							intent.putExtra("phone", phone);
							
						}
						
						String website = locationInfo.getString("website");
						if (locationInfo.has("website")) {
							
							intent.putExtra("website", website);
							
						}
						
					}
					
				/*for (int j = 0; j < locationDetails.length(); j++) {
					JSONObject two = locationDetails.getJSONObject(0);
					
					Log.i("two", two.toString());
				}
				
				}*/
					startActivity(intent);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
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
		
	//favorites icon
		case R.id.add:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Add Favs");
				alert.setMessage("Feature Coming Soon...");
				alert.setCancelable(false);
				alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});
				alert.show();

			break;
			//share icon
		case R.id.share:
			AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
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
			
			startActivity(new Intent(MoreDetails.this, Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}

}
