package com.cs492.finalproject.spotifyapp;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
/**
 * Created by Sophia on 3/20/2018.
 */

public class TrackItemAdapter extends RecyclerView.Adapter<TrackItemAdapter.TrackItemViewHolder> {

    private ArrayList<SpotifyUtils.TrackItem> mTrackItems;
    private OnTrackItemClickListener mOnTrackItemClickListener;


    public TrackItemAdapter(OnTrackItemClickListener onTrackItemClickListener) {
        mOnTrackItemClickListener = onTrackItemClickListener;
    }

    public void updateTrackData(ArrayList<SpotifyUtils.TrackItem> trackItems) {
        mTrackItems = trackItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTrackItems != null) {
            return mTrackItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public TrackItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.track_item, parent, false);
        return new TrackItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackItemViewHolder holder, int position) {
        holder.bind(mTrackItems.get(position));
    }

    public interface OnTrackItemClickListener {
        void onTrackItemClick(String trackID);
    }

    class TrackItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrackTextView;
        private ImageView mTrackImageView;

        public TrackItemViewHolder(View itemView) {
            super(itemView);
            mTrackTextView = (TextView)itemView.findViewById(R.id.tv_track_name);
            mTrackImageView = (ImageView)itemView.findViewById(R.id.iv_track_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String trackID = mTrackItems.get(getAdapterPosition()).ID;
                    mTrackImageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                    mOnTrackItemClickListener.onTrackItemClick(trackID);
                    //get tracks in playlist
                }
            });
        }

        public void bind(SpotifyUtils.TrackItem trackItem) {
            mTrackTextView.setText(trackItem.name);
            mTrackImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }
    }
}
