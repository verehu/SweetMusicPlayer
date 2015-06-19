package com.huwei.sweetmusicplayer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

@Deprecated
public class OnlineLrcUtil {
	private static String TAG = "OnlineLrcUtil";
	private static OnlineLrcUtil instance;
	public static final String lrcRootPath = Environment
			.getExternalStorageDirectory().toString()
			+ "/SweetMusicPlayer/Lyrics/";

	public static final String queryLrcURLRoot = "http://geci.me/api/lyric/";

	public static OnlineLrcUtil getInstance() {
		if (null == instance) {
			instance = new OnlineLrcUtil();
		}

		return instance;
	}

	public String getQueryLrcURL(String title, String artist) {
		return queryLrcURLRoot + Encode(title) + "/" + Encode(artist);
	}

	public String getLrcURL(String title, String artist) {
		String queryLrcURLStr = getQueryLrcURL(title, artist);
		try {
			URL url = new URL(queryLrcURLStr);
			URLConnection urlConnection = url.openConnection();
			urlConnection.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			StringBuffer sb = new StringBuffer();

			String temp;
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}

			JSONObject jObject = new JSONObject(sb.toString());
			int count = jObject.getInt("count");
			int index = count == 0 ? 0 : new Random().nextInt() % count;
			JSONArray jArray = jObject.getJSONArray("result");
			JSONObject obj = jArray.getJSONObject(index);
			return obj.getString("lrc");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	public String Encode(String str) {

		try {
			return URLEncoder.encode(str.trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;

	}


	public boolean wrtieContentFromUrl(String urlPath, String lrcPath) {
		Log.i(TAG, "lrcURL" + urlPath);

		try {
			URL url = new URL(urlPath);

			URLConnection urlConnection = url.openConnection();
			urlConnection.connect();

			HttpURLConnection httpConn = (HttpURLConnection) urlConnection;
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				File file = new File(lrcRootPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				BufferedReader bf = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(lrcPath),
								"utf-8")));

				char c[] = new char[256];
				int temp = -1;
				while ((temp = bf.read()) != -1) {
					bf.read(c);
					out.write(c);
				}

				bf.close();
				out.close();

				return true;
			}

			// System.out.println("getFile:"+str);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public String getLrcPath(String title, String artist) {
		return lrcRootPath + title + " - " + artist + ".lrc";
	}
}
