package com.java.utils.isnull;

import java.util.Collection;

/**
 * Created by Tim on 2017/5/22.
 * 对对象判断是否为空
 */
public class IsNullUtil {

    /**
     * array is null
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isNullArray(T[] array) {
        if(array == null || array.length == 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * string is null
     * @param str
     * @return
     */
    public static boolean isNullString(String str) {
        if(str != null && str.length() > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * collection is null
     * @param collection
     * @return
     */
    public static boolean isNullCollection(Collection<?> collection) {
        if(collection != null && !collection.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * map is null
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullMap(Map map){
        if(map != null && !map.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
}
