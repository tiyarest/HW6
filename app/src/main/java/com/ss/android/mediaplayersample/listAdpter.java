package com.ss.android.mediaplayersample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.ss.android.mediaplayersample.bean.Feed;

import java.util.List;


public class listAdpter extends RecyclerView.Adapter<listAdpter.MyViewHolder>{


    private List<Feed> list;
    private final ListItemClickListener mOnClickListener;

    public listAdpter(List<Feed> list,ListItemClickListener listener) {
        this.list = list;
        Log.d("positionpositionposi",String.valueOf(list.size()));
        mOnClickListener = listener;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.simpel_list_item, parent, shouldAttachToParentImmediately);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        private final StandardGSYVideoPlayer videoPlayer;
        private final ImageView imageView ;
        private final Button button ;

        public MyViewHolder(View itemView) {
            super(itemView);
            videoPlayer=itemView.findViewById(R.id.video_player);
            imageView = new ImageView(itemView.getContext());
            button = itemView.findViewById(R.id.spcific);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if (mOnClickListener != null) {
                        mOnClickListener.onListItemClick(clickedPosition);
                    }
                }
            });
        }
        public  void bind(int position){
            Log.d("positionposition",String.valueOf(position));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            videoPlayer.setUp(list.get(position).getVideoUrl(), true, "测试视频");
            //增加封面
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.mipmap.xxx1);
            videoPlayer.setThumbImageView(imageView);
            //增加title
            videoPlayer.getTitleTextView().setVisibility(View.INVISIBLE);
            //设置返回键
            videoPlayer.getBackButton().setVisibility(View.INVISIBLE);
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            videoPlayer.getFullscreenButton().setVisibility(View.INVISIBLE);
            //设置旋转
            //是否可以滑动调整
            videoPlayer.setIsTouchWiget(true);
            //设置返回按键功能
            videoPlayer.startPlayLogic();
        }

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}