package com.mindscape.wallpicker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Hakimi on 30/4/2020.
 */
public class FavouriteViewAdapter extends RecyclerView.Adapter<FavouriteViewAdapter.FavouriteViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public FavouriteViewAdapter (Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favourite_item, parent, false);
        return  new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        if (! mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_NAME));

        final Context context = holder.imageView.getContext();

        Picasso.get()
                .load(name)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavouriteDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("theLink", name);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public FavouriteViewHolder (View view) {
            super(view);
            imageView = view.findViewById(R.id.favouriteImageView);

        }
    }
}
