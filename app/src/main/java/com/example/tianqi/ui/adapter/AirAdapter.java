package com.example.tianqi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.model.bean.AirBean;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.MyHolder> {
    private List<AirBean> mList=new ArrayList<>();
    private List<int[]> mListBg=new ArrayList<>();
    private int[] valuesPM25={0,35,75,115,150,175};
    private int[] valuesPM10={0,50,100,150,200,300};
    private int[] valuesSO2={0,50,100,150,200,300};
    private int[] valuesNO2={0,40,80,120,160,200};
    private int[] valuesCO= {0,5,10,15,20,25};
    private int[] valuesO3= {0,50,100,150,200,250};
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_air_container, parent, false);
        mListBg.add(valuesPM25);
        mListBg.add(valuesPM10);
        mListBg.add(valuesSO2);
        mListBg.add(valuesNO2);
        mListBg.add(valuesCO);
        mListBg.add(valuesO3);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.setItemData(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<AirBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_airTitle)
        TextView mTitle;

        @BindView(R.id.tv_airHint)
        TextView mHint;

        @BindView(R.id.tv_airValue)
        TextView mValue;

        @BindView(R.id.bt_indication)
        Button  mIndication;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemData(AirBean airBean, int position) {
            mTitle.setText(airBean.getTitle());
            mHint.setText(airBean.getHint());
            int value = airBean.getValue();

            mValue.setText(value+"");

            mIndication.setBackground(WeatherUtils.aqiTypeBg(mListBg.get(position),value));


            LogUtils.i(AirAdapter.this,"--------------、、---"+airBean.getValue()+"");
        }
    }

}
