package com.gcart.android.ggi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactScreen extends Activity {
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		final TextView txtShopname = (TextView) findViewById(R.id.ctShopName);
		final TextView txtAddress = (TextView) findViewById(R.id.ctAddress);
		final TextView txtPhone = (TextView) findViewById(R.id.ctPhone);
		//final TextView txtHandphone = (TextView) findViewById(R.id.ctHandphone);
		final TextView txtPinbb = (TextView) findViewById(R.id.ctPinbb);
		//final TextView txtEmail = (TextView) findViewById(R.id.ctEmail);
		final TextView txtBank1 = (TextView) findViewById(R.id.ctBank1);
		final TextView txtAccName1 = (TextView) findViewById(R.id.ctAccount1);
		final TextView txtAccNumber1 = (TextView) findViewById(R.id.ctAccountNumber1);
		final TextView txtBank2 = (TextView) findViewById(R.id.ctBank2);
		final TextView txtAccName2 = (TextView) findViewById(R.id.ctAccount2);
		final TextView txtAccNumber2 = (TextView) findViewById(R.id.ctAccountNumber2);
		final TextView txtNews = (TextView) findViewById(R.id.ctNews);
		txtShopname.setText(AppConfiguration.ctName);
		txtAddress.setText(AppConfiguration.ctAddress);
		txtPhone.setText("Phone : " + AppConfiguration.ctPhone);
		//txtHandphone.setText("Line : " + AppConfiguration.ctLine);
		txtPinbb.setText("IG : " + AppConfiguration.ctIG);
		//txtEmail.setText("Email : " + AppConfiguration.ctEmail);
		txtBank1.setText("Bank 1 : " + AppConfiguration.ctBank1);
		txtAccName1.setText("Atas Nama : " + AppConfiguration.ctAccount1);
		txtAccNumber1.setText("No Rek : " + AppConfiguration.ctAccnumber1);
		txtBank2.setText("Bank 2 : " + AppConfiguration.ctBank2);
		txtAccName2.setText("Atas Nama : " + AppConfiguration.ctAccount2);
		txtAccNumber2.setText("No Rek : " + AppConfiguration.ctAccnumber2);
		txtNews.setText("News : \n" + AppConfiguration.ctNews);
	}

}
