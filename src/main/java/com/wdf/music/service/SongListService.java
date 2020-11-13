package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 歌单 service 接口
 */
public interface SongListService {
    /**
     * 增加
     */
    boolean insert(Map<String, Object> params);

    /**
     * 修改
     */
    boolean update(Map<String, Object> params);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    Object selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单
     */
    List<Object> allSongList();

    /**
     * 根据标题精确查找歌单列表
     */
    List<Object> songListOfTitle(String title);

    /**
     * 根据标题模糊查找歌单列表
     */
    List<Object> likeTitle(String title);

    /**
     * 根据风格模糊查找歌单列表
     */
    List<Object> likeStyle(String style);

    /**
     * 根据 ID 查询 PIC
     */
    String songPicOfId(Integer id);

    /**
     * 查询歌单的所有风格
     */
    List<Map<String, Object>> AllSongListStyle();
}
