package com.manonline.examples.jndi.service.service;

/**
 * Created by davidqi on 2/22/17.
 */
public interface DBService {
    //获取数据库位置
    String getLocation();

    //获取数据库状态
    String getState();

    //访问数据库
    void accessDB();

    //设置数据库信息
    void setProperty(int index, String property);
}
