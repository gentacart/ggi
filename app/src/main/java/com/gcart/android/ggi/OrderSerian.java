package com.gcart.android.ggi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderSerian extends Activity {
	public Context mc;
	public AppConfiguration appConf;
	public String[] order;
	double totalpayment = 0;
    int totalqty = 0;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.kirim1);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
	    TextView txtproduct = new TextView(this);
	    TextView txtisiserian = new TextView(this);
	    TextView txtminorder = new TextView(this);
	    TextView txtqty = new TextView(this);
	    Button btnOrder = new Button(this);
	    final EditText qty = new EditText(this);
	    btnOrder.setText("Order");
	    qty.setHint("Order : (Dalam seri)");
	    qty.setInputType(InputType.TYPE_CLASS_NUMBER);
	    txtproduct.setText("Product : " + AppConfiguration.orderproductname);
	    txtisiserian.setText("Isi : " + AppConfiguration.orderserian + " pcs");
	    txtminorder.setText("");
	    txtqty.setText("Order (dalam seri) : ");
	    if (!AppConfiguration.orderminorder.equalsIgnoreCase("0"))
	    	txtminorder.setText("Minimal Order : " + AppConfiguration.orderminorder + " Seri");
	    container.addView(txtproduct);
	    container.addView(txtisiserian);
	    container.addView(txtminorder);
	    //container.addView(txtqty);
	    container.addView(qty);
	    container.addView(btnOrder);
	    
	    btnOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String _qty = qty.getText().toString();
				if (_qty.equalsIgnoreCase("")) {
					Toast.makeText(v.getContext(), "Masukan jumlah order (dalam seri)", Toast.LENGTH_SHORT).show();
				} else {
					AppConfiguration.orderqty = _qty;
					finish();
					Intent ord = new Intent(v.getContext(),OrderSerian2.class);
					startActivity(ord);
				}
			}
		});
	    
	}

} 