### 由于之前没有注册APi store，近期停止注册；所以使用的不是百度API，而是ip.138.com查询网的api
#### 链接：http://user.ip138.com/ip/  

----


#### 缓存数据主要使用okhttp中的缓存数据实现，代码在IPRetrofit中
#### 主要代码
<pre>
        //配置okhttp中的Cache
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

</pre>
<pre>
         //拦截器
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
</pre>
<pre>
         @Headers("Cache-Control:public ,max-age=2592000")
         @GET("query/")
         Call<IPDataResponse> get(@Query("ip") String ip, @Query("datatype") String datatype, @Query("token") String token);
</pre>
#### 多个IP分割
<pre>
    //先将其他字符进行替换，然后使用splite分割
    String[] splitIPString(String IP){
            StringBuffer bf=new StringBuffer();
    
            for(int i=0;i<IP.length();i++){
                if(IP.charAt(i)=='.'||(IP.charAt(i)>='0'&&IP.charAt(i)<='9')){
                    bf.append(IP.charAt(i));
                }else{
                    bf.append(';');
                }
            }
            return bf.toString().split(";");
     }
</pre>


