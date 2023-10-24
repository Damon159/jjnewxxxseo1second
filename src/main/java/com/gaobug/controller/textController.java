    package com.gaobug.controller;


import com.gaobug.Getsitemapurl;
import com.gaobug.Textmysql;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class textController{
    ArrayList listJson =Textmysql.getFileContent("configweb.txt", true);
//    @RequestMapping(value="/getdatas")
//    @ResponseBody String getdatas() {
//        System.out.println("getdatas");
//        Textmysql.getData("../data");
//        return "getData success !!!!";
//    }

    @RequestMapping(value="/getData")
    @ResponseBody String getData(@RequestParam String root, String passd,String ip) {
        //domain dbnamee
        Map arrrymap = Textmysql.readConfigSqlTxt("configsql.txt");
        System.out.println("root"+root);
        System.out.println("passd"+passd);
        System.out.println("ip"+ip);
        //Textmysql.getKeyWords(root,passd,arrrymap,ip);
        for (Object key : arrrymap.keySet()) {
            Textmysql.ConnectionMysql(key.toString(), root, passd, arrrymap.get(key).toString() , ip);
//            root1.schedule(new Runnable()
//            {
//                @Override
//                public void run() {
//
//                    System.out.println("Louding Data !!!!");
//
//                }
//            },0, TimeUnit.SECONDS);
        }
        return "Data success !!!!";
    }

    @RequestMapping(value="/getkeys")
    @ResponseBody String getkeys() {
        Textmysql.getKeyWordstxt();
        return "getKeys success !!!!";
    }

    @RequestMapping(value = "/watch")
    @ResponseBody String watching(HttpServletRequest httpServletRequest){
        String data=httpServletRequest.getParameter("data");
        String passd=httpServletRequest.getParameter("passd");
        System.out.println(passd);
        System.out.println(data);
        if (!passd.equals("9f1IyC7E0g"))
            return "passd wrong!!!";

        String path="logs/urllogs/"+data+".txt";
        File file=new File(path);
        if (!file.exists())
            return "NO-->>"+data+"logs!!!!";
            ArrayList logs=Textmysql.filedata("logs/urllogs/"+data+".txt");
            String alls="";
        for (int i = 0; i < logs.size(); i++) {
            alls+=logs.get(i)+"<br>";
        }
            return alls;

    }
    @RequestMapping(value = "/putData")
    @ResponseBody String putData(@RequestParam String content){
        if (content.equals("ya")){
            Textmysql.getfileyh("../yahookey");
            return  "Data success !!!!";
        }
        if (content.equals("yb")){
            Textmysql.putHunterDataYoutube("../youtube","youtube_keywords"); //youtube
            return  "Data success !!!!";
        }
        if (content.equals("go")){
            Textmysql.putHunterDataGoogleImage3("../googleimage","google_image_keyword"); //googleiamge
            return  "Data success !!!!";
        }
        return "fail write again!!!";

    }


}