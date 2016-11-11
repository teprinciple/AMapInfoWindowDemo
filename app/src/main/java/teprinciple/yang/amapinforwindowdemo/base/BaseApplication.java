package teprinciple.yang.amapinforwindowdemo.base;

import android.app.Application;

/**
 * Created by Teprinciple on 2016/11/11.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

    }

    public static BaseApplication getIntance() {
        return mApplication;
    }

}
