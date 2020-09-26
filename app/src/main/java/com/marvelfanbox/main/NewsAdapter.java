package com.marvelfanbox.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NewsItemModel> items;
    private Context context;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_ITEM = 1 ;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView newsThumb;
        private TextView newsHeading;
        private TextView newsDescription;
        private View layout;


        public ItemViewHolder(View v) {
            super(v);
            newsThumb = v.findViewById(R.id.newsThumb);
            newsHeading = v.findViewById(R.id.newsHeading);
            newsDescription = v.findViewById(R.id.news_description);
            layout = v;
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
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public NewsAdapter(ArrayList<NewsItemModel> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new NewsAdapter.ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_wallaper, parent, false);
            return new NewsAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NewsAdapter.ItemViewHolder) {
            NewsAdapter.ItemViewHolder h = (NewsAdapter.ItemViewHolder) holder;
            final NewsItemModel model = items.get(position);
            h.newsHeading.setText(model.getTitle());

            String content = model.getContent();
            String linkToSource = "<a href='" + model.getUrlToSource() + "'> Read More </a>";

            if (Build.VERSION.SDK_INT >= 24) {
                content = content + Html.fromHtml(linkToSource, Html.FROM_HTML_MODE_COMPACT);
            } else {
                content = content + Html.fromHtml(linkToSource);
            }
            h.newsDescription.setText(content);

//            Glide.with(context)
//                    .load(url)
//                    .override(800, 400)
//                    .error(R.drawable.placeholder_movieimage)
//                    .centerCrop()
//                    .into(h.newsThumb);
            h.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openArticleInWebView(model.getUrlToSource());
                }
            });
        } else if (holder instanceof NewsAdapter.LoadingViewHolder) {
            showLoadingView((NewsAdapter.LoadingViewHolder) holder, position);
        }

    }

    private void openArticleInWebView(String urlToSource) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("SOURCE_URL", urlToSource);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateDataSet(ArrayList<NewsItemModel> newItems) {
        Log.e("TEST112", "Inside Update Dataset");
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    private void showLoadingView(NewsAdapter.LoadingViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
    }

}
