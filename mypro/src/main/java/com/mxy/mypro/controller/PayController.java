package com.mxy.mypro.controller;

import com.mxy.mypro.entity.PayInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PayController {

    @PostMapping("/info/addpay")
    public String addPay(@RequestBody List<PayInfo> payInfo) {

        return "";
    }
}
