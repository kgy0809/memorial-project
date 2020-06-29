package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.mapper.PlazaMapper;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:05
 */
@Controller
@RequestMapping("plaza")
public class PlazaController {

    @Autowired
    private PlazaMapper plazaMapper;
    @Autowired
    private UserMapper userMapper;


    @RequestMapping(method = RequestMethod.GET)
    public String plaza() {
        return "system/plaza/plaza_list";
    }

    /**
     * 查询全部
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public PageResultDTO<Plaza> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Plaza.class);
        example.setOrderByClause(" time desc ");
        List<Plaza> plazas = plazaMapper.selectByExample(example);
        if (!plazas.isEmpty()){
            for (Plaza plaza : plazas) {
                StringBuffer stringBuffer = new StringBuffer();
                User user = userMapper.selectByPrimaryKey(plaza.getUserId());
                plaza.setLoginName(user.getName());
                String[] split = plaza.getName().split("-");
                for (String s : split) {
                    String[] split1 = s.split(",");
                    stringBuffer.append(split1[0] + "-" + split1[1]).append(",");
                }
                plaza.setImg(stringBuffer.deleteCharAt(stringBuffer.length() -1).toString());
            }
        }
        PageInfo<Plaza> sysBannerPageInfo = new PageInfo<>(plazas);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), sysBannerPageInfo.getList());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResultDTO delete(@PathVariable("id") String id) {
        plazaMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查看详情
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, Map<String, Object> map) {
        Plaza plaza = plazaMapper.selectByPrimaryKey(id);
        map.put("plaza", plaza);
        return "system/plaza/plaza_view";
    }

}
