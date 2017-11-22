package org.shunt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;


/**
 * @author GuoXinYuan
 *
 */
public class TagHolder {
    @Autowired
    ShuntProperties shuntProperties;
    
    public static String X_TAG_NAME = "x-tag";

    private static final ThreadLocal<Map<String, String>> X_TAG_MAP = new ThreadLocal<>();

    public static void put(String key, String value) {
        Map<String, String> map = X_TAG_MAP.get();
        if (map == null)
            map = new HashMap<>();
        map.put(key, value);
        X_TAG_MAP.set(map);
    }

    public static void putHeader(HttpServletRequest req) {
        put(X_TAG_NAME, req.getHeader(X_TAG_NAME));
    }
    
    public static void getHeader(HttpRequest req) {
        Map<String, String> map = X_TAG_MAP.get();
        if (map != null) {
            String xTag = map.get(X_TAG_NAME);
            if (xTag != null) {
                req.getHeaders().add(X_TAG_NAME, xTag);
            }
        }
    }

    public static boolean checkMap(Map<String, String> map) {
        if (map != null) {
            String mapCanary = map.get(X_TAG_NAME);
            if (mapCanary != null) {
                Map<String, String> headerMap = X_TAG_MAP.get();
                if (headerMap != null) {
                    String localCanary = headerMap.get(X_TAG_NAME);
                    return localCanary != null && mapCanary.equals(localCanary);
                }
            }
        }
        return false;
    }

}
