package com.ss.android.mediaplayersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.ss.android.mediaplayersample.bean.Feed;
import com.ss.android.mediaplayersample.bean.FeedResponse;
import com.ss.android.mediaplayersample.newtork.IMiniDouyinService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

public class SimpleList extends AppCompatActivity implements listAdpter.ListItemClickListener{
    public RecyclerView mRv;
    private List<Feed> mFeeds = new ArrayList<>();
    OrientationUtils orientationUtils;
    private listAdpter lAdpter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.108.10.39:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMiniDouyinService.class).getFeedsAll().
                enqueue(new Callback<FeedResponse>() {
                    @Override public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                        mFeeds=response.body().getFeeds();
                        Log.d("bbbbb", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                    }

                    @Override public void onFailure(Call<FeedResponse> call, Throwable t) {
                        Log.d("ccccc", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    }
                });
        mRv = findViewById(R.id.simple_listview);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        for (int i=0;i<3;i++)
        {
            Feed f = new Feed();
            f.setVideoUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
            mFeeds.add(f);
        }
        Log.d("number",String.valueOf(mFeeds.size()));
        lAdpter = new listAdpter(mFeeds,this);
        mRv.setAdapter(lAdpter);

    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
    public static class MyViewHolder extends ViewHolder {

        private  final  StandardGSYVideoPlayer videoPlayer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPlayer= itemView.findViewById(R.id.video_player);
        }
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(SimpleList.this,DetailPlayerActivity.class);
        intent.putExtra("Vedio_URL",mFeeds.get(clickedItemIndex).getVideoUrl());
        startActivity(intent);
    }

}
