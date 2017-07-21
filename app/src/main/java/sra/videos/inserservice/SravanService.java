package sra.videos.inserservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sra.video.india.utils.AppUtils;
import sra.video.india.utils.Video;
import sra.video.sradb.APPDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sra.tube.Sravan;
import com.sra.tube.Tube;

public class SravanService {

	
	private Context mCtx;
	private APPDB  mDb;
	/*
	 * CREATE TABLE "sravana" ("" TEXT unique,"" TEXT,"" TEXT unique ,
	 * "" TEXT unique ,"" TEXT,"" TEXT,"" integer DEFAULT (null) ,
	 * "sra2" TEXT,"sra3" TEXT)
	 */
	private final String COL_ONE="videoid";
	private final String COL_TWO="title";
	private final String COL_THREE="url";
	private final String COL_FOUR="rtspurl";
	private final String COL_FIVE="thumb";
	private final String COL_SIX="duration";
	private final String COL_SEVEN="isfav";
	private final String TAB_NAME="sravan";
	private String TAG="SravanService";
	private  int totalResults = 0;
	private String nextPageToken;
	
	
	public SravanService(Context ctx){
		mCtx=ctx;
	}

	public int getTotalResults(){
		Log.v(TAG," getTotalResults :"+totalResults);
		return  totalResults;
	}

	public String getNextPageToken(){
		Log.v(TAG," nextPageToken :"+nextPageToken);
		return  nextPageToken;
	}

    public void insertService(Tube tube){

        mDb=new APPDB(mCtx);
        String title = "",url="",rtspUrl= "",videoid= "",duration= "";


        int len = tube.getItems().length;
        for(int i=0;i<len;i++){


            Sravan s = new Sravan(tube,i);
			if(i==0){
				totalResults = s.getTotalResults();
				nextPageToken = s.getNextPageToken();
			}
            ContentValues cv=new ContentValues();
            cv.put(COL_ONE,s.getVideoId());
            cv.put(COL_TWO,s.getTitle());
            cv.put(COL_THREE,s.getUrl());
            cv.put(COL_FOUR,s.getChannelTitle());
            cv.put(COL_FIVE,s.getUrl());
            cv.put(COL_SIX,s.getNextPageToken());
            cv.put(COL_SEVEN,"0");
            try{
                mDb.inSert(TAB_NAME, COL_ONE,cv);
            }
            catch (Exception e) {
                Log.w(TAG," first EXP insertService ");
                int cc=AppUtils.GetCount(mCtx);
                cc--;
                AppUtils.SetCount(mCtx,cc);
            }
            cv.clear();

        }
        closeService();
    }
	

	
	
	public List<Video>  getVideos(){
		mDb=new APPDB(mCtx);
		Cursor c=mDb.getResult(" select "+COL_ONE+","+COL_TWO+","+COL_THREE+","+COL_FOUR+","+COL_FIVE+","+COL_SIX+" from "+TAB_NAME+" order by rowid");
		List<Video> videos = new ArrayList<Video>();
		for(int i=0;i<c.getCount();i++){
			c.moveToPosition(i);
			videos.add(new Video(c.getString(1),c.getString(2), c.getString(4), c.getString(3), c.getString(0),c.getString(5)));
		}
		c.close();
		mDb.close();
		APPDB.CloseDataBase();
		return videos;
	}
	

	public List<Video>  getFavVideos(){
		mDb=new APPDB(mCtx);
		Cursor c=mDb.getResult(" select "+COL_ONE+","+COL_TWO+","+COL_THREE+","+COL_FOUR+","+COL_FIVE+","+COL_SIX+" from "+TAB_NAME+" where isfav=1 order by rowid ");
		List<Video> videos = new ArrayList<Video>();
		for(int i=0;i<c.getCount();i++){
			c.moveToPosition(i);
			videos.add(new Video(c.getString(1),c.getString(2), c.getString(4), c.getString(3), c.getString(0),c.getString(5)));
		}
		c.close();
		mDb.close();
		APPDB.CloseDataBase();
		return videos;
	}
	
	public int getFavCount(){
		APPDB db=new APPDB(mCtx);
		int count=db.getCount(TAB_NAME+" where isFav=1 order by sra2");
		APPDB.CloseDataBase();
		return count;
	}
	
	
	public int getCount(){
		APPDB db=new APPDB(mCtx);
		int count=db.getCount(TAB_NAME);
		APPDB.CloseDataBase();
		return count;
	}
	
	
	
	public int addToFav(String videoid, int v){
		
		ContentValues cv=new ContentValues();
		cv.put(COL_SEVEN,v);
		cv.put("sra2", System.currentTimeMillis());

		
		
		APPDB db=new APPDB(mCtx);
		int count=db.update(TAB_NAME, cv,  COL_ONE+" = '"+videoid+"'");
		APPDB.CloseDataBase();
		return  count;
	}
	
	public int deleteVideo(){
		
		APPDB db=new APPDB(mCtx);
		int count=db.delete(TAB_NAME,null,null);
		APPDB.CloseDataBase();
		return  count;
		
	}
	
	
	private  void closeService(){
		mDb.close();
		APPDB.CloseDataBase();
		
	}

	/*'
	while compiling: create TABLE IF NOT EXISTS sravan ( videoid text ,title text ,
	 url text ,rtspurl  text , thumb text ,duration text ,isfav integer  , sra2 text ,sra3 text

	 */
	
	
}
