package com.cs492.finalproject.spotifyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.koushikdutta.ion.Ion;

/**
 * Created by tasniakabir on 3/18/18.
 */

public class CategoryItemAdapter extends BaseAdapter {

    private ArrayList<SpotifyUtils.CategoryItem> mCategoryItems;
    private Context mContext; //stores context of main activity
    private String mToken;

    public CategoryItemAdapter(Context context, ArrayList<SpotifyUtils.CategoryItem> categoryItems, String token){
        this.mContext = context;
        this.mCategoryItems = categoryItems;
        this.mToken = token;
    }

    @Override
    public int getCount() {
        if (mCategoryItems != null) {
            return mCategoryItems.size();
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
        final SpotifyUtils.CategoryItem categoryItem = mCategoryItems.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.category_item, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageButton);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.name);


        Ion.with(imageView)
                .placeholder(R.drawable.sample_7)
                .fitCenter()
                .load(categoryItem.imageURL);
        nameTextView.setText(categoryItem.name);

        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlaylistActivity.class);
                intent.putExtra(SpotifyUtils.CategoryItem.EXTRA_CATEGORY_ITEM, categoryItem.ID);
                intent.putExtra("token", mToken);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }
}
