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

import com.cmozie.ontap.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class WhatToDrink.
 */
public class WhatToDrink extends Fragment {

	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.whattodrink, container,false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		
	}
	


	

	public void getApiResults(){
		
		//String baseUrl = "http://api.brewerydb.com/v2/beer/random?key=4b77a2665f85f929d4a87d30bbeae67b";
		
	}
	
	

}
