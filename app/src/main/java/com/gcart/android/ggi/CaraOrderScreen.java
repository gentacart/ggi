package com.gcart.android.ggi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CaraOrderScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		final TextView txtShopname = (TextView) findViewById(R.id.ctShopName);
		txtShopname.setText(AppConfiguration.ctNews);
	}
}
