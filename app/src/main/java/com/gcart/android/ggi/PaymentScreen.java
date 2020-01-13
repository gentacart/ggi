package com.gcart.android.ggi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import gentalib.Order;

public class PaymentScreen extends Activity {
	public String[] param = new String[20];
	public AppConfiguration appConf;
    private List<Order> listorder = new ArrayList<>();
    private JsonAdapter<List> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kirim1);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration.tariff = 0;
		AppConfiguration.jneKecamatan = "";
		AppConfiguration.jneKota= "";
		AppConfiguration.jnereg = "";
		AppConfiguration.paket = "";
		AppConfiguration.ttlweight = 0;
		AppConfiguration.totalweight = 0;
		AppConfiguration.totalongkir = "0";
		AppConfiguration.totalbarang = 0;
		AppConfiguration.ds = "0";
		AppConfiguration.totalpayment = 0;
		AppConfiguration.orderconfirm.clear();
		AppConfiguration.jsonresponse = "";
        LinearLayout container = (LinearLayout) findViewById(R.id.layoutId);
        try {
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Order.class);
            adapter = moshi.adapter(type);
            listorder = adapter.fromJson(appConf.get("order"));

            if (listorder.size() == 0) {
                TextView title = new TextView(this);
                title.setText("Your order is empty");
                container.addView(title);
            } else {
                int totalqty = 0;
                double totalpayment = 0;
                double totalweight = 0;
                for(int i = 0; i < listorder.size(); i++) {
                    totalqty += listorder.get(i).getQty();
                    totalpayment += listorder.get(i).getTotalprice();
                    totalweight += listorder.get(i).getTotalweight();
                }
                TextView info = new TextView(this);
                info.setText("HARAP CENTANG DAN KLIK TOMBOL DI BAWAH UNTUK MELAKUKAN PENGIRIMAN");
                TextView txtqty = new TextView(this);
                txtqty.setText("Total Barang: " + totalqty);
                TextView txtpayment = new TextView(this);
                txtpayment.setText("Total Harga : " + AppConfiguration.formatNumber(totalpayment));
                container.addView(info);
                container.addView(txtqty);
                container.addView(txtpayment);

                final CheckBox[] chk = new CheckBox[listorder.size()];

                CheckBox selAll = new CheckBox(this);
                selAll.setText("Select All");
                container.addView(selAll);

                selAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.d("selectall","hasil : " + b);
                        for(int i = 0; i < chk.length; i++) {
                            chk[i].setChecked(b);
                        }
                    }
                });

                for(int i = 0; i < listorder.size(); i++) {
                    CheckBox ch = new CheckBox(this);
                    ch.setText(listorder.get(i).getProductname() + " " + listorder.get(i).getColor() +
                            "\nJumlah : " + listorder.get(i).getQty() + " Warna : " +
                            listorder.get(i).getColor() + "\nNews : "  + listorder.get(i).getNews());
                    container.addView(ch);
                    chk[i] = ch;
                }
                Button btnKirim = new Button(this);
                btnKirim.setText("Pengiriman Ekspedisi");
                Button btnAmbil = new Button(this);
                btnAmbil.setText("Ambil Toko");
                Button btnTitip = new Button(this);
                btnTitip.setText("Titip Toko");
                Button btnSubmit = new Button(this);
                btnSubmit.setText("Ekspedisi Lain");
                container.addView(btnSubmit);
                container.addView(btnKirim);
                container.addView(btnAmbil);
                container.addView(btnTitip);


                //handle button click
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < listorder.size(); i++) {
                            if(chk[i].isChecked()) {
                                AppConfiguration.orderconfirm.add(listorder.get(i));
                                AppConfiguration.totalbarang += listorder.get(i).getQty();
                                AppConfiguration.totalpayment += listorder.get(i).getTotalprice();
                                AppConfiguration.totalweight += listorder.get(i).getTotalweight();
                            }
                        }
                        if(AppConfiguration.orderconfirm.size() == 0) {
                            Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                            Intent pscreen = new Intent(v.getContext(),PaymentScreen2Eks.class);
                            startActivity(pscreen);
                        }
                    }
                });

                btnAmbil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idorders = "";
                        for(int i = 0; i < listorder.size(); i++) {
                            if(chk[i].isChecked()) {
                                idorders += listorder.get(i).getIdorder() + ",";
                                AppConfiguration.orderconfirm.add(listorder.get(i));
                                AppConfiguration.totalbarang += listorder.get(i).getQty();
                                AppConfiguration.totalpayment += listorder.get(i).getTotalprice();
                                AppConfiguration.totalweight += listorder.get(i).getTotalweight();
                            }
                        }
                        if(AppConfiguration.orderconfirm.size() == 0) {
                            Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
                        } else {
                            String bank = "-";
                            String rekening = "-";
                            String jumlah = "-";
                            String pengirim = "Ambil Toko";
                            String nohp = "-";
                            String penerima = "Ambil Toko";
                            String telepon = "-";
                            String alamat = "-";

                            String username = appConf.get("loginusername");
                            param[0] = username;
                            param[1] = "-";
                            param[2] = "-";
                            param[3] = "-";
                            param[4] = alamat;
                            param[5] = "-";
                            param[6] = pengirim;
                            param[7] = penerima;
                            param[8] = idorders;
                            param[9] = telepon;
                            param[10] = nohp;
                            param[11] = String.valueOf(0);
                            new DoPayment(v.getContext()).execute();
                        }
                    }
                });



                btnKirim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < listorder.size(); i++) {
                            if(chk[i].isChecked()) {
                                AppConfiguration.orderconfirm.add(listorder.get(i));
                                AppConfiguration.totalbarang += listorder.get(i).getQty();
                                AppConfiguration.totalpayment += listorder.get(i).getTotalprice();
                                AppConfiguration.totalweight += listorder.get(i).getTotalweight();
                            }
                        }
                        if(AppConfiguration.orderconfirm.size() == 0) {
                            Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
                        } else {
                            new DoSearchProvince(v.getContext()).execute();
                        }
                    }
                });

                btnTitip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < listorder.size(); i++) {
                            if(chk[i].isChecked()) {
                                AppConfiguration.orderconfirm.add(listorder.get(i));
                                AppConfiguration.totalbarang += listorder.get(i).getQty();
                                AppConfiguration.totalpayment += listorder.get(i).getTotalprice();
                                AppConfiguration.totalweight += listorder.get(i).getTotalweight();
                            }
                        }
                        if(AppConfiguration.orderconfirm.size() == 0) {
                            Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                            Intent pscreen = new Intent(v.getContext(),PaymentScreen2Titip.class);
                            startActivity(pscreen);
                        }
                    }
                });

            }

        } catch (IOException e) {

        } catch (NullPointerException e) {

        }
	}

	
	class DoPayment extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoPayment(Context context) {
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
	    	String ret = SendData.doPaymentNota(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	    	Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
        	finish();
	    }
	}

	class DoSearchProvince extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoSearchProvince(Context context) {
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
					.url("https://pro.rajaongkir.com/api/province")
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
			Log.d("province",result);
			try {
				JSONObject jo = new JSONObject(result);
				JSONObject jo2 = new JSONObject(jo.getString("rajaongkir"));
				AppConfiguration.province = AppConfiguration.parseJSON(jo2.getString("results"));
			} catch (JSONException e){
				Log.d("jsonerror",e.toString());
			}
			finish();
			Intent productList = new Intent(context,Province.class);
			startActivity(productList);
		}
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

}
