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
import android.support.v4.*;
import android.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.cmozie.classes.Network;
import com.cmozie.ontap.EventDetails;
import com.cmozie.ontap.Favorites;
import com.cmozie.ontap.MainActivity;
import com.cmozie.ontap.MoreDetails;
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
		 
		 getApiResults();
		
		
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
			
			final Fragment newFragment = null;
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
						Log.i("Venue Is", venueEvent);
						myMap.put("venueName", venueEvent);
						//eventVenue.setText(venueEvent);
					}
					
					if (allEvents.has("website")) {
						String eventWebsite = allEvents.getString("website");
						myMap.put("website", eventWebsite);
						//eventsWebsite.setText(eventWebsite);
					}
					
					arrayList.add(myMap);
					
					ListView lv = (ListView)getActivity().findViewById(R.id.eventsList);
					ListAdapter adapter = new SimpleAdapter(getActivity(), arrayList, R.layout.listitems, new String[]{"name","type" },new int[]{R.id.listBeerType, R.id.listBeerCompany});
					
					lv.setAdapter(adapter);
					
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(), EventDetails.class);
							String name = arrayList.get(+arg2).get("name");
							String description = arrayList.get(+arg2).get("description");
							String type = arrayList.get(+arg2).get("type");
							String venue = arrayList.get(+arg2).get("venueName");
							String website = arrayList.get(+arg2).get("website");
							
							intent.putExtra("name", name);
							intent.putExtra("description", description);						
							intent.putExtra("venueName", venue);
							intent.putExtra("type", type);
							intent.putExtra("website", website);
							
							Log.i("venue", venue);
				         	//Toast.makeText(getActivity(), "You Clicked at "+test.get(+arg2).get("name"), Toast.LENGTH_SHORT).show();
							
							
							
			                startActivity(intent);
							
			                
			                
						}
					});
					
					/*for (int j = 0; j <  arrayList.size(); j++) {
						
						if (allEvents.has("name")) {
							String name = allEvents.getString("name");
							//eventName.setText(name);
						}
							
						if (allEvents.has("description")) {
							String descript = allEvents.getString("description");
							//eventDescription.setText(descript);
						}
						if (allEvents.has("type")) {
							String typeEvent = allEvents.getString("type");
							//eventType.setText(typeEvent);
						}
						if (allEvents.has("venueName")) {
							String venueEvent = allEvents.getString("venueName");
							//eventVenue.setText(venueEvent);
						}
						
						if (allEvents.has("website")) {
							String eventWebsite = allEvents.getString("website");
							//eventsWebsite.setText(eventWebsite);
						}
					}
				*/
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
		
	
