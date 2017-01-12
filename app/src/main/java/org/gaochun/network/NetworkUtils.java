package org.gaochun.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2017-1-10.
 * 描    述：网络检测工具类
 * ===========================================
 */

public class NetworkUtils {

    private NetworkUtils() {

    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    /**
     * 判断WIFI是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }


    /**
     * 判断移动网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }


    private static boolean isConnected(@NonNull Context context, int type) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //检测API是否小于21，因为API21之后getNetworkInfo(int networkType)方法被弃用
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return isConnected(connMgr, type);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork);
            if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }




    //没有连接网络
    public static final int NETWORK_NONE = -1;
    //移动网络
    public static final int NETWORK_MOBILE = 0;
    //无线网络
    public static final int NETWORK_WIFI = 1;



    /**
     * 检测网络类型
     * @param context
     * @return
     */
    public static int checkNetWorkState(@NonNull Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //检测API是否小于21，因为API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifiNetworkInfo.isConnected()) {
                return NETWORK_WIFI;
            } else if (mobileNetworkInfo.isConnected()) {
                return NETWORK_MOBILE;
            } else {
                return NETWORK_NONE;
            }

            /*NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                if (mNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                    return NETWORK_WIFI;
                } else if (mNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                    return NETWORK_MOBILE;
                } else {
                    return NETWORK_NONE;
                }
            }*/

        } else {

            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo mNetworkInfo;

            for (Network mNetwork : networks) {
                mNetworkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (mNetworkInfo != null) {
                    if (mNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                        //Log.e("------------>", "NETWORK_WIFI");
                        return NETWORK_WIFI;
                    } else if (mNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                        //Log.e("------------>", "NETWORK_MOBILE");
                        return NETWORK_MOBILE;
                    }
                } else {
                    //Log.e("------------>", "NETWORK_NONE");
                    return NETWORK_NONE;
                }
            }
        }
        return NETWORK_NONE;
    }
}
