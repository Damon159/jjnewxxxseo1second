package com.gaobug;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;


public class Html_2 {
    public static String  setHtml(Map dataMap,String url,String nexturl,String randUrl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb,Map mapId, int idTotal,ArrayList hotkeys,
                                  int randint,ArrayList shellflaglist,int [] discountnum){
        try{
        //获取数据
        String productname=dataMap.get("product_name")!=null?dataMap.get("product_name").toString():"";
        String des=dataMap.get("product_description")!=null?dataMap.get("product_description").toString():"";
        String mainimage=dataMap.get("product_main_img")!=null?dataMap.get("product_main_img").toString():"";
        String cate1=dataMap.get("product_cate1")!=null?dataMap.get("product_cate1").toString():"";
        String cate2=dataMap.get("product_cate2")!=null?dataMap.get("product_cate2").toString():"";
        String cate3=dataMap.get("product_cate3")!=null?dataMap.get("product_cate3").toString():"";
        String related=dataMap.get("product_related")!=null?dataMap.get("product_related").toString():"";
        String price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        String product_review=dataMap.get("product_review")!=null?dataMap.get("product_review").toString():"";
        String cate2url=  "/?" + nexturl;
        String cate3url= "/?" + randUrl;
        String cate4url= "/?" + url;
        //String cate4url= "/?" + url;
        String product_url=dataMap.get("product_url")!=null?dataMap.get("product_url").toString():"";
        String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
        String id="";
        String googleImage=dataMap.get("google_image_keyword")!=null?dataMap.get("google_image_keyword").toString():"";
        String product_price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        String youtube_keywords=dataMap.get("youtube_keywords")!=null?dataMap.get("youtube_keywords").toString():"";
        String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            String imagedes=dataMap.get("desimage")!=null?dataMap.get("desimage").toString():"";
        String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//关键词
        if (product_url.indexOf("api.mercari.jp")>0){
            id=product_url.substring(product_url.indexOf("get?id=")+7);
            id=id.replace("\"}","");
        }
        //填充内容
        //String
        String hotkey=Getsitemapurl.randGetOne("jptitle.txt",hotkeys,randint);
        product_model=id+"-"+product_model;
        int i1 = randint % discountnum.length;
        int discountrand= discountnum[i1];
        //String title=discountrand+"% OFF"+hotkey+" "+productname+cate2+" "+cate3+"&"+hostName;
        String title=productname;
        String h1name=productname;

        String meta_keywords=cate2+"&"+productname+cate3;
        String serverName=hostName.replaceAll("http://|https://","");
        String products_description=des+related ;
        String back_tag_1="";
        String classname1=Getsitemapurl.MD5("class1");
        String classname2=Getsitemapurl.MD5("class2");
        html=html.replace("{#mainimage}",mainimage);
        html=html.replace("{#title}",title);
        html=html.replace("{#product_model}",product_model);
        html=html.replace("{#product_price}",product_price);
        html=html.replace("{#cate1}",cate1);
        html=html.replace("{#cate2url}",cate2url);
        html=html.replace("{#cate2}",cate2);
        html=html.replace("{#cate3url}",cate3url);
        html=html.replace("{#cate3}",cate3);
        html=html.replace("{#cate4url}",cate4url);
        html=html.replace("{#cate4}",productname);
        String shell_link="<a href=\""+hostName+"\">home</a>";
        ArrayList alst;
        int urlCount;
        html=html.replaceAll("\\{#products_image_url}", Matcher.quoteReplacement(mainimage));
        html=html.replaceAll("\\{#meta_title}", Matcher.quoteReplacement(title));
        html=html.replace("{#h1title}","<h1>"+h1name+"</h1>");
        html=html.replace("{#h1title1#}","<h1>"+h1name+"</h1>");
        html=html.replaceAll("\\{#h1title\\d+}","");
        //选两个h标签
        String yys;
        int shellcount=Textmysql.ASCIcount(hostName);

        //填充yahoo
        //随机标签
            String [] taghead={"<li>", "<span>", "<div>",  "<div><P>", "<div><span>", "<div><strong>", "<div><br>", "<div><td>", "<div><hr>", "<div><ul><li>",  "<div><span><p><strong>"};
            String [] tagend={"</li>", "</span>", "</div>",  "</p></div>", "</span></div>", "</strong></div>", "</br></div>", "</td></div>", "</hr></div>", "</li></ul></div>", "</strong></p></span></div>"};
        String metades="";
        if (yahoo_keys!="") {
            String [] keys=yahoo_keys.split("Φfenge");
            String [] dess=null;
            if (yahoo_des.indexOf("Φfenge")>0){
                 dess=yahoo_des.split("Φfenge");
            }
            for (int i = 0; i < keys.length; i++) {
                String tite=keys[i];
                String yahoodes="";
                if (dess!=null) {
                    if (dess.length - 1 < i) {
                        int temp = dess.length - 1;
                        yahoodes = dess[temp];
                    } else {
                        yahoodes = dess[i];
                    }
                }
                html = html.replaceFirst("\\{#h\\d+title\\d+}", Matcher.quoteReplacement(tite));

                html = html.replaceFirst("\\{#google_des}", taghead[shellcount%5]+Matcher.quoteReplacement(yahoodes)+tagend[shellcount%5]);
            }

        }
        //填充youutbe
        if (youtube_keywords!="") {
            String [] keyvideo=youtube_keywords.split("###");
            if (keyvideo.length==2) {
                String[] youtubekeys = keyvideo[0].split("Φfenge");
                if (youtubekeys.length > 0) {
                    int forcount = (shellcount * 7 + 12) % youtubekeys.length;
                   if (forcount>10)
                       forcount-=4;
                    int temp=forcount;
                    for (int i = 0; i < forcount; i++) {
                        temp = (temp + 1) % youtubekeys.length;
                        html = html.replaceFirst("\\{#h\\d+title\\d+}", Matcher.quoteReplacement(youtubekeys[temp]));

                    }
                }
                String[] youtubevideos = keyvideo[1].split("Φfenge");
                if (youtubevideos.length > 0) {
                    int forcount = (shellcount * 7 + 12) % youtubevideos.length;
                    yys = keyvideo[0].replace("Φfenge", ".");
                    html = html.replace("{#remain_tag_1}", "<p>" + yys + "</p>");
                    youtubevideos[forcount]=youtubevideos[forcount].replaceAll("watch\\?v=","embed/");
                    html = html.replace("{#youtube}<br>", "<iframe src=\"https://www.youtube.com" + youtubevideos[forcount] + "\" width=\"853\" height=\"480\"></iframe>");
                }
            }
        }

        String subproductnam="";
        if (des.indexOf("商品の説明")>0&&des.indexOf("商品の情報")>0) {
            subproductnam = des.substring(des.indexOf("商品の説明") + 5, des.indexOf("商品の情報"));
            subproductnam=subproductnam.replaceAll("<[\\s\\S]*?>","");
        }

        String meta_description=hotkey+metades+","+productname+","+cate3+subproductnam+"-"+serverName;
          html=html.replaceAll("<h\\d+>\\{#h\\d+title\\d+}</h\\d+>","");
          html=html.replaceAll("\\{#h\\d+title\\d+}","");
          html=html.replaceAll("\\{#h\\d+title}","");
          html=html.replace("<h1></h1>","");
            html=html.replace("<h2></h2>","");
            html=html.replace("<h3></h3>","");
            html=html.replace("<h4></h4>","");
            html=html.replace("<h5></h5>","");
        html = html.replace("{#youtube}<br>", "");
        html = html.replace("{#remain_tag_1}", "");
        html = html.replace("{#remain_tag_2}", "");
        html=html.replace("{#google_des}","");
        //html=html.replace("<meta name=\"keywords\" content=\"{#meta_keywords}\" />","");
        html=html.replace("{#meta_keywords}",meta_keywords);
        html=html.replace("{#meta_description}",meta_description);
        html = html.replace("{#products_description}", products_description);
        //html = html.replace("{#products_image}", products_imagehtml);
        html = html.replace("{#product_model}", product_model);
        //html = html.replace("{#breadcrumbs}",breadcrumbshtml);
        html=html.replace("{#products_name}",productname);
        html=html.replace("{#back_tag_1}",back_tag_1);
        html=html.replace("{#current_url}",hostName+"/?"+url);
        html=html.replace("{#img_title}",productname);
        html=html.replace("{#shell_link}",shell_link);
        html=html.replace("{#google_images}",googleImage);
        html=html.replace("{#class_name_1}",classname1);
        html=html.replace("{#class_name}",classname2);
        String shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
        if (product_review!="")
        html=html.replace("{#reviews}",product_review);
        else  html=html.replace("{#reviews}","");
        //列表
        String json = Getsitemapurl.productJson(productname, des,
                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,cate4url,cate1,cate2,cate3,"json7",imagedes);
        //产品
        String json1 = Getsitemapurl.productJson(productname, des,
                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,cate4url,cate1,cate2,cate3,"json8",imagedes);
        html = html.replace("{#json_data}", "<script type=\"application/ld+json\">\n"+json+"\n"+"</script>\n<script type=\"application/ld+json\">\n"+json1+"\n</script>\n");

        //获取a连接
        urlCount=Getsitemapurl.urlCount(html,"{#products_url}");
        alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal);

        String [] urls=html.split("\\{#products_url}>|\\{#products_url}");
        String htmlreplaceurls="";
        // 非redis 存储
        for (int j = 0; j < urls.length-1; j++)
        {
            url=(String) alst.get(j);
            List content = Getsitemapurl.getProductTxt(url, readConfigWeb,idTotal);
            url= (String) content.get(1);
            Map map1 = (Map) JSON.parse((String) content.get(0));
            productname = map1.get("product_name")!=null?map1.get("product_name").toString():"";

            shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
            String temp="\"/?"+url+shellflag+"\""+" title=\""+productname+"\" >"+productname;
            htmlreplaceurls+=urls[j]+temp;
        }


        html = htmlreplaceurls+urls[urls.length-1];


        String subpost="<form action=\""+hostName+ "/search\" method=\"post\">\n" +
                "<input size=\"40\" name=\"o\"  type=\"text\" />\n" +
                "<input name=\"Search\" value=\"検索\" type=\"submit\" />\n" +
                "</form>";
        html=html.replace("{#domain}",subpost);
        html=html.replace("{#mainimage}",mainimage);
        html=html.replace("[##a##]","");

        return html;
        } catch (Exception e){
            e.printStackTrace();
            return html;
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
