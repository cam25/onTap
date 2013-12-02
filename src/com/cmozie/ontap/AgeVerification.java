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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AgeVerification extends Activity {
	static TextView displayDate;
    private Button pickDate;
    private Button go;
    private static final int AGE_LIMIT = 1990;
    private static final int DEFAULT_END_YEAR = 2013;
   public static Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_verification);
        displayDate = (TextView) findViewById(R.id.displayDate);
        pickDate = (Button) findViewById(R.id.pickDate);
        go = (Button) findViewById(R.id.go);
        
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
      go.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            startActivity(new Intent(AgeVerification.this, MainActivity.class));
            finish();
		}
	});
        
        pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
       
        
    }   
    
    
    public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker datePicker;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar cal = Calendar.getInstance();
   
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    
    
    
    return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
    	
    	if (year <= 1990) {
    		
    			
    		displayDate .setText(String.valueOf(day) + "/"
                    + String.valueOf(month + 1) + "/" + String.valueOf(year));
    		
    	        	
    			
		}
    	
        
    }
    
    }

}
