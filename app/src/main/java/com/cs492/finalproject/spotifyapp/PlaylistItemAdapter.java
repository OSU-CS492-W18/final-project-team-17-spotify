package com.cs492.finalproject.spotifyapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by tasniakabir on 3/18/18.
 */

public class PlaylistItemAdapter extends RecyclerView.Adapter<PlaylistItemAdapter.PlaylistItemViewHolder> {

    private ArrayList<SpotifyUtils.PlaylistItem> mPlaylistItems;
    private OnPlaylistItemClickListener mOnPlaylistItemClickListener;


    public PlaylistItemAdapter(OnPlaylistItemClickListener onPlaylistItemClickListener) {
        mOnPlaylistItemClickListener = onPlaylistItemClickListener;
    }

    public void updatePlaylistData(ArrayList<SpotifyUtils.PlaylistItem> items, ArrayList<SpotifyUtils.PlaylistItem> playlistItems) {
        mPlaylistItems = playlistItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPlaylistItems != null) {
            return mPlaylistItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public PlaylistItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        return new PlaylistItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistItemViewHolder holder, int position) {
        holder.bind(mPlaylistItems.get(position));
    }

    public interface OnPlaylistItemClickListener {
        void onPlaylistItemClick(String playlistID);
    }

    class PlaylistItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mPlaylistTextView;
        private ImageView mPlaylistImageView;

        public PlaylistItemViewHolder(View itemView) {
            super(itemView);
            mPlaylistTextView = (TextView)itemView.findViewById(R.id.tv_playlist_name);
            mPlaylistImageView = (ImageView)itemView.findViewById(R.id.iv_playlist_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tracksURL = mPlaylistItems.get(getAdapterPosition()).tracksURL;
                    mOnPlaylistItemClickListener.onPlaylistItemClick(tracksURL);
                    //get tracks in playlist

                }
            });
        }

        public void bind(SpotifyUtils.PlaylistItem playlistItem) {
            mPlaylistTextView.setText(playlistItem.name);
            Ion.with(mPlaylistImageView)
                    .placeholder(R.drawable.sample_7)
                    .load(playlistItem.imageURL);
        }
    }
}