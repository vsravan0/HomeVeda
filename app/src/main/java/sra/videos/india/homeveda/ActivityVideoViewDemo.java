package sra.videos.india.homeveda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

public class ActivityVideoViewDemo extends Activity implements
		OnPreparedListener, OnCompletionListener {

	private final static String START_LOADING_VIDEO = "start";
	private final static String STOP_LOADING_VIDEO = "stop";

	private MyHandler handler;
	private VideoView videoView;
	private ProgressDialog progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);

		super.setContentView(R.layout.activity_video);
		videoView = (VideoView) findViewById(R.id.YoutubeVideoView);
		videoView.setOnPreparedListener(this);
		videoView.setOnCompletionListener(this);

		progressBar = new ProgressDialog(this);
		progressBar.setMessage("Loading...");

		handler = new MyHandler();

		startPlay();
	}

	private void startPlay() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				Bundle b = new Bundle();
				b.putString(START_LOADING_VIDEO, "");
				msg.setData(b);
				handler.sendMessage(msg);

			}
		}).start();

	}

	private class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (bundle.containsKey(START_LOADING_VIDEO)) {
				playVideo();
			} else if (bundle.containsKey(STOP_LOADING_VIDEO)) {
				// progressBar.dismiss();
				disminProgress();
			}
		}
	}

	private void disminProgress() {
		new CountDownTimer(5000, 2500) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			
					if(progressBar!=null&&progressBar.isShowing())
				progressBar.dismiss();
			
			}
		}.start();
	}

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		if(progressBar!=null&&progressBar.isShowing())
			progressBar.cancel();
	}
	
	
	
	private void playVideo() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressBar = new ProgressDialog(ActivityVideoViewDemo.this);
				progressBar.setMessage("Loading...");
				progressBar.show();
				String url = getIntent().getStringExtra("videourl");
				videoView.setVideoURI(Uri.parse(url));
				videoView.setMediaController(new MediaController(
						ActivityVideoViewDemo.this));
				videoView.requestFocus();
				videoView.start();

			}
		});

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.i("ON COMPLETION", "" + mp.getCurrentPosition());
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.i("ON PREPARED", "" + mp.getCurrentPosition());

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				Bundle b = new Bundle();
				b.putString(STOP_LOADING_VIDEO, "");
				msg.setData(b);
				handler.sendMessage(msg);
			}
		});

	}

}