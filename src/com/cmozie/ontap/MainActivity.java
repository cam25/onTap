/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 5, 2013
 */
package com.cmozie.ontap;


import android.os.Bundle;
import android.app.ActionBar;
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
import android.widget.Button;

import com.cmozie.fragclasses.Events;
import com.cmozie.fragclasses.FindABrew;
import com.cmozie.fragclasses.Network;
import com.cmozie.fragclasses.WhatToDrink;



// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {
	
	 Boolean connection = false;
	//instatiates tabs
	ActionBar.Tab WTD,FAB,EVNT;
	
	//sets tab fragments to each  custom class
	Fragment tabFrag1 = new WhatToDrink();
	Fragment tabFrag2 = new FindABrew();
	Fragment tabFrag3 = new Events();
	Context context;
	public MenuItem add;
	public MenuItem share;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		connection = Network.getConnectionStatus(context);
		
		Log.i("Connection", Network.getConnectionType(context));
		
		
		//enables action bar
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    //set title to action bar
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    
	    //sets text to tabs
	    WTD = actionBar.newTab().setText("What To Drink");
	    FAB = actionBar.newTab().setText("Find A Brew");
	    EVNT = actionBar.newTab().setText("Events");
	    
	    //listens for click event on the tabs
	    WTD.setTabListener(new TabListener(tabFrag1));
	    FAB.setTabListener(new TabListener(tabFrag2));
	    EVNT.setTabListener(new TabListener(tabFrag3));
	   
	    //adds the specific tabs to the action bar
	    actionBar.addTab(WTD);
	    actionBar.addTab(FAB);
	    actionBar.addTab(EVNT);
	
		
	}
	
//TabListener class 
	/**
 * The listener interface for receiving tab events.
 * The class that is interested in processing a tab
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTabListener<code> method. When
 * the tab event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TabEvent
 */
public class TabListener implements ActionBar.TabListener {
		 
	    Fragment fragment;
	   
	 
	    /**
    	 * Instantiates a new tab listener.
    	 *
    	 * @param fragment the fragment
    	 */
    	public TabListener(Fragment fragment) {
	        // TODO Auto-generated constructor stub
	        this.fragment = fragment;
	    }
	 
	    /* (non-Javadoc)
    	 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
    	 */
    	@Override
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
    	Log.i("Selected Tab is ",String.valueOf(tab.getPosition()));
    	
    	if (tab.getPosition() == 1) {
			add.setVisible(false);
			share.setVisible(false);
		}
    	if (tab.getPosition() == 2) {
			add.setVisible(false);
		}
    	
    	ft.replace(R.id.container, fragment);
	    }
	 
	    /* (non-Javadoc)
    	 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
    	 */
    	@Override
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
    		add.setVisible(true);
    		share.setVisible(true);
    		
	        ft.remove(fragment);
	    }
	 
	    /* (non-Javadoc)
    	 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
    	 */
    	@Override
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // TODO Auto-generated method stub
	 
    		
    		if (tab.getPosition() == 0) {
        		add.setVisible(true);
        		share.setVisible(true);
    		}
    		else if (tab.getPosition() == 1) {
    			add.setVisible(false);
    			share.setVisible(false);
			}
    		
    		else if (tab.getPosition() == 2) {
    			add.setVisible(false);
        		share.setVisible(true);
			}
			
    		
	    }
    	
	}
	    
	  
	 /* (non-Javadoc)
 	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
 	 */
 	@Override   
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		add = menu.findItem(R.id.add);
		share = menu.findItem(R.id.share);
		
		return true;
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		
		//opens alert for add
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
			
			//opens alert for share
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
			  //opens favorites activity
		case R.id.favorites:
			
			startActivity(new Intent(MainActivity.this, Favorites.class));

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
}
	

	
