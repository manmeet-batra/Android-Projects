package com.example.adwaz.async;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adwaz.utils.CustomProgressDialog;

/**
 * This class is used as a background thread. The server call will be
 * established and the result will be get by the Http call according to user
 */
public class WebServiceAsync {

	// String Variables and int variables
	private HttpResponse response;
	HttpPost httppost;
	private int receivedId;
	private Task task;
	// interface instance
	private static OnWebServiceProcess interfaceListener;
	// Log Tag
	private static final String TAG = "WebServiceAsync";
	private int isGetPost = 0;
	private final int GET = 0, POST = 1;
	// boolean Variable
	private boolean isSuccess;
	// User defined Classes
	private static CustomProgressDialog dialog;
	private static WebServiceAsync instance;
	private final String CHAR_SET = "UTF-8";
	private String[] key, value;
	private static Context ctx;

	/**
	 * This interface used to reduce redundant use of code for server request
	 * and works with {@link WebServiceAsync}
	 */
	public interface OnWebServiceProcess {
		void getServerValues(String response, int id);

		void setServerError(int id, String msg);
	}

	/**
	 * This is singleton method of {@link WebServiceAsync} class. This return
	 * new instance of this class is not created otherwise return old instance
	 * 
	 * @param context
	 *            {@link Context} of current class is passed
	 * @param callback
	 *            {@link OnWebServiceProcess} callback to return back the result
	 * @return {@link WebServiceAsync} instance returned
	 */
	public static WebServiceAsync getInstance(Context context,
			OnWebServiceProcess callback) {

		if (instance == null) {
			Log.i(TAG, "Instance Created");
			ctx = context;
			instance = new WebServiceAsync();

		}
		dialog = CustomProgressDialog.getInstance(context);
		interfaceListener = callback;

		return instance;

	}

	private WebServiceAsync() {
	}

	/**
	 * This method is used to runTask in background
	 * 
	 * @param url
	 *            from where you want to get response
	 * @param id
	 *            unique id to be passed
	 */
	public void get(String url, int id, String userName, String password) {
		receivedId = id;
		isGetPost = GET;
		Task task = new Task(null, null);
		if (userName != null && password != null) {
			task.execute(url + "/" + userName + "/" + password);
		} else {
			task.execute(url);
		}

	}

	/**
	 * This method is used to runTask in background
	 * 
	 * @param url
	 *            from where you want to get response
	 * @param id
	 *            unique id to be passed
	 */
	public void getPair(String url, int id, String[] key1, String[] value1) {
		receivedId = id;
		isGetPost = GET;
		this.key = key1;
		this.value = value1;
		Task task = new Task(key, value);
		String data = encodeUrl(key, value);
		task.execute(url, data);

	}

	public void post(String url, int id, String[] key1, String[] value1) {
		receivedId = id;
		isGetPost = POST;
		this.key = key1;
		this.value = value1;
		this.task = new Task(key, value);
		synchronized (this) {
			task.execute(url);
		}
	}

	/**
	 * This class is used to run in background
	 * 
	 * @author Amit
	 * 
	 */
	private class Task extends AsyncTask<String, Void, String> {

		private String[] key;
		private String[] value;

		@Override
		protected synchronized void onPostExecute(String result) {
			dialog.dismissDialog();
			if (isSuccess == false) {
				interfaceListener.setServerError(receivedId, result);
			} else {
				interfaceListener.getServerValues(result, receivedId);
			}

			/*
			 * try { Thread.sleep(10000); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
		}

		public Task(String[] key, String[] value) {
			this.key = key;
			this.value = value;
		}

		@Override
		protected synchronized void onPreExecute() {
			dialog.showDialog("Loading...", 0, "");
		}

		@Override
		protected synchronized String doInBackground(String... params) {

			String getResult = null;
			StringBuilder content = new StringBuilder();
			switch (isGetPost) {
			case GET:
				try {
					/*
					 * HttpGet httpGet = new HttpGet(params[0]);
					 * 
					 * HttpParams myParams = new BasicHttpParams();
					 * HttpConnectionParams.setConnectionTimeout(myParams,
					 * 10000); HttpConnectionParams.setSoTimeout(myParams, 60 *
					 * 1000); DefaultHttpClient defaultClient = new
					 * DefaultHttpClient( myParams);
					 */

					URL url = new URL(params[0]);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setReadTimeout(10 * 1000);
					urlConnection.setConnectTimeout(30 * 1000);

					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()), 8);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						content.append(line);
					}

					getResult = content.toString();
					isSuccess = true;
					bufferedReader.close();
				} catch (UnknownHostException e) {
					isSuccess = false;
					getResult = "Internet Connection error";
				} catch (Exception e) {
					isSuccess = false;
					getResult = "Web service response error";
					e.printStackTrace();
				} finally {

				}

				break;
			case POST:
				StringBuilder content1 = new StringBuilder();
				HttpURLConnection urlConnection = null;
				try {
					URL url = new URL(params[0]);
					/*
					 * URL url = new URL(
					 * "http://universalcloud.netsmartz.biz/UniversalCloud/SaveUserImages"
					 * );
					 */
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setReadTimeout(10 * 1000);
					urlConnection.setConnectTimeout(30 * 1000);
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.setUseCaches(true);
					urlConnection.setChunkedStreamingMode(1024);
					// Enable POST method
					urlConnection.setRequestMethod("POST");

					urlConnection
							.setRequestProperty("Connection", "Keep-Alive");

					urlConnection.setRequestProperty("Content-Type",
							"application/json");

					/*
					 * urlConnection.setRequestProperty("Accept-Charset",
					 * "UTF-8");
					 */
					JSONObject json = new JSONObject();
					try {
						if ((key != null) && key.length > 0) {
							for (int i = 0; i < key.length; i++) {
								if (key[i] != null && value[i] != null) {
									json.put(key[i], value[i]);
								}
							}
						}
					} catch (JSONException e) { // TODO Auto-generated catch
												// block
						e.printStackTrace();
					}
					/*
					 * BufferedOutputStream bufferedOut = new
					 * BufferedOutputStream( urlConnection.getOutputStream());
					 * bufferedOut.write(json.toString().getBytes(), 0, json
					 * .toString().getBytes().length);
					 */
					/*
					 * try { FileOutputStream fOut = new FileOutputStream(new
					 * File( Environment.getExternalStorageDirectory(),
					 * "jsontext.txt")); OutputStreamWriter osw = new
					 * OutputStreamWriter(fOut); osw.write(json.toString());
					 * osw.flush(); osw.close(); } catch (Exception e) {
					 * e.printStackTrace(); }
					 */
					urlConnection.setRequestProperty("Content-Length", ""
							+ Integer.toString(json.toString().length()));

					DataOutputStream dataout = new DataOutputStream(
							urlConnection.getOutputStream());
					dataout.writeBytes(json.toString());
					/*
					 * dataout.write(json.toString().getBytes(), 0, json
					 * .toString().getBytes().length);
					 */
					int serverResponseCode = urlConnection.getResponseCode();
					String serverResponseMessage = urlConnection
							.getResponseMessage();
					Log.i("Server Response Code ", "" + serverResponseCode);
					Log.i("Server Response Message", serverResponseMessage);
					/*
					 * BufferedReader bufferedReader = new BufferedReader( new
					 * InputStreamReader( urlConnection.getInputStream()), 8);
					 * String line; while ((line = bufferedReader.readLine()) !=
					 * null) { content.append(line); }
					 */

					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()), 1000);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						content1.append(line);
					}
					getResult = content1.toString();
					isSuccess = true;
					// bufferedReader.close();
					urlConnection.disconnect();

				} catch (UnknownHostException e) {
					e.printStackTrace();
					isSuccess = false;
					getResult = "Internet Connection error";
				} catch (Exception e) {
					e.printStackTrace();
					getResult = "Web service response error";
					isSuccess = false;
				} finally {
					urlConnection.disconnect();
					value = null;
					key = null;
				}

				break;
			default:
				break;
			}
			return getResult;

		}
	}

	/**
	 * This method is used to make key/value pair into string and pass to
	 * postserver
	 * 
	 * @param key
	 *            set key[]
	 * @param value
	 *            set value[]
	 * @return string
	 */
	private String encodeUrl(String[] key, String[] value) {
		if (key == null && value == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		int length = key.length;
		if (length != value.length) {
			return "";
		}
		for (int i = 0; i < length; i++) {

			if (!(key instanceof String[])) {
				continue;
			}

			if (first)
				first = false;
			else
				sb.append("&");
			try {
				sb.append(key[i] + "=" + URLEncoder.encode(value[i], CHAR_SET));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}