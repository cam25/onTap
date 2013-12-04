package com.cmozie.ontap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BreweryDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brewery_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.brewery_details, menu);
		return true;
	}

}
