package teprinciple.yang.amapinforwindowdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.amap.api.maps2d.model.LatLng;

import java.io.File;

import teprinciple.yang.amapinforwindowdemo.base.BaseApplication;


/**
 * Created by Teprinciple on 2016/8/22.
 * 跳转到高德地图进行导航
 */
public class NavigationUtils {

    private static Context mContext;

    public static void Navigation(LatLng latLng){
        mContext = BaseApplication.getIntance().getBaseContext();


        if(isInstallPackage("com.autonavi.minimap")){
//            Toast.makeText(getContext(), "安装有高德地图", Toast.LENGTH_SHORT).show();
            SkipToGD(latLng);
        }else {
            Toast.makeText(mContext, "请下载安装高德地图", Toast.LENGTH_SHORT).show();
            DownloadMapApp();
        }
    }

    /**
     * 到应用市场下载高德地图app
     */
    private static void DownloadMapApp() {
        //显示手机上所有的market商店
        Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 判断是否安装 高德地图app
     * @param packageName
     * @return
     */
    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 跳转到高德地图进行导航
     */
    private static void SkipToGD(LatLng latLng) {
        //跳转导航
        //dev 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
        //style 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；
        // 5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
        Uri uri = Uri.parse("androidamap://navi?sourceApplication=CloudPatient&lat="+latLng.latitude+"&lon="+latLng.longitude+"&dev=1&style=2");
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
