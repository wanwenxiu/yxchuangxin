package com.yxld.yxchuangxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;

/**
 * Created by yishangfei on 2016/11/2 0002.
 */
public class Wuyeadapter1 extends BaseAdapter{
    private Context context;
    private int[] icon;
    private String[] name;


    public Wuyeadapter1(int[] icon, String[] name, Context context ) {
        this.context = context;
        this.icon=icon;
        this.name=name;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            LayoutInflater lif=LayoutInflater.from(context);
            view=lif.inflate(R.layout.item_gridview,null);
            holder.imageView= (ImageView) view.findViewById(R.id.imageView);
            holder.textView= (TextView) view.findViewById(R.id.textView2);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.textView.setText(name[position]);
        holder.imageView.setImageResource(icon[position]);
        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
