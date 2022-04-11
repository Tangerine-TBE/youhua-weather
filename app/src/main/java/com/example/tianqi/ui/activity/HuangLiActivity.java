package com.example.tianqi.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.utils.Contents;
import com.tiantian.tianqi.R;

import java.util.List;

import butterknife.BindView;

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.activity
 * @class describe
 * @time 2020/9/28 18:46
 * @class describe
 */
public class HuangLiActivity extends BaseMainActivity {

    @BindView(R.id.tv_hl_date)
    TextView tv_hl_date;
    @BindView(R.id.dt_haugnli)
    DiyToolbar dt_haugnli;
    @BindView(R.id.tv_hl_nongli)
    TextView tv_hl_nongli;
    @BindView(R.id.tv_hl_suici)
    TextView tv_hl_suici;
    @BindView(R.id.tv_yi)
    TextView tv_yi;
    @BindView(R.id.tv_ji)
    TextView tv_ji;
    @BindView(R.id.tv_jishenyiqu)
    TextView tv_jishenyiqu;
    @BindView(R.id.tv_xiongsheng)
    TextView tv_xiongsheng;
    @BindView(R.id.tv_c_hl_text)
    TextView tv_c_hl_text;
    @BindView(R.id.tv_s_hl_text)
    TextView tv_s_hl_text;
    @BindView(R.id.tv_jiri)
    TextView tv_jiri;
    @BindView(R.id.toolbar_icon)
    ImageView toolbar_icon;


    @Override
    public int getLayoutId() {
        return R.layout.activity_haungli;
    }

    @Override
    protected void intView() {
        dt_haugnli.setTitle("天气黄历");
        Intent intent = getIntent();
        String data = intent.getStringExtra(Contents.HL_DATA);
        if (!TextUtils.isEmpty(data)) {
            HuangLiBean resultBean = JSON.parseObject(data, HuangLiBean.class);
            if (resultBean != null) {
                showHuangLi(resultBean.getResult());
            }
        }

    }
    private void showHuangLi(HuangLiBean.ResultBean resultBean) {
        tv_hl_date.setText(resultBean.getYangli());
        tv_hl_nongli.setText( resultBean.getYinliDate());
        tv_hl_suici.setText(resultBean.getYinliYear()+"  星期" + resultBean.getWeek());


        // 宜
        StringBuffer stringBuffer1 = new StringBuffer();
        for (String s : resultBean.getYiList()) {
            stringBuffer1.append(s+"  ");
        }
        tv_yi.setText(stringBuffer1);


        //忌
        StringBuffer stringBuffer2 = new StringBuffer();
        for (String s : resultBean.getJiList()) {
            stringBuffer2.append(s+"  ");
        }
        tv_ji.setText(stringBuffer2);

        tv_xiongsheng.setText(resultBean.getJishen());
        tv_jishenyiqu.setText(resultBean.getXiongshen());
        tv_c_hl_text.setText(resultBean.getChongsha()+"  "+resultBean.getSha());
        tv_s_hl_text.setText(resultBean.getWuxing());
        tv_jiri.setText(resultBean.getJishen());

    }

    @Override
    protected void intEvent() {
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
