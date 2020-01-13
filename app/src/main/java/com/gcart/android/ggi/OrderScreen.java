package com.gcart.android.ggi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrderScreen extends Activity {
	public Context mc;
	public AppConfiguration appConf;
	public String[] order;
	double totalpayment = 0;
    int totalqty = 0;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.order);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    final ListView listview = (ListView) findViewById(R.id.listvieworder);
	    final TextView total = (TextView) findViewById(R.id.ordertotal);
	    String listorder = appConf.get("order");
	    if (!listorder.equalsIgnoreCase("")) {
	    	order = AppConfiguration.splitString(listorder, '~', false);
	    	for (int i = 0; i < order.length; i++) {
		    	String [] arr = AppConfiguration.splitString(order[i], ';', false);
		    	totalqty += Integer.parseInt(arr[2]);
		    	totalpayment += Double.parseDouble(arr[4]);
		    }
		    total.setText("Total Qty : " + totalqty + "\nTotal Payment : " + totalpayment);
		    total.setTypeface(null, Typeface.BOLD);
	    } else {
	    	order = new String[] {""};
	    }
	   
	    
	    final StableArrayAdapter adapter = new StableArrayAdapter(this,order);
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent,final View view, int position,long id) {
			
		}

	    });
	  }

	  private class StableArrayAdapter extends ArrayAdapter<String> {
	    private final String[] ord;

	    public StableArrayAdapter(Context context, String[] _ord) {
	      super(context, R.layout.roworder, _ord);
	     this.ord = _ord;
	    }

	  
	    @Override
	    public View getView(int position, View view, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) mc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView= inflater.inflate(R.layout.roworder, null, true);
		    TextView txtTitle = (TextView) rowView.findViewById(R.id.txtorder);
		    if (!ord[position].equalsIgnoreCase("")) {
		    	String[] arr = AppConfiguration.splitString(ord[position], ';', false);
			    txtTitle.setText(arr[1] + "\nQty : " + arr[2] + " Total  : " + arr[4] + "\nWarna : " + arr[6]);
		    } else {
			    txtTitle.setText("No Order");
		    }
		    
		    return rowView;
	    }

	  }

	} 