package com.marvelfanbox.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WallpaperFragment extends Fragment {

    private RecyclerView wallpapersGrid;
    private RecyclerView.LayoutManager layoutManager;
    private WallpapersAdapter wAdapter;
    private ArrayList<String> urls, allUrls;

    int lastW = 10;

    boolean isLoading = false;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        progressBar = getView().findViewById(R.id.wallpaperLoader);
        progressBar.setVisibility(View.GONE);

        wallpapersGrid = getView().findViewById(R.id.wallpapersGrid);

        layoutManager = new LinearLayoutManager(getActivity());
        wallpapersGrid.setLayoutManager(layoutManager);

        allUrls = new ArrayList<>();
        String arr[] = getResources().getStringArray(R.array.wallpapers);
        for (String a : arr) {
            allUrls.add(a);
        }
        urls = new ArrayList<>(allUrls.subList(1, lastW));
        wAdapter = new WallpapersAdapter(urls, getActivity());
        wallpapersGrid.setAdapter(wAdapter);
        initScrollListener();

    }

    private void initScrollListener() {
        wallpapersGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading && (lastW + 10) <= (allUrls.size() + 1)) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == urls.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        urls.add(null);
        wAdapter.notifyItemInserted(urls.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                urls.remove(urls.size() - 1);
                int scrollPosition = urls.size();
                wAdapter.notifyItemRemoved(scrollPosition);
//                int currentSize = scrollPosition;\

                if((lastW + 10) <= (allUrls.size() + 1)) {
                    wAdapter.updateDataSet(new ArrayList<String>(allUrls.subList(lastW, lastW + 10)));
                    lastW = lastW + 10;
                }
                isLoading = false;
            }
        }, 2000);


    }
}
