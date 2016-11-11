package teprinciple.yang.amapinforwindowdemo;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import teprinciple.yang.amapinforwindowdemo.adapter.InfoWinAdapter;
import teprinciple.yang.amapinforwindowdemo.base.BaseActivity;
import teprinciple.yang.amapinforwindowdemo.entity.Constant;
import teprinciple.yang.amapinforwindowdemo.utils.CheckPermissionsActivity;

public class MainActivity extends CheckPermissionsActivity implements AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMapLocationListener {

    private MapView mapView;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption;
    private AMap aMap;
    private UiSettings uiSettings;
    private InfoWinAdapter adapter;
    private Marker oldMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //在执行onCreateView时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        initOperation();
    }

    private void initView() {
        mapView = (MapView) initV(R.id.mapView);
    }

    private void initOperation() {
        initMap();
        Location();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            aMap.setOnMapClickListener(this);
        }
        uiSettings.setZoomControlsEnabled(false); //隐藏缩放控件
        //自定义InfoWindow
        aMap.setOnMarkerClickListener(this);
        adapter = new InfoWinAdapter();
        aMap.setInfoWindowAdapter(adapter);

        addMarkerToMap(Constant.CHENGDU,"成都","中国四川省成都市");
    }


    /**
     * 定位
     */
    private void Location() {
        mLocationClient = new AMapLocationClient(this);
        //设置监听
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置为高精度定位模式 网络+GPS
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：  该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); //管理地图的生命周期
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); //管理地图的生命周期
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); //管理地图的生命周期
    }

    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        if (oldMarker != null) {
            oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (oldMarker != null) {
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        }
        oldMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected));
        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }

    //定位成功的监听
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                String city = aMapLocation.getCity();
                String address = aMapLocation.getAddress();
                addMarkerToMap(latLng,city,address);

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    private void addMarkerToMap(LatLng latLng, String title, String snippet) {
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal))
        );
    }
}
