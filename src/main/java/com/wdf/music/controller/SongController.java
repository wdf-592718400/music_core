package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.SongService;
import com.wdf.music.utils.Consts;
import com.wdf.music.utils.DeleteResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 歌曲管理 controller
 */
@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    // 添加歌曲
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile multipartFile){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        params.put("singerId", request.getParameter("singerId").trim()); // 歌手 ID
        params.put("name", request.getParameter("name").trim()); // 歌名
        params.put("introduction", request.getParameter("introduction").trim()); // 简介
        params.put("pic", "/img/songPic/song.jpg"); // 默认图片
        params.put("lyric", request.getParameter("lyric").trim()); // 歌词
        // 上传歌曲文件
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "歌曲上传失败");
            return jsonObject;
        }
        // 文件名 = 当前时间戳毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeUrlPath = "/song/" + fileName;
        try {
            multipartFile.transferTo(dest);
            params.put("url", storeUrlPath);
            boolean flag = songService.insert(params);
            if(flag){
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "保存成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败");
            jsonObject.put("avator", storeUrlPath);
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败" + e.getMessage());
        }
        return jsonObject;
    }

    // 根据歌手 ID 查询歌曲
    @RequestMapping(value = "/singer/detail", method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request) {
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    // 修改歌曲
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id").trim());
        params.put("name", request.getParameter("name").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        params.put("lyric", request.getParameter("lyric").trim());

        boolean flag = songService.update(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除歌曲
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSong(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id").trim());
        Map<String, String> songMsg = songService.getSongOfId(id);
        boolean flag = songService.delete(id);
        if(flag){
            DeleteResources.delSong(songMsg.get("url"));
            DeleteResources.delSongPic(songMsg.get("pic"));
        }
        return flag;
    }

    // 更新歌曲图片
    @RequestMapping(value = "/updateSongPic", method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file")MultipartFile multipartFile, @RequestParam("id")int id){
        Map<String, String> songMsg = songService.getSongOfId(id);
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        // 文件名 = 当前时间戳毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                System.getProperty("file.separator") + "songPic";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeAvatorPath = "/img/songPic/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("pic", storeAvatorPath);
            boolean flag = songService.update(params);
            if(flag){
                DeleteResources.delSingerPic(songMsg.get("pic"));
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "文件上传成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败" + e.getMessage());
        }
        return jsonObject;
    }

    // 更新歌曲
    @RequestMapping(value = "/updateSongUrl", method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file")MultipartFile multipartFile, @RequestParam("id")int id){
        Map<String, String> songMsg = songService.getSongOfId(id);
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        // 文件名 = 当前时间戳毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeAvatorPath = "/song/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("url", storeAvatorPath);
            boolean flag = songService.update(params);
            if(flag){
                DeleteResources.delSong(songMsg.get("url"));
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "文件上传成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败" + e.getMessage());
        }
        return jsonObject;
    }

    // 根据歌曲 ID 查询歌曲
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request) {
        String singerId = request.getParameter("id");
        return songService.selectByPrimaryKey(Integer.parseInt(singerId));
    }

    // 根据歌曲名字精确查询歌曲
    @RequestMapping(value = "/songOfSongName", method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request) {
        String songName = request.getParameter("songName");
        return songService.songOfName(songName);
    }

    // 根据歌曲名字模糊查询歌曲
    @RequestMapping(value = "/likeSongOfName", method = RequestMethod.GET)
    public Object likeSongOfName(HttpServletRequest request) {
        String songName = request.getParameter("songName");
        return songService.likeSongOfName(songName);
    }

    // 查询不包含指定 ID 的歌曲
    @RequestMapping(value = "/allSongOfSelect", method = RequestMethod.GET)
    public Object allSongOfSelect(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        if(!request.getParameter("id").equals("")) {
            String[] ids = request.getParameter("id").split(", ");
            params.put("id", ids);
        }else{
            params.put("id", null);
        }
        return songService.allSongOfSelect(params);
    }

    // 查询所有歌曲
    @RequestMapping(value = "/allSong", method = RequestMethod.GET)
    public Object allSong() {
        return songService.allSong();
    }

}
