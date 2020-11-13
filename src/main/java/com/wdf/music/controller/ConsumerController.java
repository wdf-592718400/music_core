package com.wdf.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdf.music.service.ConsumerService;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    // 添加前端用户
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("username", request.getParameter("username").trim());
        params.put("password", request.getParameter("password").trim());
        params.put("sex", request.getParameter("sex").trim());
        params.put("phoneNum", request.getParameter("phoneNum").trim());
        params.put("email", request.getParameter("email").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        params.put("location", request.getParameter("location").trim());
        params.put("avator", request.getParameter("avator").trim());
        System.out.println(consumerService.verifyUsername(params.get("username").toString()));
        if (consumerService.verifyUsername(params.get("username").toString())) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "用户名已存在");
            return jsonObject;
        }
        if (consumerService.verifyPhoneNumber(params.get("phoneNum").toString())) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "手机号已存在");
            return jsonObject;
        }
        if (consumerService.verifyEmail(params.get("email").toString())) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "邮箱已存在");
            return jsonObject;
        }
        // 把生日转换成 Date 格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            params.put("birth", dateFormat.parse(request.getParameter("birth").trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean flag = consumerService.insert(params);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    // 修改前端用户
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("id", request.getParameter("id").trim());
        params.put("username", request.getParameter("username").trim());
        if (request.getParameter("password") != null) {
            params.put("password", request.getParameter("password").trim());
        }

        params.put("sex", request.getParameter("sex").trim());
        params.put("phoneNum", request.getParameter("phoneNum").trim());
        params.put("email", request.getParameter("email").trim());
        params.put("introduction", request.getParameter("introduction").trim());
        params.put("location", request.getParameter("location").trim());
        if (consumerService.verifyUsernameOfUpdate(params)) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "用户名已存在");
            return jsonObject;
        }
        if (consumerService.verifyPhoneNumberOfUpdate(params)) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "手机号已存在");
            return jsonObject;
        }
        if (consumerService.verifyEmailOfUpdate(params)) {
            jsonObject.put(Consts.CODE, 2);
            jsonObject.put(Consts.MSG, "邮箱已存在");
            return jsonObject;
        }
        // 把生日转换成 Date 格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            params.put("birth", dateFormat.parse(request.getParameter("birth").trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean flag = consumerService.update(params);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除前端用户
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id").trim());
        String avator = consumerService.getAvatorOfId(id);
        boolean flag = consumerService.delete(id);
        if (flag) {
            DeleteResources.delConsumerPic(avator);
        }
        return flag;
    }

    // 根据主键查询整个对象
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        return consumerService.selectByPrimaryKey(Integer.parseInt(request.getParameter("id").trim()));
    }

    // 查询所有前端用户
    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
    public Object allConsumer() {
        return consumerService.allConsumer();
    }

    // 验证密码
    @RequestMapping(value = "/verifyPassword", method = RequestMethod.GET)
    public Object verifyPassword(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", request.getParameter("username").trim());
        params.put("password", request.getParameter("password").trim());
        return consumerService.verifyPassword(params);
    }

    // 根据账号查询用户信息
    @RequestMapping(value = "/getByUserName", method = RequestMethod.GET)
    public Object getByUserName(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", request.getParameter("username").trim());
        return consumerService.getByAccountNumber(params);
    }

    // 更新前端用户头像
    @RequestMapping(value = "/updateConsumerPic", method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile multipartFile, @RequestParam("id") int id) {
        String avator = consumerService.getAvatorOfId(id);
        JSONObject jsonObject = new JSONObject();
        if (multipartFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        // 文件名 = 当前时间戳毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                System.getProperty("file.separator") + "consumerPic";
        // 如果文件路径不存在, 新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        // 实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        // 存储到数据库的文件地址
        String storeAvatorPath = "/img/consumerPic/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("avator", storeAvatorPath);
            boolean flag = consumerService.update(params);
            if (flag) {
                DeleteResources.delConsumerPic(avator);
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "文件上传成功");
                jsonObject.put("avator", storeAvatorPath);
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

    // 前端用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // trim 函数去掉参数中的空格
        params.put("password", request.getParameter("password").trim());
        if (consumerService.verifyUsername(request.getParameter("username").trim())) {
            params.put("username", request.getParameter("username").trim());
        } else if (consumerService.verifyPhoneNumber(request.getParameter("username").trim())) {
            params.put("phoneNum", request.getParameter("username").trim());
        } else if (consumerService.verifyEmail(request.getParameter("username").trim())) {
            params.put("email", request.getParameter("username").trim());
        } else {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名或密码错误");
            return jsonObject;
        }
        boolean flag = consumerService.verifyPassword(params);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "登录成功");
            jsonObject.put("userMsg", consumerService.getByAccountNumber(params));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "用户名或密码错误");
        return jsonObject;
    }

}
