package com.wdf.music.service;

import java.util.Map;

/**
 * 管理员 service 接口
 */
public interface RankService {
    /**
     * 增加
     * @param params
     * @return
     */
    boolean insert(Map<String, String> params);
    /**
     * 查总分
     */
    int selectScoreSum(long songListId);
    /**
     * 查评分人数
     */
    int selectRankSum(long songListId);
    /**
     * 计算平均分
     */
    int rankOfSongListId(long songListId);
}
