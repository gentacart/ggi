package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentHistoryNotaScreen extends Activity {
	public String[] param = new String[6];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kirim1);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration.tariff = 0;
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 final String[] arrorder = AppConfiguration.splitString(appConf.get("paymentproses"), '~', false);
		 if (arrorder.length == 0) {
			 TextView title = new TextView(this);
			 title.setText("Tidak ada pembayaran nota");
			 container.addView(title);
		 } else {
			 
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				 TextView t = new TextView(this);
				 t.setText("Total Bayar : " + row[5] + "\nBank : " + row[7]
						 + "\nRekening Anda : " + row[6] + "\nID NOTA : \n" + row[0]
						+ "\n\n"
						 );
				 container.addView(t);
			 }
			 
		 }
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	class DoPayment extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoPayment(Context context) {
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
	    	String ret = SendData.doPayment(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	    	Toast.makeText(this.context, "success", Toast.LENGTH_SHORT).show();
        	finish();
	    }
	}
}
