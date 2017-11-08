package com.example.hp.ipsearch.data;

import java.util.List;

/**
 * Created by HP on 2017/11/7.
 */

public class IPDataResponse {
    private String ret;
    private String ip;
    private List<String> data;
    private IPData ipData;
    private String msg;


    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
        setIpData(new IPData(data.get(0),data.get(1),data.get(2),data.get(3),ip));
    }

    public IPData getIpData() {
        if(ipData==null&&data!=null)setIpData(new IPData(data.get(0),data.get(1),data.get(2),data.get(3),ip));
        else if(ipData==null&&data==null&&msg!=null)setIpData(new IPData(ip,msg));
        return ipData;
    }

    public void setIpData(IPData ipData) {
        this.ipData = ipData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
