package com.haard.mecasync;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Sendvals extends ActionBarActivity {
	
	String op="";
	String[][] tt=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		String clss=intent.getStringExtra(MainActivity.sent_cls);
		String roll=intent.getStringExtra(MainActivity.sent_roll);
		setContentView(R.layout.activity_sendvals);
		String id = intent.getStringExtra(MainActivity.ID1);
		String url ="http://mec.ac.in/attn/view4stud.php";					//TEST ---- "http://posttestserver.com/post.php"; 
		op = example(url,clss);

		if (Integer.parseInt(id) == 1) {
		int p = 0, fl=0,k;
        TextView disp = (TextView)findViewById(R.id.textView1);
       	disp.setTextSize(20);
       	disp.setMovementMethod(new ScrollingMovementMethod());				//Scrolling
       		      
        org.jsoup.nodes.Document doc = Jsoup.parse(op.toString());   		//convert html table to 2d string
		doc.select("br").append("\\n");
		
		
		
        Elements tables = doc.select("table");
        
        for (org.jsoup.nodes.Element table : tables) {
        	Elements trs = table.select("tr");
            String[][][] trtd = new String[tables.size()][trs.size()][];
            for (int i = 0; i < trs.size(); i++) 
            	{Elements tds = trs.get(i).select("td");
                 trtd[p][i] = new String[tds.size()];
                 for (int j = 0; j < tds.size(); j++) 
                	{trtd[p][i][j] = tds.get(j).text();
                	}
                }

            String Buffer = "";
            String RollNo = "";
            for (int q=3; q < trtd[0].length; q++) 
            	{RollNo = trtd[0][q][0].substring(trtd[0][q][0].length()-2,trtd[0][q][0].length());
            	 try {
            		 for (k=0; k < trtd[0][q].length; k++)
            		 	{Buffer = Buffer +trtd[0][1][k]+"="+trtd[0][q][k]+"\n";
            		 	}
            	 	 }
            	 catch (NumberFormatException e) { 
	           		 }
            	 
            	 if(!roll.equals(""))
            	 if ((Integer.parseInt(RollNo)==Integer.parseInt(roll)) )
            	 	{fl=1;  Log.w("DISP", RollNo); break;
            	 	}
            	 Buffer="\n";            	
            	}
        if(fl==1){ disp.setText(Buffer); Log.w("DISP", RollNo); break;}
        else{Toast.makeText(getBaseContext(), "Invalid Roll Number!", Toast.LENGTH_LONG).show();
        	Intent in=new Intent(this,MainActivity.class);
        	startActivity(in);break;
        	} 
        }
       p=p+1;
	}
		else{
			 timetable();
			}
	}
		 
	public void timetable() {
		ActionBar action=getSupportActionBar();
		action.setTitle("TimeTable");
		int p=0;
		
		org.jsoup.nodes.Document doc = Jsoup.parse(op.toString());
		doc.select("br").append("\n");

		Elements tables = doc.select("table");

		for (org.jsoup.nodes.Element table : tables) {

			Elements trs = table.select("tr");
			String[][][] trtd = new String[tables.size()][trs.size()][];
			for (int i = 0; i < trs.size(); i++) {
				Elements tds = trs.get(i).select("td");
				trtd[p][i] = new String[tds.size()];
				for (int j = 0; j < tds.size(); j++) {
					trtd[p][i][j] = tds.get(j).text();

				}	

			}
			 
           p++;
        	   
		if(p==2){
			tt=trtd[1];		
			String[] row= { trtd[1][1][1],null,null,null,null,null};			   
			String[] column={trtd[1][1][1],null,null,null,null,null,null};
			 
			for(int i=0;i<trtd[1].length;i++)
				row[i]=trtd[1][i][0];
		        
			column = trtd[1][0];
		    int rl=row.length; int cl=column.length;
		     
		     Log.d("--", "R-Lenght--"+rl+"   "+"C-Lenght--"+cl);
		     
		  ScrollView sv = new ScrollView(this);
		  TableLayout tableLayout = createTableLayout(row, column,rl, cl);
		  HorizontalScrollView hsv = new HorizontalScrollView(this);
		     
		  hsv.addView(tableLayout);
		  sv.addView(hsv);
		  setContentView(sv);
		}
	  }
	}
	
	public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
	     // get row from table with rowIndex
	     TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

	     // get cell from row with columnIndex
	     TextView textView = (TextView)tableRow.getChildAt(columnIndex);

	     // make it black
	     textView.setBackgroundColor(Color.BLACK);
	 }
	 
	public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex){
	  
	     // get row from table with rowIndex
	     TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

	     // get cell from row with columnIndex
	     TextView textView = (TextView)tableRow.getChildAt(columnIndex);
	     
	     textView.setText("Hello");
	 }

	 private TableLayout createTableLayout(String [] rv, String [] cv,int rowCount, int columnCount) {
	     // 1) Create a tableLayout and its params
	     TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
	     TableLayout tableLayout = new TableLayout(this);
	     tableLayout.setBackgroundColor(Color.BLACK);
         // tableLayout.set
	     // 2) create tableRow params
	     TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(200,200);
	     tableRowParams.setMargins(1, 1, 1, 1);
	     tableRowParams.weight = 1;

	     for (int i = 0; i < 6; i++) {
	     // 3) create tableRow
	         TableRow tableRow = new TableRow(this);
	         tableRow.setBackgroundColor(Color.WHITE);

	         for (int j= 0; j < 7; j++) {
	     // 4) create textView
	             TextView textView = new TextView(this);
	             textView.setBackgroundColor(Color.WHITE);
	             textView.setGravity(Gravity.CENTER);
	             setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	             String s1 = Integer.toString(i);
	    String s2 = Integer.toString(j);
	    String s3 = s1 + s2;
	    int id = Integer.parseInt(s3);
	    textView.setTextSize(8);
	    
	    Log.d("TAG", "-___>"+id);
	                if(i==0){
	                	Log.d("TAAG", "set Column Headers");
	    	              	}
	                else if(j==0){
	               Log.d("TAAG", "Set Row Headers");
	              }
	              textView.setText(""+tt[i][j]);
	              
	      // 5) add textView to tableRow
	             tableRow.addView(textView, tableRowParams);
	         }

	      // 6) add tableRow to tableLayout
	         tableLayout.addView(tableRow, tableLayoutParams);
	     }

	     return tableLayout;
	 }
		 
	public String example(String url, String clss){
		String output = null;
		
		try {
		output = new MyAsyncTask(this).execute(url,clss).get();
		
		} 
		catch(CancellationException e){
			output="";
			Toast.makeText(getBaseContext(), "Could Not Connect to mec.ac.in", Toast.LENGTH_LONG).show();
			Toast.makeText(getBaseContext(), "Please Try Again Later!", Toast.LENGTH_LONG).show();
			try{
			Intent in=new Intent(this,MainActivity.class);
        	startActivity(in);
        	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);}catch(Exception f){}
			Log.w("HTTP8:",e );}
		
		catch (InterruptedException e) {e.printStackTrace();}						// TODO Auto-generated catch block
		
		catch (ExecutionException e) {e.printStackTrace();}							// TODO Auto-generated catch block

		return output;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sendvals, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
	} 
	
	
}


