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
                    String playlistID = mPlaylistItems.get(getAdapterPosition()).ID;
                    mOnPlaylistItemClickListener.onPlaylistItemClick(playlistID);
                    //get tracks in playlist
                }
            });
        }

        public void bind(SpotifyUtils.PlaylistItem playlistItem) {
            mPlaylistTextView.setText(playlistItem.name);
            Ion.with(mPlaylistImageView)
                    .placeholder(R.drawable.sample_7)
                    .centerCrop()
                    .load(playlistItem.imageURL);

        }
    }
}

    /*public PlaylistItemAdapter(Context context, ArrayList<SpotifyUtils.PlaylistItem> playlistItems){
        this.mContext = context;
        this.mPlaylistItems = playlistItems;
    }

    @Override
    public int getCount() {
        if (mPlaylistItems != null) {
            return mPlaylistItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SpotifyUtils.PlaylistItem playlistItem = mPlaylistItems.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.playlist_item, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageButton);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.name);


        // 4
        Ion.with(imageView)
                .placeholder(R.drawable.sample_7)
                .fitCenter()
                .load(playlistItem.imageURL);
        nameTextView.setText(playlistItem.name);

        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //open playlist to show tracks (clicking tracks should open spotify directly)
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlaylistActivity.class);
                intent.putExtra(SpotifyUtils.PlaylistItem.EXTRA_PLAYLIST_ITEM, playlistItem.ID);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }
} */
