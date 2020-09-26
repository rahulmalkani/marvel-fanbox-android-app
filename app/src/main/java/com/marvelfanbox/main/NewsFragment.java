package com.marvelfanbox.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private RecyclerView newsFeeds;
    private RecyclerView.LayoutManager layoutManager;
    private NewsAdapter nAdapter;
    JSONObject result = null;
    private ArrayList<NewsItemModel> newsItems;
    private ProgressBar newsProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsProgressBar = getView().findViewById(R.id.news_progressbar);

        newsFeeds = getView().findViewById(R.id.newsFeeds);
        layoutManager = new LinearLayoutManager(getActivity());
        newsFeeds.setLayoutManager(layoutManager);
        newsItems = new ArrayList<>();

        getNewsFeeds("http://www.mocky.io/v2/5d53072f2e0000510081dd54");

    }

    private void getNewsFeeds(String url) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.getContext());

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articles = response.getJSONArray("articles");
                    Log.e("CHECK123", articles.length() + "");


                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject article = articles.getJSONObject(i);
                        NewsItemModel model = new NewsItemModel();

                        String data = article.getString("title");
                        model.setTitle(data);

                        data = article.getString("content");
                        model.setContent(data);

                        data = article.getString("urlToImage");
                        model.setUrlToImage(data);

                        data = article.getString("url");
                        model.setUrlToSource(data);

                        data = article.getString("publishedAt");
                        model.setDateOfPublish(data);

                        JSONObject obj = article.getJSONObject("source");
                        model.setSource(obj.getString("name"));

                        newsItems.add(model);

                    }
                    nAdapter = new NewsAdapter(newsItems, getActivity());
                    newsFeeds.setAdapter(nAdapter);
                    newsProgressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("CHECK123", e.getMessage() + "");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}
