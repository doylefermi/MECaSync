package com.haard.mecasync;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	Spinner sp;
	public final static String sent_cls="com.haard.mecasync.cls";
	public final static String sent_roll="com.haard.mecasync.roll";
	public final static String ID1 = "com.haard.mecasync.id1";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sp = (Spinner) findViewById(R.id.spinner1);
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
        	 
        	public void onItemSelected(AdapterView<?> arg0, View arg1,
    				int arg2, long arg3) {}   								// TODO Auto-generated method stub	
        	public void onNothingSelected(AdapterView<?> arg0) {}			// TODO Auto-generated method stub
    	});
        
        Button btn = (Button) findViewById(R.id.submitbut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            	if (isOnline())
            		{senddata(v);											// Start func if NET
            		}
            	else{startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);}
            }
        });
        
        Button btn1 = (Button)findViewById(R.id.time);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            	if (isOnline())
            		{senddatatime(v);										// Start func if NET
            		}
            	else{startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);}
            }
        });
         
    }
   
    /* -------------------------------CONNECTIVITY-------------------------------*/
    public boolean isOnline() {
    	ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
    	if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable())
    		{Toast.makeText(getBaseContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
    	     return false;
    		}
    	return true;
    }
    /* -------------------------------CONNECTIVITY-------------------------------*/
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event){  
            if(keyCode==KeyEvent.KEYCODE_BACK)  
            {  	/*for (int i=0; i < 2; i++)
            			{Toast.makeText(getBaseContext(), "haarddev@gmail.com", Toast.LENGTH_LONG).show();
            			} */
            	finish();
            }  
            return true;  
        }
   
       @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId()==R.id.about)
    	{Intent i = new Intent(MainActivity.this, About.class);
        startActivity(i);
        return true;
    	}
    	else return super.onOptionsItemSelected(item);	
    }

    public void senddata(View view) {    	 
    	String id1="1";  
    	Intent intent=new Intent(this, Sendvals.class);
    	String cls=sp.getSelectedItem().toString();
    	EditText rollno=(EditText)findViewById(R.id.rollno);
    	String roll=rollno.getText().toString();
    	if(!roll.equals("")){
        	try{
        	intent.putExtra(sent_cls, cls);
        	intent.putExtra(sent_roll, roll);
        	intent.putExtra(ID1, id1);
        	startActivity(intent);
        	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        	}catch(Exception e){}}
        	else
        	{Toast.makeText(getBaseContext(), "Invalid Roll Number!", Toast.LENGTH_LONG).show();}    	
    }
    
    public void senddatatime(View view) {    	 
    	String id1="2";  
    	Intent intent=new Intent(this, Sendvals.class);
    	String cls=sp.getSelectedItem().toString();
    	EditText rollno=(EditText)findViewById(R.id.rollno);
    	String roll=rollno.getText().toString();
    	try{
        	intent.putExtra(sent_cls, cls);
        	intent.putExtra(sent_roll, roll);
        	intent.putExtra(ID1, id1);
        	startActivity(intent);
        	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        	}catch(Exception e){}
        }
}


