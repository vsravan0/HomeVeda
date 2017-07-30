package sra.videos.india.homeveda.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sra.tube.EntityPlayList;
import com.sra.tube.Tube;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import sra.video.india.utils.AppUtils;
import sra.video.india.utils.Constants;
import sra.video.india.utils.Library;
import sra.video.india.utils.Video;
import sra.videos.india.homeveda.ActivityMain;
import sra.videos.india.homeveda.R;
import sra.videos.india.homeveda.adapter.AdapterPlayList;
import sra.videos.inserservice.SravanService;

public class ActivityPlayLists extends AppCompatActivity {



    private RecyclerView mRvPlayList;
    private  AdapterPlayList mAdapterPlayList;
    private Context mCtx;
    private SravanService mService;
    private List<Video> mList;
    private ProgressBar mPbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_lists);
        mRvPlayList =(RecyclerView)findViewById(R.id.id_rv_playlists);
        mPbar =(ProgressBar)findViewById(R.id.id_progress_playlist);
        mCtx= getApplicationContext();



        mService = new SravanService(mCtx);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvPlayList.setLayoutManager(manager);
        if(!AppUtils.isNetAvaliable(mCtx)) {
            noInternet();
        }else{
            waitAndStart("");

        }



    }



    private void noInternet() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ActivityPlayLists.this);
        alertDialog.setTitle(Constants.NET_NOT_TITLE);
        alertDialog
                .setMessage(Constants.NET_NOT_MESSAGE);
        alertDialog.setPositiveButton(Constants.CLOSE,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(mService.getPlayListCount()>0){
                            dialog.dismiss();
                        }else{
                            finish();
                        }

                    }
                });
        alertDialog.show();
    }


private final String TAG="ActivityPlayList";

    private void loadFromDb() {
        try {
            List<Video> videos = mService.getPlayLists();
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
            if (msg.what == -1) {

            } else
                populateListWithVideos(msg);

        };
    };

    private void populateListWithVideos(Message msg) {
        mPbar.setVisibility(ProgressBar.GONE);
        Library lib = (Library) msg.getData().get(Constants.LIBRARY);
        int lsize = 0;
        if (mList == null) {
            mList = lib.getVideos();
            lsize = 0;
        } else {
            lsize = mList.size();
            mList = (lib.getVideos());
        }
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvPlayList.setHasFixedSize(true);
        if(mList!=null&&mList.size()>0) {
            mAdapterPlayList = new AdapterPlayList(mCtx, mList);
            mRvPlayList.setAdapter(mAdapterPlayList);
        }
        Log.v(TAG," mList :"+mList +" size :"+mList.size());
    }




    public void waitAndStart(final String  pageToken) {

        //  mPbar.setVisibility(ProgressBar.VISIBLE);
        mPbar.setVisibility(ProgressBar.VISIBLE);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                loadVideos(pageToken);
            }
        };
        Thread th = new Thread(r);
        th.start();
    }

    private void loadVideos( String pageToken)
    {

        try {
            Log.v(TAG," Load Play List :"+pageToken);


            InputStream inputStream = AppUtils.getStream(Constants.USER_CHANNEL_ID_VEDA,pageToken,true);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(inputStream);
            EntityPlayList response = gson.fromJson(reader, EntityPlayList.class);
            SravanService ser = new SravanService(mCtx);
            ser.insertService(null,response,true);
            List<Video> videos = ser.getPlayLists();
            Library lib = new Library(Constants.USER_CHANNEL_ID_VEDA, videos);
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
