package com.example.adwaz.async;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adwaz.listeners.OnWebServiceProcess;
import com.example.adwaz.utils.CustomProgressDialog;

/**
 * HTTP-POST used with worker thread to hit server and provide response to
 * associated class(s)
 */
public class WebServiceAsyncHttpPost extends AsyncTask<Void, Void, Void> {
	/**
	 * Declare variables
	 */
	private HttpResponse response;
	private OnWebServiceProcess interfaceListener = null;

	private Context context;

	private String getUrl = null;
	private String getResult = null;
	private String TAG = "WEB_SERVICE_ASYNC_HTTP-POST";
	private String exception = "";
	private int receivedId;
	private boolean isSuccess;
	private static CustomProgressDialog dialog;
	String[] keyArray, valueArray;

	/**
	 * Constructor Definition
	 * 
	 * @param Activity
	 *            context
	 * @param web
	 *            service url
	 * @param activity
	 *            process id
	 * @param OnWebServiceProcess
	 *            listener
	 * @param string
	 *            array of keys
	 * @param string
	 *            array of values respect to keys
	 */
	public WebServiceAsyncHttpPost(Context context, String url, int id,
			OnWebServiceProcess listener, String[] keyArr, String[] valueArr) {
		// set the global values for the constructor parameters
		this.interfaceListener = listener;
		this.getUrl = url;
		this.context = context;
		this.receivedId = id;

		if (keyArr != null) {
			this.keyArray = new String[keyArr.length];
			System.arraycopy(keyArr, 0, this.keyArray, 0, keyArr.length);
		}
		if (valueArr != null) {
			this.valueArray = new String[valueArr.length];
			System.arraycopy(valueArr, 0, this.valueArray, 0, valueArr.length);
		}
		// dialog = CustomProgressDialog.getInstance(context);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		// send result to associated class/activity
		if (interfaceListener != null) {
			interfaceListener.getServerValues(getResult, receivedId, isSuccess,
					exception);
		} else {
			interfaceListener.setServerError(receivedId,
					"Error in interface attached " + exception);
		}
		// dialog.dismissDialog();

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// dialog.showDialog("Loading...", 0, "");

	}

	@Override
	protected Void doInBackground(Void... params) {
		// hit server to fetch response
		hitServer();
		return null;
	}

	/**
	 * This method hits the server for response
	 */
	@SuppressWarnings("unchecked")
	public void hitServer() {

		StringBuilder content1 = new StringBuilder();
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(getUrl);
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

			urlConnection.setRequestProperty("Connection", "Keep-Alive");

			urlConnection
					.setRequestProperty("Content-Type", "application/json");

			/*
			 * urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
			 */
			JSONObject json = new JSONObject();
			try {
				if ((keyArray != null) && keyArray.length > 0) {
					for (int i = 0; i < keyArray.length; i++) {
						if (keyArray[i] != null && valueArray[i] != null) {
							json.put(keyArray[i], valueArray[i]);
						}
					}
				}
			} catch (JSONException e) { // TODO Auto-generated catch
										// block
				e.printStackTrace();
			}
			/*
			 * BufferedOutputStream bufferedOut = new BufferedOutputStream(
			 * urlConnection.getOutputStream());
			 * bufferedOut.write(json.toString().getBytes(), 0, json
			 * .toString().getBytes().length);
			 */
			/*
			 * try { FileOutputStream fOut = new FileOutputStream(new File(
			 * Environment.getExternalStorageDirectory(), "jsontext.txt"));
			 * OutputStreamWriter osw = new OutputStreamWriter(fOut);
			 * osw.write(json.toString()); osw.flush(); osw.close(); } catch
			 * (Exception e) { e.printStackTrace(); }
			 */
			urlConnection.setRequestProperty("Content-Length",
					"" + Integer.toString(json.toString().length()));

			DataOutputStream dataout = new DataOutputStream(
					urlConnection.getOutputStream());
			dataout.writeBytes(json.toString());
			/*
			 * dataout.write(json.toString().getBytes(), 0, json
			 * .toString().getBytes().length);
			 */
			int serverResponseCode = urlConnection.getResponseCode();
			String serverResponseMessage = urlConnection.getResponseMessage();
			Log.i("Server Response Code ", "" + serverResponseCode);
			Log.i("Server Response Message", serverResponseMessage);
			/*
			 * BufferedReader bufferedReader = new BufferedReader( new
			 * InputStreamReader( urlConnection.getInputStream()), 8); String
			 * line; while ((line = bufferedReader.readLine()) != null) {
			 * content.append(line); }
			 */

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()), 1000);
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
		}

		catch (Exception e) {
			e.printStackTrace();
			getResult = "Web service response error";
			isSuccess = false;
		} finally {

			urlConnection.disconnect();
			keyArray = null;
			valueArray = null;
		}
	}
}
