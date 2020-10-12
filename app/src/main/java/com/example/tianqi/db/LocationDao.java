package com.example.tianqi.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.views.ILocationCallback;
import com.example.tianqi.utils.Contents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public class LocationDao implements ILocationDao {
    private Object mLock = new Object();
    private static volatile LocationDao singleton = null;
    private final LocationDBHelper mDbHelper;
    private ILocationCallback mILocationCallback=null;

    private LocationDao() {
        mDbHelper = new LocationDBHelper(BaseApplication.getAppContext());
    }

    public static LocationDao getInstance() {
                if (singleton == null) {
                    singleton = new LocationDao();
            }
        return singleton;
    }


    @Override
    public void addCity(LocationBean locationBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            boolean addSuccess = false;
            try {

                db = mDbHelper.getWritableDatabase();
                db.delete(Contents.LOCATION_TABLE, Contents.CITY + "=?", new String[]{locationBean.getCity()});
                ContentValues values = new ContentValues();
                values.put(Contents.CITY, locationBean.getCity());
                values.put(Contents.WEA, locationBean.getWea());
                values.put(Contents.HIGH_TEAM, locationBean.getHighTeam());
                values.put(Contents.LOW_TEAM, locationBean.getLowTeam());
                values.put(Contents.LATITUDE, locationBean.getLatitude());
                values.put(Contents.LONGITUDE, locationBean.getLongitude());
                db.beginTransaction();
                db.insert(Contents.LOCATION_TABLE, null, values);
                db.setTransactionSuccessful();
                addSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                addSuccess = false;
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
                if (mILocationCallback != null) {
                    mILocationCallback.addSuccess(addSuccess);
                }
            }
        }
    }

    @Override
    public void deleteCity(LocationBean locationBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            boolean delSuccess = false;
            try {
                db = mDbHelper.getWritableDatabase();
                db.beginTransaction();
                db.delete(Contents.LOCATION_TABLE, Contents.CITY + "=?", new String[]{locationBean.getCity()});
                db.setTransactionSuccessful();
                delSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
                if (mILocationCallback != null) {
                    mILocationCallback.deleteSuccess(delSuccess);
                }
            }
        }
    }

    @Override
    public void getSelectCity() {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            List<LocationBean> list = new ArrayList<>();
            try {
                db = mDbHelper.getReadableDatabase();
                db.beginTransaction();
                Cursor query = db.query(Contents.LOCATION_TABLE, null, null, null, null, null, null);
                while (query.moveToNext()) {
                    String city = query.getString(query.getColumnIndex(Contents.CITY));
                    String wea = query.getString(query.getColumnIndex(Contents.WEA));
                    double high = query.getDouble(query.getColumnIndex(Contents.HIGH_TEAM));
                    double low = query.getDouble(query.getColumnIndex(Contents.LOW_TEAM));
                    double latitude = query.getDouble(query.getColumnIndex(Contents.LATITUDE));
                    double longitude = query.getDouble(query.getColumnIndex(Contents.LONGITUDE));
                    list.add(new LocationBean(city, longitude, latitude,wea,high,low));
                }
                db.setTransactionSuccessful();
                if (mILocationCallback != null & list.size() != 0) {
                    mILocationCallback.onCityList(list);
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
    public void updateCity(LocationBean locationBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            boolean addSuccess = false;
            try {

                db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Contents.CITY, locationBean.getCity());
                values.put(Contents.WEA, locationBean.getWea());
                values.put(Contents.HIGH_TEAM, locationBean.getHighTeam());
                values.put(Contents.LOW_TEAM, locationBean.getLowTeam());
                values.put(Contents.LATITUDE, locationBean.getLatitude());
                values.put(Contents.LONGITUDE, locationBean.getLongitude());
                db.beginTransaction();
                db.update(Contents.LOCATION_TABLE,values,Contents.CITY+"=?",new String[]{locationBean.getCity()});
                db.setTransactionSuccessful();
                addSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                addSuccess = false;
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
                if (mILocationCallback != null) {
                    mILocationCallback.updateSuccess(addSuccess);
                }
            }
        }
    }

    @Override
    public void updateLocationCity(LocationBean locationBean) {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            try {
                db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Contents.CITY, locationBean.getCity());
                values.put(Contents.WEA, locationBean.getWea());
                values.put(Contents.HIGH_TEAM, locationBean.getHighTeam());
                values.put(Contents.LOW_TEAM, locationBean.getLowTeam());
                values.put(Contents.LATITUDE, locationBean.getLatitude());
                values.put(Contents.LONGITUDE, locationBean.getLongitude());
                db.beginTransaction();
                Cursor query = db.query(Contents.LOCATION_TABLE, null, null, null, null, null, null);
                if (query.moveToFirst()) {
                    int id = query.getInt(0);
                    db.update(Contents.LOCATION_TABLE,values,  Contents.ID+"=?",new String[]{id+""});
                    db.setTransactionSuccessful();

                }
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
    public  List<LocationBean> getCityList() {
        synchronized(mLock) {
            SQLiteDatabase db = null;
            List<LocationBean> list = new ArrayList<>();
            try {
                db = mDbHelper.getReadableDatabase();
                db.beginTransaction();
                Cursor query = db.query(Contents.LOCATION_TABLE, null, null, null, null, null, null);
                while (query.moveToNext()) {
                    String city = query.getString(query.getColumnIndex(Contents.CITY));
                    double latitude = query.getDouble(query.getColumnIndex(Contents.LATITUDE));
                    double longitude = query.getDouble(query.getColumnIndex(Contents.LONGITUDE));
                    list.add(new LocationBean(city, longitude, latitude));
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
    public void registerCallback(ILocationCallback iLocationCallback) {
      this.mILocationCallback=iLocationCallback;
    }

    @Override
    public void unregisterCallback(ILocationCallback iLocationCallback) {

    }
}
