package com.gcart.android.ggi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SendData {
	//change this webfolder only
	static String webfolder = "ggi";
	static String server = "https://olshop18.com/" + webfolder;
	static String controller = server + "/index.php/servicehttps/";
	static String controllerweb = server + "/index.php/web/";
	static String folderimage = server + "/product/";
	static String rajaongkirapi = "c2f000c2fb9d8b8ac7d1659148c026fc";
	static String rajaongkirserver = "https://pro.rajaongkir.com/api";
	static String originkota = "2102";


	public static String doUpdateCategory(String  arg) {
		String ret = "";
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg)
				.build();
		Request request = new Request.Builder()
				.url(controller + "listcategory").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		OkHttpClient client = new OkHttpClient();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdateProduct(String  offset,String username) {
		String ret = "";
		RequestBody formBody = new FormEncodingBuilder()
				.add("offset",offset)
				.add("cat",AppConfiguration.categoryproduct)
				.add("username",username)
				.build();
		Request request = new Request.Builder()
				.url(controller + "product").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		OkHttpClient client = new OkHttpClient();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doSearchProduct(String term,String username) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("term",term)
				.add("username",username)
				.add("cat",AppConfiguration.categoryproduct)
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchproduct").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doListstock(String id) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("id", id)
				.build();
		Request request = new Request.Builder()
				.url(controller + "liststock").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdateResi() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadresi").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doLogin(String [] arg) {
		String ret = "";
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("password", arg[1])
				.build();
		Request request = new Request.Builder()
				.url(controller + "login").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		OkHttpClient client = new OkHttpClient();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doOrder(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idproduct",arg[1])
				.add("qty",arg[2])
				.add("news",arg[3])
				.add("shippingaddress",arg[4])
				.add("color",arg[5])
				.build();
		Request request = new Request.Builder()
				.url(controller + "neworder").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}


	public static String doOrderSeri(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idproduct",arg[1])
				.add("qty",arg[2])
				.add("news",arg[3])
				.add("shippingaddress",arg[4])
				.build();
		Request request = new Request.Builder()
				.url(controller + "neworderseri").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doOrderEcer(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idproduct",arg[1])
				.add("color",arg[2])
				.build();
		Request request = new Request.Builder()
				.url(controller + "neworderecer").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doRefreshOrder(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "order").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}



	public static String doRefreshPaymentNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshpayment").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshnota").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshNotaHistory(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshnotahistory").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void doGetPicRow(ImageView img,String id) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("id", id)
				.build();
		Request request = new Request.Builder()
				.url(controller + "getpict").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] decodedString = Base64.decode(ret, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		img.setImageBitmap(decodedByte);

	}

	public static String doRegister(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("phone", arg[1])
				.add("address",arg[2])
				.add("email",arg[3])
				.add("password",arg[4])
				.build();
		Request request = new Request.Builder()
				.url(controller + "register").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doContact() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "printcontact").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doPayment(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("bank", arg[1])
				.add("account", arg[2])
				.add("amount",arg[3])
				.add("news",arg[4])
				.add("paymentdate",arg[5])
				.add("namapengirim","")
				.add("namapenerima","")
				.add("idorder",arg[6])
				.add("telepon","")
				.add("nohp","")
				.add("keterangan",arg[7])
				.add("banktoko",arg[8])
				.add("rekeningtoko",arg[9])
				.build();
		Request request = new Request.Builder()
				.url(controller + "newpayment").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doPaymentNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("bank", arg[1])
				.add("account",arg[2])
				.add("amount",arg[3])
				.add("news",arg[4])
				.add("paymentdate",arg[5])
				.add("namapengirim",arg[6])
				.add("namapenerima",arg[7])
				.add("idorder",arg[8])
				.add("hppenerima",arg[9])
				.add("hppengirim",arg[10])
				.add("ongkir",arg[11])
				.add("kota",AppConfiguration.jneKota)
				.add("kecamatan",AppConfiguration.jneKecamatan)
				.add("jnereg",AppConfiguration.jnereg)
				.add("paket",AppConfiguration.paket)
				.add("ds",AppConfiguration.ds)
				.add("jsonresponse",AppConfiguration.jsonresponse)
				.build();
		Request request = new Request.Builder()
				.url(controller + "newnota").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String saveImage() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadpic").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdatePromo() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadpromo").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doCaraOrder() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "printcaraorder").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doInfosistem() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "printinfosistem").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	

	
}
