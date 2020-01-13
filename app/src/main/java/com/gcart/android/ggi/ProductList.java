package com.gcart.android.ggi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import gentalib.EndlessRecyclerViewScrollListener;
import gentalib.Product;
import gentalib.ProductAdapter;
import gentalib.RecyclerTouchListener;

public class ProductList extends Activity {
	public Context mc;
	public AppConfiguration appConf;
	private List<Product> listproduct = new ArrayList<>();
	private RecyclerView recyclerView;
	private ProductAdapter mAdapter;
	private JsonAdapter<List> adapter;
	private EndlessRecyclerViewScrollListener scrollListener;
	private int pageOffset = 0;
	private int searchmode = 0;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.productlist);
	    mc = this;
	    appConf = new AppConfiguration(this);
	    listproduct.clear();
	    final Button btnSearch  = (Button) findViewById(R.id.btnSearchProduct);
	    final EditText edtSearch = (EditText) findViewById(R.id.editProductSearch);

		try {
			Moshi moshi = new Moshi.Builder().build();
			Type type = Types.newParameterizedType(List.class, Product.class);
			adapter = moshi.adapter(type);
			listproduct = adapter.fromJson(AppConfiguration.listproduct);
			recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product);
			recyclerView.setHasFixedSize(true);
			mAdapter = new ProductAdapter(listproduct);
			LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
			//GridLayoutManager lm = new GridLayoutManager(getApplicationContext(),2);
			recyclerView.setLayoutManager(lm);
			scrollListener = new EndlessRecyclerViewScrollListener(lm) {
				@Override
				public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
					// Triggered only when new data needs to be appended to the list
					// Add whatever code is needed to append new items to the bottom of the list
					if(searchmode == 0) loadNext();
				}
			};
			// Adds the scroll listener to RecyclerView
			recyclerView.addOnScrollListener(scrollListener);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
			recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
			recyclerView.setAdapter(mAdapter);

			//set onclick method
			recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
						@Override
						public void onClick(View view, int position) {
							AppConfiguration.product = listproduct.get(position);
							Intent intentProduct = new Intent(view.getContext(),ProductDetail.class);
							startActivity(intentProduct);
						}

						@Override
						public void onLongClick(View view, int position) {

						}

					})
			);
			mAdapter.notifyDataSetChanged();

		} catch(IOException e) {

		} catch (NullPointerException e) {
			Toast.makeText(mc, "error : " + e.toString() , Toast.LENGTH_LONG).show();
		} catch (JsonDataException e) {
			Toast.makeText(mc, "error : " + e.toString() , Toast.LENGTH_LONG).show();
		}

		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String searchTerm = edtSearch.getText().toString();
				if (searchTerm.length() < 2) {
					Toast.makeText(v.getContext(),"pencarian minimal 2 karakter",Toast.LENGTH_SHORT).show();
				} else {
					searchmode = 1;
					new DoSearchProduct(v.getContext(),searchTerm).execute();
				}
			}
		});
	}

	public void loadNext() {
		new LoadNext(mc).execute();
	}
	class LoadNext extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		LoadNext(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("loading product...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			String username = appConf.get("loginusername");
            pageOffset += 50;
			String ret = SendData.doUpdateProduct("" + pageOffset,username);
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			try {
				int oldsize = pageOffset + listproduct.size();
				Moshi moshi = new Moshi.Builder().build();
				Type type = Types.newParameterizedType(List.class, Product.class);
				List<Product> newdata = new ArrayList<>();
				JsonAdapter<List> newadapter = moshi.adapter(type);
				newdata = newadapter.fromJson(result);
				listproduct.addAll(newdata);
				mAdapter.notifyItemRangeInserted(oldsize,listproduct.size());

				Log.d("pageoffset","pageoffset " + pageOffset);
			} catch (IOException e) {

			}
		}
	}

	class DoSearchProduct extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;
		private String term;

		DoSearchProduct(Context context,String _term) {
			this.context = context;
			this.term = _term;

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
			String ret = SendData.doSearchProduct(term,username);
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			try {
				listproduct.clear();
				listproduct = adapter.fromJson(result);
				mAdapter.updateData(listproduct);
				mAdapter.notifyDataSetChanged();
			} catch(IOException e) {

			}
		}
	}
}