package org.gaochun.network;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2017-1-10.
 * 描    述：测试 MainActivity
 * ===========================================
 */
public class MainActivity extends BaseActivity {

    //用于检测网络的广播对象
    NetWorkBroadcastReceiver mNetBroadcastReceiver;

    private TextView mTv;
    private TextView mTv2;
    private TextView mTv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTv = (TextView) findViewById(R.id.textView);
        mTv2 = (TextView) findViewById(R.id.textView2);
        mTv3 = (TextView) findViewById(R.id.textView3);


        //启动时判断网络状态
        boolean netConnect = this.isNetConnect();

        if (netConnect) {
            mTv.setVisibility(View.GONE);
        } else {
            mTv.setVisibility(View.VISIBLE);
        }


        mTv3.setText("当前网络是否可用："+NetworkUtils.isConnected(this)+"\nWifi是否连接："+NetworkUtils.isWifiConnected(this)+"\nMobile网络是否连接"+NetworkUtils.isMobileConnected(this));
    }

    @Override
    public void onNetWorkChange(int netMobile) {
        super.onNetWorkChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetworkUtils.NETWORK_NONE) {
            mTv.setVisibility(View.VISIBLE);
            mTv2.setText("当前网络状态：" + netMobile);
            mTv3.setText("当前网络是否可用："+NetworkUtils.isConnected(this)+"\nWifi是否连接："+NetworkUtils.isWifiConnected(this)+"\nMobile网络是否连接"+NetworkUtils.isMobileConnected(this));
        } else {
            mTv.setVisibility(View.GONE);
            mTv2.setText("当前网络状态：" + netMobile);
            mTv3.setText("当前网络是否可用："+NetworkUtils.isConnected(this)+"\nWifi是否连接："+NetworkUtils.isWifiConnected(this)+"\nMobile网络是否连接"+NetworkUtils.isMobileConnected(this));
        }
    }


    //在onResume()方法注册
    @Override
    protected void onResume() {
        if (mNetBroadcastReceiver == null) {
            mNetBroadcastReceiver = new NetWorkBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetBroadcastReceiver, filter);
        System.out.println("---------------->注册广播");
        super.onResume();
    }


    //onPause()方法注销
    @Override
    protected void onPause() {
        unregisterReceiver(mNetBroadcastReceiver);
        System.out.println("---------------->注销广播");
        super.onPause();
    }

}
