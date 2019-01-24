package com.ss.android.mediaplayersample.newtork;



import com.ss.android.mediaplayersample.bean.FeedResponse;
import com.ss.android.mediaplayersample.bean.PostVideoResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Xavier.S
 * @date 2019.01.17 20:38
 */
public interface IMiniDouyinService {
    @Multipart
    @POST("minidouyin/video")
    Call<PostVideoResponse> createVideo(@Query("student_id")String studengtId ,
                                        @Query("user_name")String userName , @Part MultipartBody.Part cover_image,
                                        @Part MultipartBody.Part video);

    @GET("minidouyin/feed/")
    Call<FeedResponse> getFeedsAll();
}
