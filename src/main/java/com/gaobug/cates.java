package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cates {
    public static String setHtml(Map dataMap, Map catemap, String brorginurl, String html, String hostName, String secondname, String cateurlhtml, Map readConfigWeb
    , int idTotal, String cateurlid, ArrayList hotkeys,Map idurlmap){
        try {
            String catstrs="";
            String cateurlshtml="";
            String firstpage="";
            String midpage="";
            String endpage="";

            for (int i = 1; i <= catemap.size(); i++) {
                   catstrs+="<li><a href=\""+hostName+ "/"+secondname+"goodcatesxshl/cate-"+i+ "\" >"+catemap.get(String.valueOf(i))+"</a></li>";
            }
            html=html.replace("{#catesurl}",catstrs);
            int pageNum=1;
            String cateid=cateurlid.replace("goodcatesxshl","");
            String subpage=cateid;
            cateid=cateid.replaceAll("_(.*)","");
            if (subpage.indexOf("_")>0){
                String pp=subpage.substring(subpage.indexOf("_")+1);
                pageNum=Integer.valueOf(pp);
            }

             List<String> urls = FileUtils.readLines(new File("../cate/" + cateid + ".txt"));
             html=html.replace("{#size}",String.valueOf(urls.size()));
             int numpage=55;
             int totalpage=urls.size()/numpage;
             int result=urls.size()%numpage;
             if (result>0)
                 totalpage++;
             int startIndex=(pageNum-1)*numpage;
             String allpages="";
             int start=1;
             int end=5;
            int befor=pageNum-1;
            int after=pageNum+1;
             if (totalpage<=5){ //小于5页
                 end=totalpage;
             }else {
                start=pageNum-2;
                end=pageNum+2;
                if (start<1){ //第1，2页情况
                    start=1;
                    end=5;
                }
                if (end>totalpage){
                    end=totalpage;
                    start=end-5;
                }

             }
            for (int s = start; s <=end; s++) {
                if (s==pageNum){
                    String pages = "<strong class=\"current\">"+pageNum+"</strong>";
                    allpages+=pages;
                }else {
                    String pages = "<a href=\"  " + hostName + "/" + secondname + "goodcatesxshl/" + cateid + "_"+ s+ " \" >" + s + "</a>";
                    allpages+=pages;
                }

            }
            if (pageNum>1)
                allpages="<a href=\"  "+hostName+ "/"+secondname+"goodcatesxshl/"+cateid+ "_"+befor+ " \" >"+"[&lt;&lt;&nbsp;前へ]</a>"+allpages;
            if (pageNum<end)
                allpages=allpages+"<a href=\"  "+hostName+ "/"+secondname+"goodcatesxshl/"+cateid+ "_"+after+ " \" >"+"[次へ&nbsp;&gt;&gt;]</a>";
            html=html.replace("{#page}",allpages);

            int pagttt;
            if (pageNum*numpage>urls.size()) {
                pagttt = urls.size();
            }else {
                pagttt=pageNum*numpage;
            }
            for (int i = startIndex; i <pagttt; i++) {
                List content = Getsitemapurl.getProductTxt((String) urls.get(i), readConfigWeb, idTotal);
                if (content.size() >= 2) {
                    String name="";
                    String price="";
                    String image="";
                    String cateurlnn = (String)content.get(1);
                    Map map1 = (Map)JSON.parse((String)content.get(0));
                    name=map1.get("product_name").toString();
                    price=map1.get("product_price").toString();
                    image=map1.get("product_main_img").toString();
                    int  pricec= (int) (Double.valueOf(price).intValue());
                    int  orprice= (int) (Double.valueOf(price).intValue()*1.3);
                    String cateurlhtmltemp=cateurlhtml.replace("{#url}",hostName+"/"+secondname+cateurlnn);
                    cateurlhtmltemp=cateurlhtmltemp.replace("{#productname}",name);
                    cateurlhtmltemp=cateurlhtmltemp.replace("{#mainimage}",image);
                    cateurlhtmltemp=cateurlhtmltemp.replace("{#price}",String.valueOf(orprice));
                    cateurlhtmltemp=cateurlhtmltemp.replace("{#specialprice}",String.valueOf(pricec));
                    cateurlshtml+=cateurlhtmltemp;
                }
            }
            html=html.replace("{#urls}",cateurlshtml);
            String hotkey = "";
            int hucountall=Textmysql.ASCIcount(brorginurl);
            hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, hucountall);
            String serverName = hostName.replaceAll("http://|https://", "");
            String catename= (String) catemap.get(cateid.replace("cate-",""));
            String title=catename+" "+hotkey+" - "+serverName;
            String title1=catename;
            String keywords=hotkey+" "+catename;
            String description=catename+","+hotkey+ " - "+serverName;


            html=html.replace("{#title}",title);
            html=html.replace("{#title1}",title1);
            html=html.replace("{#keywords}",keywords);
            html=html.replace("{#keydescription}",description);
            html=html.replace("{#current_url}",brorginurl);
            html=html.replace("{#servername}",serverName);
            html=html.replace("{#hostname}",hostName);

            int urlCount = Getsitemapurl.urlCount(html, "{#producturl");
            ArrayList alst = Getsitemapurl.getLastnew(brorginurl, idurlmap, urlCount);
            for (int i = 0; i < alst.size(); i++) {
                //int a = (int)(Math.random() * (double)secondarrayList.size());
                //String secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
                String url = (String) alst.get(i);
                String color=Textmysql.colorid(Textmysql.ASCIcount(alst.get(i).toString()));
//                int tempcount=Textmysql.ASCIcount(url);
//                String  secondnumber=secondarrayList.get(tempcount%secondarrayList.size()).toString();
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
                if (content.size() >= 2) {
                    String name ;
                    url = (String) content.get(1);
                    Map map1 = (Map) JSON.parse((String) content.get(0));
                    name = map1.get("product_name").toString();
                    html = html.replace("{#producturl" + i + "}", hostName+"/"+secondname + url);
                    html = html.replace("{#productname" + i + "}", name);
                    html = html.replace("{#productname" + i + "}", name);
                    html = html.replace("{#colr"+i+"}", color);
                }
            }
            return html;
//            String [] hhh=html.split("<head>");
//            if (hhh.length<2){
//                return html;
//            }else {
//                html = hhh[0] + "<head>\n" + style + hhh[1];
//                return html;
//            }
        } catch (Exception var63) {
            //Document doc = Jsoup.parse(html);
            //var63.printStackTrace();
            return "";
        }
    }

//    public static Map getProductNameImage(String url, Map readConfigWeb, int idTotal, Map urlNameImage) {
//        String productnameImage;
//        if (urlNameImage.get(url) != null) {
//            productnameImage = (String)urlNameImage.get(url);
//        } else {
//            List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
//            url = (String)content.get(1);
//            Map map1 = (Map)JSON.parse((String)content.get(0));
//            String productname = map1.get("product_name") != null ? map1.get("product_name").toString() : "";
//            String image = map1.get("product_main_img") != null ? map1.get("product_main_img").toString() : "";
//            productnameImage = productname + "Φfenge" + image;
//            urlNameImage.put(url, productnameImage);
//        }
//
//        urlNameImage.put("productnameImage", productnameImage);
//        return urlNameImage;
//    }
}
