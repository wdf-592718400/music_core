package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 歌曲 service
 */
public interface SongService {
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
     * 查询所有歌曲
     */
    List<Object> allSong();

    /**
     * 根据歌名精确查询列表
     */
    List<Object> songOfName(String name);

    /**
     * 根据歌名模糊查询列表
     */
    List<Object> likeSongOfName(String name);

    /**
     * 查询不包括指定 ID 的歌曲
     */
    List<Object> allSongOfSelect(Map<String, Object> params);

    /**
     * 根据歌手 ID 查询
     */
    List<Map<String, Object>> songOfSingerId(Integer singerId);

    /**
     * 根据 ID 查询
     */
    Map<String, String> getSongOfId(Integer id);
}
