package com.memorial.item.service;

import com.memorial.mapper.*;
import com.memorial.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 8:49
 */
@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ShareMapper shareMapper;
    @Autowired
    private MusicMapper musicMapper;
    @Autowired
    private DetailsShareMapper detailsShareMapper;
    @Autowired
    private BannerAncestralMapper bannerAncestralMapper;
    @Autowired
    private BannerPlazaMapper bannerPlazaMapper;

    public List<Banner> queryBannerList() {
        return bannerMapper.selectAll();
    }

    public List<Share> queryShareList() {
        return shareMapper.selectAll();
    }

    public List<Music> queryMusicList() {
        return musicMapper.selectAll();
    }

    public DetailsShare shareDetailsList() {
        return detailsShareMapper.selectAll().get(0);
    }

    public DetailsShare shareDetailsList1() {
        return detailsShareMapper.selectAll().get(1);
    }

    public DetailsShare shareDetailsList2() {
        return detailsShareMapper.selectAll().get(2);
    }

    public List<BannerPlaza> queryBanner1List() {
        return bannerPlazaMapper.selectAll();
    }

    public List<BannerAncestral> queryBanner2List() {
        return bannerAncestralMapper.selectAll();
    }
}
