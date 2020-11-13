package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 歌手 service
 */
public interface SingerService {
    // 增加
    boolean insert(Map<String, Object> params);
    // 修改
    boolean update(Map<String, Object> params);
    // 删除
    boolean delete(Integer id);
    // 根据主键查询整个对象
    Object selectByPrimaryKey(Integer id);
    // 查询所有歌手
    List<Object> allSinger();
    // 根据歌手名字模糊查询列表
    List<Object> singerOfName(String name);
    // 根据性别查询
    List<Object> singerOfSex(Integer sex);
    // 根据 ID 查询 PIC
    String singerPicOfId(Integer id);
}
