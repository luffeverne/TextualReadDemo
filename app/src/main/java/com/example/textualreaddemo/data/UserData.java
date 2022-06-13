package com.example.textualreaddemo.data;
/**
 * 用户数据
 * 最后更改时间：2022-6-11 17:00
 * @author luffe
 */
public class UserData {
    private String userName;
    private String password;

    public UserData() {
    }

    public UserData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
