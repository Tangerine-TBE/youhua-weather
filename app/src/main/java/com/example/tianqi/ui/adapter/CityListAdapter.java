package com.example.tianqi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Administrator
 * @date: 2020/7/5 0005
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.MyHolder> {

    private List<LocationBean> mLocationBeans=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener=null;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_container, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.setItemData(mLocationBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocationBeans.size();
    }

    public void setData(List<LocationBean> cityList) {
        mLocationBeans.clear();
        mLocationBeans.addAll(cityList);
        notifyDataSetChanged();
    }

    public void deleteCity(int adapterPosition) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.deleteOnClick(mLocationBeans.get(adapterPosition),adapterPosition);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener {
        void deleteOnClick(LocationBean city,int position);
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city_name)
        TextView mCityName;

        @BindView(R.id.city_wea)
        TextView mCityWea;

        @BindView(R.id.city_tem)
        TextView mCityTem;

        @BindView(R.id.wea_icon)
        ImageView mWeaIcon;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setItemData(LocationBean bean) {
            mCityName.setText(bean.getCity());
            String wea = bean.getWea();
            String weatherPhenomena = WeatherUtils.weatherPhenomena(wea);
            mCityWea.setText(weatherPhenomena);
           mCityTem.setText((int)bean.getHighTeam()+"°"+"/"+ (int)bean.getLowTeam()+"℃");

            int icon = WeatherUtils.weatherIcon(wea);
            Glide.with(mWeaIcon.getContext()).load(icon).into(mWeaIcon);

        }
    }
}
