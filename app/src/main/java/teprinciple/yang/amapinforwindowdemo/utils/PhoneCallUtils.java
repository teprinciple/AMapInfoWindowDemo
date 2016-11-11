package teprinciple.yang.amapinforwindowdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import teprinciple.yang.amapinforwindowdemo.base.BaseApplication;


/**
 * Created by Teprinciple on 2016/8/22.
 * 打电话工具类
 */
public class PhoneCallUtils {
    private static Context mContext  = BaseApplication.getIntance().getBaseContext();;
    public static void call(String phoneNum){

        if (TextUtils.isEmpty(phoneNum)){ //电话号码为空
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:"+phoneNum);   //设置要操作界面的具体内容  拨打电话固定格式： tel：
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
