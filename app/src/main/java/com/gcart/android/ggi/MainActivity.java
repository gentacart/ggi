package com.gcart.android.ggi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
	public String[] param = new String[2];
	public String imei = "";
	public Context ct;
	public AppConfiguration appConf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration appConf = new AppConfiguration(this);
		if (appConf.get("loginusername").equalsIgnoreCase("")) {
			finish();
			Intent login = new Intent(this,LoginScreen.class);
			startActivity(login);
		} else {
			finish();
			Intent home = new Intent(this,HomeScreen.class);
			startActivity(home);
		}
		ct = this;	      
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	 
	
}
