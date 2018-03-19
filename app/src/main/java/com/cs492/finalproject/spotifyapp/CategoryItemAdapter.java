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

    public CategoryItemAdapter(Context context, ArrayList<SpotifyUtils.CategoryItem> categoryItems){
        this.mContext = context;
        this.mCategoryItems = categoryItems;
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


        // 4
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
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }



    /*
    private ArrayList<SpotifyUtils.CategoryItem> mCategoryItems;
    private OnCategoryItemClickListener onCategoryItemClickListener;
    private Context mContext; //stores context of main activity

    public interface OnCategoryItemClickListener {
        void onCategoryItemClick(SpotifyUtils.CategoryItem categoryItem);
    }

    public CategoryItemAdapter(Context context, OnCategoryItemClickListener clickListener) {
        mContext = context; //current state of app
        onCategoryItemClickListener = clickListener;
    }

    public void updateForecastItems(ArrayList<SpotifyUtils.CategoryItem> categoryItems) {
        mCategoryItems = categoryItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCategoryItems != null) {
            return mCategoryItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, int position) {
        holder.bind(mCategoryItems.get(position));
    }

    class CategoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mForecastDateTV;
        private TextView mForecastTempDescriptionTV;
        private final DateFormat mDateFormatter = DateFormat.getDateTimeInstance();

        public CategoryItemViewHolder(View itemView) {
            super(itemView);
            mForecastDateTV = itemView.findViewById(R.id.tv_forecast_date);
            mForecastTempDescriptionTV = itemView.findViewById(R.id.tv_forecast_temp_description);
            itemView.setOnClickListener(this);
        }


        public void bind(SpotifyUtils.CategoryItem categoryItem) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String temperatureUnitsValue = sharedPreferences.getString(
                    mContext.getString(R.string.pref_units_key),
                    mContext.getString(R.string.pref_units_default_value)
            );
            String temperatureUnitsAbbr = SpotifyUtils.getTemperatureUnitsAbbr(mContext, temperatureUnitsValue);

            String dateString = mDateFormatter.format(forecastItem.dateTime);
            String detailString = forecastItem.temperature + temperatureUnitsAbbr + " - " + forecastItem.description;
            mForecastDateTV.setText(dateString);
            mForecastTempDescriptionTV.setText(detailString);

            /*
            String dateString = dateFormatter.format(forecastItem.dateTime);
            String detailString = forecastItem.temperature +
                    WeatherPreferences.getDefaultTemperatureUnitsAbbr() +
                    " - " + forecastItem.description;
            mForecastDateTV.setText(dateString);
            mForecastTempDescriptionTV.setText(detailString);

        }

        @Override
        public void onClick(View v) {
            SpotifyUtils.CategoryItem categoryItem = mCategoryItems.get(getAdapterPosition());
            onCategoryItemClickListener.onCategoryItemClick(categoryItem);
        }
    }
    */
}
