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

import java.util.ArrayList;
import java.util.List;
import com.cmozie.ontap.MoreDetails;
import com.cmozie.ontap.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class FindABrew.
 */
public class FindABrew extends Fragment {
	
	//beer object
	String[] beers = {
	    	"Samuel Adams Octoberfest",
	    	"Samuel Adams WinterLager",
	    	"Samuel Adams DoubleBock"
	     };
	
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
		 EditText ed = (EditText)getActivity().findViewById(R.id.beerText);
	     ListView lv = (ListView)getActivity().findViewById(R.id.listView1);
	     Button search = (Button)getActivity().findViewById(R.id.searchButn);
	     ImageButton scan = (ImageButton)getActivity().findViewById(R.id.scannerButn);
	     ed.setText("Sam Adams");
	    
	     //arraylist for listview
	    List<String> arrayList = new ArrayList<String>();
	    
	    arrayList.add("Beer1");
	    arrayList.add("Beer2");
	    arrayList.add("Beer3");
	    
	    //array adapter for listview
	    ArrayAdapter<String> arrayAdapter = (new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, beers));
		lv.setAdapter(arrayAdapter);
		
		
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
		
		//search button
		search.setOnClickListener(new OnClickListener() {
			
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
		});

		//scan button
		scan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("Scan A Beer");
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

}
