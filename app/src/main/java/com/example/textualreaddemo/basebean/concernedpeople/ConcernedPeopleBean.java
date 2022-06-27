package com.example.textualreaddemo.basebean.concernedpeople;

import java.util.ArrayList;
import java.util.List;

public class ConcernedPeopleBean {

    private String name;
    private int id;

    public ConcernedPeopleBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 生成测试作者信息
     */
    public static List<ConcernedPeopleBean> setConcernedPeople(){

        List<ConcernedPeopleBean> concernedPeople = new ArrayList<>();



        for (int i = 0; i < 7; i++) {
            ConcernedPeopleBean people = new ConcernedPeopleBean();
            people.setId(i);
            people.setName("作者"+i);
            concernedPeople.add(people);
        }
        return concernedPeople;
    }
}
