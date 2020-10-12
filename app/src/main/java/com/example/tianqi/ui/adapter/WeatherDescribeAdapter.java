package com.example.tianqi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_ad.utils.SizeUtils;
import com.example.tianqi.model.bean.DescribeBean;
import com.tamsiree.rxkit.RxDeviceTool;
import com.tiantian.tianqi.R;


import java.util.ArrayList;
import java.util.List;

public class WeatherDescribeAdapter extends RecyclerView.Adapter<WeatherDescribeAdapter.MyHolder>  {
    private   List<DescribeBean.Des> mDescribeBeanList=new ArrayList<>();
    private boolean isHaveIcon;
   public WeatherDescribeAdapter(boolean isHaveIcon) {
        this.isHaveIcon=isHaveIcon;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_describe, parent, false);
        if (isHaveIcon) {
         view.getLayoutParams().width= (RxDeviceTool.getScreenWidth(parent.getContext())- SizeUtils.dip2px(parent.getContext(),20f))/5;
        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.setItemData(mDescribeBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDescribeBeanList.size();
    }

    public void setData( List<DescribeBean.Des> data) {
        mDescribeBeanList.clear();
        mDescribeBeanList.addAll(data);
        notifyDataSetChanged();
    }

    public class MyHolder  extends RecyclerView.ViewHolder {

        private TextView mValue;
        private ImageView mIcon;
        private TextView mTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mValue= itemView.findViewById(R.id.values);
            mTitle= itemView.findViewById(R.id.title);
            mIcon= itemView.findViewById(R.id.des_icon);
        }

        public void setItemData(DescribeBean.Des des) {

            if (isHaveIcon) {
                mIcon.setVisibility(View.VISIBLE);
                mIcon.setImageResource(des.getIcon());
            }

            mTitle.setText(des.getTitle());
            mValue.setText(des.getValues());



        }
    }
}
