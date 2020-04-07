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
 * Created by Hakimi on 15/3/2020.
 */
public class AbyssViewAdapter extends RecyclerView.Adapter<AbyssViewAdapter.AbyssViewHolder> {
    private List<WallpaperResults> wallResults;

    public AbyssViewAdapter(List<WallpaperResults> results) {
        this.wallResults = results;
    }

    @NonNull
    @Override
    public AbyssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_content, parent, false);
        return new AbyssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbyssViewAdapter.AbyssViewHolder holder, int position) {
        final Context context = holder.imageView.getContext();
        final WallpaperResults results = wallResults.get(position);

        Picasso.get()
                .load(results.getUrl_thumb())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("abyss", results);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.wallResults.size();
    }

    public static class AbyssViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout parentLayout;

        public AbyssViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }
}
