package com.example.hp.ipsearch.data;

import java.util.List;

/**
 * Created by HP on 2017/11/7.
 */

public class IPData {
    private String Country="";
    private String Provincial="";
    private String City="";
    private String Operator="";
    private String Ip="";
    private String Msg="";

    public IPData(String country,String provincial,String city, String operator,String ip){
        if(country!=null)Country=country;
        if(provincial!=null)Provincial=provincial;
        if(city!=null)City=city;
        if(operator!=null)Operator=operator;
        if(Ip!=null)Ip=ip;
    }
    public IPData(List<String> data){
        Country=data.get(0);
        Provincial=data.get(1);
        City=data.get(3);
        Operator=data.get(4);
    }
    public IPData(String ip,String msg){
        Ip=ip;
        Msg=msg;
    }


    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvincial() {
        return Provincial;
    }

    public void setProvincial(String provincial) {
        Provincial = provincial;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        this.Ip = ip;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
