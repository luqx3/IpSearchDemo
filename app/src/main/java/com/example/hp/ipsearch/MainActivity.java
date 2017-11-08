package com.example.hp.ipsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.support.design.widget.Snackbar;


import com.example.hp.ipsearch.adapter.IPDataAdapter;
import com.example.hp.ipsearch.data.IPData;
import com.example.hp.ipsearch.data.IPDataResponse;
import com.example.hp.ipsearch.retrofit.IPRetrofit;
import com.example.hp.ipsearch.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private final String token="91512cf1f06256d18786aaf33642e415";
    private final String jsonp="jsonp";

    TextInputLayout textInputLayout;
    Button searchbtn,clearbtn;
    ListView listView;
    IPDataAdapter ipDataAdapter;
    List<IPData> list;
    String[] SearchIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    void initView(){
        textInputLayout=(TextInputLayout) findViewById(R.id.ip);
        searchbtn=(Button)findViewById(R.id.search);
        clearbtn=(Button)findViewById(R.id.clear);
        listView=(ListView)findViewById(R.id.listview);
        list= new ArrayList<IPData>();
        ipDataAdapter=new IPDataAdapter(this,list);
        listView.setAdapter(ipDataAdapter);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchIP=splitIPString(textInputLayout.getEditText().getText().toString());//textInputLayout.getEditText().getText().toString().split(";");
                for(String itemSearchIP:SearchIP){
                    if(!itemSearchIP.equals(""))searchData(itemSearchIP,jsonp,token);
                }
//                if(NetUtils.isConnected(MainActivity.this)){
//
//                }else{
//
//                }

            }
        });
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                ipDataAdapter.notifyDataSetChanged();
            }
        });
    }
    public void searchData( final String ip,String datatype,String token ){

        Call<IPDataResponse> call= IPRetrofit.getInstance(getApplicationContext()).getIPAPI().get(ip,datatype,token);
        call.enqueue(new Callback<IPDataResponse>() {
            @Override
            public void onResponse(Call<IPDataResponse> call, Response<IPDataResponse> response) {

                if(response.body().getRet().equals("ok")){
                    list.add(response.body().getIpData());
                    ipDataAdapter.notifyDataSetChanged();
                }else{
                    list.add(new IPData(ip,response.body().getMsg()));
                    ipDataAdapter.notifyDataSetChanged();
                    Log.d(TAG, response.body().getMsg()==null?"":response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<IPDataResponse> call, Throwable t) {
                if(!NetUtils.isConnected(MainActivity.this))
                    Snackbar.make(searchbtn, "IP:"+ip+"  缓存搜索失败", Snackbar.LENGTH_LONG).show();
            }
        });

    }
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



}
