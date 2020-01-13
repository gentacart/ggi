package com.gcart.android.ggi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import gentalib.Nota;

public class NotaScreen extends Activity {
	public String[] param = new String[6];
	public AppConfiguration appConf;
	private List<Nota> listnota = new ArrayList<>();
	private JsonAdapter<List> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment1);
		appConf = new AppConfiguration(getApplicationContext());
		LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		try {
			Moshi moshi = new Moshi.Builder().build();
			Type type = Types.newParameterizedType(List.class, Nota.class);
			adapter = moshi.adapter(type);
			listnota = adapter.fromJson(appConf.get("nota"));

			if (listnota.size() == 0) {
				TextView title = new TextView(this);
				title.setText("tidak ada nota");
				container.addView(title);
			} else {
				final CheckBox[] chk = new CheckBox[listnota.size()];
				for(int i = 0; i < listnota.size(); i++) {
					CheckBox ch = new CheckBox(this);
					ch.setText("NO NOTA : " + listnota.get(i).getIdnota() + "\n" +
							listnota.get(i).getConfirmdate() + "\n" +
							"Total : " + AppConfiguration.formatNumber(listnota.get(i).getTotalprice()) + "\n" +
							"Ongkir : " + AppConfiguration.formatNumber(listnota.get(i).getOngkir()) + "\n" +
							"Total Bayar : " + AppConfiguration.formatNumber(listnota.get(i).getTotalbayar()) + "\n" +
							"Alamat Pengiriman : " + listnota.get(i).getAlamat() + "\n" +
							"Nama pengirim : " + listnota.get(i).getNamapengirim() + "\n" +
							"HP Pengirim : " + listnota.get(i).getHppengirim() + "\n" +
							"Nama Penerima  : " + listnota.get(i).getNamapenerima() + "\n" +
							"HP Penerima : " + listnota.get(i).getHppenerima() + "\n" +
							"Order :\n " + listnota.get(i).getListorder() + "\n");
					container.addView(ch);
					chk[i] = ch;
				}
				Button btnSubmit = new Button(this);
				btnSubmit.setText("Konfirmasi Bayar");
				container.addView(btnSubmit);

				btnSubmit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String ret2 = "";
						double totalpayment = 0;
						for(int i = 0; i < listnota.size(); i++) {
							if(chk[i].isChecked()) {
								ret2 += listnota.get(i).getIdnota() + ",";
								totalpayment += listnota.get(i).getTotalbayar();
							}
						}
						if(ret2.equalsIgnoreCase("")) {
							Toast.makeText(v.getContext(), "Pilih nota",Toast.LENGTH_LONG).show();
						} else {
							AppConfiguration.idorder = ret2;
							AppConfiguration.totalpayment = totalpayment;
							finish();
							Intent pscreen = new Intent(v.getContext(),PaymentNotaScreen.class);
							startActivity(pscreen);
						}

					}
				});

			}
		} catch (IOException e) {

		} catch (NullPointerException e) {

		}

		final String[] arrorder = AppConfiguration.splitString(appConf.get("paymentproses"), '~', false);
		 if (arrorder.length == 0) {

		 } else {


			 final String[] chkstr = new String[arrorder.length];
			 


			 

		 }
         
	}
	
	
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	
}
