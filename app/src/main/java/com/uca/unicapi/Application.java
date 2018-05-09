package com.uca.unicapi;

import com.tumblr.remember.Remember;
import io.realm.Realm;

/**
 * Created by Stephane on 05/03/2018.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Remember.init(getApplicationContext(), "com.uca.unicapi.sync");
    }
}
