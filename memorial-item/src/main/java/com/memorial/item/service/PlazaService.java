package com.memorial.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.enums.ExceptionEnum;
import com.memorial.common.exception.LyException;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.PlazaMapper;
import com.memorial.mapper.PlazaMsgImgMapper;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.Plaza;
import com.memorial.pojo.PlazaMsgImg;
import com.memorial.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:47
 */
@Service
public class PlazaService {

    @Autowired
    private PlazaMapper plazaMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlazaMsgImgMapper plazaMsgImgMapper;

    /**
     * 广场查询
     *
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Plaza> queryPlazasList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Plaza.class);
        example.setOrderByClause(" time desc ");
        List<Plaza> plazas = plazaMapper.selectByExample(example);
        for (Plaza plaza : plazas) {
            User user = userMapper.selectByPrimaryKey(plaza.getUserId());
            /*List<PlazaMsgImg> plazaMsgImgs = plazaMsgImgMapper.selectAll();*/
            plaza.setImg(user.getImg());
            plaza.setLoginName(user.getName());
            List<String> list = new ArrayList<>();
            String[] split = plaza.getName().split("-");
            if (split.length > 0) {
                for (String s : split) {
                    String[] split1 = s.split(",");
                    String name = split1[0] + "·" + split1[1];
                    list.add(name);
                }
            }
            plaza.setList(list);
        }
        return new PageInfo<>(plazas);
    }

    /**
     * 创建祭祀
     *
     * @param name
     * @param text
     */
    public void creationPlaza(String name, String text, String id) {
        if (StringUtils.isEmpty(text)){
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        Plaza plaza = new Plaza();
        plaza.setId(idWorker.nextId() + "");
        plaza.setUserId(id);
        plaza.setTime(new Date());
        plaza.setText(text);
        plaza.setName(name);
        plazaMapper.insert(plaza);
    }
}
