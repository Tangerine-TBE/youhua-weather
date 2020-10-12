package com.example.tianqi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class FiveWeatherAdapter extends RecyclerView.Adapter<FiveWeatherAdapter.MyHolder> {
    private DayWeatherBean.ResultBean mBean = null;
    List<DayWeatherBean.ResultBean.DailyBean.AstroBean> mAstroList=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener=null;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_five2_weather, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
      //  LogUtils.i(this, "onBindViewHolder--------------------->" + mBean);
        holder.setItemData(mBean.getDaily(), position);
    }

    @Override
    public int getItemCount() {
        return mAstroList.size();
    }

    public void setData(DayWeatherBean.ResultBean daily,boolean isShowMore) {
        mAstroList.clear();
        List<DayWeatherBean.ResultBean.DailyBean.AstroBean> astro = daily.getDaily().getAstro();
        mAstroList.addAll(isShowMore?astro:astro.subList(0, 5));
        this.mBean = daily;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_five_week)
        TextView mWeek;

        @BindView(R.id.iv_five_weaIcon)
        ImageView mIcon;

        @BindView(R.id.tv_five_wea)
        TextView mWea;

        @BindView(R.id.tv_five_aqi)
        TextView mAir;

        @BindView(R.id.tv_five_team)
        TextView mTeam;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setItemData(DayWeatherBean.ResultBean.DailyBean bean, int position) {

            DayWeatherBean.ResultBean.DailyBean.AstroBean astroBean = bean.getAstro().get(position);
            String date = astroBean.getDate().substring(0, 10);
            if (position == 0) {
                mWeek.setText("今天");
            } else if (position == 1) {
                mWeek.setText("明天");
            } else if ((position == 2)){
                mWeek.setText("后天");
            } else {
                String week = DateUtil.getWeek(date);
                mWeek.setText(week);
            }

            DayWeatherBean.ResultBean.DailyBean.SkyconBean skyconBean = bean.getSkycon().get(position);
            String value = skyconBean.getValue();
            mWea.setText(WeatherUtils.weatherPhenomena(value));

            int icon = WeatherUtils.weatherIcon(value);
            Glide.with(mIcon.getContext()).load(icon).into(mIcon);

            DayWeatherBean.ResultBean.DailyBean.TemperatureBean temperatureBean = bean.getTemperature().get(position);
            int max = (int)temperatureBean.getMax();
            int min =(int)temperatureBean.getMin();
            mTeam.setText(min+"/"+(WeatherUtils.addTemSymbol(max)));

            int chn = (int)bean.getAir_quality().getAqi().get(position).getAvg().getChn();
            mAir.setText(WeatherUtils.aqiType(chn));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });


        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
