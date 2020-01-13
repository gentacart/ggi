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

public class PaymentScreen3 extends Activity {
	public String[] param = new String[6];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 final String[] arrorder = AppConfiguration.splitString(appConf.get("paymentproses"), '~', false);
		 if (arrorder.length == 0) {
			 TextView title = new TextView(this);
			 title.setText("tidak ada nota");
			 container.addView(title);
		 } else {
			 	int totalqty = 0;
			 	double totalpayment = 0;
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				if (row[10].equalsIgnoreCase("0")) {
					totalqty += Integer.parseInt(row[2]);
					totalpayment += Double.parseDouble(row[4]);
				}
			 }
			 final CheckBox[] chk = new CheckBox[arrorder.length];
			 final String[] chkstr = new String[arrorder.length];
			 
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				 CheckBox ch = new CheckBox(this);
				 ch.setText("NO NOTA : " + row[12] + "\n" +
							row[8] + "\n" +
							"Total : " + row[10] + "\n" +
							"Ongkir : " + row[13] + "\n" +
							"Total Bayar : " + row[14] + "\n" +
							"Alamat Pengiriman : " + row[1] + "\n" +
							"Nama pengirim : " + row[2] + "\n" +
							"HP Pengirim : " + row[11] + "\n" +
							"Nama Penerima  : " + row[3] + "\n" +
							"Telepon : " + row[4] + "\n" +
							"Order :\n " + row[0] + "\n");
				 container.addView(ch);
				 chk[i] = ch;
				 chkstr[i] = arrorder[i] + ";";
			 } 
			 Button btnSubmit = new Button(this);
			 btnSubmit.setText("Konfirmasi Bayar");
			 container.addView(btnSubmit);
			 
			 btnSubmit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String ret = "";
						String ret2 = "";
						double totalpayment = 0;
						for(int i = 0; i < arrorder.length; i++) {
							if(chk[i].isChecked()) {
								String[] _rw = AppConfiguration.splitString(chkstr[i], ';', false);
								ret += chkstr[i] + "~";
								ret2 += _rw[12] + ",";
								totalpayment += Double.parseDouble(_rw[14]);
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
         
	}
	
	
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	
}
