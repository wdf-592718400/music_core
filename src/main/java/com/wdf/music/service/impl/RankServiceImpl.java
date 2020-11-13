package com.wdf.music.service.impl;

import com.wdf.music.service.RankService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RankServiceImpl implements RankService {

    @Resource
    SqlSession sqlSession;


    /**
     * 增加
     *
     * @param params
     * @return
     */
    @Override
    public boolean insert(Map<String, String> params) {
        return sqlSession.insert("rank.insert", params) > 0;
    }

    /**
     * 查总分
     *
     * @param songListId
     */
    @Override
    public int selectScoreSum(long songListId) {
        return sqlSession.selectOne("rank.selectScoreSum", songListId);
    }

    /**
     * 查评分人数
     *
     * @param songListId
     */
    @Override
    public int selectRankSum(long songListId) {
        return sqlSession.selectOne("rank.selectRankSum", songListId);
    }

    /**
     * 计算平均分
     *
     * @param songListId
     */
    @Override
    public int rankOfSongListId(long songListId) {
        int rankNum = sqlSession.selectOne("rank.selectRankSum", songListId);
        if(rankNum == 0){
            return 5;
        }
        return (int)sqlSession.selectOne("rank.selectScoreSum", songListId) / rankNum;
    }
}
