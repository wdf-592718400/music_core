package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.ListSongService;
import com.wdf.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 歌曲管理 controller
 */
@RestController
@RequestMapping("/list/song")
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    // 添加歌曲
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        params.put("songId", request.getParameter("songId").trim()); // 歌曲 ID
        params.put("songListId", request.getParameter("songListId").trim()); // 歌单 ID
        boolean flag = listSongService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "保存失败");
        return jsonObject;
    }

    // 根据歌单 ID 查询歌曲
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object listSongOfSongListId(HttpServletRequest request) {
        String songListId = request.getParameter("songListId");
        return listSongService.listSongOfSongListId(Integer.parseInt(songListId));
    }

    // 删除歌曲
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(HttpServletRequest request){
        System.out.println(request.getParameter("songId").trim());
        System.out.println(request.getParameter("songListId").trim());
        Map<String, Object> params = new HashMap<>();
        params.put("songId", request.getParameter("songId").trim()); // 歌曲 ID
        params.put("songListId", request.getParameter("songListId").trim()); // 歌单 ID
        return listSongService.delete(params);
    }

}
