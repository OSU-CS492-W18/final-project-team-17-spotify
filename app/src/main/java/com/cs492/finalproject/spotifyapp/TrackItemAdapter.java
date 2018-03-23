package com.cs492.finalproject.spotifyapp;
import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import com.koushikdutta.ion.Ion;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
/**
 * Created by Sophia on 3/20/2018.
 */

public class TrackItemAdapter extends RecyclerView.Adapter<TrackItemAdapter.TrackItemViewHolder> {

    private ArrayList<SpotifyUtils.TrackItem> mTrackItems;
    private OnTrackItemClickListener mOnTrackItemClickListener;
    private SQLiteDatabase mDB;

    public TrackItemAdapter(OnTrackItemClickListener onTrackItemClickListener, SQLiteDatabase db) {
        mOnTrackItemClickListener = onTrackItemClickListener;
        mDB = db;
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
                    if ( v.isActivated() ) {
                        mTrackImageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                        ContentValues row = new ContentValues();
                        row.put(FavoritesContract.FavoriteTracks.COLUMN_NAME, mTrackItems.get(getAdapterPosition()).name);
                        String[] arg = { mTrackItems.get(getAdapterPosition()).name };
                        mDB.delete(FavoritesContract.FavoriteTracks.TABLE_NAME, FavoritesContract.FavoriteTracks.COLUMN_NAME + " = ?", arg);
                        v.setActivated(false);
                    } else {
//                    String trackID = mTrackItems.get(getAdapterPosition()).URI;
                        mTrackImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        ContentValues row = new ContentValues();
                        row.put(FavoritesContract.FavoriteTracks.COLUMN_NAME, mTrackItems.get(getAdapterPosition()).name);
                        mDB.insert(FavoritesContract.FavoriteTracks.TABLE_NAME, null, row);
                        v.setActivated(true);
                    }
//                    mOnTrackItemClickListener.onTrackItemClick(trackID);
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
