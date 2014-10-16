package com.haard.mecasync;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

@SuppressLint("NewApi")
public class MyAsyncTask extends AsyncTask<String, Void, String> {
	 
    private static final int REGISTRATION_TIMEOUT = 10 * 1000;
    private static final int WAIT_TIMEOUT = 3 * 1000;
    private final HttpClient httpclient = new DefaultHttpClient();
 
    final HttpParams params = httpclient.getParams();
    HttpResponse response;
    private String content =  null;
    private boolean error = false;
 
    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
 
    public MyAsyncTask(Context context){
    	this.mContext = context;								        //Notification manager
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }
 
    protected void onPreExecute(){
    	createNotification("Data downloading from mec.ac.in","");
    }
 
    @SuppressLint("NewApi")
	protected String doInBackground(String... urls){
    	String URL = null; 
        try{
        	URL = urls[0];
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
  
            HttpPost httpPost = new HttpPost(URL);
            String p=urls[1].toString();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            
            nameValuePairs.add(new BasicNameValuePair("class",p));
            nameValuePairs.add(new BasicNameValuePair("Submit","View+"));
      
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httpPost);						//Response
 
            StatusLine statusLine = response.getStatusLine();
            
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){				//Check the Http Request
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                content = out.toString();
                }
            else{
                Log.w("HTTP1:",statusLine.getReasonPhrase());				//Close the connection
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());//}
            	}
            } 
        catch(ConnectTimeoutException e){
        	Log.w("HTTP7:",e );
            content = "";
            error = true;
            cancel(true);       
        }
        catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
            content = e.getMessage();
            error = true;
            cancel(true);
        } 
        
        catch (IOException e) {
            Log.w("HTTP3:",e );
            content = e.getMessage();
            error = true;
            cancel(true);
        }        
        catch (Exception e) {
            Log.w("HTTP4:",e );
            content = e.getMessage();
            error = true;
            cancel(true);
        }       
         return content;												//Return stuff
    }
 
	protected void onCancelled() {mNotificationManager.cancel(NOTIFICATION_ID);}
 
    protected void onPostExecute(String content) {
    if (error) {
    	createNotification("Data download ended abnormally!",content);
        }
    else {
        mNotificationManager.cancel(NOTIFICATION_ID);
        }
    }
 
    private void createNotification(String contentTitle, String contentText) {
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
        .setSmallIcon(android.R.drawable.stat_sys_download)
        .setAutoCancel(true)
        .setContentTitle(contentTitle)
        .setContentText(contentText);
    	 mNotification = builder.build();								//Current notification
    	 mNotificationManager.notify(NOTIFICATION_ID, mNotification);   //Show the notification
    }
 
}