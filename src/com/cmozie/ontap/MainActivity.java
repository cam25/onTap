/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 4, 2013
 */
package com.cmozie.ontap;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import com.cmozie.*;
import com.cmozie.fragclasses.Events;
import com.cmozie.fragclasses.FindABrew;
import com.cmozie.fragclasses.WhatToDrink;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {

	public static Context context;
	public static  List<String> arrayList;
	 String[] beers = {
		    	"Samuel Adams Octoberfest",
		    	"Samuel Adams WinterLager",
		    	"Samuel Adams DoubleBock"
		     };
	
	 
	 
	ActionBar.Tab WTD,FAB,EVNT;
	//sets tab elements to each class
	Fragment tabFrag1 = new WhatToDrink();
	Fragment tabFrag2 = new FindABrew();
	Fragment tabFrag3 = new Events();
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//enables action bar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setTitle("onTap");
	    
	    
	    //sets text to tabs
	    WTD = actionBar.newTab().setText("What To Drink");
	    FAB = actionBar.newTab().setText("Find A Brew");
	    EVNT = actionBar.newTab().setText("Events");
	    
	    //listens for click event on the tabs
	    WTD.setTabListener(new TabListener(tabFrag1));
	    FAB.setTabListener(new TabListener(tabFrag2));
	    EVNT.setTabListener(new TabListener(tabFrag3));
	   
	    actionBar.addTab(WTD);
	    actionBar.addTab(FAB);
	    actionBar.addTab(EVNT);
	  /*   EditText ed = (EditText) findViewById(R.id.beerText);
	     ListView lv = (ListView) findViewById(R.id.list);
	     Button search = (Button) findViewById(R.id.searchButn);
	     ImageButton scan = (ImageButton) findViewById(R.id.scannerButn);
	     ed.setText("Searched Beer");
	    
	     //arraylist for listview
	    List<String> arrayList = new ArrayList<String>();
	    
	    arrayList.add("Beer1");
	    arrayList.add("Beer2");
	    arrayList.add("Beer3");
	    
	    //array adapter for listview
	    ArrayAdapter<String> arrayAdapter = (new ArrayAdapter(this,android.R.layout.simple_list_item_1, beers));
		lv.setAdapter(arrayAdapter);
		
		//starts intent for listview item select
lv.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.i("TEST", "TEST");
		startActivity(new Intent(MainActivity.this, MoreDetails.class));
		
	}
});

search.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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

scan.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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
*/

		/*TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
		
		
		//tabbar setup
		tabHost.setup();
		TabSpec tab1 =tabHost.newTabSpec("Tab 1");
	
		tab1.setContent(R.id.tab1);
		tab1.setIndicator("What To Drink?");

		TabSpec tab2 = tabHost.newTabSpec("Tab 2");
		tab2.setIndicator("Find A Beer");
	
		tab2.setContent(R.id.tab2);

		TabSpec tab3=tabHost.newTabSpec("Tab 3");
		tab3.setIndicator("Events");
		tab3.setContent(R.id.tab3);

		
		//adding tabs to the host
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		*/
		
	}
	
//TabListener class 
	public class TabListener implements ActionBar.TabListener {
		 
	    Fragment fragment;
	 
	    public TabListener(Fragment fragment) {
	        // TODO Auto-generated constructor stub
	        this.fragment = fragment;
	    }
	 
	    @Override
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	        ft.replace(R.id.container, fragment);
	    }
	 
	    @Override
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	        ft.remove(fragment);
	    }
	 
	    @Override
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	 
	    }
	}
	    
	  
	 @Override   
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		
		//returns to main activity
		case R.id.add:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Add Favs");
				alert.setMessage("Feature Coming Soon...");
				alert.setCancelable(false);
				alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});
				alert.show();

			break;
		case R.id.share:
			AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
			alert2.setTitle("Share Feature");
			alert2.setMessage("Feature Coming Soon...");
			alert2.setCancelable(false);
			alert2.setPositiveButton("Alright", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
				}
			});
			alert2.show();
			  break;
		case R.id.favorites:
			
			startActivity(new Intent(MainActivity.this, Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
}
	

	
