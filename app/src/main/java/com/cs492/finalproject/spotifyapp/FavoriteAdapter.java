//package com.cs492.finalproject.spotifyapp;
//
///**
// * Created by Sophia on 3/23/2018.
// */
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.cs492.finalproject.spotifyapp.SpotifyUtils;
//
//import java.io.Serializable;
//import java.text.DateFormat;
//import java.util.ArrayList;
//
//public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteItemViewHolder> {
//    public static class FavoriteItem implements Serializable {
//        public static final String HI = "com.cs492.finalproject.spotifyapp.SpotifyUtils";
//        public String favorite;
//    }
//
//    private ArrayList<FavoriteItem> mFavoriteItems = new ArrayList<>();
//    private OnFavoriteItemClickListener mFavoriteItemClickListener;
//    private Context mContext;
//
//    public interface OnFavoriteItemClickListener {
//        void onFavoriteItemClick(FavoriteItem favoriteItem);
//    }
//
//    public FavoriteAdapter(Context context, OnFavoriteItemClickListener clickListener) {
//        mContext = context;
//        mFavoriteItemClickListener = clickListener;
//    }
//
//    public void updateFavoriteItem(FavoriteItem favoriteItem) {
//        mFavoriteItems.add(favoriteItem);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mFavoriteItems != null) {
//            return mFavoriteItems.size();
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public FavoriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.favorite_item, parent, false);
//        return new FavoriteItemViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(FavoriteItemViewHolder holder, int position) {
//        holder.bind(mFavoriteItems.get(position));
//    }
//
//    class FavoriteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView mFavoriteTV;
//
//        public FavoriteItemViewHolder(View itemView) {
//            super(itemView);
//            mFavoriteTV = itemView.findViewById(R.id.tv_favorite_item);
//            System.out.println(itemView.getRootView());
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(FavoriteItem favoriteItem) {
////            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//            String favorite = favoriteItem.favorite;
//            mFavoriteTV.setText(favorite);
//        }
//
//        @Override
//        public void onClick(View v) {
//            FavoriteItem favoriteItem = mFavoriteItems.get(getAdapterPosition());
//            mFavoriteItemClickListener.onFavoriteItemClick(favoriteItem);
//        }
//
//
//    }
//}
