package com.marvelfanbox.main;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;


public class PhasesFragment extends Fragment {

    //static ShimmerFrameLayout container;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TabLayout tabLayout;
    MyAdapter mAdapter;
    List<String> phase1, phase2, phase3, phase4;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        container = (ShimmerFrameLayout) getActivity().findViewById(R.id.shimmer_view_container);
//        container.startShimmer();

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.phase3);
        mediaPlayer.setVolume(0.8f, 0.8f);
        mediaPlayer.setLooping(true);
        recyclerView = (RecyclerView) getView().findViewById(R.id.list_of_movies);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        phase1 = Arrays.asList(getResources().getStringArray(R.array.phase1));
        mAdapter = new MyAdapter(phase1, getActivity());
        recyclerView.setAdapter(mAdapter);

        tabLayout = getActivity().findViewById(R.id.tab_layout);
        setTabs();
        addTabsListner();


    }

    @Override
    public void onResume() {
        super.onResume();
//        container.stopShimmer();
//        container.setVisibility(View.GONE);
    }

    public void setTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Phase 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Phase 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Phase 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Phase 4"));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.toolbarColor));
    }
    private void addTabsListner() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        phase1 = Arrays.asList(getResources().getStringArray(R.array.phase1));
                        mAdapter.updateDataSet(phase1);
                        recyclerView.setAdapter(mAdapter);
                        stopMusic();
                        break;
                    case 1:
                        phase2 = Arrays.asList(getResources().getStringArray(R.array.phase2));
                        mAdapter.updateDataSet(phase2);
                        recyclerView.setAdapter(mAdapter);
                        stopMusic();
                        break;
                    case 2:
                        phase3 = Arrays.asList(getResources().getStringArray(R.array.phase3));
                        mAdapter.updateDataSet(phase3);
                        recyclerView.setAdapter(mAdapter);
                        playMusic();
                        break;
                    case 3:
                        phase4 = Arrays.asList(getResources().getStringArray(R.array.phase4));
                        mAdapter.updateDataSet(phase4);
                        recyclerView.setAdapter(mAdapter);
                        stopMusic();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void playMusic() {
        if(mediaPlayer.isPlaying())
            return;
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }
    private void stopMusic() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            mediaPlayer.pause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }
}
