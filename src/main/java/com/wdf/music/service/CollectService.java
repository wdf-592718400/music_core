package com.wdf.music.service;

import java.util.List;
import java.util.Map;


public interface CollectService {
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
    boolean deleteById(Integer id);
    /**
     * 查询所有收藏
     */
    List<Map<String, Object>> allCollect();
    /**
     * 查询某个用户的收藏列表
     */
    List<Map<String, Object>> collectOfUserId(Integer userId);
    /**
     * 查询某个用户是否收藏了某个歌曲
     */
    boolean existSongId(Map<String, Object> params);


}
