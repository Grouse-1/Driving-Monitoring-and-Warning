package com.mxy.mypro.controller;

import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.service.FamilyService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class familyInfoController {

    private final FamilyService familyService;
    @Autowired
    public familyInfoController(FamilyService familyService) {
        this.familyService = familyService;
    }
    @GetMapping("/info/family")
    public PaginationResult<Family> GetFamilyInfo(String name, String page, String pageSize) {
        int Ipage = Integer.parseInt(page);
        int IPageSize = Integer.parseInt(pageSize);
        PaginationResult<Family> list;
        if(name.equals("")){
            list = familyService.getFamily(Ipage, IPageSize);
            return list;
        }else {
            System.out.println("name"+name);
            list = familyService.selectByName(name, Ipage, IPageSize);
            return list;
        }

//        System.out.println("11111"+name+Ipage+IPageSize);
//        if(name.equals("")){
//            PaginationResult<family> list = familyService.GetPage(Ipage,IPageSize);
//            System.out.println("22222"+list);
//            return list;
//        }
//        else {
//            List<family> list = familyService.selectByName(name);
//            System.out.println("22222"+list);
//            return new PaginationResult<>(list, 1, 1);
//        }
        //return null;

    }

    @PostMapping("/info/addfamily")
    public int AddFamily(@RequestBody Family mfamily) {
        int result = familyService.Insert(mfamily);
        System.out.println("add:"+result);
        return result;
    }
//
    @PostMapping("/info/editfamily")
    public int EditFamily(@RequestBody Family mfamily) {
        System.out.println(mfamily.toString());
        return familyService.editfamily(mfamily);
    }
    @PostMapping("/info/delfamily")
    public int DelFamily(@RequestBody Map id) {
//        int Iid = Integer.parseInt(id);
        //System.out.println("del:"+(Integer) id.get("id"));
        //return  0;
        return familyService.deleteFamily((Integer)id.get("id"));
    }

}
