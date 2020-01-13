package com.gcart.android.ggi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentScreen2 extends Activity {
	public String[] param = new String[12];
	public AppConfiguration appConf;
	public String idorders = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		for(int i = 0; i < AppConfiguration.orderconfirm.size(); i++) {
			idorders += AppConfiguration.orderconfirm.get(i).getIdorder() + ",";
		}
		TextView txtTotalbarang = new TextView(this);
		txtTotalbarang.setText("Total Barang : " + AppConfiguration.totalbarang + " Pcs");
		TextView txtOngkir = new TextView(this);
		txtOngkir.setText("Ongkir : " + AppConfiguration.formatNumber(AppConfiguration.totalongkir) + " IDR");
		TextView txtTotalbayar = new TextView(this);
		double total = AppConfiguration.totalpayment + Double.parseDouble(AppConfiguration.totalongkir);
		txtTotalbayar.setText("Total Harga + Ongkir : " + AppConfiguration.formatNumber(total) + " IDR");
		TextView txtTotalberat = new TextView(this);
		txtTotalberat.setText("Total Berat : " + AppConfiguration.formatNumber(AppConfiguration.totalweight) + " gram");
		container.addView(txtTotalbarang);
		container.addView(txtOngkir);
		container.addView(txtTotalbayar);
		container.addView(txtTotalberat);
		 Button btnSubmit = new Button(this);
		 final EditText edtBank = new EditText(this);
		 final EditText edtRekening = new EditText(this);
		 final EditText edtJumlah = new EditText(this);
		 final EditText edtPengirim = new EditText(this);
		 final EditText edtHPpengirim = new EditText(this);
		 final EditText edtPenerima = new EditText(this);
		 final EditText edtHPpenerima = new EditText(this);
		 final EditText edtAlamat = new EditText(this);
		final CheckBox dropship = new CheckBox(this);
		dropship.setText("Kirim Dropship");
		dropship.setChecked(true);

		 edtBank.setHint("A/N. BANK :");
		 edtRekening.setHint("A/N. REKENING ANDA : ");
		 edtJumlah.setHint("Total Bayar + Ongkir : ");
		 edtPengirim.setHint("Nama Pengirim : ");
		edtHPpengirim.setHint("No HP Pengirim : ");
		 edtPenerima.setHint("Nama Penerima : ");
		edtHPpenerima.setHint("Nomor Telepon : ");
		 edtAlamat.setHint("Alamat Penerima : ");
		 edtRekening.setInputType(InputType.TYPE_CLASS_NUMBER);
		 edtRekening.setInputType(InputType.TYPE_CLASS_NUMBER);
		 edtJumlah.setInputType(InputType.TYPE_CLASS_NUMBER);
		edtHPpenerima.setInputType(InputType.TYPE_CLASS_PHONE);
		edtHPpengirim.setInputType(InputType.TYPE_CLASS_PHONE);
		 //container.addView(edtJumlah);
		 //container.addView(edtBank);
		 //container.addView(edtRekening);
		container.addView(dropship);
		 container.addView(edtPengirim);
		 container.addView(edtHPpengirim);
		 container.addView(edtPenerima);
		 container.addView(edtHPpenerima);
		 container.addView(edtAlamat);
		 final DatePicker datePicker = new DatePicker(this);
		 //container.addView(datePicker);
		 btnSubmit.setText("KONFIRMASI");
		 container.addView(btnSubmit);
		dropship.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(dropship.isChecked()) {
					edtPengirim.setVisibility(View.VISIBLE);
					edtHPpengirim.setVisibility(View.VISIBLE);
					Log.d("dropship","1");
				} else {
					edtPengirim.setText("");
					edtHPpengirim.setText("");
					edtPengirim.setVisibility(View.GONE);
					edtHPpengirim.setVisibility(View.GONE);
					Log.d("dropship","0");
				}
			}
		});
		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String bank = edtBank.getText().toString();
				String rekening = edtRekening.getText().toString();
				String jumlah = edtJumlah.getText().toString();
				String pengirim = edtPengirim.getText().toString();
				String hppengirim = edtHPpengirim.getText().toString();
				String penerima = edtPenerima.getText().toString();
				String hppenerima = edtHPpenerima.getText().toString();
				String alamat = edtAlamat.getText().toString();
				if (dropship.isChecked()) {
					AppConfiguration.ds = "1";
				}else {
					AppConfiguration.ds = "0";
				}

				String username = appConf.get("loginusername");
				param[0] = username;
				param[1] = "";
				param[2] = "";
				param[3] = "";
				param[4] = alamat;
				//param[5] = "";
				param[5] = datePicker.getYear()  + "-" + checkDigit(datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + " 00:00:00";
				param[6] = pengirim;
				param[7] = penerima;
				param[8] = idorders;
				param[9] = hppenerima;
				param[10] = hppengirim;
				param[11] = AppConfiguration.totalongkir;
				new DoPayment(v.getContext()).execute();	
			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
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
	        Log.d("result : ",result);
	    	Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
        	finish();
	    }
	}
}
