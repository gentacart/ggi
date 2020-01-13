package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CategoryScreen extends Activity {
	public String[] param = new String[20];
	public AppConfiguration appConf;
	public Button[] category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kirim1);
		appConf = new AppConfiguration(getApplicationContext());
		LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		String listcategory = appConf.get("categorylist");
		try {
			JSONArray jsonArray = new JSONArray(listcategory);
			if (jsonArray.length() == 0 ) {

			} else {
				AppConfiguration.productlist.clear();
				category = new Button[jsonArray.length()];
				for (int i=0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.getJSONObject(i);
					final String cat = jo.getString("id");
					category[i] = new Button(this);
					category[i].setText(jo.getString("name"));
					//category[i].setCategoryId(jo.getString("id"));

					category[i].setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							AppConfiguration.categoryproduct = cat;
							new DoUpdateProduct(view.getContext()).execute();
						}
					});


					container.addView(category[i]);

				}
			}

		} catch (JSONException e) {
			Log.d("search error : ", e.toString());
		}
	}
	
	class DoUpdateProduct extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;

		DoUpdateProduct(Context context) {
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
			String username = appConf.get("loginusername");
	    	String ret = SendData.doUpdateProduct("0",username);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
			appConf.set("product", result);
			Log.d("resultproduct",result);
			AppConfiguration.listproduct = result;
			Intent productList = new Intent(context,ProductList.class);
			startActivity(productList);
	    }
	}
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

}
