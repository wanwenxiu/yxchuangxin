package com.yxld.yxchuangxin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.CxwyJfWyRecord;
import com.yxld.yxchuangxin.entity.CxwyMallOrder;

import java.util.List;

/**
 * Created by yishangfei on 2016/10/18 0018.
 */

public class FeiYongListAdapter extends RecyclerView.Adapter<FeiYongListAdapter.ItemViewHolder> {
    private List<CxwyJfWyRecord> mlist;
    private LayoutInflater mInflater;
    private Context context;
    private int bianhao;
    private int curposition;

    public FeiYongListAdapter(Context context, List<CxwyJfWyRecord> list) {
        this.mlist = list;
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 使用RecyclerView，ViewHolder是可以复用的。这根使用ListView的VIewHolder复用是一样的
         * ViewHolder创建的个数好像是可见item的个数+3
         */
        ItemViewHolder holder = new ItemViewHolder(mInflater.inflate(
                R.layout.feiyong_list_item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        // 填充数据
        final CxwyJfWyRecord cm = mlist.get(position);
        holder.mDestail.setText(cm.getJfWyRecFwStatus());
        holder.mTime.setText(cm.getuString());
        holder.mRemarks.setText(cm.getJfWyRecRemark());
        holder.mJiaofei_name.setText("缴费人:"+cm.getJfWyRecJiaofeirenName());
        switch (cm.getJfWyRecJiaofeiType()){
            case 0:
                holder.mPay_status.setText("支付方式:现金");
                break;
            case 1:
                holder.mPay_status.setText("支付方式:支付宝");
                break;
            case 2:
                holder.mPay_status.setText("支付方式:微信");
                break;
            case 3:
                holder.mPay_status.setText("支付方式:银联");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mDestail;
        private TextView mPay_status;
        private TextView mTime;
        private TextView mRemarks;
        private TextView mJiaofei_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mDestail = (TextView) itemView.findViewById(R.id.destail);
            mPay_status = (TextView) itemView.findViewById(R.id.pay_status);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mRemarks= (TextView) itemView.findViewById(R.id.remarks);
            mJiaofei_name= (TextView) itemView.findViewById(R.id.jiaofei_name);
        }
    }
}
