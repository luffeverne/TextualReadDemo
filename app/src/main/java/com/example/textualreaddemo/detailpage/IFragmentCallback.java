package com.example.textualreaddemo.detailpage;

/**
 * DetailContentFragment 向  DetailActivity 通信（传递数据）
 */
public interface IFragmentCallback {
    void sendMsgToActivity(String msg);
}
