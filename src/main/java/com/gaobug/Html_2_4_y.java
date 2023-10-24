package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Html_2_4_y {
    public static String  setHtml(Map dataMap,String url,String nexturl,String randUrl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb,Map mapId, int idTotal,ArrayList hotkeys,
                                  int randint,ArrayList shellflaglist,int [] discountnum){
        try{
        //获取数据
        String productname=dataMap.get("product_name")!=null?dataMap.get("product_name").toString():"";
        String googlerand=dataMap.get("google_img_sum")!=null?dataMap.get("google_img_sum").toString():"";
        String des=dataMap.get("product_description")!=null?dataMap.get("product_description").toString():"";
        String mainimage=dataMap.get("product_main_img")!=null?dataMap.get("product_main_img").toString():"";
        String cate1=dataMap.get("product_cate1")!=null?dataMap.get("product_cate1").toString():"ホーム";
        String cate2=dataMap.get("product_cate2")!=null?dataMap.get("product_cate2").toString():"";
        String cate3=dataMap.get("product_cate3")!=null?dataMap.get("product_cate3").toString():"";
        String related=dataMap.get("product_related")!=null?dataMap.get("product_related").toString():"";
        //String price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        String product_review=dataMap.get("product_review")!=null?dataMap.get("product_review").toString():"";
        String cate2url=  "/?" + nexturl;
        String cate3url= "/?" + randUrl;
        String cate4url= "/?" + url;
        String product_url=dataMap.get("product_url")!=null?dataMap.get("product_url").toString():"";
        String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
        String id="";
        String desimage=dataMap.get("desimage")!=null?dataMap.get("desimage").toString():"";
        String googleImage=dataMap.get("google_image_keyword")!=null?dataMap.get("google_image_keyword").toString():"";
        String product_price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        String youtube_keywords=dataMap.get("youtube_keywords")!=null?dataMap.get("youtube_keywords").toString():"";
        String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            yahoo_keys=yahoo_keys.replaceAll("<[\\s\\S]*?>","");
        String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//关键词
            yahoo_des=yahoo_des.replaceAll("<[\\s\\S]*?>","");
        if (product_url.indexOf("api.mercari.jp")>0){
            id=product_url.substring(product_url.indexOf("get?id=")+7);
            id=id.replace("\"}","");
        }
            String gimges="";
            String gtitle="";
            if (!googleImage.equals("")) {
                String [] goose=googleImage.split("###");
                if (goose.length>0) {
                    gimges = goose[1];
                    gtitle = goose[0];
                    gtitle=gtitle.replace("Φfenge",",");
                }
            }
        //填充内容
        String hotkey=Getsitemapurl.randGetOne("jptitle.txt",hotkeys,randint);
        //随机热销词
       // int randhotkey= (int) (Math.random()*hotkeys.size());
        //String hotkey=hotkeys.get(randhotkey).toString();
        product_model=id+"-"+product_model;
        //int i1 = randint % discountnum.length;
        //int discountrand= discountnum[i1];
        //String title=discountrand+"% OFF"+hotkey+" "+productname+cate2+" "+cate3+"&"+hostName;
        String title=productname;
        String h1name=productname;

        String meta_keywords=cate2+"&"+productname+cate3;
        String serverName=hostName.replaceAll("http://|https://","");
        String products_description=des+"<strong>"+productname+"</strong>"+"<p>"+gtitle+"</p>";
        String back_tag_1="";
        String classname1=Getsitemapurl.MD5("class1");
        String classname2=Getsitemapurl.MD5("class2");
        html=html.replace("{#mainimage}",mainimage);
        html=html.replace("{#title}",title);
        html=html.replace("{#product_model}","");
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
        //html=html.replace("{#h1title}","<h1>"+h1name+"</h1>");
        html=html.replace("{#h1title1}","<h1>"+h1name+"</h1>");
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

                html = html.replaceFirst("\\{#googleDes}", taghead[shellcount%5]+Matcher.quoteReplacement(yahoodes)+tagend[shellcount%5]);
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
                yys = keyvideo[0].replace("Φfenge", ".");
                   html = html.replace("{#youtube}", taghead[shellcount%6] + yys + tagend[shellcount%6]);
                //String[] youtubevideos = keyvideo[1].split("Φfenge");
//                if (youtubevideos.length > 0) {
//                    int forcount = (shellcount * 7 + 12) % youtubevideos.length;
//                    yys = keyvideo[0].replace("Φfenge", ".");
//                    html = html.replace("{#remain_tag_1}", "<p>" + yys + "</p>");
//                    youtubevideos[forcount]=youtubevideos[forcount].replaceAll("watch\\?v=","embed/");
//                    html = html.replace("{#youtube}<br>", "<iframe src=\"https://www.youtube.com" + youtubevideos[forcount] + "\" width=\"853\" height=\"480\"></iframe>");
//                }
            }
        }

            String subproductnam="";
            subproductnam=products_description.substring(0,products_description.length()/3);
            String meta=subproductnam.replaceAll("<([\\s\\S]*?)>","");
            String meta_description=product_price+"円"+hotkey+metades+","+productname+","+cate3+meta+"-"+serverName;
          html=html.replaceAll("<h\\d+>\\{#h\\d+title\\d+}</h\\d+>","");
          html=html.replaceAll("\\{#h\\d+title\\d+}","");
          html=html.replaceAll("\\{#h\\d+title}","");
          html=html.replace("<h1></h1>","");
            html=html.replace("<h2></h2>","");
            html=html.replace("<h3></h3>","");
            html=html.replace("<h4></h4>","");
            html=html.replace("<h5></h5>","");
        html = html.replace("{#youtube}<br>", "");
        html = html.replace("{#youtube}", "");
        html = html.replace("{#rand_title}<br>", "");
        html = html.replace("{#googleDes}", "");
        html = html.replace("{#view}", "");
        html = html.replace("{#remain_tag_1}", googlerand);
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

        html=html.replace("{#class_name_1}",classname1);
        html=html.replace("{#class_name}",classname2);

            String [] reviews;
        if (product_review!=""){
            reviews=product_review.split("###");
            String [] relateds=related.split("Ω");
            related="";
            for (int i = 0; i < relateds.length; i++) {
                String rw="";
                if (i<reviews.length)
                {
                    rw="<br><p>"+reviews[i]+"</p>";
                }
                related+=relateds[i]+rw;
            }
        }
            related=related.replace("Ω","<br>");
        html=html.replace("{#google_images}",gimges+related);
        html=html.replace("{#reviews}","");
            String shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
        //列表
            String json = Getsitemapurl.productJson(productname, des,
                    mainimage, product_price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json9",desimage);
//        //产品
//        String json1 = Getsitemapurl.productJson(productname, des,
//                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,cate4url,cate1,cate2,cate3,"json8");

        html=html.replace("{#breadcrumbsLdJson}","");
        html=html.replace("{#productLdJson}","");
            //html=html.replaceAll("\\{#json_data}","");
        html = html.replace("{#json_data}", "<script type=\"application/ld+json\">\n"+json+"\n"+"</script>");


        //获取a连接
        urlCount=Getsitemapurl.urlCount(html,"{#products_url}");
        alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal);
            String [] urls=html.split("\\{#products_url}>|\\{#products_url}");
            String htmlreplaceurls="";
            //<a href="https://gvmplc.com?otqi651b632" title="SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L" >SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L</a>
            // 非redis 存储
            for (int j = 0; j < urls.length-1; j++)
            {
                url=(String) alst.get(j);
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb,idTotal);
                if (content.size()<2)
                    continue;
                url= (String) content.get(1);
                Map map1 = (Map) JSON.parse((String) content.get(0));
                productname = map1.get("product_name")!=null?map1.get("product_name").toString():"";
                shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
                //lr模板
                 String temp="\"/?"+url+shellflag+"\""+"title=\""+productname+"\" >"+productname;
                //cc模板
                //String temp="<a href=\"/?"+url+shellflag+"\""+" title=\""+productname+"\" >"+productname+"</a>";
                htmlreplaceurls+=urls[j]+temp;
            }
            html = htmlreplaceurls+urls[urls.length-1];


            urlCount=Getsitemapurl.urlCount(html,"{#href#}");
            alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal);
            for (int i = 0; i < alst.size(); i++) {
                html=html.replaceFirst("\\{#href#}",hostName+"/?"+alst.get(i));
            }
            html=html.replace("{#href#}",hostName);




        String subpost="<form action=\""+hostName+ "/search\" method=\"post\">\n" +
                "<input size=\"40\" name=\"o\"  type=\"text\" />\n" +
                "<input name=\"Search\" value=\"検索\" type=\"submit\" />\n" +
                "</form>";
        html=html.replace("{#domain}",subpost);
        html=html.replace("{#mainimage}",mainimage);
        html=html.replace("[##a##]","");
        html=unicodeToCN(html);
            Document doc = Jsoup.parse(html);
            return doc.toString();
            //return html;
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
    public static String unicodeToCN(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
}
