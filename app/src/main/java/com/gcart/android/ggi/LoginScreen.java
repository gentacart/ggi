package com.gcart.android.ggi;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends Activity {
	public String[] param = new String[5];
	public String imei = "";
	public String username = "";
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		appConf = new AppConfiguration(this);
		final Button btnRegister = (Button) findViewById(R.id.btnRegister);
		final Button btnLogin = (Button) findViewById(R.id.btnLogin);
		final EditText edtUsername = (EditText) findViewById(R.id.loginUsername);
		final EditText edtPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 param[0] = edtUsername.getText().toString();
	        	 param[1] = edtPassword.getText().toString();
	        	 username = param[0];
	        	 if (param[0].equalsIgnoreCase("") || param[1].equalsIgnoreCase("")) {
	        		 Toast.makeText(v.getContext(), "isi username dan password", Toast.LENGTH_SHORT).show();
	        	 } else {
	        		 new GetTask(v.getContext()).execute();
	        	 }
	        	 	
	         }
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 Intent register = new Intent(v.getContext(),RegisterScreen.class);
	        	 startActivity(register);
	         }
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	class GetTask extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    GetTask(Context context) {
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
	    	String ret = SendData.doLogin(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        if (result.equalsIgnoreCase("1")) {
	        	appConf.set("loginusername", username);
	        	finish();
	        	 Intent main = new Intent(context,MainActivity.class);
	        	 startActivity(main);
	        } else {
	        	Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();
	        }
	    }
	}
	
}


