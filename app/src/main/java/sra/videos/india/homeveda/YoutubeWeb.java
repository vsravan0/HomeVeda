package sra.videos.india.homeveda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

@SuppressLint("SetJavaScriptEnabled")
public class YoutubeWeb extends Activity {
	WebView wv;

	FrameLayout mContentView;
	WebView mWebView;
	FrameLayout mCustomViewContainer;
	View mCustomView;
	private String TAG="YoutubeWeb";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.videowebview);

		mContentView = (FrameLayout) findViewById(R.id.main_content);
		mWebView = (WebView) findViewById(R.id.webView);
		mCustomViewContainer = (FrameLayout) findViewById(R.id.fullscreen_custom_content);

		wv = (WebView) findViewById(R.id.webView);
		wv.getSettings().setPluginState(PluginState.ON);
		wv.getSettings().setJavaScriptEnabled(true);
		 wv.setWebViewClient(new MyWebViewClient(this));

		//wv.setWebChromeClient(new WebChromeClient());
		//wv.setWebViewClient(new WebViewClient());
		String vid = getIntent().getStringExtra("video");
		String curl = "https://www.youtube.com/watch?v=";
		// https://www.youtube.com/watch?v=Ie9Z1WcLfp4
		String url = curl + vid;

		Log.w(" vid ", vid);
		Log.w(" curl ", curl);
		Log.w(" url ", url);
		wv.loadUrl(url);

	}




	public class MyWebViewClient extends WebViewClient {

		public Activity mActivity;

		public MyWebViewClient(Activity activity) {
			super();
			mActivity = activity;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.w(TAG, "shouldOverrideUrlLoading");
			return true;
		}

		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
			Log.w(TAG, "shouldOverrideUrlLoading 2");

			return true;
		}

		@Override
		public void onPageFinished(final WebView view, String url) {
			Log.w(" onPageFinished", "onPageFinished");

			String javascript = "javascript:"
					+ "var iframes = document.getElementsByTagName('iframe');"
					+ "for (var i = 0, l = iframes.length; i < l; i++) {"
					+ "   var iframe = iframes[i],"
					+ "   a = document.createElement('a');"
					+ "   a.setAttribute('href', iframe.src);"
					+ "   d = document.createElement('div');"
					+ "   d.style.width = iframe.offsetWidth + 'px';"
					+ "   d.style.height = iframe.offsetHeight + 'px';"
					+ "   d.style.top = iframe.offsetTop + 'px';"
					+ "   d.style.left = iframe.offsetLeft + 'px';"
					+ "   d.style.position = 'absolute';"
					+ "   d.style.opacity = '0';"
					+ "   d.style.filter = 'alpha(opacity=0)';"
					+ "   d.style.background = 'black';"
					+ "   a.appendChild(d);"
					+ "   iframe.offsetParent.appendChild(a);" + "}";
			view.loadUrl(javascript);

			super.onPageFinished(view, url);
		}
	}

}
