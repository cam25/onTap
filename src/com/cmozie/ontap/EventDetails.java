package com.cmozie.ontap;

import java.util.HashMap;

import com.cmozie.fragclasses.Events;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EventDetails extends Activity {

	public static TextView eventName;
	public static TextView eventType;
	public static TextView eventDescription;
	public static TextView eventVenue;
	public static TextView eventsWebsite;
	public static TextView eventSchedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
		setContentView(R.layout.activity_event_details);
		eventName = (TextView)findViewById(R.id.eventName);
		 eventType = (TextView)findViewById(R.id.eventType);
		 eventDescription = (TextView)findViewById(R.id.eventDescription);
		 eventVenue = (TextView)findViewById(R.id.venue);
		 eventsWebsite = (TextView)findViewById(R.id.eventWebsite);
		eventSchedule = (TextView)findViewById(R.id.scheduleEvent);
		
		
		eventName.setText(getIntent().getExtras().getString("name"));
		eventType.setText(getIntent().getExtras().getString("type"));
		eventDescription.setText(getIntent().getExtras().getString("description"));
		eventVenue.setText(getIntent().getExtras().getString("venueName"));
		
		
		eventsWebsite.setText(getIntent().getExtras().getString("website"));
		
eventSchedule.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				  Intent intent = new Intent(Intent.ACTION_EDIT);
				  intent.setType("vnd.android.cursor.item/event");
				  
				  intent.putExtra("title", eventName.getText().toString());
				  intent.putExtra("description", eventDescription.getText().toString());
				  intent.putExtra("availability", 1);
				  startActivity(intent);

				
			}
		});
eventsWebsite.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String web =  eventsWebsite.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web)); 
        startActivity(callIntent);
	}
});

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent homeIntent = new Intent(this, MainActivity.class);
			  homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			  getApplicationContext().startActivity(homeIntent);
	    break;
		
		
	
			  //favorites activity
		case R.id.favorites:
			
			startActivity(new Intent(this, Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
}
