package com.gcart.android.ggi;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterScreen extends Activity {
	public String[] param = new String[6];
	public AppConfiguration appConf;
	public String username = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		appConf = new AppConfiguration(this);
		final Button button = (Button) findViewById(R.id.regSubmit);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText regPhone = (EditText)findViewById(R.id.regPhone);
				EditText regUsername = (EditText)findViewById(R.id.regName);
				EditText regAddress = (EditText) findViewById(R.id.regAddress);
				//EditText regEmail = (EditText) findViewById(R.id.regEmail);
				EditText regPwd1 = (EditText)findViewById(R.id.regPwd1);
				EditText regPwd2 = (EditText)findViewById(R.id.regPwd2);
				if (regPhone.getText().toString().equalsIgnoreCase("") || regUsername.getText().toString().equalsIgnoreCase("") || regPwd1.getText().toString().equalsIgnoreCase("")
						|| regAddress.getText().toString().equalsIgnoreCase("")
						) {
					Toast.makeText(v.getContext(), "Isi dengan lengkap", Toast.LENGTH_SHORT).show();
				} else {
					if (regPwd1.getText().toString().equalsIgnoreCase(regPwd2.getText().toString())) {
						param[0] = regUsername.getText().toString();
						param[1] = regPhone.getText().toString();
						param[2] = regAddress.getText().toString();
						param[3] = "-";
						param[4] = regPwd1.getText().toString();
						username = param[0];
						new GetTask(v.getContext()).execute();
					} else {
						Toast.makeText(v.getContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
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
			String ret = SendData.doRegister(param);
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			if (result.equalsIgnoreCase("1")) { //username sudah ada
				Toast.makeText(this.context, "username sudah pernah terdaftar", Toast.LENGTH_LONG).show();
			} else {
				appConf.set("loginusername", username);
				finish();
				Toast.makeText(context,"harap hub admin untuk pengaktifan agar bisa lihat produk nya",Toast.LENGTH_LONG).show();
				Intent main = new Intent(context,MainActivity.class);
				startActivity(main);
			}
		}
	}

}


