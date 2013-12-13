/*
 * project 			onTap
 * 
 * package			com.cmozie.ontap
 * 
 * name				cameronmozie
 * 
 * date				Dec 12, 2013
 */
package com.cmozie.ontap;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashPage.
 */
public class SplashPage extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//makes screen full
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_page);
		
		//timer for splash page
		int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(SplashPage.this, AgeVerification.class));
                        finish();
                }
        }, secondsDelayed * 3000);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_page, menu);
		return true;
	}

}
