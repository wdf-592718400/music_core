package com.wdf.music.service;

import java.util.List;
import java.util.Map;

/**
 * 歌手 service
 */
public interface ConsumerService {
    /**
     * 增加
     * @param params
     * @return
     */
    boolean insert(Map<String, Object> params);

    /**
     * 修改
     * @param params
     * @return
     */
    boolean update(Map<String, Object> params);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     * @param id
     * @return
     */
    Map<String, Object> selectByPrimaryKey(Integer id);

    /**
     * 查询所有用户
     * @return
     */
    List<Object> allConsumer();

    /**
     * 验证密码
     * @param params
     * @return
     */
    boolean verifyPassword(Map<String, Object> params);

    /**
     * 判断用户名是否存在
     */
    boolean verifyUsername(String username);

    /**
     * 判断手机号是否存在
     */
    boolean verifyPhoneNumber(String phoneNum);

    /**
     * 判断邮箱是否存在
     */
    boolean verifyEmail(String email);

    /**
     * 判断用户名是否存在
     */
    boolean verifyUsernameOfUpdate(Map<String, Object> params);

    /**
     * 判断手机号是否存在
     */
    boolean verifyPhoneNumberOfUpdate(Map<String, Object> params);

    /**
     * 判断邮箱是否存在
     */
    boolean verifyEmailOfUpdate(Map<String, Object> params);

    /**
     * 根据账号查询用户信息
     * @param params
     * @return
     */
    Map<String, Object> getByAccountNumber(Map<String, Object> params);

    /**
     * 根据 ID 查询头像信息
     * @return
     */
    String getAvatorOfId(Integer id);
}
