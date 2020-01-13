package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderEceran extends Activity {
	public Context mc;
	public String[] param = new String[12];
	public AppConfiguration appConf;
	public String[] order;
	public EditText[] qty;
	public EditText[] news;
	public String[] arrcolor;
	public String[] qtycolor;
	public String[] colorname;
	public String[] arg;
	double totalpayment = 0;
    int totalqty = 0;
    int idx = 0;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.kirim1);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
	    TextView txtproduct = new TextView(this);
	    Button btnOrder = new Button(this);
	    btnOrder.setText("Order");
	    txtproduct.setText("Product : " + AppConfiguration.product.getName());
	    container.addView(txtproduct);
	    qty = new EditText[AppConfiguration.listStock.size()];
		news = new EditText[AppConfiguration.listStock.size()];
	    qtycolor = new String[AppConfiguration.listStock.size()];
	    colorname = new String[AppConfiguration.listStock.size()];
	    for(int i=0; i < AppConfiguration.listStock.size(); i++) {
	    	qty[idx] = new EditText(this);
			news[idx] = new EditText(this);
	    	qty[idx].setInputType(InputType.TYPE_CLASS_NUMBER);
	    	qtycolor[idx] = AppConfiguration.listStock.get(i).getIdcolor();
	    	colorname[idx] = AppConfiguration.listStock.get(i).getColorname();
	    	TextView tx = new TextView(this);
			tx.setText(AppConfiguration.listStock.get(i).getColorname()  + " Stock : " + AppConfiguration.listStock.get(i).getStock() + " pcs");
	    	tx.setGravity(Gravity.CENTER);
	    	qty[idx].setBackgroundResource(R.drawable.txtstyle);
			news[idx].setBackgroundResource(R.drawable.txtstyle);
			qty[idx].setHint("Jumlah : ");
			news[idx].setHint("Berita : ");
	    	container.addView(tx);
	    	container.addView(qty[idx]);
			container.addView(news[idx]);
	    	idx++;
	    }
	    container.addView(btnOrder);
	    
	    btnOrder.setOnClickListener(new View.OnClickListener() {
			String arrcolor = "";
			@Override
			public void onClick(View v) {
				for(int i = 0; i<idx;i++) {
					String _qty = qty[i].getText().toString();
					String _news = news[i].getText().toString();
					if (_qty.equalsIgnoreCase("")) _qty = "0";
					if (_news.equalsIgnoreCase("")) _news = "";
					if (!_qty.equalsIgnoreCase("0"))
						arrcolor = arrcolor  + qtycolor[i] + ";" + colorname[i] + ";" + _qty + ";" + _news + "~";
				}
				String username = appConf.get("loginusername");
				if (username.equalsIgnoreCase("")) {
					appConf.set("loginusername", "");
					finish();
				}
				param[0] = username;
				param[1] = AppConfiguration.product.getId();
				param[2] = arrcolor;
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
	    	String ret = SendData.doOrderEcer(param);
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