/**
  * Copyright 2022 bejson.com 
  */
package com.example.textualreaddemo.beanRetrofit;

import java.util.List;

/**
 * Auto-generated: 2022-06-08 21:45:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
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