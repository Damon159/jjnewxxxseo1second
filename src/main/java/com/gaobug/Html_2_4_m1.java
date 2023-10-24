package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;


public class Html_2_4_m1 {
    public static String  setHtml(Map dataMap,String url,String nexturl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList){
        try{
        //获取数据
        String productname=dataMap.get("product_name")!=null?dataMap.get("product_name").toString():"";
        String des=dataMap.get("product_description")!=null?dataMap.get("product_description").toString():"";
        String mainimage=dataMap.get("product_main_img")!=null?dataMap.get("product_main_img").toString():"";
        String cate1=dataMap.get("product_cate1")!=null?dataMap.get("product_cate1").toString():"ホーム";
        String cate2=dataMap.get("product_cate2")!=null?dataMap.get("product_cate2").toString():"";
        String cate3=dataMap.get("product_cate3")!=null?dataMap.get("product_cate3").toString():"";
        String related=dataMap.get("product_related")!=null?dataMap.get("product_related").toString():"";
        //String google_img_sum=dataMap.get("google_img_sum")!=null?dataMap.get("google_img_sum").toString():"";
        //String product_review=dataMap.get("product_review")!=null?dataMap.get("product_review").toString():"";
        //product_review=product_review.replace("###",",");
        String imagedes=dataMap.get("desimage")!=null?dataMap.get("desimage").toString():"";
            int urlnun;
            urlnun=Textmysql.ASCIcount(url);
            String secondnumber=secondarrayList.get(urlnun%secondarrayList.size()).toString();
            String [] imges = new String[0];
            String allimgesdes="";
            if (!imagedes.equals("")&&imagedes.indexOf("Φfenge")>0){
                imges=imagedes.split("Φfenge");
            }
            for (int i = 0; i < imges.length; i++) {
                allimgesdes+="<img src=\""+imges[i]+"\"><br>";
            }
        String cate2url=  "/"+secondnumber+"/" + nexturl+".html";
        String cate3url= "/"+secondnumber+"/" + url+".html";
        //String cate4url= "/?" + url;
        //String cate4url= "/?" + url;
        String googleImage=dataMap.get("google_image_keyword")!=null?dataMap.get("google_image_keyword").toString():"";
        String product_price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        //String youtube_keywords=dataMap.get("youtube_keywords")!=null?dataMap.get("youtube_keywords").toString():"";
        //String yourutbevideo=dataMap.get("ybvideo")!=null?dataMap.get("ybvideo").toString():"";
            String serverName=hostName.replaceAll("http://|https://","");
            String title=productname+" "+cate2+" "+cate3;
            String h1name=title;
            String h2name=product_price+"円"+title;
            String title1=title+" - "+serverName;

            String gimges="";
            String gtitle="";
            String randtitle=productname+" - "+serverName;
            String color=Textmysql.colorid(urlnun);
        if (!googleImage.equals("")) {
            String [] goose=googleImage.split("###");
            if (goose.length==2) {
                gimges = goose[1];
                if (goose[0].indexOf(",")>0||goose[0].indexOf("Φfenge")>0) {
                    String[] gtitles = goose[0].split("Φfenge|,");
                    int gcoung = urlnun % gtitles.length;
                    for (int i = 0; i < gtitles.length; i++) {
                        if (i == gcoung) {
                            gtitle += "<strong>" + title + "</strong>" + gtitles[i] + ",";
                        } else {
                            gtitle += gtitles[i] + ",";
                        }
                    }
                }

            }
        }
            String hotkey="";
            if (hotkeyflag) {
                 hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, urlnun);
            }else{
                //随机热销次
                int randhotkey= (int) (Math.random()*hotkeys.size());
                 hotkey=hotkeys.get(randhotkey).toString();
            }
        String meta_keywords=cate2+","+productname+","+cate3;

        related=related.replace("Ω","<br>");

        String products_description=des+allimgesdes;
//        products_description=products_description.replace("<p>","");
//        products_description=products_description.replace("</p>","");
//        products_description=products_description.replace("<br>","");
//        products_description=products_description.replace("<tr>","");
//        products_description=products_description.replace("</tr>","");
//        products_description=products_description.replace("<style>","");
//        products_description=products_description.replace("<b>","");
//        products_description=products_description.replace("</b>","");
//        products_description=products_description.replace("</style>","");
//        products_description=products_description.replace("</style>","");
//        products_description=products_description.replace("<th>","");
//        products_description=products_description.replace("</th>","");
//        products_description=products_description.replace("<table border=\"1\">","");
//        products_description=products_description.replace("</table>","");
//        products_description=products_description.replaceAll("<table([\\s\\S]*?)>","");
        String classname1=Getsitemapurl.MD5("class1");
        String classname2=Getsitemapurl.MD5("class2");
        html=html.replace("{#hosturl}",hostName);
        html=html.replace("{#mainimage}",mainimage);
        html=html.replace("{#title}",title);
        html=html.replace("{#product_model}","");
        html=html.replace("{#product_price}",product_price);
        html=html.replace("{#cate1}",cate1);
        html=html.replace("{#cate1url}",hostName);
        html=html.replace("{#cate2url}",hostName+cate2url);
        html=html.replace("{#cate2}",cate2);
        html=html.replace("{#cate3url}",hostName+cate3url);
        html=html.replace("{#cate3}",productname);
        html=html.replace("{#classname1}",classname1);
        html=html.replace("{#classname2}",classname2);
        html=html.replace("{#productrandname}",hotkey);
        html=html.replace("{#randname1}",randtitle);
        html=html.replace("{#product_name}",productname);
        html=html.replace("{#title1}",title1);
        //html=html.replace("{#cate4url}",hostName+cate4url);
        //html=html.replace("{#cate4}",productname);
        ArrayList alst;

        html=html.replace("{#products_image_url}", mainimage);
        html=html.replace("{#meta_title}", title);
        //html=html.replace("{#h1title}","<h1>"+h1name+"</h1>");
        html=html.replace("{#h1title}",h1name);
        html=html.replaceFirst("\\{#h2title}",Matcher.quoteReplacement(h2name));
        html=html.replace("{#h2title}",title);
        int shellcount=Textmysql.ASCIcount(hostName);
        //String randname=Textmysql.rand(cc%4+1);
        //填充yahoo
        //随机标签
           // String [] taghead={"<li>", "<span>", "<div>",  "<div><P>", "<div><span>", "<div><strong>", "<div><br>", "<div><td>", "<div><hr>", "<div><ul><li>",  "<div><span><p><strong>"};
            //String [] tagend={"</li>", "</span>", "</div>",  "</p></div>", "</span></div>", "</strong></div>", "</br></div>", "</td></div>", "</hr></div>", "</li></ul></div>", "</strong></p></span></div>"};
//        if (yahoo_keys!="") {
//            String [] keys=yahoo_keys.split("Φfenge");
//            String [] dess=null;
//            if (yahoo_des.indexOf("Φfenge")>0){
//                 dess=yahoo_des.split("Φfenge");
//            }
//            for (int i = 0; i < keys.length; i++) {
//                String tite=keys[i];
//                String yahoodes="";
//                if (dess!=null) {
//                    if (dess.length - 1 < i) {
//                        int temp = dess.length - 1;
//                        yahoodes = dess[temp];
//                    } else {
//                        yahoodes = dess[i];
//                    }
//                }
//                html = html.replaceFirst("\\{#h\\d+title\\d+}", Matcher.quoteReplacement(tite));
//
//                html = html.replaceFirst("\\{#googleDes}", taghead[shellcount%5]+Matcher.quoteReplacement(yahoodes)+tagend[shellcount%5]);
//            }
//
//        }
//        //填充youutbe
//        if (youtube_keywords!="") {
//            String [] keyvideo=youtube_keywords.split("###");
//            if (keyvideo.length==2) {
//                String[] youtubekeys = keyvideo[0].split("Φfenge");
//                if (youtubekeys.length > 0) {
//                    int forcount = (shellcount * 7 + 12) % youtubekeys.length;
//                   if (forcount>10)
//                       forcount-=4;
//
//                }
//                yys = keyvideo[0].replace("Φfenge", ".");
//
//
//                String[] youtubevideos = keyvideo[1].split("Φfenge");
//                if (youtubevideos.length > 0) {
//                    int forcount = (shellcount * 7 + 12) % youtubevideos.length;
//                    //yys = keyvideo[0].replace("Φfenge", ".");
//                    //html = html.replace("{#remain_tag_1}", "<p>" + yys + "</p>");
//                    youtubevideos[forcount]=youtubevideos[forcount].replaceAll("watch\\?v=","embed/");
//                    html = html.replace("{#youtubevideo}", "<iframe src=\"https://www.youtube.com" + youtubevideos[forcount] + "\" width=\"853\" height=\"480\"></iframe>");
//                }
//
//            }
//        }
            //html = html.replace("{#youtubevideo}", "");
           // youtube_keywords=youtube_keywords.replace("Φfenge","");
           // html = html.replace("{#youtube}", taghead[shellcount%6] + youtube_keywords + tagend[shellcount%6]+yourutbevideo);
//        String subproductnam="";
//        subproductnam=products_description.substring(0,products_description.length()/3);
//        String meta=subproductnam.replaceAll("<([\\s\\S]*?)>","");
        //String meta_description=product_price+"円 "+productname;
        //html=html.replace("<meta name=\"keywords\" content=\"{#meta_keywords}\" />","");
        html=html.replace("{#meta_keywords}",meta_keywords);
        html=html.replace("{#meta_description}",h2name);
        html = html.replace("{#products_description}", products_description);
        //html = html.replace("{#products_image}", products_imagehtml);
        //html = html.replace("{#breadcrumbs}",breadcrumbshtml);
        html=html.replace("{#products_name}",productname);
        html=html.replace("{#current_url}",hostName+"/"+secondnumber+"/"+url+".html");
        html=html.replace("{#hosturl}",hostName+"/"+secondnumber+"/"+url+".html");
        html=html.replace("{#google_images}",gimges+"<br>"+related);
        html=html.replace("{#google_title}",gtitle);
        html=html.replace("{#randname}", "");
        //html=html.replaceAll("\\{#google_img_sum}",google_img_sum);
        html=html.replace("{#color}",color);
        String shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
//        if (product_review!="")
//        html=html.replace("{#reviews}",product_review);
//        else  html=html.replace("{#reviews}","");
//            html=html.replace("{#domain}","");
            //html = html.replace("{#youtube}","");
            //列表
        String json = Getsitemapurl.productJson(title, des,
                mainimage, product_price, hostName + "/"+secondnumber+"/" + url+shellflag+".html", hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json10",imagedes);
        //产品
//        String json1 = Getsitemapurl.productJson(productname, des,
//                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json8",imagedes);
            html = html.replace("{#json_data}", "<script type=\"application/ld+json\">\n"+json+"\n"+"</script>");

            //获取a连接
           int  urlCount=Getsitemapurl.urlCount(html,"{#products_url}");
            alst=Getsitemapurl.getLastnew(url,idurlmap,urlCount);
            String [] urls=html.split("\\{#products_url}");
            String htmlreplaceurls="";
            //<a href="https://gvmplc.com?otqi651b632" title="SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L" >SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L</a>
            // 非redis 存储
            for (int j = 0; j < urls.length-1; j++)
            {
                url=(String) alst.get(j);
                int tempcount=Textmysql.ASCIcount(url);
                secondnumber=secondarrayList.get(tempcount%secondarrayList.size()).toString();

                List content = Getsitemapurl.getProductTxt(url, readConfigWeb,idTotal);
                if (content.size()<2)
                    continue;
                url= (String) content.get(1);
                Map map1 = (Map) JSON.parse((String) content.get(0));
                productname = map1.get("product_name")!=null?map1.get("product_name").toString():"";
                shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
                //lr模板
                //String temp="\""+hostName+"/?"+url+shellflag+"\""+"title=\""+productname+"\" >"+productname;
                //cc模板
                //String temp="<a href=\"/?"+url+shellflag+"\""+" title=\""+productname+"\" >"+productname+"</a>";
                //mertem
                //String temp=hostName+"/"+secondname+"?"+url+shellflag;
                //ss
                String temp="<a title=\""+productname+"\" href=\""+"/"+secondnumber+"/"+url+shellflag+".html"+"\" style=\"color: #"+color+"\">"+productname+"</a>";
                htmlreplaceurls+=urls[j]+temp;
            }
            html = htmlreplaceurls+urls[urls.length-1];


//            urlCount=Getsitemapurl.urlCount(html,"{#href#}");
//            alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal);
//            for (int i = 0; i < alst.size(); i++) {
//                html=html.replaceFirst("\\{#href#}",hostName+"/?"+alst.get(i));
//            }
//            html=html.replace("{#href#}",hostName);

//            return html;
            Document doc = Jsoup.parse(html);
            return doc.toString();
        } catch (Exception e){
            Document doc = Jsoup.parse(html);
            e.printStackTrace();
            return doc.toString();
        }

    }

    public static Map getProductNameImage(String url,Map readConfigWeb,int idTotal,Map urlNameImage){
        String productnameImage;
        //Redis 里面存在
        if(urlNameImage.get(url)!=null) {
            productnameImage = (String) urlNameImage.get(url);
            //System.out.println("In Redis---->" + url);
        }
        else {//Redis 不存在
            List content = Getsitemapurl.getProductTxt(url, readConfigWeb,idTotal);
            url= (String) content.get(1);
            Map map1 = (Map) JSON.parse((String) content.get(0));

            String productname=map1.get("product_name")!=null?map1.get("product_name").toString():"";
            String image=map1.get("product_main_img")!=null?map1.get("product_main_img").toString():"";
            productnameImage = productname+"Φfenge"+image;
            urlNameImage.put(url,productnameImage);
        }
        urlNameImage.put("productnameImage",productnameImage);
        return urlNameImage;
    }

}
