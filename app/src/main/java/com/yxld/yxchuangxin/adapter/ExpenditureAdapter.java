package com.yxld.yxchuangxin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.mine.ExpenditureFragment;
import com.yxld.yxchuangxin.entity.CxwyMallUserBalance;

import java.util.List;

/**
 * Created by yishangfei on 2016/9/7 0007.
 * 交易记录  支出 适配器
 */
public class ExpenditureAdapter extends RecyclerView.Adapter<ExpenditureAdapter.ItemViewHolder>{
    private List<CxwyMallUserBalance> mlist;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public ExpenditureAdapter(Context context, List<CxwyMallUserBalance> list) {
        this.mlist = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        // 填充数据
        CxwyMallUserBalance sb = mlist.get(i);
        itemViewHolder.mShop.setText(sb.getBalanceGoodsname());
        itemViewHolder.mMoney.setText("-"+sb.getBalanceJine()+"元");
        itemViewHolder.mTime.setText(sb.getBalanceTime());
        itemViewHolder.mOrder.setText("订单号:"+sb.getBalanceOrderbianhao());

        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!itemViewHolder.itemView.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = itemViewHolder.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                });
                itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = itemViewHolder.getPosition();
                        mOnItemClickListener.onItemLongClick(v, pos);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        /**
         * 使用RecyclerView，ViewHolder是可以复用的。这根使用ListView的VIewHolder复用是一样的
         * ViewHolder创建的个数好像是可见item的个数+3
         */
        Log.e("ListAdapter", "onCreateViewHolder");
        ItemViewHolder holder = new ItemViewHolder(mInflater.inflate(
                R.layout.expenditure_item_layout, viewGroup, false));
        return holder;
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 处理item的点击事件和长按事件
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mShop;
        private TextView mMoney;
        private TextView mTime;
        private TextView mOrder;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mShop = (TextView) itemView.findViewById(R.id.expenditure_shop);
            mMoney = (TextView) itemView.findViewById(R.id.expenditure_money);
            mTime = (TextView) itemView.findViewById(R.id.expenditure_time);
            mOrder = (TextView) itemView.findViewById(R.id.expenditure_order);
        }
    }

}
