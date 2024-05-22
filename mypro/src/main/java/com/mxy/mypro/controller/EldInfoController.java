package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.elderly;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.ElderlyService;
import com.mxy.mypro.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EldInfoController {
    private static final Logger log = LoggerFactory.getLogger(EldInfoController.class);
    private final ElderlyService elderlyService;
    @Autowired
    public EldInfoController(ElderlyService elderlyService) {
        this.elderlyService = elderlyService;
    }
    @GetMapping("/info/elderly")
    public PaginationResult<elderly> GetElderlyInfo(String name, String page, String pageSize) {
        int Ipage = Integer.parseInt(page);
        int IPageSize = Integer.parseInt(pageSize);
        System.out.println("11111/info/elderly"+name+Ipage+IPageSize);
        PaginationResult<elderly> list;
        if(name.equals("")){
            list = elderlyService.GetPage(Ipage,IPageSize);
            System.out.println("22222"+list.getData());
            return list;
        }
        else {
            list = elderlyService.selectByName(name, Ipage,IPageSize);
            System.out.println("22222"+list.getData());
            return list;
        }
        //return null;
    }

    @PostMapping("/info/addelderly")
    public int AddElderly(@RequestBody elderly eld) {
        int result = elderlyService.insert(eld);
        System.out.println("add:"+result);
        return result;
//        System.out.println("3333"+eld);
//        return 0;
    }

    @PostMapping("/info/editelderly")
    public int EditElderly(@RequestBody elderly eld) {
        System.out.println("eldid1111:"+eld.getLocationId());
        return elderlyService.editadmin(eld);
    }


    @PostMapping("/info/delelderly")
    public int DelElderly(@RequestBody Map id) {
//        int Iid = Integer.parseInt(id);
        //System.out.println("del:"+(Integer) id.get("id"));
        //return  0;
        return elderlyService.deleteelderly((Integer)id.get("id"));
    }
    @GetMapping("/info/elderlyInfo")
    public List<elderly> GetEInfo(String name) {

        return elderlyService.getByName(name);
    }

    @GetMapping("/info/numbers")
    public int GetNum(String location) {
        return elderlyService.getNum(location);
    }
    @GetMapping("/info/elderlyID")
    public int GetID(String token) {
        String role = JwtUtils.getClaimsByToken(token).getSubject();
        String family = role.split(":")[0];
        int id = elderlyService.getID(family);
        //System.out.println("family123:"+family+",id:"+id);
        return id;
    }
    @GetMapping("/info/elderlyName")
    public String GetName(Integer elderid) {
        if(elderid == null || elderid == 0){
            return null;
        }else {
            return elderlyService.getName(elderid);
        }
    }
    @GetMapping("/info/elderlyRFID")
    public Map<Integer, String> GetRFID(String rfid) {
        rfid = rfid.replace(" ", "");
        //System.out.println("rfid111:"+rfid);

        if(rfid == null|| rfid.equals("")){
            return null;
        }else {
            List<elderly> list =  elderlyService.getByRFID(rfid);
            Integer id = list.get(0).getId();
            String name = list.get(0).getName();
            Map<Integer, String> map = new HashMap<>();
            map.put(id, name);
            return map;
        }
    }
}
