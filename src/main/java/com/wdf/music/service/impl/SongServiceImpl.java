package com.wdf.music.service.impl;

import com.wdf.music.service.SongService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SongServiceImpl implements SongService {

    @Resource
    SqlSession sqlSession;

    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("song.insert", params) > 0;
    }

    @Override
    public boolean update(Map<String, Object> params) {
        return sqlSession.update("song.update", params) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return sqlSession.delete("song.delete", id) > 0;
    }

    @Override
    public Object selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("song.selectByPrimaryKey", id);
    }

    @Override
    public List<Object> allSong() {
        return sqlSession.selectList("song.allSong");
    }

    @Override
    public List<Object> songOfName(String name) {
        return sqlSession.selectList("song.songOfName", name);
    }

    @Override
    public List<Object> likeSongOfName(String name) {
        return sqlSession.selectList("song.likeSongOfName", "%" + name + "%");
    }

    @Override
    public List<Object> allSongOfSelect(Map<String, Object> params) {
        return sqlSession.selectList("song.allSongOfSelect", params);
    }

    @Override
    public List<Map<String, Object>> songOfSingerId(Integer singerId) {
        return sqlSession.selectList("song.songOfSingerId", singerId);
    }

    /**
     * 根据 ID 查询
     *
     * @param id
     */
    @Override
    public Map<String, String> getSongOfId(Integer id) {
        return sqlSession.selectOne("song.getSongOfId", id);
    }
}
