package com.wdf.music.utils;

import java.io.File;

public class DeleteResources {
    /**
     * 删除歌手图像
     */
    public static void delSingerPic(String url){
        if(!url.equals("/img/singerPic/singer.png")){
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                    System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator") +
                    url.split("/")[url.split("/").length - 1];
            File f = new File(filePath);
            boolean res = f.delete();
            System.out.println("filePath-->" + filePath);
            System.out.println("删除歌手头像-->" + (res ? "成功" : "失败"));
        }
    }

    /**
     * 删除歌曲
     */
    public static void delSong(String url){
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song" + System.getProperty("file.separator") +
                    url.split("/")[url.split("/").length - 1];
            File f = new File(filePath);
            boolean res = f.delete();
            System.out.println("filePath-->" + filePath);
            System.out.println("删除歌曲-->" + (res ? "成功" : "失败"));
    }

    /**
     * 删除歌曲图片
     */
    public static void delSongPic(String url){
        if(!url.equals("/img/songPic/song.jpg")){
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                    System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator") +
                    url.split("/")[url.split("/").length - 1];
            File f = new File(filePath);
            boolean res = f.delete();
            System.out.println("filePath-->" + filePath);
            System.out.println("删除歌曲图片-->" + (res ? "成功" : "失败"));
        }
    }

    /**
     * 删除歌单图片
     */
    public static void delSongListPic(String url){
        if(!url.equals("/img/songListPic/songList.png")){
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                    System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator") +
                    url.split("/")[url.split("/").length - 1];
            File f = new File(filePath);
            boolean res = f.delete();
            System.out.println("filePath-->" + filePath);
            System.out.println("删除歌手头像-->" + (res ? "成功" : "失败"));
        }
    }

    /**
     * 删除用户图像
     */
    public static void delConsumerPic(String url){
        if(!url.equals("/img/consumerPic/consumer.png")){
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                    System.getProperty("file.separator") + "consumerPic" + System.getProperty("file.separator") +
                    url.split("/")[url.split("/").length - 1];
            File f = new File(filePath);
            boolean res = f.delete();
            System.out.println("filePath-->" + filePath);
            System.out.println("删除歌手头像-->" + (res ? "成功" : "失败"));
        }
    }
}
