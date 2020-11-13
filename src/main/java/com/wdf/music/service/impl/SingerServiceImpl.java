package com.wdf.music.service.impl;

import com.wdf.music.service.SingerService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SingerServiceImpl implements SingerService {

    @Resource
    SqlSession sqlSession;

    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("singer.insert", params) > 0;
    }

    @Override
    public boolean update(Map<String, Object> params) {
        return sqlSession.update("singer.update", params) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return sqlSession.delete("singer.delete", id) > 0;
    }

    @Override
    public Object selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("singer.selectByPrimaryKey", id);
    }

    @Override
    public List<Object> allSinger() {
        return sqlSession.selectList("singer.allSinger");
    }

    @Override
    public List<Object> singerOfName(String name) {
        return sqlSession.selectList("singer.singerOfName", name);
    }

    @Override
    public List<Object> singerOfSex(Integer sex) {
        return sqlSession.selectList("singer.singerOfSex", sex);
    }

    @Override
    public String singerPicOfId(Integer id) {
        return sqlSession.selectOne("singer.singerPicOfId", id);
    }
}
