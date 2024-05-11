package com.mxy.mypro.service;

import com.mxy.mypro.entity.BuyData;
import com.mxy.mypro.mapper.BuyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyDataService {
    private final BuyDataMapper buyDataMapper;

    @Autowired
    public BuyDataService(BuyDataMapper buyDataMapper) {
        this.buyDataMapper = buyDataMapper;
    }


    public List<BuyData> getData(int elderid) {
        return buyDataMapper.getData(elderid);
    }
}
