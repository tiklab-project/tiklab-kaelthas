package io.thoughtware.kaelthas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

public class JsonTest {
    public static void main(String[] args) {
        String json = "{\"description\":\"11 days, 2:27:32.73\",\"deviceModel\":\"TL-SG5428\",\"runningTime\":\"11 days, 2:27:32.75\"}";

        Map map = JSON.parseObject(json, Map.class);
        System.out.println(map.get("description"));

    }
}
