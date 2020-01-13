package com.gcart.android.ggi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceEksScreen extends Activity {
	public String[] param = new String[10];

	public AppConfiguration appConf;
	public String[] ekspedisi = new String[ AppConfiguration.ekspedisi.size()];
    public String[] pkt = new String[ AppConfiguration.ekspedisi.size()];
	public String[] cost_ekspedisi = new String[AppConfiguration.ekspedisi.size()];
	public Spinner edtEkspedisi;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jne1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		Button btnSubmit = new Button(this);
		btnSubmit.setText("Submit");
		edtEkspedisi = new Spinner(this);

		try {
			for (int i = 0; i < AppConfiguration.ekspedisi.size(); i++) {
				JSONObject js = new JSONObject(AppConfiguration.ekspedisi.get(i).toString());
				JSONArray js2 = new JSONArray(js.getString("cost"));
				JSONObject js3 = new JSONObject(js2.get(0).toString());
				Log.d("costcost",js3.getString("value"));
                pkt[i] = js.getString("service");
				ekspedisi[i] = js.getString("service") + " (" + AppConfiguration.formatNumber(js3.getString("value")) + ")";
				cost_ekspedisi[i] = js3.get("value").toString();
 			}
			ArrayAdapter adptr = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,ekspedisi);
			edtEkspedisi.setAdapter(adptr);

		} catch(JSONException e) {

		}
		container.addView(edtEkspedisi);
		if (AppConfiguration.cekongkir == 0) container.addView(btnSubmit);





		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppConfiguration.totalongkir = cost_ekspedisi[edtEkspedisi.getSelectedItemPosition()];
                AppConfiguration.paket = pkt[edtEkspedisi.getSelectedItemPosition()];
				finish();
				Intent ps = new Intent(v.getContext(),PaymentScreen2.class);
				startActivity(ps);
			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }




}
