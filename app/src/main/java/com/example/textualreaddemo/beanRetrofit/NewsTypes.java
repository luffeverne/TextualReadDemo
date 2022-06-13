/**
  * Copyright 2022 bejson.com 
  */
package com.example.textualreaddemo.beanRetrofit;

import java.util.List;

/**
 * 新闻类型数据相关类
 */
public class NewsTypes {

    public String code;
    public String msg;
    public List<Data> data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data{
        public String typeId;
        public String typeName;

        public String getTypeId() {
            return typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        @Override
        public String toString() {
            return "NewsTypesData{" +
                    "typeId='" + typeId + '\'' +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsTypes{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}