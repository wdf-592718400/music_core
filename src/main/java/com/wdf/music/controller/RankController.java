package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.RankService;
import com.wdf.music.utils.Consts;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RankController {
    @Resource
    private RankService rankService;

    /**
     * 新增评价
     * @param request
     * @return
     */
    @RequestMapping(value = "/rank/add", method = RequestMethod.POST)
    public Object insert(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("songListId", request.getParameter("songListId"));
        params.put("consumerId", request.getParameter("consumerId"));
        params.put("score", request.getParameter("score"));
        boolean flag = rankService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "评价失败!");
        return jsonObject;
    }

    /**
     * 计算平均分
     * @param request
     * @return
     */
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Object rankOfSongListId(HttpServletRequest request){
        return rankService.rankOfSongListId(Long.parseLong(request.getParameter("songListId")));
    }
}
