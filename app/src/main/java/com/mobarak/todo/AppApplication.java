package com.mobarak.todo;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {
    private static AppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static AppApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
