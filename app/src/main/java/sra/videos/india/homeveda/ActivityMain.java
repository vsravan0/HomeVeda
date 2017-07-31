package sra.videos.india.homeveda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.gson.Gson;
import com.sra.tube.Tube;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import sra.video.india.utils.AppUtils;
import sra.video.india.utils.Constants;
import sra.video.india.utils.Library;
import sra.video.india.utils.Video;
import sra.video.sradb.APPDB;
import sra.videos.india.homeveda.adapter.ThumbNailAdapter;
import sra.videos.inserservice.SravanService;

/**
 * Created by sravan on 16/07/17.
 */

public class ActivityMain extends AppCompatActivity implements AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {
    private String TAG="ActivityMain";
    private ProgressBar mPbar;
    private ListView mLv;
    private SravanService mService;
    private Context mCtx;
    private int totalitem = 0;
    private int visbCount=0;
    private String nextPageToken;
    private String channelId="";
    private boolean isNet = false,isAdded= false;
    private AppCompatTextView mTvTitle;

    private boolean isPlayList = false;

    private Intent intent = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent= getIntent();
        channelId=intent.getStringExtra(Constants.APP_NAME);
        Toast.makeText(getApplicationContext()," Channel ID "+channelId,Toast.LENGTH_LONG).show();


        mPbar =(ProgressBar)findViewById(R.id.id_pbar_loading);
        mLv =(ListView)findViewById(R.id.lvthumbnail);
        mCtx= getApplicationContext();
        mTvTitle =(AppCompatTextView)findViewById(R.id.id_tv_channel_title);

        mService = new SravanService(mCtx);
        mLv.setOnScrollListener(this);
        mLv.setOnItemClickListener(this);
        mLv.setOnItemLongClickListener(this);
        if(!AppUtils.isNetAvaliable(mCtx)) {
            noInternet();
        }else{
            waitAndStart("");

        }
//girish12ramesh@gmail.com
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        log("onScrollStateChanged scrollState:"+scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {


        visbCount=firstVisibleItem;

        if (totalItemCount != 0 && totalItemCount < totalitem
                && (firstVisibleItem + visibleItemCount == totalItemCount)) {

            isNet = AppUtils.isNetAvaliable(mCtx);
            Log.v(TAG," isNet :"+isNet+" isAdded:"+isAdded);
            if ( isNet && !isAdded) {
                waitAndStart(nextPageToken);
                sPos = totalItemCount;
                isAdded = true;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        log("onItemClick position:"+position);
        sPos = position;


        Video vv=(Video)parent.getItemAtPosition(position);

		/*TextView tv = (TextView) v.findViewById(R.id.text);
		ImageView iv = (ImageView) v.findViewById(R.id.image);
		String videoId = iv.getTag().toString();
		*/


        String videoId = vv.getViodeoid();


        sPos = position;

        try {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent(this,
                    Constants.DEVELOPER_KEY, videoId);

            startActivityForResult(intent, 121);

        } catch (Exception e) {
           /* Intent intent = new Intent(ActivityMain.this,
                    ActivityVideoViewDemo.class);
            */
            Intent intent = new Intent(ActivityMain.this,
                    YoutubeWeb.class);
            intent.putExtra("video",videoId);

            intent.putExtra("videourl",vv.getTitle());
            startActivityForResult(intent, 121);

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        log("onItemLongClick position:"+position);
        return false;
    }

    private void log(String msg){
        Log.v(TAG,msg);
    }

    private void loadFromDb() {
        try {
            List<Video> videos = mService.getVideos();
            Library lib = new Library(Constants.AUR_USERNAME, videos);
            Bundle data = new Bundle();
            data.putSerializable(Constants.LIBRARY, lib);
            Message msg = Message.obtain();
            msg.setData(data);
            responseHandler.sendMessage(msg);
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }
    }

    private Handler responseHandler= new Handler() {

        public void handleMessage(Message msg) {
            mPbar.setVisibility(ProgressBar.GONE);

            if (msg.what == -1) {

            } else
                populateListWithVideos(msg);

        };
    };
    private List<Video> mList;
    public static int sPos = 0;

    private void populateListWithVideos(Message msg) {
        Library lib = (Library) msg.getData().get(Constants.LIBRARY);
        int lsize = 0;
        if (mList == null) {
            mList = lib.getVideos();
            lsize = 0;
        } else {
            lsize = mList.size();
            mList = (lib.getVideos());
        }

        if(mList!=null&&mList.size()>0){
            String channelTitle = mList.get(0).getChannelName();
            mTvTitle.setText(channelTitle);
        }
        ThumbNailAdapter adapter = new ThumbNailAdapter(mCtx, mList);
        mLv.setAdapter(adapter);
        mLv.setSelection(sPos);
    }



    private void noInternet() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ActivityMain.this);
        alertDialog.setTitle(Constants.NET_NOT_TITLE);
        alertDialog
                .setMessage(Constants.NET_NOT_MESSAGE);
        alertDialog.setPositiveButton(Constants.CLOSE,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(mService.getCount()>0){
                            dialog.dismiss();
                        }else{
                            finish();
                        }

                    }
                });
        alertDialog.show();
    }

    private void waitAndStart(final String  pageToken) {

     mPbar.setVisibility(ProgressBar.VISIBLE);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                loadVideos(pageToken); // Load in Back ground thread
            }
        };
        new Thread(r).start();
    }

    private void loadVideos( String pageToken) {
        Log.v(TAG," loadVideos start ");

        try {

            if(!AppUtils.isNetAvaliable(mCtx)){


                loadFromDb();
                Log.v(TAG," Net not avaliable");

                return;

            }

            Log.v(TAG," loadVideos:"+pageToken);
            InputStream inputStream = AppUtils.getStream(channelId,pageToken, isPlayList); // Net Work operation
            Reader reader = new InputStreamReader(inputStream); // parsing
            Gson gson = new Gson();
            Tube response = gson.fromJson(reader, Tube.class); //parsed

            mService.insertService(response,null,false); // Storing data into Db to Cache
            totalitem = mService.getTotalResults();
            nextPageToken = mService.getNextPageToken();
            List<Video> videos = mService.getVideos();
            Library lib = new Library(channelId, videos);
            Bundle data = new Bundle();
            data.putSerializable(Constants.LIBRARY, lib);
            Message msg = Message.obtain();
            msg.setData(data);
            responseHandler.sendMessage(msg);
        } catch (Exception e) {
            Log.e(TAG," loadVideos :"+ e.toString());
            if(responseHandler!=null)
                responseHandler.sendEmptyMessage(-1);

        }
    }

}