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
import org.json.JSONObject;

import com.cmozie.classes.AsyncRequest;
import com.cmozie.classes.SearchAsyncTask;
import com.cmozie.ontap.MoreDetails;
import com.cmozie.ontap.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;

// TODO: Auto-generated Javadoc
/**
 * The Class FindABrew.
 */
public class FindABrew extends Fragment {
	
	private static final int RESULT_OK = 0;
	private static final int RESULT_CANCELED = 0;
	//beer object
	String[] beers = {
	    	"Samuel Adams Octoberfest",
	    	"Samuel Adams WinterLager",
	    	"Samuel Adams DoubleBock"
	     };
	public static HashMap<String, String> map;
	public static List<Map<String,String>> test;
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.findabrew, container,false);
		return rootView;
		
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		//UI elements 
		// EditText ed = (EditText)getActivity().findViewById(R.id.beerText);
	     ListView lv = (ListView)getActivity().findViewById(R.id.listView1);
	     //Button search = (Button)getActivity().findViewById(R.id.searchButn);
	     ImageButton scan = (ImageButton)getActivity().findViewById(R.id.scannerButn);
	     //ed.setText("Sam Adams");
	    SearchView sv = (SearchView)getActivity().findViewById(R.id.searchView1);
	    
	sv.setOnQueryTextListener(new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			getApiResults(query);
			return true;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}
	});
	     //arraylist for listview
	    List<String> arrayList = new ArrayList<String>();
	    
	    arrayList.add("Beer1");
	    arrayList.add("Beer2");
	    arrayList.add("Beer3");
	    
	    //array adapter for listview
	    
		
		//search button
		/*search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("Search a Beer");
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
		});*/

		//scan button
		scan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
				
				
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
			       intent.putExtra("SAVE_HISTORY", false);//th
			       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			    
				 startActivityForResult(intent, 0);
				
			}
		});
		    
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        
		
	        if (requestCode == 0) {
	        	
	           
	            	Log.i("RESULTCODE", String.valueOf(resultCode));
	                    String contents = intent.getStringExtra("SCAN_RESULT"); //this is the result
	                    
	                    String beer = contents;
	                    Log.i("beer", beer);
	                    String query = "http://api.brewerydb.com/v2/search/?q="+ beer +"&type=brewery&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
	                    
	                   
	            } else 
	            if (resultCode == RESULT_CANCELED) {
	              Log.i("Test", "Test");
	            }
	          
	        
	       
	    }
public  void getApiResults(String beer){
		
		String baseUrl = "http://api.brewerydb.com/v2/search/?q="+ beer +"&type=brewery&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
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
			 SearchAsyncTask wtdRequest = new SearchAsyncTask();
			 wtdRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
	
	
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
			JSONArray data = json.getJSONArray("data");
			 test = new ArrayList<Map<String,String>>();
			for (int i = 0; i < data.length(); i++) {
				JSONObject one = data.getJSONObject(i);
				map = new HashMap<String, String>();
				 map.put("name", one.getString("name"));
				
			
				 test.add(map);
				Log.i("array", test.toString());
				// TODO Auto-generated method stub
				
				
			}
			ListView lv = (ListView)getActivity().findViewById(R.id.listView1);
			
			//starts intent for listview item select
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), MoreDetails.class);
	                startActivity(intent);
					
				}
			});
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

}
