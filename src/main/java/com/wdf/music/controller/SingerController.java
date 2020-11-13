package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.SingerService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;
    @Autowired
    private SongService songService;

    // 添加歌手
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("name", request.getParameter("name").trim());
        params.put("sex", request.getParameter("sex").trim());
        params.put("pic", request.getParameter("pic").trim());
        params.put("location", request.getParameter("location").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        // 把生日转换成 Date 格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            params.put("birth", dateFormat.parse(request.getParameter("birth").trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean flag = singerService.insert(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    // 修改歌手
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id").trim());
        params.put("name", request.getParameter("name").trim());
        params.put("sex", request.getParameter("sex").trim());
        params.put("location", request.getParameter("location").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        // 把生日转换成 Date 格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            params.put("birth", dateFormat.parse(request.getParameter("birth").trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean flag = singerService.update(params);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除歌手
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id").trim());
        List<Map<String, Object>> songMsg = songService.songOfSingerId(id);
        boolean flag = singerService.delete(id);
        if(flag){
            for (Map<String, Object> item : songMsg) {
                Object songId = item.get("id");
                Map<String, String> songUrlAndPic = songService.getSongOfId(Integer.parseInt(songId.toString()));
                DeleteResources.delSong(songUrlAndPic.get("url"));
                DeleteResources.delSongPic(songUrlAndPic.get("pic"));
                songService.delete(Integer.parseInt(songId.toString()));
            }
        }
        return flag;
    }

    // 根据主键查询整个对象
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        return singerService.selectByPrimaryKey(Integer.parseInt(request.getParameter("id").trim()));
    }

    // 查询所有歌手
    @RequestMapping(value = "/allSinger", method = RequestMethod.GET)
    public Object allSinger(){
        return singerService.allSinger();
    }

    // 根据歌手名字模糊查询整个对象
    @RequestMapping(value = "/singerOfName", method = RequestMethod.GET)
    public Object singerOfName(HttpServletRequest request){
        return singerService.singerOfName("%" + request.getParameter("name").trim() + "%");
    }

    // 根据歌手性别查询整个对象
    @RequestMapping(value = "/singerOfSex", method = RequestMethod.GET)
    public Object singerOfSex(HttpServletRequest request){
        return singerService.singerOfSex(Integer.parseInt(request.getParameter("sex").trim()));
    }

    // 更新歌手图片
    @RequestMapping(value = "/updateSingerPic", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file")MultipartFile multipartFile, @RequestParam("id")int id){
        String singerPic = singerService.singerPicOfId(id);
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
                System.getProperty("file.separator") + "singerPic";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeAvatorPath = "/img/singerPic/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("pic", storeAvatorPath);
            boolean flag = singerService.update(params);
            if(flag){
                DeleteResources.delSingerPic(singerPic);
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
}
