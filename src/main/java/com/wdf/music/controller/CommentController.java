package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.CommentService;
import com.wdf.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 添加评论
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("userId", request.getParameter("userId"));
        params.put("type", request.getParameter("type"));
        if(request.getParameter("songId") != null){
            params.put("songId", request.getParameter("songId"));
        }
        if(request.getParameter("songListId") != null){
            params.put("songListId", request.getParameter("songListId"));
        }
        params.put("content", request.getParameter("content"));
//        params.put("up", request.getParameter("up"));
        boolean flag = commentService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "评论成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "评论失败");
        return jsonObject;
    }

    // 修改评论
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id"));
        params.put("userId", request.getParameter("userId"));
        params.put("type", request.getParameter("type"));
        if(request.getParameter("songId") != null){
            params.put("songId", request.getParameter("songId"));
        }
        if(request.getParameter("songListId") != null){
            params.put("songListId", request.getParameter("songListId"));
        }
        params.put("content", request.getParameter("content"));
//        params.put("up", request.getParameter("up"));
        boolean flag = commentService.update(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除评论
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteComment(HttpServletRequest request){
        return commentService.delete(Integer.parseInt(request.getParameter("id")));
    }

    // 根据主键查询整个对象
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        return commentService.selectByPrimaryKey(Integer.parseInt(request.getParameter("id")));
    }

    // 查询所有评论
    @RequestMapping(value = "/allComment", method = RequestMethod.GET)
    public Object allComment(){
        return commentService.allComment();
    }

    // 查询某个歌曲下的所有评论
    @RequestMapping(value = "/commentOfSongId", method = RequestMethod.GET)
    public Object commentOfSongId(HttpServletRequest request){
        return commentService.commentOfSongId(Integer.parseInt(request.getParameter("songId")));
    }

    // 查询某个歌单下的所有评论
    @RequestMapping(value = "/commentOfSongListId", method = RequestMethod.GET)
    public Object commentOfSongListId(HttpServletRequest request){
        return commentService.commentOfSongListId(Integer.parseInt(request.getParameter("songListId")));
    }

    // 给某个评论点赞
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Object like(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        String upUserId = commentService.upUserIdOfId(Integer.parseInt(request.getParameter("id")));
        if(upUserId != null){
            for (String id : upUserId.split(", ")) {
                if(request.getParameter("upUserId").equals(id)){
                    jsonObject.put(Consts.CODE, 2);
                    jsonObject.put(Consts.MSG, "该用户已经点过赞了");
                    return jsonObject;
                }
            }
            params.put("upUserId", upUserId + ", " + request.getParameter("upUserId"));
        }else{
            params.put("upUserId", request.getParameter("upUserId"));
        }
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id"));
        params.put("up", request.getParameter("up"));
        boolean flag = commentService.update(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "点赞失败");
        return jsonObject;
    }
}
