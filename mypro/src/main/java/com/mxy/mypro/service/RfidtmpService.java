package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxy.mypro.entity.Rfidtmp;
import com.mxy.mypro.mapper.AdminMapper;
import com.mxy.mypro.mapper.RfidtmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RfidtmpService {
    private final RfidtmpMapper rfidtmpMapper;
    @Autowired
    public RfidtmpService(RfidtmpMapper rfidtmpMapper) {
        this.rfidtmpMapper = rfidtmpMapper;
    }
    public int rfidSave(Rfidtmp rfidtmp){
        int count = rfidtmpMapper.selectcount();
        System.out.println("count:"+count);
        if(count == 0){
            System.out.println("暂存rfid");
            return rfidtmpMapper.Insert(rfidtmp);
        }else if(count == 1){
            System.out.println("修改暂存rfid");
            rfidtmp.setId(Integer.valueOf(rfidtmpMapper.getID()));
            return rfidtmpMapper.UpdateById(rfidtmp);
        }else {
            System.out.println("---------error--------------数据库暂存失败");
            return 0;
        }
    }

    public List<Rfidtmp> getRfid() {
        return rfidtmpMapper.selectList(null);
    }

    public void delete(Integer id) {
        rfidtmpMapper.deleteById(id);
    }

    public void updateLocation(String rfidID, int locationID) {
        System.out.println("locationID:"+locationID);
        rfidtmpMapper.updateLocation(rfidID, locationID);
    }
}
