package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.gcart.android.ggi.AppConfiguration.province;

public class Province extends Activity {
	public String[] param = new String[10];
	public AppConfiguration appConf;
	public String[] provinces = new String[ province.size() + 1];
	public String[] id_provinces = new String[province.size() + 1];
	public String[] cities;
	public String[] id_cities;
	public String[] subdistrict;
	public String[] id_subdistrict;
	public String[] eks = new String[6];
	public Spinner edtEks;
	public  Spinner edtProvince;
	public  Spinner edtCity;
	public Spinner edtSubdistrict;
	public TextView txtErr;
	public static String idprovince;
	public static String idcity = "";
	public static String idsubdistrict = "";
	public static String ideks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jne1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		Button btnSubmit = new Button(this);
		TextView txCity = new TextView(this);
		TextView txtKecamatan = new TextView(this);
		txtErr = new TextView(this);
		txCity.setText("Kota / Kabupaten");
		txCity.setTypeface(null,Typeface.BOLD);
		txtKecamatan.setText("Kecamatan");
		txtKecamatan.setTypeface(null,Typeface.BOLD);
		eks[0] = "jne";
		eks[1] = "jnt";
		eks[2] = "pos";
		eks[3] = "wahana";
		eks[4] = "tiki";
		eks[5] = "sicepat";
		edtEks = new Spinner(this);
		ArrayAdapter adptr = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,eks);
		edtEks.setAdapter(adptr);
		edtProvince = new Spinner(this);
		edtCity = new Spinner(this);
		edtSubdistrict = new Spinner(this);
		provinces[0] = "Pilih provinsi";
		id_provinces[0] = "0";
		try {
			for (int i = 0; i < province.size(); i++) {
				JSONObject jo = new JSONObject(province.get(i).toString());
				provinces[i + 1] = jo.getString("province");
				id_provinces[i + 1] = jo.getString("province_id");
			}
		} catch (JSONException e) {

		}

		ArrayAdapter adapterspin = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,provinces);
		edtProvince.setAdapter(adapterspin);
		container.addView(edtEks);
		 container.addView(edtProvince);
		container.addView(txCity);
		container.addView(edtCity);
		container.addView(txtKecamatan);
		container.addView(edtSubdistrict);
		 container.addView(btnSubmit);
		container.addView(txtErr);
		btnSubmit.setText("Submit");


		//user change province
		edtProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (i != 0) {
					Province.idprovince = id_provinces[i];
					Province.idcity = "";
					Province.idsubdistrict = "";
					new DoSearchCity(view.getContext()).execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		//user change city
		edtCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (i != 0) {
					Province.idcity = id_cities[i];
					new DoSearchSubdistrict(view.getContext()).execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _eks = edtEks.getSelectedItem().toString();
				if (Province.idcity.equalsIgnoreCase("")) {
					txtErr.setText("Pilih kota / kecamatan");
				} else {
					String _idcity = id_cities[edtCity.getSelectedItemPosition()];
					String _idsubdistrict = id_subdistrict[edtSubdistrict.getSelectedItemPosition()];
					AppConfiguration.jnereg = edtEks.getSelectedItem().toString();
					AppConfiguration.jneKota  = edtCity.getSelectedItem().toString();
					AppConfiguration.jneKecamatan  = edtSubdistrict.getSelectedItem().toString();
					new DoSearchCost(v.getContext(),_idsubdistrict,_eks).execute();
				}

			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


	class DoSearchCity extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoSearchCity(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("please wait...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(SendData.rajaongkirserver + "/city?province=" + Province.idprovince)
					.get()
					.addHeader("key", SendData.rajaongkirapi)
					.build();
			try {
				Response response = client.newCall(request).execute();
				return response.body().string();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			AppConfiguration appConf = new AppConfiguration(context);
			mDialog.dismiss();
			try {
				JSONObject jo = new JSONObject(result);
				JSONObject jo2 = new JSONObject(jo.getString("rajaongkir"));
				AppConfiguration.city = AppConfiguration.parseJSON(jo2.getString("results"));
				cities = new String[AppConfiguration.city.size() + 1];
				id_cities = new String[AppConfiguration.city.size() + 1];
				cities[0] = "Pilih Kota";
				id_cities[0] = "0";
				for (int i = 0; i < AppConfiguration.city.size(); i++) {
					JSONObject js = new JSONObject(AppConfiguration.city.get(i).toString());
					cities[i+1] = js.getString("city_name");
					id_cities[i+1] = js.getString("city_id");
				}
				ArrayAdapter adptr = new ArrayAdapter(this.context, android.R.layout.simple_spinner_dropdown_item,cities);
				edtCity.setAdapter(adptr);
			} catch (JSONException e){
				Log.d("jsonerror",e.toString());
			}
		}
	}

	class DoSearchSubdistrict extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoSearchSubdistrict(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("please wait...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(SendData.rajaongkirserver + "/subdistrict?city=" + Province.idcity)
					.get()
					.addHeader("key", SendData.rajaongkirapi)
					.build();
			try {
				Response response = client.newCall(request).execute();
				return response.body().string();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			AppConfiguration appConf = new AppConfiguration(context);
			mDialog.dismiss();
			try {
				JSONObject jo = new JSONObject(result);
				JSONObject jo2 = new JSONObject(jo.getString("rajaongkir"));
				AppConfiguration.subdistrict = AppConfiguration.parseJSON(jo2.getString("results"));
				subdistrict = new String[AppConfiguration.subdistrict.size()];
				id_subdistrict = new String[AppConfiguration.subdistrict.size()];
				for (int i = 0; i < AppConfiguration.subdistrict.size(); i++) {
					JSONObject js = new JSONObject(AppConfiguration.subdistrict.get(i).toString());
					subdistrict[i] = js.getString("subdistrict_name");
					id_subdistrict[i] = js.getString("subdistrict_id");
				}
				ArrayAdapter adptr = new ArrayAdapter(this.context, android.R.layout.simple_spinner_dropdown_item,subdistrict);
				edtSubdistrict.setAdapter(adptr);
			} catch (JSONException e){
				Log.d("jsonerror",e.toString());
			}
		}
	}

	class DoSearchCost extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;
		String _idsubdistrict;
		String ekspedisi;

		DoSearchCost(Context context,String _subd,String _eks) {
			this.context = context;
			this._idsubdistrict = _subd;
			this.ekspedisi = _eks;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("please wait...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			OkHttpClient client = new OkHttpClient();
			int weight = 0;
			if (AppConfiguration.cekongkir == 0) {
				weight = (int) AppConfiguration.totalweight;
			} else {
				weight = 1000;
			}

			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, "origin=" + SendData.originkota +  "&originType=subdistrict" +  "&destination=" + this._idsubdistrict + "&destinationType=subdistrict&weight=" + weight + "&courier=" + this.ekspedisi);
			Request request = new Request.Builder()
					.url(SendData.rajaongkirserver + "/cost")
					.post(body)
					.addHeader("key", SendData.rajaongkirapi)
					.addHeader("content-type", "application/x-www-form-urlencoded")
					.build();
			try {
				Response response = client.newCall(request).execute();
				return response.body().string();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			AppConfiguration appConf = new AppConfiguration(context);
			mDialog.dismiss();
			AppConfiguration.jsonresponse = result;
			Log.d("rajaongkir",result);
			try {
				JSONObject jo = new JSONObject(result);
				JSONObject jo2 = new JSONObject(jo.getString("rajaongkir"));
				JSONArray jo3 = new JSONArray(jo2.getString("results"));
				JSONObject jo4 = new JSONObject(jo3.get(0).toString());
				AppConfiguration.ekspedisi = AppConfiguration.parseJSON(jo4.get("costs").toString());
				if (AppConfiguration.ekspedisi.size() == 0) {
					txtErr.setText("Pencarian paket tidak ditemukan");
				} else {
					finish();
					Intent svc = new Intent(context,ServiceEksScreen.class);
					startActivity(svc);
				}


			} catch (JSONException e){
				Log.d("jsonerror",e.toString());
			};

		}
	}
}
