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
import java.util.List;

 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
 

import android.os.Environment;
import android.util.Log;

@Deprecated
public class BaiduLrcUtil {
	private static String TAG="BaiduLrcUtil";
	private static BaiduLrcUtil instance;
	public static final String lrcRootPath = Environment
			.getExternalStorageDirectory().toString()
			+ "/SweetMusicPlayer/Lyrics/";
	public static String baiduXMLPath = "http://box.zhangmen.baidu.com/x?op=12&count=1&title=";
	public static String baiduLrcPath = "http://box.zhangmen.baidu.com/bdlrc/";

 

	public static BaiduLrcUtil getInstance() {
		if (null == instance) {
			instance = new BaiduLrcUtil();
		}

		return instance;
	}

	public String getMusicXmlPath(String title, String artist) {
		System.out.println(baiduXMLPath + Encode(title) + "$$" + Encode(artist)
				+ "$$$$");
		return baiduXMLPath + Encode(title) + "$$" + Encode(artist) + "$$$$";
	}

	public String queryLrcID(String title, String artist) {
		String xmlPath = getMusicXmlPath(title, artist);
//		SAXReader reader = new SAXReader();
		String xmlContent = getXMLbyURL(xmlPath);

		if (xmlContent != null) {

			try {
//				Document document = reader.read(xmlPath);
				Document document = DocumentHelper.parseText(xmlContent);
				Element root = document.getRootElement();

				List urlChildList = root.elements("url");
				for (int i = 0; i < urlChildList.size(); i++) {
					Element urlElement = (Element) urlChildList.get(i);
					Element lrcidElement = urlElement.element("lrcid");
					System.out.println(lrcidElement.getData());

					if (!"0".equals(lrcidElement.getData())) {
						return lrcidElement.getData().toString();

					}
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, e.toString());
			}
		}
		return null;
	}

	public String getLrcUrlPath(String lrcid) {
		int id = Integer.parseInt(lrcid);
		System.out.println(baiduLrcPath + id / 100 + "/" + id + ".lrc");
		return baiduLrcPath + id / 100 + "/" + id + ".lrc";
	}

	// 歌手，歌曲名中的空格进行转码
	public String Encode(String str) {
	 
		 
			try {
				return URLEncoder.encode(str.trim(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 

		return str;
	}

	// 歌词文件网络地址，歌词文件本地缓冲地址
	public boolean wrtieContentFromUrl(String urlPath, String lrcPath) {
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
				

				BufferedReader bf=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"iso8859-1"));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
					    new FileOutputStream(lrcPath),"utf-8")));  
				
		 
				char c[]=new char[256];
				int temp=-1;
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

	public String getXMLbyURL(String url) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response;

		try {
			response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
