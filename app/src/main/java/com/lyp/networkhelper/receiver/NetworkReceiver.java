package com.lyp.networkhelper.receiver;

/**
 * Created by lyp on 2016/11/29.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lyp.networkhelper.util.NetworkUtil;

/**
 * 用于接受网络状态的变化的receiver
 *
 */
public class NetworkReceiver extends BroadcastReceiver
{
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String TA_ANDROID_NET_CHANGE_ACTION = "ta.android.net.conn.CONNECTIVITY_CHANGE";

    private static Boolean networkAvailable = false; //默认网络状态
    private static NetworkUtil.netType type;

    private static NetworkReceiver receiver;


    private NetworkReceiver() {
        super();
    }

    /**
     * 单例模式
     * @return
     */
    public static NetworkReceiver getNetWorkReceiver() {
        if (receiver == null) {
            synchronized (NetworkReceiver.class) {
                if (receiver == null) {
                    receiver = new NetworkReceiver();
                }
            }
        }
        return receiver;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        receiver = NetworkReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)
                || intent.getAction().equalsIgnoreCase(TA_ANDROID_NET_CHANGE_ACTION)) {

            if (!NetworkUtil.isNetworkAvailable(context) && networkAvailable == true) {

                Log.i("NETWORK", "当前网络不可用");
                networkAvailable = false;
                Toast.makeText(context, "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else if (NetworkUtil.isNetworkAvailable(context) && networkAvailable == false) {

                Log.i("NETWORK", "网络连接成功");
                networkAvailable = true;
                type = NetworkUtil.getAPNType(context);
                Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * 注册网络监听
     *
     * @param context
     */
    public static void registerNetworkStateReceiver(Context context) {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    /**
     * 显示当前网络状态
     *
     * @param context
     */
    public static void checkNetWorkState(Context context) {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    /**
     * 注销网络监听
     *
     * @param context
     */
    public static void unRegisterNetworkStateReceiver(Context context) {
        if (receiver != null) {
            try {
                context.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static NetworkUtil.netType getNetworkType() {
        return type;
    }

}
