package com.example.textualreaddemo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络广播监听器工具类
 * 最后更改时间：2022-6-20 9:10
 * author: houdeng
 */

public class NetworkBroadcastUtils {


    public void GetNetWorkConnectivity(Context context, MyConnectivityReceiver receiver) {
        //过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); //添加意图Action网络意图
        context.registerReceiver(receiver, filter);//注册 register广播receiver
    }

    public static class MyConnectivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connMag.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connMag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (!wifi.isConnected() && !mobile.isConnected()) {
                //没网络 获取数据库
                networkMethod.NoNetWork();
            }else if(mobile.isConnected()){
                networkMethod.mobileNetWork();
            } else {
                //有网络 走请求
                //请求数据
                networkMethod.haveNetWork();
            }
        }

        private NetworkMethod networkMethod;
        public void setNetworkMethod(NetworkMethod networkM){
            this.networkMethod=networkM;
        }

        public interface NetworkMethod{
            void haveNetWork();
            void NoNetWork();
            void mobileNetWork();
        }
    }

}