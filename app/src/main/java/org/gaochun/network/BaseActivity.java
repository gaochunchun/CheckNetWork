package org.gaochun.network;

import android.app.Activity;
import android.os.Bundle;
/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2017-1-10.
 * 描    述：BaseActivity
 * ===========================================
 */
public class BaseActivity extends Activity implements NetWorkBroadcastReceiver.NetEvevt {

    public static NetWorkBroadcastReceiver.NetEvevt evevt;

    //网络类型
    private int mNetType;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        evevt = this;

        checkNetWork();
    }


    /**
     * 初始化时判断网络是否可用
     */
    public boolean checkNetWork() {

        this.mNetType = NetworkUtils.checkNetWorkState(this);
        return isNetConnect();

        // if (netMobile == NetUtil.NETWORK_WIFI) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == NetUtil.NETWORK_MOBILE) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == NetUtil.NETWORK_NONE) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetWorkChange(int netMobile) {

        this.mNetType = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (mNetType == NetworkUtils.NETWORK_WIFI) {
            return true;
        } else if (mNetType == NetworkUtils.NETWORK_MOBILE) {
            return true;
        } else if (mNetType == NetworkUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }
}
