package com.example.tianqi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Administrator
 * @date: 2020/8/1 0001
 */
public class FiveAirAdapter extends RecyclerView.Adapter<FiveAirAdapter.MyHolder> {
    private DayWeatherBean.ResultBean mBean = null;
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_five_air_container, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.setItemData(mBean.getDaily(), position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setData(DayWeatherBean.ResultBean resultBean) {
        if (resultBean != null) {
            this.mBean = resultBean;
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_date)
        TextView mDate_tv;

        @BindView(R.id.tv_date_number)
        TextView mDate_number_tv;


        @BindView(R.id.tv_airLever)
        TextView mAirLever_tv;

        @BindView(R.id.bt_airLeverBg)
        Button mAirLeverBg_bt;

        @BindView(R.id.tv_airLeverTitle)
        TextView mArLeverTitle_tv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setItemData(DayWeatherBean.ResultBean.DailyBean daily, int position) {

            DayWeatherBean.ResultBean.DailyBean.AstroBean astroBean = daily.getAstro().get(position);
            String date = astroBean.getDate().substring(0, 10);
            String substring = DateUtil.StrToData(date);
            mDate_number_tv.setText(substring);

            if (position == 0) {
                mDate_tv.setText("今天");
            } else if (position == 1) {
                mDate_tv.setText("明天");
            } else {
                String week = DateUtil.getWeek(date);
                mDate_tv.setText(week);
            }

            int chn = (int)daily.getAir_quality().getAqi().get(position).getAvg().getChn();
            mAirLever_tv.setText(chn+"");
            mArLeverTitle_tv.setText(WeatherUtils.aqiType(chn));

            mAirLeverBg_bt.setBackground(WeatherUtils.aqiTypeBg(values,chn));


        }
    }
    private int[] values={0,50,100,150,200,300};
}
