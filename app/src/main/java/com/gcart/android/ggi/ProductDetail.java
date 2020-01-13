package com.gcart.android.ggi;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import gentalib.StockProduct;

public class ProductDetail extends Activity {
	public String param;
	public String[] paramOrder = new String[6];
	public String[] arrparam;
	public AppConfiguration appConf;
	public String [] arrstock;
	public String nameproduct;
	public String descproduct;
	public String priceproduct;
	public String idproduct;
	public String weight;
	public String serian;
	public String minorder;
	public String jualseri;
	public String savepic;
	public Activity act;
	public static Context ctx;
	private static final int REQUEST_WRITE_STORAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productdetail);
		act = this;
		ctx = this;
		final Button btnOrderProduct = (Button) findViewById(R.id.btnOrderProduct);
		final Button btnSavePic = (Button) findViewById(R.id.btnSavePic);
		final TextView txtProductName = (TextView) findViewById(R.id.det_productname);
		final ImageView imgV = (ImageView) findViewById(R.id.det_image);
		final Button btnShare = (Button) findViewById(R.id.btnShare);
		appConf = new AppConfiguration(getApplicationContext());
		nameproduct = AppConfiguration.product.getName();
		descproduct = AppConfiguration.product.getDescription();
		priceproduct = String.valueOf(AppConfiguration.product.getPrice());
		serian = AppConfiguration.product.getSerian();
		minorder = AppConfiguration.product.getMinorder();
		jualseri = AppConfiguration.product.getJualseri();
		idproduct = AppConfiguration.product.getId();
		weight = String.valueOf(AppConfiguration.product.getWeight());
		AppConfiguration.orderidproduct  = AppConfiguration.product.getId();

		Picasso.get().load(   "https://gentadb.com/pic/" + SendData.webfolder + "/product/" + idproduct + ".jpg").memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgV);
		txtProductName.setText(nameproduct + "\n" + descproduct + "\n" + AppConfiguration.formatNumber(priceproduct)
		+ "\n" + weight + " Kg");
		btnOrderProduct.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
				 new DoListStock(v.getContext()).execute();
	         }
		});
		
		btnShare.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 int permissionCheck = ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
				 if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
					 ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE );
				 } else {
					 final Dialog dialog = new Dialog(v.getContext());

					 dialog.setContentView(R.layout.sharedialog);
					 dialog.setTitle("Save Picture");

					 final EditText editText=(EditText)dialog.findViewById(R.id.editText);
					 editText.setText(nameproduct + " " + AppConfiguration.formatNumber(priceproduct) + " " + descproduct + "");
					 Button save=(Button)dialog.findViewById(R.id.save);
					 Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
					 save.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
							 ClipData clip = ClipData.newPlainText(editText.getText(), editText.getText());
							 clipboard.setPrimaryClip(clip);
							 onShareItem(v);
							 dialog.dismiss();
						 }
					 });
					 btnCancel.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 dialog.dismiss();
						 }
					 });
					 dialog.show();
				 }

	         }
		});
		
		
		btnSavePic.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
				 int permissionCheck = ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
				 if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
					 ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE );
				 } else {
					 if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
						 //new DoSavePic(v.getContext()).execute();
						 final Dialog dialog = new Dialog(v.getContext());

						 dialog.setContentView(R.layout.savedialog);
						 dialog.setTitle("Save Picture");

						 final EditText editText=(EditText)dialog.findViewById(R.id.editText);
						 editText.setText(nameproduct + " " + AppConfiguration.formatNumber(priceproduct) + " " + descproduct + "");
						 Button save=(Button)dialog.findViewById(R.id.save);
						 Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
						 save.setOnClickListener(new View.OnClickListener() {
							 @Override
							 public void onClick(View v) {
								 savepic = editText.getText().toString();
								 new DoSavePic(v.getContext()).execute();
								 dialog.dismiss();
							 }
						 });
						 btnCancel.setOnClickListener(new View.OnClickListener() {
							 @Override
							 public void onClick(View v) {
								 dialog.dismiss();
							 }
						 });
						 dialog.show();
					 } else {
						 Toast.makeText(v.getContext(), "SD CARD not available" , Toast.LENGTH_SHORT).show();
					 }
				 }

	         }
		});
		
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case REQUEST_WRITE_STORAGE:
				if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
					//TODO
					Toast.makeText(this,"silahkan mencoba lagi",Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
		}
	}

	private static Target getTarget(final String _savepic) {
		final ProgressDialog mloading = new ProgressDialog(ctx.getApplicationContext());
		Target target = new Target() {
			@Override
			public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
				try {
					//save to gallery
					String path = Environment.getExternalStorageDirectory() + File.separator + SendData.webfolder;
					File dr = new File(path);
					if (!dr.exists()) dr.mkdir();
					File f = new File( path + File.separator + _savepic + ".jpg");
					FileOutputStream out = new FileOutputStream(f);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
					mloading.dismiss();
					Toast.makeText(ctx.getApplicationContext(),"berhasil",Toast.LENGTH_SHORT).show();
					MediaScannerConnection.scanFile(ctx.getApplicationContext(), new String[] {
									f.getAbsolutePath()},
							null, new MediaScannerConnection.OnScanCompletedListener() {

								public void onScanCompleted(String path, Uri uri)

								{


								}

							});
				} catch (IOException e) {
					e.printStackTrace();
					mloading.dismiss();
				}
			}

			@Override
			public void onBitmapFailed(Exception e, Drawable errorDrawable) {
				mloading.dismiss();
			}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {
				mloading.show();
			}
		};
		return target;
	}
	
	public String checkDigit(int number) {
        return number<=9?"0"+number:String.valueOf(number);
    }


	class DoListStock extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoListStock(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("loading stock..");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			String ret = SendData.doListstock(AppConfiguration.product.getId());
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			try {
				Moshi moshi = new Moshi.Builder().build();
				Type type = Types.newParameterizedType(List.class, StockProduct.class);
				JsonAdapter<List> adapter = moshi.adapter(type);
				AppConfiguration.listStock.clear();
				AppConfiguration.listStock = adapter.fromJson(result);
				Log.d("liststock",AppConfiguration.listStock.toString());

				if (AppConfiguration.product.getJualseri().equalsIgnoreCase("1")) {
					Intent ord = new Intent(context,OrderSerian.class);
					startActivity(ord);
				} else {
					Intent ord = new Intent(context,OrderEceran.class);
					startActivity(ord);
				}

			} catch(IOException e) {
				Toast.makeText(context,"mohon coba lagi",Toast.LENGTH_SHORT).show();
			}

		}
	}

	class DoSavePic extends AsyncTask<Object, Void, ByteArrayOutputStream> {
		Context context;
		ProgressDialog mDialog;

		DoSavePic(Context context) {
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
		protected ByteArrayOutputStream doInBackground(Object... params) {
			String url = "https://gentadb.com/pic/" + SendData.webfolder + "/product/" + AppConfiguration.product.getId() + ".jpg";
			Bitmap bm = null;
			try {
				URL aURL = new URL(url);
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
			} catch (IOException e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 95, bytes);
			return bytes;
		}

		@Override
		protected void onPostExecute(ByteArrayOutputStream result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			String path = Environment.getExternalStorageDirectory() + File.separator + SendData.webfolder;
			File dr = new File(path);
			if (!dr.exists()) dr.mkdir();

			File f = new File(path + File.separator + savepic + ".jpg");
			try {
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(result.toByteArray());
				fo.close();
				//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
				MediaScannerConnection.scanFile(context, new String[]{

								f.getAbsolutePath()},

						null, new MediaScannerConnection.OnScanCompletedListener() {

							public void onScanCompleted(String path, Uri uri) {


							}

						});
			} catch (IOException e) {
				Toast.makeText(context, e.getMessage() + " " + path, Toast.LENGTH_LONG).show();
			} finally {
				Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void onShareItem(View v) {
		// Get access to bitmap image from view
		ImageView ivImage = (ImageView) findViewById(R.id.det_image);
		// Get access to the URI for the bitmap
		Uri bmpUri = getLocalBitmapUri(ivImage);
		if (bmpUri != null) {
			// Construct a ShareIntent with link to image
			Intent shareIntent = new Intent();
			shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
			shareIntent.setType("image/*");
			// Launch sharing dialog for image
			startActivity(Intent.createChooser(shareIntent, "Share Image"));
		} else {
			// ...sharing failed, handle error
		}
	}

	// Returns the URI path to the Bitmap displayed in specified ImageView
	public Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable){
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			String path = Environment.getExternalStorageDirectory() + File.separator + SendData.webfolder;
			File dr = new File(path);
			if (!dr.exists()) dr.mkdir();

			File file = new File( path + File.separator + "share_image_" + System.currentTimeMillis() + ".png" );
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();

			bmpUri = FileProvider.getUriForFile(
					act.getApplicationContext(),
					act.getApplicationContext()
							.getPackageName() + ".provider", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}
}
