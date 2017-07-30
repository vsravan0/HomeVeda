package sra.videos.india.homeveda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sra.video.india.utils.Video;
import sra.videos.india.homeveda.R;

/**
 * Created by Girish on 7/29/2017.
 */

public class AdapterPlayList extends RecyclerView.Adapter<AdapterPlayList.ViewHolder> {
    private List<Video> mVideoPlaylist;
    private Picasso mPicaso;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterPlayList(Context ctx, List<Video> myDataset) {

        mVideoPlaylist = myDataset;
        mPicaso = Picasso.with(ctx);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder( view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // we need to use picasso  holder.mImage.setText(mDataset[position]);
        Video user = mVideoPlaylist.get(position);
        String title = user.getTitle();

        String number = user.getThumbUrl();
        // Display the guest name
        holder.mTitle.setText(title);
        mPicaso.load(user.getThumbUrl()).placeholder(R.drawable.loading).error(R.drawable.loading_thumbnail).into(holder.mImage);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mVideoPlaylist.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImage;
        public TextView mTitle;


        public ViewHolder(View itemView ) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.text);

        }
    }

}