package sra.video.india.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class AppUtils {

	public static boolean isNetAvaliable(Context ctx) {

		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}



	public static void log(String tag, String msg) {
		Log.w(tag + ":" + Calendar.getInstance().get(Calendar.SECOND) + ":"
				+ Calendar.getInstance().get(Calendar.MILLISECOND), msg);
	}



	public static String converToString(InputStream isr) {

		try {

			return IOUtils.toString(isr);
		} catch (IOException e) {

			Log.w(" STREAM CONVERT ERROR ", e.toString());
			return null;
		}

	}

	public static String convertToString(InputStream inputStream)
			throws IOException {
		if (inputStream != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(
						inputStream, "UTF-8"), 1024);
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				inputStream.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	/*
	https://www.googleapis.com/youtube/v3/search?key=AIzaSyDkpWLe_b2u61zC4j4CYzlqVYJvq6XDCuQ&channelId=
	UC74TAQxOYvQDPyGt1klqTqw&part=snippet,id&order=date&maxResults=20&position=10


/

https://www.googleapis.com/youtube/v3/search?key=AIzaSyDkpWLe_b2u61zC4j4CYzlqVYJvq6XDCuQ&channelId
=UC74TAQxOYvQDPyGt1klqTqw&part=snippet,id&order=date&maxResults=50&pageToken=CFoQAA


	 HttpGet("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=25&playlistId="
      + string3 + string2 + "&fields=items(snippet/title,snippet/thumbnails,snippet/resourceId),nextPageToken&key="
       + Constants.API_KEY)).getEntity().getContent(), "UTF-8"));


https://gdata.youtube.com/feeds/api/videos?author=homeveda&v=2&alt=jsonc&max-results=50&start-index=1
	 */



	public static InputStream getStream(String channelId, String pageToken) {
		try {
			String surl = getUrl(channelId,pageToken);
			URL url = new URL(surl);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			urlConnection.connect();
			return urlConnection.getInputStream();
		} catch (Exception e) {
			Log.w(" ERROR :", e.toString());
			return null;
		}
	}
private static String TAG="SraAppUtils";
	private static String getUrl(String channelId, String pageToken){

		String token="";
		if(pageToken.length()>0){
			token="&pageToken="+pageToken;
		}
		String url=Constants.APP_URL+channelId+"&part=snippet,id&order=date&maxResults=50"+token;
		Log.v(TAG," url:"+url);
		return  url;


		/*
		https://www.googleapis.com/youtube/v3/search?key=AIzaSyDkpWLe_b2u61zC4j4CYzlqVYJvq6XDCuQ&channelId
=UC74TAQxOYvQDPyGt1klqTqw&part=snippet,id&order=date&maxResults=50&pageToken=CFoQAA

		 */


	}

	public static List<Video> getVideos(JSONArray jsonArray, Context ctx) {

		List<Video> videos = new ArrayList<Video>();
		String title = "", url = "", rtspUrl = "", videoid = "", duration = "";
		JSONObject jsonObject = null;
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				try {
					title = jsonObject.getString("title");
					url = jsonObject.getJSONObject("player")
							.getString("mobile");
					rtspUrl = jsonObject.getJSONObject("content")
							.getString("6");
					videoid = jsonObject.getString("id");
					duration = jsonObject.getString("duration");

				} catch (JSONException ignore) {
					url = jsonObject.getJSONObject("player").getString(
							"default");
					rtspUrl = url;

				}
				String thumbUrl = jsonObject.getJSONObject("thumbnail")	.getString("sqDefault");
				
				videos.add(new Video(title, url, thumbUrl, rtspUrl, videoid,duration));

			}
		} catch (JSONException e) {
			Log.e("Feck", e.toString());
		} catch (NullPointerException e) {
			Log.e("NullPointerException", e.toString());
		}
		return videos;
	}

	public static void SetCount(Context ctx, int count) {
		SharedPreferences pref = ctx.getSharedPreferences("sra.videos.india.homeveda", Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putInt("total", count);
		edit.commit();
	}

	public static int GetCount(Context ctx) {
		SharedPreferences pref = ctx.getSharedPreferences("sra.videos.india.homeveda", Context.MODE_PRIVATE);
		return pref.getInt("total", 0);

	}
	
	public static void  showToast(Context ctx, String msg){
		
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
		
	}
	
	public static void SetPref(Context ctx, String key) {
		SharedPreferences pref = ctx.getSharedPreferences("sra.videos.india.homeveda", Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString(Constants.ISANIM,key);
		

		
		edit.commit();
	}
	
	public static void clearCache(Context ctx){
		SharedPreferences pref = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.clear();
		edit.commit();
	}

	public static String GetPref(Context ctx, String key) {
		SharedPreferences pref = ctx.getSharedPreferences("sra.videos.india.homeveda", Context.MODE_PRIVATE);
		 
		return pref.getString(key,"0");

	}

}
