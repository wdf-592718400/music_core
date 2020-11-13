package com.wdf.music.service;

import java.util.Map;

/**
 * 管理员 service 接口
 */
public interface AdminService {
    boolean verifyPassword(Map<String, String> params);
}
