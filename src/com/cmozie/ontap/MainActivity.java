/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 15, 2013
 */
package com.cmozie.ontap;


import java.util.HashMap;

import org.json.JSONArray;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.cmozie.classes.Network;
import com.cmozie.fragclasses.Events;
import com.cmozie.fragclasses.FindABrew;
import com.cmozie.fragclasses.WhatToDrink;
import com.cmozie.fragclasses.Events.ShareEvent;

import com.cmozie.fragclasses.WhatToDrink.PassTheData;
import com.cmozie.fragclasses.WhatToDrink.shareData;



// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements PassTheData, shareData, ShareEvent {
	
	 Boolean connection = false;
	//instatiates tabs
	ActionBar.Tab WTD,FAB,EVNT;
	
	//sets tab fragments to each  custom class
	public static Fragment tabFrag1 = new WhatToDrink();
	public static Fragment tabFrag2 = new FindABrew();
	public static Fragment tabFrag3 = new Events();
	Context context;
	public MenuItem add;
	public MenuItem share;
	 public static HashMap<String, String> map;
	
	
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
	
	    //adds frags to backstack on transaction
	  FragmentTransaction transactions = getFragmentManager().beginTransaction();
		transactions.addToBackStack(null);
		transactions.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transactions.commit();
		
		
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        if (getFragmentManager().getBackStackEntryCount() == 0)
	        {
	        	Log.i("back", "stack");
	
	        	//finishs the app on back 
	            this.finish();
	            return false;
	        }
	        else
	        {
	            getFragmentManager().popBackStack();
	            Log.i("back", "Has Stack");

	            return false;
	        }



	    }

	    return super.onKeyDown(keyCode, event);
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
    	ft.replace(R.id.container, fragment);
    	if (tab.getPosition() == 1) {
			add.setVisible(false);
			share.setVisible(false);

		}
    	if (tab.getPosition() == 2) {
			add.setVisible(false);
			
		}
    	
    	
    	
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
	        
	        if (tabFrag2.isVisible()) {
    			InputMethodManager hideKeyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       		 hideKeyboard.hideSoftInputFromWindow(FindABrew.searchField.getWindowToken(), 0);
       		 //clears textview
       		 FindABrew.searchField.setText("");
			}
    		
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
			if (tabFrag1.isVisible()) {
				passTheData();
			}
			
			//WhatToDrink.storeFile(this, "favorites", WhatToDrink.map, true);
			break;
			
			//opens alert for share
		case R.id.share:
			
			if (tabFrag3.isVisible()) {
				eventShare();
			}
			if (tabFrag1.isVisible()) {
				shareIntent();
			}
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


	/* (non-Javadoc)
	 * @see com.cmozie.fragclasses.WhatToDrink.PassTheData#passTheData()
	 */
	@Override
	public void passTheData() {
		// TODO Auto-generated method stub
		map = new HashMap<String, String>();
		String test = WhatToDrink.beerName.getText().toString();
		String description = WhatToDrink.beerDescription.getText().toString();
		
		map.put("description", description);
		map.put("beerName", test);
		WhatToDrink.storeFile(context, "favorites", map, true);
		Log.i("test", test);
	}


	/* (non-Javadoc)
	 * @see com.cmozie.fragclasses.WhatToDrink.shareData#shareIntent()
	 */
	@Override
	public void shareIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_TEXT,"Beer's Name:" + WhatToDrink.beerNam + "\n" + "Beer Description" + "\n " + WhatToDrink.description);
		startActivity(intent); 
		
	}


	/* (non-Javadoc)
	 * @see com.cmozie.fragclasses.Events.ShareEvent#eventShare()
	 */
	@Override
	public void eventShare() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_TEXT,"Event:" + Events.eventName.getText().toString() + "\n" +"Event Type:"+  Events.eventType.getText().toString());
		startActivity(intent);
		
	}


	


	
}
	

	
