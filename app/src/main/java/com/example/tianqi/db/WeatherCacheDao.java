package com.example.tianqi.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.presenter.views.IWeaCacheDaoCallback;

import java.util.ArrayList;
import java.util.List;

public class WeatherCacheDao implements IWeatherCacheDao{
    private Object mLock = new Object();

    private final LocationDBHelper mDbHelper;
    private IWeaCacheDaoCallback mIWeaCacheDaoCallback=null;
    private static WeatherCacheDao  sInstance;
    private final List<String> mList;

    public static WeatherCacheDao getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherCacheDao();
        }
        return sInstance;
    }
     private WeatherCacheDao() {
         mDbHelper= new LocationDBHelper(BaseApplication.getAppContext());
         mList = queryCity();
     }


    @Override
    public void addWeaCache(WeatherCacheBean weatherCacheBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            boolean addSuccess = false;
            try {
                if (mList.contains(weatherCacheBean.getCity())) {
                    updateWeaCache(weatherCacheBean);

                } else {
                    db = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Contents.CITY, weatherCacheBean.getCity());
                    values.put(Contents.WEA_DESCRIBE, weatherCacheBean.getDescribe());
                    values.put(Contents.TF_DATA, weatherCacheBean.getTfData());
                    values.put(Contents.TF_QUALITY, weatherCacheBean.getTfQuality());
                    values.put(Contents.TF_WINDY, weatherCacheBean.getTfWindy());
                    values.put(Contents.TF_TIME, weatherCacheBean.getTfTime());
                    values.put(Contents.TF_TEAM, weatherCacheBean.getTfTeam());
                    values.put(Contents.TF_WEA_ICON, weatherCacheBean.getTfWeaIcon());
                    values.put(Contents.RAIN_STATE, weatherCacheBean.getTfRainState());
                    values.put(Contents.FIVE_WEA, weatherCacheBean.getFiveWea());
                    values.put(Contents.LIFE_INDEX, weatherCacheBean.getLifeIndex());
                    db.beginTransaction();
                    db.insert(Contents.WEATHER_CACHE, null, values);
                    db.setTransactionSuccessful();
                    addSuccess = true;
                    mList.add(weatherCacheBean.getCity());
                }

            } catch (Exception e) {
                e.printStackTrace();
                addSuccess = false;
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
                if (mIWeaCacheDaoCallback != null) {
                    mIWeaCacheDaoCallback.addCacheSuccess(addSuccess);
                }
            }
        }
    }

    private void updateWeaCache(WeatherCacheBean weatherCacheBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            try {
                db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Contents.CITY, weatherCacheBean.getCity());
                if (!TextUtils.isEmpty(weatherCacheBean.getDescribe())) {
                    values.put(Contents.WEA_DESCRIBE, weatherCacheBean.getDescribe());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfData())) {
                    values.put(Contents.TF_DATA, weatherCacheBean.getTfData());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfQuality())) {
                    values.put(Contents.TF_QUALITY, weatherCacheBean.getTfQuality());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfWindy())) {
                    values.put(Contents.TF_WINDY, weatherCacheBean.getTfWindy());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfTime())) {
                    values.put(Contents.TF_TIME, weatherCacheBean.getTfTime());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfTeam())) {
                    values.put(Contents.TF_TEAM, weatherCacheBean.getTfTeam());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfWeaIcon())) {
                    values.put(Contents.TF_WEA_ICON, weatherCacheBean.getTfWeaIcon());
                }
                if (!TextUtils.isEmpty(weatherCacheBean.getTfRainState())) {
                    values.put(Contents.RAIN_STATE, weatherCacheBean.getTfRainState());
                }

                if (!TextUtils.isEmpty(weatherCacheBean.getFiveWea())) {
                    values.put(Contents.FIVE_WEA, weatherCacheBean.getFiveWea());
            }

                if (!TextUtils.isEmpty(weatherCacheBean.getLifeIndex())) {
                    values.put(Contents.LIFE_INDEX, weatherCacheBean.getLifeIndex());
                }
                db.beginTransaction();
                db.update(Contents.WEATHER_CACHE,values,Contents.CITY+"=?",new String[]{weatherCacheBean.getCity()});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    private List<String> queryCity() {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            List<String> list = new ArrayList<>();
            try {
                db = mDbHelper.getReadableDatabase();
                db.beginTransaction();
                Cursor query = db.query(Contents.WEATHER_CACHE, null, null, null, null, null, null);
                while (query.moveToNext()) {
                    String city = query.getString(query.getColumnIndex(Contents.CITY));
                    list.add(city);
                }
                db.setTransactionSuccessful();
                query.close();
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
            return list;
        }
    }


    @Override
    public void deleteWeaCache(String city) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            try {
                db = mDbHelper.getWritableDatabase();
                db.beginTransaction();
                db.delete(Contents.WEATHER_CACHE, Contents.CITY + "=?", new String[]{city});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    @Override
    public void queryWeaCache() {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            List<WeatherCacheBean> list = new ArrayList<>();
            try {
                db = mDbHelper.getReadableDatabase();
                db.beginTransaction();
                Cursor query = db.query(Contents.WEATHER_CACHE, null, null, null, null, null, null);
                while (query.moveToNext()) {
                    String city = query.getString(query.getColumnIndex(Contents.CITY));
                    String wea_describe = query.getString(query.getColumnIndex(Contents.WEA_DESCRIBE));
                    String tf_data = query.getString(query.getColumnIndex(Contents.TF_DATA));
                    String tf_quality = query.getString(query.getColumnIndex(Contents.TF_QUALITY));
                    String tf_windy = query.getString(query.getColumnIndex(Contents.TF_WINDY));


                    String tf_time = query.getString(query.getColumnIndex(Contents.TF_TIME));
                    String tf_team = query.getString(query.getColumnIndex(Contents.TF_TEAM));
                    String tf_icon = query.getString(query.getColumnIndex(Contents.TF_WEA_ICON));
                    String tf_rain_state = query.getString(query.getColumnIndex(Contents.RAIN_STATE));



                    String five_wea = query.getString(query.getColumnIndex(Contents.FIVE_WEA));
                    String life_index = query.getString(query.getColumnIndex(Contents.LIFE_INDEX));
                    list.add(new WeatherCacheBean(city
                            , wea_describe
                            , tf_data
                            , tf_quality
                            , tf_windy
                            , tf_time
                            , tf_team
                            , tf_icon
                            , tf_rain_state
                            , five_wea
                            , life_index));

                }
                db.setTransactionSuccessful();
                if (mIWeaCacheDaoCallback != null & list.size() != 0) {
                    mIWeaCacheDaoCallback.onWeaCacheList(list);
                }
                query.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }


    @Override
    public void registerCallback(IWeaCacheDaoCallback iWeaCacheDaoCallback) {
        this.mIWeaCacheDaoCallback=iWeaCacheDaoCallback;
    }

    @Override
    public void unregisterCallback(IWeaCacheDaoCallback iWeaCacheDaoCallback) {

    }
}
