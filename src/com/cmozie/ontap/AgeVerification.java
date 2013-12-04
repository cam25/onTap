package com.cmozie.ontap;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AgeVerification extends Activity {
	private static TextView displayDate;
    private static Button pickDate;
    private static Button go;
    private static final int AGE_LIMIT = 1990;
    private static final int DEFAULT_END_YEAR = 2013;
    public static String filename = "StoredBirthdays";
  SharedPreferences sharedData;
   public static Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_verification);
        displayDate = (TextView) findViewById(R.id.displayDate);
        pickDate = (Button) findViewById(R.id.pickDate);
        go = (Button) findViewById(R.id.go);
        
        //shared preference getting the preference at the filename location
        sharedData = getSharedPreferences(filename, 0);
        
        //opens date picker dialog
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        
        
       
        
       
       
        go.setVisibility(View.GONE);
        
        //on click of verify button 
      go.setOnClickListener(new View.OnClickListener() {
		
    
    	  
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			 sharedData = getSharedPreferences(filename, 0);
    		SharedPreferences.Editor editor = sharedData.edit();
    	
    		editor.putString("birthdate", displayDate.toString());
    		Log.i("test",editor.toString());
    		editor.commit();
    		Log.i("prefs", String.valueOf(sharedData.contains(filename)));
    	       
    		//shared preference trials
    	       if (sharedData.contains(filename)) {
    	    	   startActivity(new Intent(AgeVerification.this, MainActivity.class));
    	           finish();
    		}
            startActivity(new Intent(AgeVerification.this, MainActivity.class));
            finish();
		}
	});
        
      //opens date picker on button select 
        pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                if (go.isShown()) {
					go.setVisibility(View.GONE);
				}
            }
        });
       
        
    }   
    
    //date picker fragment
    public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker datePicker;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar cal = Calendar.getInstance();
   
    //sets the values of the year to ints
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    
    
    
    return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //on set of the date display the birthday to the text view. 
    public void onDateSet(DatePicker view, int year, int month, int day) {
    	
    	if (year <= 1990) {
    		
    		displayDate .setText(String.valueOf(day) + "/"
                    + String.valueOf(month + 1) + "/" + String.valueOf(year));
    	
    		
    		
    		
    		go.setVisibility(View.VISIBLE);
    	        	
    			
		}
    	
        
    }
    
    }

}
