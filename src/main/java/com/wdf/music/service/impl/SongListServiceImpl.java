package com.wdf.music.service.impl;

import com.wdf.music.service.SongListService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 歌单 service 实现类
 */
@Service
public class SongListServiceImpl implements SongListService {
    @Resource
    SqlSession sqlSession;
    /**
     * 增加
     *
     * @param params
     */
    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("song.list.insert", params) > 0;
    }

    /**
     * 修改
     *
     * @param params
     */
    @Override
    public boolean update(Map<String, Object> params) {
        return sqlSession.update("song.list.update", params) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return sqlSession.delete("song.list.delete", id) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Object selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("song.list.selectByPrimaryKey", id);
    }

    /**
     * 查询所有歌单
     */
    @Override
    public List<Object> allSongList() {
        return sqlSession.selectList("song.list.allSongList");
    }

    /**
     * 根据标题精确查找歌单列表
     *
     * @param title
     */
    @Override
    public List<Object> songListOfTitle(String title) {
        return sqlSession.selectList("song.list.songListOfTitle", title);
    }

    /**
     * 根据标题模糊查找歌单列表
     *
     * @param title
     */
    @Override
    public List<Object> likeTitle(String title) {
        return sqlSession.selectList("song.list.likeTitle", title);
    }

    /**
     * 根据风格模糊查找歌单列表
     *
     * @param style
     */
    @Override
    public List<Object> likeStyle(String style) {
        return sqlSession.selectList("song.list.likeStyle", style);
    }

    /**
     * 根据 ID 查询 PIC
     *
     * @param id
     */
    @Override
    public String songPicOfId(Integer id) {
        return sqlSession.selectOne("song.list.songPicOfId", id);
    }

    /**
     * 查询歌单的所有风格
     */
    @Override
    public List<Map<String, Object>> AllSongListStyle() {
        return sqlSession.selectList("song.list.AllSongListStyle");
    }
}
