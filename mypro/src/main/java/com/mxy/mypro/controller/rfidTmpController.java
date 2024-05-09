package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Rfidtmp;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.RfidtmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class rfidTmpController {
    private final RfidtmpService rfidtmpService;
    @Autowired
    public rfidTmpController(RfidtmpService rfidtmpService) {
        this.rfidtmpService = rfidtmpService;
    }
    @GetMapping("/info/rfidtmp")
    public List<Rfidtmp> getRfidtmp() {
        List<Rfidtmp> list = rfidtmpService.getRfid();
        if (list != null && !list.isEmpty()) {
            // 表中有数据

            rfidtmpService.delete(list.get(0).getId());
        } else {
            // 表中没有数据
            System.out.println("表中没有数据");
        }
        return list;
    }

}
