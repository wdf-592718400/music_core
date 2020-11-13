package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 评论 service
 */
public interface CommentService {
    // 增加
    boolean insert(Map<String, Object> params);
    // 修改
    boolean update(Map<String, Object> params);
    // 删除
    boolean delete(Integer id);
    // 根据主键查询整个对象
    Object selectByPrimaryKey(Integer id);
    // 查询所有评论
    List<Object> allComment();
    // 查询某个歌曲下的所有评论
    List<Object> commentOfSongId(Integer songId);
    // 查询某个歌单下的所有评论
    List<Object> commentOfSongListId(Integer songListId);
    // 查询点赞用户ID
    String upUserIdOfId(Integer id);
}
