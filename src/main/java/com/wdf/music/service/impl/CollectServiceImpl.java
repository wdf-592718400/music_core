package com.wdf.music.service.impl;

import com.wdf.music.service.CollectService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    SqlSession sqlSession;
    /**
     * 增加
     *
     * @param params
     */
    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("collect.insert", params) > 0;
    }

    /**
     * 删除
     *
     * @param params
     */
    @Override
    public boolean delete(Map<String, Object> params) {
        return sqlSession.delete("collect.delete", params) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean deleteById(Integer id) {
        return sqlSession.delete("collect.deleteById", id) > 0;
    }

    /**
     * 查询所有收藏
     *
     */
    @Override
    public List<Map<String, Object>> allCollect() {
        return sqlSession.selectList("collect.allCollect");
    }

    /**
     * 查询某个用户的收藏列表
     *
     * @param userId
     */
    @Override
    public List<Map<String, Object>> collectOfUserId(Integer userId) {
        return sqlSession.selectList("collect.collectOfUserId", userId);
    }

    /**
     * 查询某个用户是否收藏了某个歌曲
     *
     * @param params
     */
    @Override
    public boolean existSongId(Map<String, Object> params) {
        return (int)sqlSession.selectOne("collect.existSongId", params) > 0;
    }
}
