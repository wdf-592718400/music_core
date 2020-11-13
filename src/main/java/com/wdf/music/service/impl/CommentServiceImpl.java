package com.wdf.music.service.impl;

import com.wdf.music.service.CommentService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    SqlSession sqlSession;

    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("comment.insert", params) > 0;
    }

    @Override
    public boolean update(Map<String, Object> params) {
        return sqlSession.update("comment.update", params) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return sqlSession.delete("comment.delete", id) > 0;
    }

    @Override
    public Object selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("comment.selectByPrimaryKey", id);
    }

    @Override
    public List<Object> allComment() {
        return sqlSession.selectList("comment.allComment");
    }

    @Override
    public List<Object> commentOfSongId(Integer songId) {
        return sqlSession.selectList("comment.commentOfSongId", songId);
    }

    @Override
    public List<Object> commentOfSongListId(Integer songListId) {
        return sqlSession.selectList("comment.commentOfSongListId", songListId);
    }

    @Override
    public String upUserIdOfId(Integer id) {
        return sqlSession.selectOne("comment.upUserIdOfId", id);
    }
}
