package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderSerian2 extends Activity {
	public String[] param = new String[12];
	public Context mc;
	public AppConfiguration appConf;
	public String[] order;
	double totalpayment = 0;
    int totalqty = 0;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.kirim1);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
	    TextView txtconfirm = new TextView(this);
	    TextView txtproduct = new TextView(this);
	    TextView txtisiserian = new TextView(this);
	    TextView txtminorder = new TextView(this);
	    TextView txtqty = new TextView(this);
	    Button btnOrder = new Button(this);
	    final EditText qty = new EditText(this);
	    btnOrder.setText("Order");
	    qty.setHint("Order : (Dalam seri)");
	    qty.setInputType(InputType.TYPE_CLASS_NUMBER);
	    txtconfirm.setText("KONFIRMASI ORDER");
	    txtproduct.setText("Product : " + AppConfiguration.orderproductname);
	    txtisiserian.setText("Total Seri : " + AppConfiguration.orderqty + " Seri");
	    int serian = Integer.parseInt(AppConfiguration.orderserian);
	    int qtyorder = Integer.parseInt(AppConfiguration.orderqty);
	    int totalbarang = serian * qtyorder;
	    txtqty.setText("Total Barang : " + totalbarang + " pcs");
	    container.addView(txtconfirm);
	    container.addView(txtproduct);
	    container.addView(txtisiserian);
	    container.addView(txtqty);
	    container.addView(btnOrder);
	    
	    btnOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = appConf.get("loginusername");
				param[0] = username;
				param[1] = AppConfiguration.orderidproduct;
				param[2] = AppConfiguration.orderqty;
				param[3] = "-";
				param[4] = "-";
				new DoOrder(v.getContext()).execute();
			}
		});
	    
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
	    	String ret = SendData.doOrderSeri(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        Log.d("result : ",result);
	    	Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
        	finish();
	    }
	}

} 