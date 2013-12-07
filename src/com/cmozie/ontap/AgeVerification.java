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

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class AgeVerification.
 */
public class AgeVerification extends Activity {
	private static TextView displayDate;
    private static Button pickDate;
    private static Button go;
    public static final String filename = "StoredBirthdays";
    SharedPreferences sharedData;
   public static Context context;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_age_verification);
        displayDate = (TextView) findViewById(R.id.displayDate);
        pickDate = (Button) findViewById(R.id.pickDate);
        go = (Button) findViewById(R.id.go);
        
     
        
        //opens date picker dialog
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
       
	
       //Users birthdate is stored to shared preferences to by pass age verification on load of application more than once
       SharedPreferences example = getSharedPreferences(filename, 0);
       String test = example.getString("birthdate", "Birthdate Not Found");
       displayDate.setText(test);
        
       Log.i("test", test);
       
       //if prefs contain 
       if (example.contains("birthdate")) {
    	   startActivity(new Intent(AgeVerification.this, MainActivity.class));
           finish();
	}
        go.setVisibility(View.GONE);
        
        //on click of verify button 
      go.setOnClickListener(new View.OnClickListener() {
		
    
    	  
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//getting the text of display date
			 String message = displayDate.getText().toString();
				
			//setting shareprefrences equal to my static string filename	
			 SharedPreferences prefs = getSharedPreferences(filename, 0);
			 
			 Editor editor = prefs.edit();
			 //stores the value ot the editor
			 editor.putString("birthdate", message);
			 editor.commit();
    	       
    		
    	    
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
                if (go.isPressed()) {
        			displayDate.setText("");
        		}
            }
        });
       
        
    }   
    
    //date picker fragment
    /**
     * The Class DatePickerFragment.
     */
    public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker datePicker;
   
        //ints for minimum values
        int minimumYear,minimumMonth,minimumDay;
        
        
    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar cal = Calendar.getInstance();
   
    //sets the values of the year to ints
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
   DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year , month, day);
    
    DatePicker datePick = dialog.getDatePicker();

    //minimums set for date condition
    minimumYear = year -21;
    minimumMonth = month+ 1;
    minimumDay = day;
    
 
    
    datePick.setMaxDate(cal.getTimeInMillis());
    return dialog;
    }

    //on set of the date display the birthday to the text view. 
    /* (non-Javadoc)
     * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
    	
    	
    	//if the selected year is less than the minimumYear,(validate) or the selected month is less that the minimum month 
    	//and the selected year is the same as the minimum year,(validate) or if the selected day is less than the minimum day and year and the selected
    	//year and month are equivilant to the minimum year and month allow access
    	if (year < minimumYear ||month < minimumMonth && year == minimumYear||
               day < minimumDay && year == minimumYear && month == minimumMonth) {
    	   
    	   
    	   displayDate .setText("Your birthday is " + String.valueOf(month + 1) + "/"
                   + String.valueOf(day) + "/" + String.valueOf(year));
   	
   		
   		
   		
   		go.setVisibility(View.VISIBLE);
        
   	//else if the selected year is greater than the minimumYear(show toast), or the selected month is greater than the minimum month 
    	//and the selected year is the same as the minimum year,(show toast) or if the selected day is greater than the minimum day and year and the selected
    	//year and month are equivilant to the minimum year and month(show toast)
      }else if (year > minimumYear ||month > minimumMonth && year == minimumYear||
               day > minimumDay && year == minimumYear && month == minimumMonth) {
    	  
    	  displayDate.setText("");
  		
			Toast.makeText(getActivity(), "You must be 21 years old to access this app!", Toast.LENGTH_SHORT).show();
  		
      }
    	
    	
        
    }
    
    
    }

}
