package com.wdf.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位地址配置
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 歌手头像地址定位
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                        System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
        );
        // 歌曲图像地址定位
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                        System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator")
        );
        // 歌单图像地址定位
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                        System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator")
        );
        // 歌曲地址定位
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "song" + System.getProperty("file.separator")
        );
        // 前端用户图像地址定位
        registry.addResourceHandler("/img/consumerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                        System.getProperty("file.separator") + "consumerPic" + System.getProperty("file.separator")
        );

    }

}
