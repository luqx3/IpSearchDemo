package com.example.hp.ipsearch.retrofit;


import android.content.Context;
import com.example.hp.ipsearch.api.IPAPI;
import com.example.hp.ipsearch.util.NetUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by HP on 2017/11/7.
 */

public class IPRetrofit {
    private static IPRetrofit ipRetrofit;
    private static String BaseUrl = "http://api.ip138.com/";
    private static OkHttpClient httpClient;
    private static Context context;
    IPAPI ipapi;
    File httpCacheDirectory;

    public IPRetrofit(Context context){
        this(context,25);
    }
    public IPRetrofit(Context context,int timeout){
        this.context=context;
        httpCacheDirectory = new File(context.getCacheDir(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;//设置缓存文件大小为10M
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        httpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ipapi = retrofit.create(IPAPI.class);
    }
    public static IPRetrofit getInstance(Context context) {
        if (ipRetrofit == null) {
            ipRetrofit = new IPRetrofit(context);
        }
        return ipRetrofit;
    }
    public IPAPI getIPAPI(){
        return ipapi;
    }
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "max-age=" + 2592000)
                    .build();
            return response1;

//            Request request = chain.request();
//            if(!NetUtils.isConnected(context)){
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//               // Logger.t(TAG).w("no network");
//            }
//            Response originalResponse = chain.proceed(request);
//            if(NetUtils.isConnected(context)){
//                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                String cacheControl = request.cacheControl().toString();
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();
//            }else{
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
//                        .removeHeader("Pragma")
//                        .build();
//            }

        }

    };
}
