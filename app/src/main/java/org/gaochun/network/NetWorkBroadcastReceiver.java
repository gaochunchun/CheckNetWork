package org.gaochun.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import static org.gaochun.network.BaseActivity.evevt;


/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2017-1-10.
 * 描    述：网络检测广播
 * ===========================================
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {

    public NetEvevt mEvevt = evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int mNetType = NetworkUtils.checkNetWorkState(context);
            // 接口回调，传递当前网络状态的类型
            mEvevt.onNetWorkChange(mNetType);
        }
    }


    // 自定义网络事件接口
    public interface NetEvevt {
        public void onNetWorkChange(int mNetType);
    }


}
