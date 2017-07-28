package sra.videos.india.homeveda.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sra.video.india.utils.AppUtils;
import sra.video.india.utils.Constants;
import sra.video.india.utils.Video;
import sra.videos.india.homeveda.R;

public class ThumbNailAdapter extends BaseAdapter {

    private List<Video> mvideos;
    private static LayoutInflater inflater = null;
    private Context mCtx;
    private String isAnim = "";
    private Picasso mPicaso;

    public ThumbNailAdapter(Context ctx, List<Video> videos) {
        mvideos = videos;
        inflater = LayoutInflater.from(ctx);
        mCtx = ctx;
        isAnim = AppUtils.GetPref(mCtx, Constants.ISANIM);
        mPicaso = Picasso.with(ctx);


    }

    public int getCount() {


        //Log.w(" total Size is "+mvideos.size(),"=========");
        return mvideos.size();
    }

    public Object getItem(int position) {
        return mvideos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        protected TextView tv;
        protected ImageView iv;
    }

    public View getView(int position, View vi, ViewGroup parent) {
        ViewHolder holder = null;
        if (vi == null) {
            holder = new ViewHolder();
            vi = inflater.inflate(R.layout.item, null);
            holder.tv = (TextView) vi.findViewById(R.id.text);
            holder.iv = (ImageView) vi.findViewById(R.id.image);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        Video video = mvideos.get(position);

        holder.tv.setText(video.getTitle());

        double dd = getDuration(video.getduration());
        String text = "<font color=#cc0029>&nbsp;&nbsp;&nbsp;&nbsp[" + dd + "] </font>";
        holder.tv.append(Html.fromHtml(text));

        //imageLoader.DisplayImage(video.getThumbUrl(), holder.iv);


        mPicaso.load(video.getThumbUrl())

                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_thumbnail)

                .into(holder.iv);


        holder.tv.setTag(video.getChannelName());
        holder.iv.setTag(video.getViodeoid());

        holder.iv.setContentDescription(video.getUrl());
        isAnim = AppUtils.GetPref(mCtx, Constants.ISANIM);
        if (isAnim.equalsIgnoreCase("true")) {
            Animation anim = AnimationUtils.loadAnimation(mCtx, R.anim.scale);
            vi.setAnimation(anim);
        }


        return vi;
    }

    private double getDuration(String str) {
        try {


            double d = Double.parseDouble(str) / 60;

            double dd = Math.round(d * 100.0) / 100.0;


            return dd;
        } catch (Exception e) {
            return 0.0;
        }

    }
}