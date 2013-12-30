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

import java.util.HashMap;

import com.cmozie.fragclasses.Events;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDetails.
 */
public class EventDetails extends Fragment {

	public static TextView eventName;
	public static TextView eventType;
	public static TextView eventDescription;
	public static TextView eventVenue;
	public static TextView eventsWebsite;
	public static TextView eventSchedule;
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_event_details, container,false);
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		ActionBar actionBar = getActivity().getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
		//setContentView(R.layout.activity_event_details);
		eventName = (TextView)getActivity().findViewById(R.id.eventName);
		 eventType = (TextView)getActivity().findViewById(R.id.eventType);
		 eventDescription = (TextView)getActivity().findViewById(R.id.eventDescription);
		 eventVenue = (TextView)getActivity().findViewById(R.id.venue);
		 eventsWebsite = (TextView)getActivity().findViewById(R.id.eventWebsite);
		eventSchedule = (TextView)getActivity().findViewById(R.id.scheduleEvent);
		
		
		eventName.setText(getActivity().getIntent().getExtras().getString("name"));
		eventType.setText(getActivity().getIntent().getExtras().getString("type"));
		eventDescription.setText(getActivity().getIntent().getExtras().getString("description"));
		eventVenue.setText(getActivity().getIntent().getExtras().getString("venueName"));
		
		
		eventsWebsite.setText(getActivity().getIntent().getExtras().getString("website"));
		
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
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		
		
		case R.id.shareEvent:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_TEXT,"Event:" + eventName.getText().toString() + "\n" +"Event Type:"+  eventType.getText().toString());
			startActivity(intent);
	    break;
			  //favorites activity
		case R.id.favorites:
			
			startActivity(new Intent(getActivity(), Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.event_details, menu);
	}
}
