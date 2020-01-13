package com.gcart.android.ggi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class ResiDetail extends Activity {
	public String param;
	public String[] paramOrder = new String[6];
	public String[] arrparam;
	public AppConfiguration appConf;
	public String [] arrstock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.residetail);
		final TextView txtProductName = (TextView) findViewById(R.id.resi_productname);
		appConf = new AppConfiguration(getApplicationContext());
		param = appConf.get("productdetail");
		arrparam = AppConfiguration.splitString(param, ';', false);
		txtProductName.setText(arrparam[1] + "\n" + arrparam[2]);
		
	}
	
	public String checkDigit(int number) {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	class DoOrder extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoOrder(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Please wait...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doOrder(paramOrder);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	        finish();
	    }
	}
	
	class DoSavePic extends AsyncTask<Object, Void, ByteArrayOutputStream> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoSavePic(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Please wait...");
	        mDialog.show();
	    }

	    @Override
	    protected ByteArrayOutputStream doInBackground(Object... params) {
	    	String url = SendData.server + "/product/" + arrparam[0] + ".jpg";
	        Bitmap bm = null;
	        try { 
	            URL aURL = new URL(url); 
	            URLConnection conn = aURL.openConnection();
	            conn.connect();
	            InputStream is = conn.getInputStream();
	            BufferedInputStream bis = new BufferedInputStream(is);
	            bm = BitmapFactory.decodeStream(bis);
	            bis.close();
	            is.close();  
	       } catch (IOException e) {
	    	   Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
	       } 
	       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		   bm.compress(Bitmap.CompressFormat.JPEG, 95, bytes);
		   return bytes;
	    }

	    @Override
	    protected void onPostExecute(ByteArrayOutputStream result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        String path = Environment.getExternalStorageDirectory() + File.separator + SendData.webfolder;
			   File dr = new File(path);
			   if (!dr.exists()) dr.mkdir();
			   
			   File f = new File( path + File.separator + arrparam[1] + " " + arrparam[3] + ".jpg");
		       try {  
					f.createNewFile();
					FileOutputStream fo = new FileOutputStream(f);
				    fo.write(result.toByteArray());
				    fo.close();
				    //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
				    MediaScannerConnection.scanFile(context, new String[] {

				    		f.getAbsolutePath()},

				    		null, new MediaScannerConnection.OnScanCompletedListener() {

				    		public void onScanCompleted(String path, Uri uri)

				    		{


				    		}

				    		});
				} catch (IOException e) {
					Toast.makeText(context, e.getMessage() + " " + path, Toast.LENGTH_LONG).show();
				} finally {
					 Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
				} 
	    }
	}
}
