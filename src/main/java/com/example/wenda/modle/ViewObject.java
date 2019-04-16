package com.example.wenda.modle;


import java.util.HashMap;
import java.util.Map;

//控制器和前端的中间层，用于传递数据
//@Service
public class ViewObject {
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
