package com.example.tianqi.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.ui.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/6/29 0029
 */
public class WeatherAdapter extends FragmentStatePagerAdapter {

    List<LocationBean> mList=new ArrayList<>();
    public WeatherAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        WeatherFragment fragment = WeatherFragment.getInstance(mList.get(position));
        return fragment;
    }

    public void setData(List<LocationBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        WeatherFragment fragment = (WeatherFragment)super.instantiateItem(container, position);
        fragment.updateArguments(position);
        return super.instantiateItem(container, position);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public static Fragment instantFragment;

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        instantFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getInstantFragment(){
        return instantFragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }


}
