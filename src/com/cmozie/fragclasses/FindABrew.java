/*
 * project 			onTap
 * 
 * package			com.cmozie.fragclasses
 * 
 * name				cameronmozie
 * 
 * date				Dec 19, 2013
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

import com.cmozie.ontap.MoreDetails;
import com.cmozie.ontap.R;
import com.cmozie.utils.Network;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

// TODO: Auto-generated Javadoc
/**
 * The Class FindABrew.
 */
public class FindABrew extends Fragment {

	TextView listBeerName;
	TextView listBeerCompany;
	

	
 ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();

	public static HashMap<String, String> map;
	public static List<Map<String,String>> dataArray;
	public static ImageButton scan;
	public String labels;
	public static EditText searchField;
	public static  ListView lv;	
	public URL url;
	
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
	    lv = (ListView)getActivity().findViewById(R.id.listView1);

	     scan = (ImageButton)getActivity().findViewById(R.id.scannerButn);

	     searchField = (EditText)getActivity().findViewById(R.id.searchField);

	     searchField.setOnEditorActionListener(new OnEditorActionListener() {
			
	    	
	    	 @Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
	    		 
	    		 if (actionId == EditorInfo.IME_ACTION_GO) {
					
	    			 String search = searchField.getText().toString();

	    			 Log.i("search", search);
	    			 getApiResults(search);
	    			 getActivity();
	    			 
	    			 //hide keyboard
					InputMethodManager inputManager = (InputMethodManager)            
	    					  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
	    					    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),      
	    					    InputMethodManager.HIDE_NOT_ALWAYS);
				}
	    		 
				return false;
			}
		});

		//scan button
		scan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					
				
				Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
				
				
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
			       intent.putExtra("SAVE_HISTORY", false);//th
			       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			       
			      
			     
			       
				 startActivityForResult(intent, 0);
				
			}catch (ActivityNotFoundException e) {
				// TODO: handle exception
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			    builder.setTitle("Confirm");
			    builder.setMessage("You must install the barcode scanner application to utilize this feature do you want to download it?");

			    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing but close the dialog

			        	 Uri uri = Uri.parse("market://search?q=pname:com.google.zxing.client.android");
					        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					        
					        
					        startActivity(intent);
			            dialog.dismiss();
			        }

			    });

			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			            dialog.dismiss();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.show();
			
			}
			}
		});
		    
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@SuppressWarnings("unused")
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        
		String contents = null;
	        if (requestCode == 0) {
	
	       
			}
	        if (resultCode == -1) {
            	Log.i("RESULTCODE", String.valueOf(resultCode));
           	 contents = intent.getStringExtra("SCAN_RESULT"); //this is the result
           	 String beer = contents;
           	 
           	
           	  Log.i("beer", beer);
           	  String query = "http://api.brewerydb.com/v2/search/upc?code="+ beer +"&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
  
           getScanResults(beer);
           	  
                searchField.setText("");
                 Log.i("Query",query);
                
                 
              
			}
	       
	    }

/**
 * Gets the api results.
 *
 * @param beer the beer
 * @return the api results
 */
public  void getApiResults(String beer){

	String baseUrl = "http://api.brewerydb.com/v2/search/?q="+ beer +"/?description/?hasLabels=Y/&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
	

		
		String queryString;
		String queryString2;
		try {
			

			queryString = URLEncoder.encode(beer,"UTF-8");

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		
		 baseUrl = "http://api.brewerydb.com/v2/search/?q="+ queryString +"/?hasLabels=Y&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
		URL finalURL;
				try {
			
			 finalURL = new URL(baseUrl);
			 //finalURL2 = new URL(query);
			 SearchAsyncTask queryRequest = new SearchAsyncTask();
			
			 	
			 	Log.i("FinalURL", finalURL.toString());

			
				 queryRequest.execute(finalURL);

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}

/**
 * Gets the scan results.
 *
 * @param beer the beer
 * @return the scan results
 */
public  void getScanResults(String beer){

	
	String query = "http://api.brewerydb.com/v2/search/upc?code="+ beer +"&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";

		String queryString;
		String queryString2;
		try {
			queryString2 = URLEncoder.encode(query,"UTF-8");
			queryString = URLEncoder.encode(beer,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		
		 query = "http://api.brewerydb.com/v2/search/upc?code="+ beer +"&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
		URL finalURL2;
		try {
			
			 finalURL2 = new URL(query);
			 SearchAsyncTask searchRequest = new SearchAsyncTask();
			
			 searchRequest.execute(finalURL2);
			
			
				
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}

/**
 * The Class SearchAsyncTask.
 */
public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
	
	ProgressDialog progressIndicator;
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		listBeerName = (TextView)getActivity().findViewById(R.id.listBeerType);
		listBeerCompany = (TextView)getActivity().findViewById(R.id.listBeerCompany);
		
		progressIndicator = new ProgressDialog(getActivity());
		progressIndicator.setMessage("Getting Info...");
		progressIndicator.setIndeterminate(false);
		progressIndicator.setCancelable(true);
		progressIndicator.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		String reply = "";
		for (URL url : urls) {
		reply = Network.URLStringResponse(url);	
		}
		return reply;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
		
		
		progressIndicator.dismiss();
		try {
			JSONObject json = new JSONObject(result);
			JSONArray data = json.getJSONArray("data");
			//JSONObject label =;
			dataArray = new ArrayList<Map<String,String>>();
			
			for (int i = 0; i < data.length(); i++) {
				
				
				
				JSONObject one = data.getJSONObject(i);
				
				map = new HashMap<String, String>();
				
				
				//beer name
				if (one.has("name")) {
					 map.put("name", one.getString("name"));
				}else{
					map.put("name", "N/A");
				}
				//description
				if (one.has("description")) {
					map.put("description", one.getString("description"));
				}else if (one.has("style")) {
					
				JSONObject style = one.getJSONObject("style");
				
				String styleDescription = style.getString("description");
				
				
				Log.i("Style", styleDescription);
				map.put("description", style.getString("description"));
					
				}else{
				
					map.put("description", "No Description Available");
				
			}
				//label
				if (one.has("labels")) {
					JSONObject image = one.getJSONObject("labels");
					
					map.put("labels", image.getString("large"));
					Log.i("map", map.toString());

				}
				
				//abv 
				if (one.has("abv")) {
					 map.put("abv", one.getString("abv"));
				}else {
					map.put("abv", "N/A");
				}
				//id for brewery
				if (one.has("id")) {
					map.put("id", one.getString("id"));
				}
				//type
				if (one.has("type")) {
					map.put("type", one.getString("type"));
				}else {
					map.put("type", "N/A");
				}
				
				//available
				if (one.has("available")) {
					JSONObject available = one.getJSONObject("available");
					map.put("available", available.getString("name"));
				}else {
					map.put("available", "N/A");
				}
				
				//style
				if (one.has("style")) {
					JSONObject style = one.getJSONObject("style");
					JSONObject category = style.getJSONObject("category");
					
					map.put("style", category.getString("name"));
				}else {
					map.put("style", "N/A");
				}
				
				//add map contents to array
				dataArray.add(map);
				 
				
					ListAdapter adapter = new SimpleAdapter(getActivity(), dataArray, R.layout.listitems, new String[]{"name","company" },new int[]{R.id.listBeerType, R.id.listBeerCompany});
					
					lv.setAdapter(adapter);
				
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(), MoreDetails.class);
							
							String name = dataArray.get(+arg2).get("name");
							String abv = dataArray.get(+arg2).get("abv");
							String descriptionText = dataArray.get(+arg2).get("description");
							String styleDescript = dataArray.get(+arg2).get("labels");
							String id = dataArray.get(+arg2).get("id");
							String type = dataArray.get(+arg2).get("type");
							String availability = dataArray.get(+arg2).get("available");
							String style = dataArray.get(+arg2).get("style");
						
							
							
							intent.putExtra("id", id);
							intent.putExtra("name", name);
							intent.putExtra("abv", abv);
							intent.putExtra("description", descriptionText);
							intent.putExtra("styleDescription", styleDescript);
							intent.putExtra("type", type);
							intent.putExtra("style", style);
							intent.putExtra("available", availability);
							
			               startActivity(intent);
							
						}
					});
					
					
					
				Log.i("array",String.valueOf(dataArray));
				// TODO Auto-generated method stub
				
				
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setTitle("Scan Result");
			alert.setMessage("UPC Not Found");
			alert.setCancelable(false);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert.show();

		}
	}
}


}
