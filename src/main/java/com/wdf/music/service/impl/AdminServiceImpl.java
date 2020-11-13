package com.wdf.music.service.impl;

import com.wdf.music.service.AdminService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    SqlSession sqlSession;

    @Override
    public boolean verifyPassword(Map<String, String> params) {
        return (int)sqlSession.selectOne("admin.verifyPassword", params) > 0;
    }

}
