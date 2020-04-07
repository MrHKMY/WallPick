package com.mindscape.wallpicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.android.wallpicker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hakimi on 10/3/2020.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MainViewHolder> {
    private List<HitsResult> hitsResult;

    public MainViewAdapter(List<HitsResult> results) {
        this.hitsResult = results;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_content, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        final Context context = holder.imageView.getContext();
        final HitsResult result = hitsResult.get(position);

        Picasso.get()
                .load(result.getWebformat())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", result);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.hitsResult.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout parentLayout;

        public MainViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }
}
