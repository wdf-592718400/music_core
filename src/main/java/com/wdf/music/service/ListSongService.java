package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 歌单歌曲
 */
public interface ListSongService {
    /**
     * 增加
     */
    boolean insert(Map<String, Object> params);

    /**
     * 删除
     */
    boolean delete(Map<String, Object> params);
    /**
     * 删除
     */
    boolean deleteSongList(Integer songListId);

    /**
     * 根据主键查询整个对象
     */
    Object selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单歌曲
     */
    List<Object> allListSong();

    /**
     * 根据歌单 ID 查询所有的歌曲
     */
    List<Object> listSongOfSongListId(Integer songListId);
}
