package com.wdf.music.service.impl;

import com.wdf.music.service.ListSongService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ListSongServiceImpl implements ListSongService {
    @Resource
    SqlSession sqlSession;

    /**
     * 增加
     *
     * @param params
     */
    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("list.song.insert", params) > 0;
    }

    /**
     * 删除
     *
     * @param songListId
     */
    @Override
    public boolean deleteSongList(Integer songListId) {
        return sqlSession.delete("list.song.deleteSongList", songListId) > 0;
    }

    /**
     * 删除
     *
     * @param params
     */
    @Override
    public boolean delete(Map<String, Object> params) {
        return sqlSession.delete("list.song.delete", params) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Object selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("list.song.selectByPrimaryKey", id);
    }

    /**
     * 查询所有歌单歌曲
     */
    @Override
    public List<Object> allListSong() {
        return sqlSession.selectList("list.song.allSong");
    }

    /**
     * 根据歌单 ID 查询所有的歌曲
     *
     * @param songListId
     */
    @Override
    public List<Object> listSongOfSongListId(Integer songListId) {
        return sqlSession.selectList("list.song.listSongOfSongListId", songListId);
    }
}
