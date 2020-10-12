package com.example.tianqi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.presenter.Impl.AddressPresentImpl;

public class PresentManager {

    private static PresentManager  sInstance;
    private final AddressPresentImpl mAddressPresent;


    public static PresentManager getInstance() {
        if (sInstance == null) {
            sInstance = new PresentManager();
        }
        return sInstance;
    }

    public AddressPresentImpl getAddressPresent() {
        return mAddressPresent;
    }

    private PresentManager() {
         mAddressPresent = new AddressPresentImpl();

     }


}
