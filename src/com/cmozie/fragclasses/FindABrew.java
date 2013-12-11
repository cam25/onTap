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
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.rtp.RtpStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class FindABrew.
 */
public class FindABrew extends Fragment {
	
	private static final int RESULT_OK = 0;
	private static final int RESULT_CANCELED = 0;
	TextView listBeerName;
	TextView listBeerCompany;
	
	//beer object
	String[] beers = {
	    	"Samuel Adams Octoberfest",
	    	"Samuel Adams WinterLager",
	    	"Samuel Adams DoubleBock"
	     };
	
 ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
 private static final String BEERNAME = "name";
 private static final String COMPANY = "company";
	public static HashMap<String, String> map;
	public static List<Map<String,String>> test;
	public static ImageButton scan;
	public String labels;
	public static EditText searchField;
	
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
		// EditText ed = (EditText)getActivity().findViewById(R.id.beerText);
	     ListView lv = (ListView)getActivity().findViewById(R.id.listView1);
	     //Button search = (Button)getActivity().findViewById(R.id.searchButn);
	     scan = (ImageButton)getActivity().findViewById(R.id.scannerButn);
	     //ed.setText("Sam Adams");
	     searchField = (EditText)getActivity().findViewById(R.id.searchField);
	   
	     searchField.setOnEditorActionListener(new OnEditorActionListener() {
			
	    	 @Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
	    		 if (actionId == EditorInfo.IME_ACTION_SEND) {
					
	    			 String search = searchField.getText().toString();
	    			
	    			 Log.i("search", search);
	    			 getApiResults(search);
				}
	    		 
				return false;
			}
		});
	     //arraylist for listview
	    List<String> arrayList = new ArrayList<String>();
	    
	    arrayList.add("Beer1");
	    arrayList.add("Beer2");
	    arrayList.add("Beer3");
	    
	   

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
           	  
                
                 Log.i("Query",query);
                 
              
			}
	       
	    }
public  void getApiResults(String beer){
		
	//String capitol = Character.toString(beer.charAt(0)).toUpperCase();
	
	
	String baseUrl = "http://api.brewerydb.com/v2/search/?q="+ beer +"/?description/?hasLabels=Y/&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
	
	String query = "http://api.brewerydb.com/v2/search/upc?code="+ beer +"&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";


		
		
		
		String queryString;
		String queryString2;
		try {
			

			queryString = URLEncoder.encode(beer,"UTF-8");
			//queryString = URLEncoder.encode(beer,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		
		 baseUrl = "http://api.brewerydb.com/v2/search/?q="+ queryString +"/?hasLabels=Y&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
		 //query = "http://api.brewerydb.com/v2/search/upc?code="+ beer +"&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
		URL finalURL;
		//URL finalURL2;
		try {
			
			 finalURL = new URL(baseUrl);
			 //finalURL2 = new URL(query);
			 SearchAsyncTask wtdRequest = new SearchAsyncTask();
			
			 	
			 	Log.i("FinalURL", finalURL.toString());
				// wtdRequest.execute(finalURL2);
			
				 wtdRequest.execute(finalURL);
			
				
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
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
			 SearchAsyncTask wtdRequest = new SearchAsyncTask();
			
				 wtdRequest.execute(finalURL2);
			
			
				
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
	
	ProgressDialog progressIndicator;
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
		progressIndicator.dismiss();
		try {
			JSONObject json = new JSONObject(result);
			JSONArray data = json.getJSONArray("data");
			
			 test = new ArrayList<Map<String,String>>();
			for (int i = 0; i < data.length(); i++) {
				JSONObject one = data.getJSONObject(i);
				//JSONObject label = one.getJSONObject("labels");
				
				 //url = new URL(label.getString("large"));
				map = new HashMap<String, String>();
				if (one.has("name")) {
					 map.put("name", one.getString("name"));
				}
			
				if (one.has("description")) {
					map.put("description", one.getString("description"));
				}
				
				
				
				 
				if (one.has("abv")) {
					 map.put("abv", one.getString("abv"));
				}
				
				if (one.has("id")) {
					map.put("id", one.getString("id"));
				}
				
				 test.add(map);
				 
				 ListView lv = (ListView)getActivity().findViewById(R.id.listView1);
					ListAdapter adapter = new SimpleAdapter(getActivity(), test, R.layout.listitems, new String[]{"name","company" },new int[]{R.id.listBeerType, R.id.listBeerCompany});
					
					lv.setAdapter(adapter);
					
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(), MoreDetails.class);
							String name = test.get(+arg2).get("name");
							String abv = test.get(+arg2).get("abv");
							String descriptionText = test.get(+arg2).get("description");
							//String styleDescript = test.get(+arg2).get("large");
							String id = test.get(+arg2).get("id");
							
						
							intent.putExtra("id", id);
							intent.putExtra("name", name);
							intent.putExtra("abv", abv);
							intent.putExtra("description", descriptionText);
							//intent.putExtra("styleDescription", styleDescript);
				         	Toast.makeText(getActivity(), "You Clicked at "+test.get(+arg2).get("name"), Toast.LENGTH_SHORT).show();
							
			                startActivity(intent);
							
						}
					});
					
					
				Log.i("array",String.valueOf(test));
				// TODO Auto-generated method stub
				
				
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	}
}


}
