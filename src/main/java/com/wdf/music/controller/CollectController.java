package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.CollectService;
import com.wdf.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    // 添加收藏
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", request.getParameter("userId"));
        params.put("type", request.getParameter("type"));
        params.put("songId", request.getParameter("songId"));
        params.put("songListId", request.getParameter("songListId"));
        if(request.getParameter("songId") == null || request.getParameter("songId").equals("")){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "收藏失败");
            return jsonObject;
        }
        if(collectService.existSongId(params)){
            if(collectService.delete(params)){
                jsonObject.put(Consts.CODE, 2);
                jsonObject.put(Consts.MSG, "取消收藏成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 3);
            jsonObject.put(Consts.MSG, "取消收藏失败");
            return jsonObject;
        }
        boolean flag = collectService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "收藏失败");
        return jsonObject;
    }

     // 删除收藏
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", request.getParameter("userId"));
        params.put("songId", request.getParameter("songId"));
        return collectService.delete(params);
    }

    // 查询所有收藏
    @RequestMapping(value = "/allCollect", method = RequestMethod.GET)
    public Object allCollect(){
        return collectService.allCollect();
    }

    // 查询某个用户的收藏列表
    @RequestMapping(value = "/collectOfUserId", method = RequestMethod.GET)
    public Object collectOfUserId(HttpServletRequest request){
        return collectService.collectOfUserId(Integer.parseInt(request.getParameter("userId")));
    }
}
