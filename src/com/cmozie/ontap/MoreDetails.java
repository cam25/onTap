package com.cmozie.ontap;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MoreDetails extends Activity {

	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_details);
		
		//actionbar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    
		TextView breweryDetails = (TextView)findViewById(R.id.breweryDetails);
		
		breweryDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MoreDetails.this, BreweryDetails.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_details, menu);
		return true;
	}
	
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
