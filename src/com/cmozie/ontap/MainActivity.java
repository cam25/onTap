package com.cmozie.ontap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		
		TabSpec tab1 =tabHost.newTabSpec("Tab 1");
		tab1.setContent(R.id.tab1);
		tab1.setIndicator("Tab 1");

		TabSpec tab2 = tabHost.newTabSpec("Tab 2");
		tab2.setIndicator("Tab 2");
		tab2.setContent(R.id.tab2);

		TabSpec tab3=tabHost.newTabSpec("Tab 3");
		tab3.setIndicator("Tab 3");
		tab3.setContent(R.id.tab3);

		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
