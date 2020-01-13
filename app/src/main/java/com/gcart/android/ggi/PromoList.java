package com.gcart.android.ggi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoList extends Activity {
	public Context mc;
	public AppConfiguration appConf;
	public String[] product;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.product);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    
	    //final ListView listview = (ListView) findViewById(R.id.listview);
	    String listproduct = appConf.get("product");
	    if (!listproduct.equalsIgnoreCase("")) {
	    	product = AppConfiguration.splitString(listproduct, '~', false);
	    } else {
	    	product = new String[] {""};
	    }
	   
	    final StableArrayAdapter adapter = new StableArrayAdapter(this,product);
	    
	    /*
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,final View view, int position,long id) {
				if (!product[position].equalsIgnoreCase("")) {
					appConf.set("productdetail", product[position]);
					Intent resiDetail = new Intent(mc.getApplicationContext(),ResiDetail.class);
		        	startActivity(resiDetail);
				}
			}
	    });
	    */
	  }

	  private class StableArrayAdapter extends ArrayAdapter<String> {
	    private final String[] prod;

	    public StableArrayAdapter(Context context, String[] _prod) {
	      super(context, R.layout.row, _prod);
	     this.prod = _prod;
	    }
	  
	    @Override
	    public View getView(int position, View view, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) mc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView= inflater.inflate(R.layout.row, null, true);
		    TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		    if (!prod[position].equalsIgnoreCase("")) {
		    	String[] arr = AppConfiguration.splitString(prod[position], ';', false);
			    txtTitle.setText(arr[1]);
		    } else {
		    	txtTitle.setText("Tidak ada promo");
		    }
		   
		    return rowView;
	    }

	  }
	  
	} 