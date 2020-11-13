package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.ListSongService;
import com.wdf.music.service.SongListService;
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
import java.util.Map;

@RestController
@RequestMapping("/song/list")
public class SongListController {
    @Autowired
    private SongListService songListService;
    @Autowired
    private ListSongService listSongService;

    // 添加歌单
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("title", request.getParameter("title").trim());
        params.put("pic", request.getParameter("pic").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        params.put("style", request.getParameter("style").trim());
        boolean flag = songListService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    // 修改歌单
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id").trim());
        params.put("title", request.getParameter("title").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        params.put("style", request.getParameter("style").trim());
        boolean flag = songListService.update(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除歌单
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id").trim());
        String songListPic = songListService.songPicOfId(id);
        boolean flag = songListService.delete(id);
        if(flag){
            listSongService.deleteSongList(id);
            DeleteResources.delSongListPic(songListPic);
        }
        return flag;
    }

    // 根据主键查询整个对象
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        return songListService.selectByPrimaryKey(Integer.parseInt(request.getParameter("id").trim()));
    }

    // 查询所有歌单
    @RequestMapping(value = "/allSongList", method = RequestMethod.GET)
    public Object allSongList(){
        return songListService.allSongList();
    }

    // 根据歌单标题精确查询整个对象
    @RequestMapping(value = "/songListOfTitle", method = RequestMethod.GET)
    public Object songListOfTitle(HttpServletRequest request){
        return songListService.songListOfTitle(request.getParameter("title").trim());
    }

    // 根据歌单标题模糊查询整个对象
    @RequestMapping(value = "/likeTitle", method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request){
        return songListService.likeTitle("%" + request.getParameter("title").trim() + "%");
    }

    // 根据歌单风格模糊查询整个对象
    @RequestMapping(value = "/likeStyle", method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        return songListService.likeStyle("%" + request.getParameter("style").trim() + "%");
    }

    // 更新歌单图片
    @RequestMapping(value = "/updateSongListPic", method = RequestMethod.POST)
    public Object updateSongListPic(@RequestParam("file")MultipartFile multipartFile, @RequestParam("id")int id){
        String songListPic = songListService.songPicOfId(id);
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
                System.getProperty("file.separator") + "songListPic";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeAvatorPath = "/img/songListPic/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("pic", storeAvatorPath);
            boolean flag = songListService.update(params);
            if(flag){
                DeleteResources.delSongListPic(songListPic);
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

    // 查询歌单的所有风格分类
    @RequestMapping(value = "/AllSongListStyle", method = RequestMethod.GET)
    public Object AllSongListStyle(){
        return songListService.AllSongListStyle();
    }
}
