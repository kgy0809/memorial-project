package com.memorial.greenroom.controller;

import com.memorial.common.utils.QiniuUtil;
import com.memorial.mapper.MusicMapper;
import com.memorial.pojo.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/5 16:10
 */
@Controller
@RequestMapping("music")
public class MusicController {

    @Autowired
    private MusicMapper musicMapper;
    @Value("${yhbr.openid.ACCESSKEY}")
    private String ACCESSKEY;
    @Value("${yhbr.openid.SECRETKEY}")
    private String SECRETKEY;
    @Value("${yhbr.openid.BUCKETNAME}")
    private String BUCKETNAME;
    @Value("${yhbr.openid.FILEDOMAIN}")
    private String FILEDOMAIN;

    @GetMapping
    public String music(){
        return "system/music/add_music";
    }

    @PostMapping("insert")
    @ResponseBody
    public Map<String, Object> updateImage(@RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        if (file.getSize() == 0) {
            map.put("msg", "请添加图片！");
            map.put("code", 500);
        }
        String url = addImage(file);
        Music music = new Music();
        music.setId(1);
        music.setMusic(url);
        musicMapper.updateByPrimaryKeySelective(music);
        map.put("src", url);
        return map;
    }

    /**
     * 轮播图上传到七牛云
     * @param file
     * @return
     */
    public String addImage(MultipartFile file) {
        try {
            return new QiniuUtil(ACCESSKEY,SECRETKEY,BUCKETNAME,FILEDOMAIN).upload(UUID.randomUUID().toString().replace("-","") + ".mp3", file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
