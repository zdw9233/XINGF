package com.uyi.xinf.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/9/18.
 */
public class SerializableMap implements Serializable {

    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
