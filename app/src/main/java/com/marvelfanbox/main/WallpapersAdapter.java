package com.marvelfanbox.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class WallpapersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<String> urls;
    private Context context;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_ITEM = 1 ;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView wallpaper;


        public ItemViewHolder(View v) {
            super(v);
            wallpaper = v.findViewById(R.id.wallpaper);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return urls.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public WallpapersAdapter(List<String> urls, Context context) {
        this.context = context;
        this.urls = (ArrayList<String>) urls;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_grid_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_wallaper, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder h = (ItemViewHolder) holder;
            final String url = urls.get(position);
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions().override(800, 400)
                            .error(R.drawable.placeholder_movieimage)
                            .centerCrop()
                    )
                    .into(h.wallpaper);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }


    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void updateDataSet(List<String> newUrls) {
        Log.e("TEST112", "Inside Update Dataset");
        this.urls.addAll(newUrls);
        notifyDataSetChanged();
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
    }

}
