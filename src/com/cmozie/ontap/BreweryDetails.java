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

// TODO: Auto-generated Javadoc
/**
 * The Class BreweryDetails.
 */
public class BreweryDetails extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brewery_details);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		TextView zipcode = (TextView)findViewById(R.id.textView5);
		
		zipcode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(BreweryDetails.this);
				alert.setTitle("GPS Feature");
				alert.setMessage("Feature Coming Soon...");
				alert.setCancelable(false);
				alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});
				alert.show();
			}
		});
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
