package com.example.hp.ipsearch.api;

import com.example.hp.ipsearch.data.IPDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by HP on 2017/11/7.
 */

public interface IPAPI {
    //http://api.ip138.com/query/?ip=8.8.8.8&datatype=jsonp&token=91512cf1f06256d18786aaf33642e415
    @Headers("Cache-Control:public ,max-age=2592000")
    @GET("query/")
    Call<IPDataResponse> get(@Query("ip") String ip, @Query("datatype") String datatype, @Query("token") String token);
}
