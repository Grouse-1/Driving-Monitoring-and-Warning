package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.seller;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SellerInfoController {
    private final SellerService sellerService;
    @Autowired
    public SellerInfoController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/info/seller")
    public PaginationResult<seller> GetAdminInfo(String name, String page, String pageSize) {
        int Ipage = Integer.parseInt(page);
        int IPageSize = Integer.parseInt(pageSize);
        System.out.println("11111"+name+Ipage+IPageSize);
        PaginationResult<seller> list;
        if(name.equals("")){
            list = sellerService.GetPage(Ipage,IPageSize);
            System.out.println("22222"+list);
            return list;
        }
        else {
            list = sellerService.selectByName(name, Ipage, IPageSize);
            return list;
        }
        //return null;

    }

    @PostMapping("/info/addseller")
    public int AddAdmin(@RequestBody seller mseller) {
        int result = sellerService.insert(mseller);
        System.out.println("add:"+result);
        return result;
    }

    @PostMapping("/info/editseller")
    public int EditAdmin(@RequestBody seller mseller) {
        System.out.println(mseller.toString());
        return sellerService.editseller(mseller);
    }

    @PostMapping("/info/delseller")
    public int DelAdmin(@RequestBody Map id) {
//        int Iid = Integer.parseInt(id);
        //System.out.println("del:"+(Integer) id.get("id"));
        //return  0;
        return sellerService.deleteseller((Integer)id.get("id"));
    }
}
