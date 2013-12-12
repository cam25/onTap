/*
 * project 			onTap
 * 
 * package			com.cmozie.fragclasses
 * 
 * name				cameronmozie
 * 
 * date				Dec 5, 2013
 */
package com.cmozie.fragclasses;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmozie.ontap.Favorites;
import com.cmozie.ontap.MainActivity;
import com.cmozie.ontap.R;

// TODO: Auto-generated Javadoc
/**
 * The Class Events.
 */
public class Events extends Fragment {

	
	
	protected static final String TITLE = null;
	public static TextView eventName;
	public static TextView eventType;
	public static TextView eventDescription;
	public static TextView eventVenue;
	public static TextView eventsWebsite;
	public static TextView eventSchedule;
	public static HashMap<String, String> myMap;
	public static List<Map<String,String>> arrayList;
	ShareEvent callBack;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mainView = inflater.inflate(R.layout.events, container,false);
		return mainView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 eventName = (TextView)getActivity().findViewById(R.id.eventName);
		 eventType = (TextView)getActivity().findViewById(R.id.eventType);
		 eventDescription = (TextView)getActivity().findViewById(R.id.eventDescription);
		 eventVenue = (TextView)getActivity().findViewById(R.id.venue);
		 eventsWebsite = (TextView)getActivity().findViewById(R.id.eventWebsite);
		eventSchedule = (TextView)getActivity().findViewById(R.id.scheduleEvent);
		 getApiResults();
		
		 
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
		 
		 
	}
	
	
	public interface ShareEvent {
        public void eventShare();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBack = (ShareEvent) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
public void getApiResults(){
		
		String baseUrl = "http://api.brewerydb.com/v2/events?key=4b77a2665f85f929d4a87d30bbeae67b";
		String queryString;
		try {
			queryString = URLEncoder.encode(baseUrl,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		URL finalURL;
		try {
			 finalURL = new URL(baseUrl);
			 EventRequest evntRequest = new EventRequest();
			 evntRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
	
	private class EventRequest extends AsyncTask<URL, Void, String>{
	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}

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
			arrayList = new ArrayList<Map<String,String>>();
			try {
				JSONObject json = new JSONObject(result);
				JSONArray data = json.getJSONArray("data");
				
				
				
				
				for (int i = 0; i < data.length(); i++) {
					
					
					
					
					JSONObject allEvents = data.getJSONObject(i);
					myMap = new HashMap<String, String>();
					Log.i("events", allEvents.toString());
					//Log.i("events", allEvents.toString());
					if (allEvents.has("name")) {
						String name = allEvents.getString("name");
						myMap.put("name", name);
						//eventName.setText(name);
					}
						
					if (allEvents.has("description")) {
						String descript = allEvents.getString("description");
						myMap.put("description", descript);
						//eventDescription.setText(descript);
					}
					if (allEvents.has("type")) {
						String typeEvent = allEvents.getString("type");
						myMap.put("type", typeEvent);
						//eventType.setText(typeEvent);
					}
					if (allEvents.has("venueName")) {
						String venueEvent = allEvents.getString("venueName");
						myMap.put("venueName", venueEvent);
						//eventVenue.setText(venueEvent);
					}
					
					if (allEvents.has("website")) {
						String eventWebsite = allEvents.getString("website");
						myMap.put("website", eventWebsite);
						//eventsWebsite.setText(eventWebsite);
					}
					
					arrayList.add(myMap);
					
					for (int j = 0; j <  arrayList.size(); j++) {
						
						if (allEvents.has("name")) {
							String name = allEvents.getString("name");
							eventName.setText(name);
						}
							
						if (allEvents.has("description")) {
							String descript = allEvents.getString("description");
							eventDescription.setText(descript);
						}
						if (allEvents.has("type")) {
							String typeEvent = allEvents.getString("type");
							eventType.setText(typeEvent);
						}
						if (allEvents.has("venueName")) {
							String venueEvent = allEvents.getString("venueName");
							eventVenue.setText(venueEvent);
						}
						
						if (allEvents.has("website")) {
							String eventWebsite = allEvents.getString("website");
							eventsWebsite.setText(eventWebsite);
						}
					}
				}
				//beerDescription.setText(description2);
				//Log.i("beer", beerNam);
			} catch (JSONException e) {
				// TODO: handle exception
				
				e.printStackTrace();
				//beerName.setText(beerNam);
				//beerDescription.setText("No description");
				
			}
			
		}
			
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
	switch (item.getItemId()) {
		
		
		case R.id.share:
			
			
			  break;
			  //opens favorites activity
		case R.id.favorites:
			
			startActivity(new Intent(getActivity(), Favorites.class));

			break;
		default:
			break;
		}
	return super.onOptionsItemSelected(item);
	}
	
	
}
		
	
