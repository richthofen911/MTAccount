package net.callofdroidy.sharedaccount;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by admin on 04/04/16.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
