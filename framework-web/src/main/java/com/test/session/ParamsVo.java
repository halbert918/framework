package com.test.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/2/3
 * @Description
 */
public class ParamsVo implements Serializable {

    private String id;

    private String name;

    private List<Map<String, String>> list;

    public ParamsVo() {
        id = "123123";
        name = "test";

        Map<String, String> map = new HashMap<String, String>(){{
            put("data", "myData");
            put("date", "138575");
        }};
        list = new ArrayList<>();
        list.add(map);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }
}
