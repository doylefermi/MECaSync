package com.haard.mecasync;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		TextView disp = (TextView)findViewById(R.id.disp);
		disp.setTextSize(20);
		String s="developed by:-\n\n\t\t\th{a}rd\n\n\n\n h\t\t- Hari\n{a}\t- Abi\n\t\t\t- Alan\n\t\t\t- Anil\n\t\t\t- Athil\n\t\t\t- Athul\n r\t\t- Roshan\nd\t\t- Doyle";
		disp.setText(s);
		
		Button btn = (Button) findViewById(R.id.change);
	    btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) { change(v);
					// TODO Auto-generated method stub
					
				}	            
	        
	        });
	}

	public void change(View view) {    	  	
		ChangeLogDialog _ChangelogDialog = new ChangeLogDialog(this); 
		_ChangelogDialog.show();  
    }
}
