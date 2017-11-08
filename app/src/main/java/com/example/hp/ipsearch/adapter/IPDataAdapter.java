package com.example.hp.ipsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hp.ipsearch.R;
import com.example.hp.ipsearch.data.IPData;

import java.util.List;


/**
 * Created by HP on 2017/11/7.
 */

public class IPDataAdapter extends BaseAdapter {
    private Context context;
    private List<IPData> listItem;

    public IPDataAdapter(Context context,List<IPData> listItem){
        this.context=context;
        this.listItem=listItem;
    }

    @Override
    public int getCount() {
        if(listItem==null){
            return 0;
        }
        return listItem.size();
    }

    @Override
    public IPData getItem(int i) {
        if(listItem==null){
            return null;
        }
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            //通过inflate的方法加载布局，
            view= LayoutInflater.from(context).inflate(R.layout.item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.Ip=(TextView)view.findViewById(R.id.ip);
            viewHolder.Country=(TextView) view.findViewById(R.id.Country);
            viewHolder.Provincial=(TextView) view.findViewById(R.id.Provincial);
            viewHolder.City=(TextView) view.findViewById(R.id.City);
            viewHolder.Operator=(TextView) view.findViewById(R.id.Operator);
            viewHolder.Msg=(TextView) view.findViewById(R.id.msg);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.Ip.setText("IP地址："+listItem.get(i).getIp());
        viewHolder.Country.setText(listItem.get(i).getCountry());
        viewHolder.Provincial.setText(listItem.get(i).getProvincial());
        viewHolder.City.setText(listItem.get(i).getCity());
        viewHolder.Operator.setText(listItem.get(i).getOperator().equals("")?"":"("+listItem.get(i).getOperator()+")");
        if(!listItem.get(i).getMsg().equals("")){
            viewHolder.Msg.setText("错误描述："+listItem.get(i).getMsg());
            viewHolder.Msg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.Msg.setVisibility(View.INVISIBLE);
        }
        return view;
    }
    static class ViewHolder {
        TextView Ip;
        TextView Country;
        TextView Provincial;
        TextView City;
        TextView Operator;
        TextView Msg;
    }
}
