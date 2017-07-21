package sra.videos.ui.widget;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import java.io.IOException;
import java.net.MalformedURLException;

import sra.videos.india.homeveda.R;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class UrlImageView extends LinearLayout {

	private Context mContext;
	private Drawable mDrawable;
	private ProgressBar mSpinner;
	private ImageView mImage;

	public UrlImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public UrlImageView(Context context) {
		super(context);
		init(context);
	}

	private void init(final Context context) {
		mContext = context;
		mImage = new ImageView(mContext);
		mImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mImage.setVisibility(View.GONE);

		mSpinner = new ProgressBar(mContext);
		mSpinner.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mSpinner.setIndeterminate(true);
		addView(mSpinner);
		addView(mImage);
	}

	public void setImageDrawable(final String imageUrl) {
		mDrawable = null;
		mSpinner.setVisibility(View.VISIBLE);
		mImage.setVisibility(View.GONE);
		new Thread() {
			public void run() {
				try {
					mDrawable = getDrawableFromUrl(imageUrl);
					imageLoadedHandler.sendEmptyMessage(RESULT_OK);
				} catch (MalformedURLException e) {
					imageLoadedHandler.sendEmptyMessage(RESULT_CANCELED);
				} catch (IOException e) {
					imageLoadedHandler.sendEmptyMessage(RESULT_CANCELED);
				}
			};
		}.start();
	}

	private final Handler imageLoadedHandler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case RESULT_OK:
				mImage.setImageDrawable(mDrawable);
				mImage.setVisibility(View.VISIBLE);
				mSpinner.setVisibility(View.GONE);
				break;
			case RESULT_CANCELED:

			default:

				mImage.setImageResource(R.drawable.noimage);
				mImage.setVisibility(View.VISIBLE);
				mSpinner.setVisibility(View.GONE);

				break;
			}
			return true;
		}
	});

	private static Drawable getDrawableFromUrl(final String url)
			throws IOException, MalformedURLException {
		return Drawable.createFromStream(
				((java.io.InputStream) new java.net.URL(url).getContent()),
				"name");
	}

}
