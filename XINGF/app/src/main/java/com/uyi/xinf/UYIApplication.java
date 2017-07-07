package com.uyi.xinf;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uyi.xinf.model.dao.BaseDao;
import com.uyi.xinf.model.dao.impl.BaseDaoImpl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * uyi
 *
 * @author user
 */
public class UYIApplication extends Application {


    public static UYIApplication application;

    /**
     * 保存数据库操作对象
     */
    private static Map<String, BaseDao<?>> databases = new HashMap<String, BaseDao<?>>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        Fresco.initialize(this);
    }


    public static UYIApplication getContext() {
        return application;
    }

    public static void loginOut() {
    }


    /**
     * 获取数据库操作对象  没有则创建   自动转换类型
     */
    public static <T extends BaseDaoImpl<?>> T getBaseDao(Class<T> t) {
        String key = t.getName();
        if (databases.containsKey(key)) {
            return (T) databases.get(key);
        } else {
            Object obj;
            try {
                Class[] paramTypes = {Context.class};
                Object[] params = {UYIApplication.getContext()};
                Constructor constructor = t.getConstructor(paramTypes);
                obj = constructor.newInstance(params);
                databases.put(key, (T) obj);
                return (T) (BaseDao<?>) obj;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
