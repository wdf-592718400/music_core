package com.wdf.music.service.impl;

import com.wdf.music.service.ConsumerService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Resource
    SqlSession sqlSession;
    /**
     * 增加
     *
     * @param params
     * @return
     */
    @Override
    public boolean insert(Map<String, Object> params) {
        return sqlSession.insert("consumer.insert", params) > 0;
    }

    /**
     * 修改
     *
     * @param params
     * @return
     */
    @Override
    public boolean update(Map<String, Object> params) {
        return sqlSession.update("consumer.update", params) > 0;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        return sqlSession.delete("consumer.delete", id) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> selectByPrimaryKey(Integer id) {
        return sqlSession.selectOne("consumer.selectByPrimaryKey", id);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<Object> allConsumer() {
        return sqlSession.selectList("consumer.allConsumer");
    }

    /**
     * 验证密码
     *
     * @param params
     * @return
     */
    @Override
    public boolean verifyPassword(Map<String, Object> params) {
        return (int)sqlSession.selectOne("consumer.verifyPassword", params) > 0;
    }

    /**
     * 判断用户名是否存在
     *
     * @param username
     */
    @Override
    public boolean verifyUsername(String username) {
        return (int)sqlSession.selectOne("consumer.verifyUsername", username) > 0;
    }

    /**
     * 判断手机号是否存在
     *
     * @param phoneNum
     */
    @Override
    public boolean verifyPhoneNumber(String phoneNum) {
        return (int)sqlSession.selectOne("consumer.verifyPhoneNum", phoneNum) > 0;
    }

    /**
     * 判断邮箱是否存在
     *
     * @param email
     */
    @Override
    public boolean verifyEmail(String email) {
        return (int)sqlSession.selectOne("consumer.verifyEmail", email) > 0;
    }

    /**
     * 判断用户名是否存在
     *
     * @param params
     */
    @Override
    public boolean verifyUsernameOfUpdate(Map<String, Object> params) {
        return (int)sqlSession.selectOne("consumer.verifyUsernameOfUpdate", params) > 0;
    }

    /**
     * 判断手机号是否存在
     *
     * @param params
     */
    @Override
    public boolean verifyPhoneNumberOfUpdate(Map<String, Object> params) {
        return (int)sqlSession.selectOne("consumer.verifyPhoneNumOfUpdate", params) > 0;
    }

    /**
     * 判断邮箱是否存在
     *
     * @param params
     */
    @Override
    public boolean verifyEmailOfUpdate(Map<String, Object> params) {
        return (int)sqlSession.selectOne("consumer.verifyEmailOfUpdate", params) > 0;
    }

    /**
     * 根据 ID 查询头像信息
     *
     * @param id
     * @return
     */
    @Override
    public String getAvatorOfId(Integer id) {
        return sqlSession.selectOne("consumer.getAvatorOfId", id);
    }

    /**
     * 根据账号查询用户信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getByAccountNumber(Map<String, Object> params) {
        return sqlSession.selectOne("consumer.getByAccountNumber", params);
    }
}
