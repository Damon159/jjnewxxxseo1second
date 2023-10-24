package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;


public class Html_2_4_f_3_old {
    public static String  setHtml(Map dataMap,String url,String nexturl,String randUrl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb,Map mapId, int idTotal,ArrayList hotkeys,
                                  int randint,ArrayList shellflaglist,int [] discountnum,boolean hotkeyflag,String secondname){
        try{
        //获取数据
        String productname=dataMap.get("product_name")!=null?dataMap.get("product_name").toString():"";
        String des=dataMap.get("product_description")!=null?dataMap.get("product_description").toString():"";
        String mainimage=dataMap.get("product_main_img")!=null?dataMap.get("product_main_img").toString():"";
        String cate1=dataMap.get("product_cate1")!=null?dataMap.get("product_cate1").toString():"ホーム";
        String cate2=dataMap.get("product_cate2")!=null?dataMap.get("product_cate2").toString():"";
        String cate3=dataMap.get("product_cate3")!=null?dataMap.get("product_cate3").toString():"";
        String related=dataMap.get("product_related")!=null?dataMap.get("product_related").toString():"";
        String google_img_sum=dataMap.get("google_img_sum")!=null?dataMap.get("google_img_sum").toString():"";
        String product_review=dataMap.get("product_review")!=null?dataMap.get("product_review").toString():"";
        product_review=product_review.replace("###",",");
        String imagedes=dataMap.get("desimage")!=null?dataMap.get("desimage").toString():"";
        String cate2url=  "/"+secondname+"?" + nexturl;
        String cate3url= "/"+secondname+"?" + url;
        //String cate4url= "/?" + url;
        //String cate4url= "/?" + url;
        String googleImage=dataMap.get("google_image_keyword")!=null?dataMap.get("google_image_keyword").toString():"";
        String product_price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
        String youtube_keywords=dataMap.get("youtube_keywords")!=null?dataMap.get("youtube_keywords").toString():"";
        String yourutbevideo=dataMap.get("ybvideo")!=null?dataMap.get("ybvideo").toString():"";
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
            String hotkey="";
            if (hotkeyflag) {
                 hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, randint);
            }else{
                //随机热销次
                int randhotkey= (int) (Math.random()*hotkeys.size());
                 hotkey=hotkeys.get(randhotkey).toString();
            }
        String title=productname;
        String h1name=productname;
        String meta_keywords=cate2+","+productname+","+cate3;
        String serverName=hostName.replaceAll("http://|https://","");
        related=related.replace("Ω","<br>");
        String products_description=des+related ;
        products_description=products_description.replace("<p>","");
        products_description=products_description.replace("</p>","");
        products_description=products_description.replace("<br>","");
        products_description=products_description.replace("<tr>","");
        products_description=products_description.replace("</tr>","");
        products_description=products_description.replace("<style>","");
        products_description=products_description.replace("<b>","");
        products_description=products_description.replace("</b>","");
        products_description=products_description.replace("</style>","");
        products_description=products_description.replace("</style>","");
        products_description=products_description.replace("<th>","");
        products_description=products_description.replace("</th>","");
        products_description=products_description.replace("<table border=\"1\">","");
        products_description=products_description.replace("</table>","");
        products_description=products_description.replaceAll("<table([\\s\\S]*?)>","");
        String classname1=Getsitemapurl.MD5("class1");
        String classname2=Getsitemapurl.MD5("class2");
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
        html=html.replaceAll("\\{#classname1}",classname1);
        html=html.replaceAll("\\{#classname2}",classname2);
        //html=html.replace("{#cate4url}",hostName+cate4url);
        //html=html.replace("{#cate4}",productname);
        ArrayList alst;
        int urlCount;
        html=html.replaceAll("\\{#products_image_url}", Matcher.quoteReplacement(mainimage));
        html=html.replaceAll("\\{#meta_title}", Matcher.quoteReplacement(title));
        //html=html.replace("{#h1title}","<h1>"+h1name+"</h1>");
        html=html.replace("{#h1title1}","<h1>"+h1name+"</h1>");
        int shellcount=Textmysql.ASCIcount(hostName);
        int cc=shellcount%20+1;
        String randname=Textmysql.rand(cc%4+1);
        //填充yahoo
        //随机标签
            String [] taghead={"<li>", "<span>", "<div>",  "<div><P>", "<div><span>", "<div><strong>", "<div><br>", "<div><td>", "<div><hr>", "<div><ul><li>",  "<div><span><p><strong>"};
            String [] tagend={"</li>", "</span>", "</div>",  "</p></div>", "</span></div>", "</strong></div>", "</br></div>", "</td></div>", "</hr></div>", "</li></ul></div>", "</strong></p></span></div>"};
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
            youtube_keywords=youtube_keywords.replace("Φfenge","");
            html = html.replace("{#youtube}", taghead[shellcount%6] + youtube_keywords + tagend[shellcount%6]+yourutbevideo);
        String subproductnam="";
        subproductnam=products_description.substring(0,products_description.length()/3);
        String meta=subproductnam.replaceAll("<([\\s\\S]*?)>","");
        String meta_description=product_price+"円"+hotkey+","+productname+","+cate2+cate3+meta+"-"+serverName;
        //html=html.replace("<meta name=\"keywords\" content=\"{#meta_keywords}\" />","");
        html=html.replace("{#meta_keywords}",meta_keywords);
        html=html.replace("{#meta_description}",meta_description);
        html = html.replace("{#products_description}", products_description);
        //html = html.replace("{#products_image}", products_imagehtml);
        //html = html.replace("{#breadcrumbs}",breadcrumbshtml);
        html=html.replace("{#products_name}",productname);
        html=html.replace("{#current_url}",hostName+"/"+secondname+"?"+url);
        html=html.replace("{#hosturl}",hostName+"/"+secondname+"?"+url);
        html=html.replace("{#google_images}",gimges+"<br>"+related);
        html=html.replace("{#google_title}",gtitle);
        html=html.replaceAll("\\{#randnumber1}", String.valueOf(cc));
        html=html.replaceAll("\\{#randname}", randname);
        html=html.replaceAll("\\{#google_img_sum}",google_img_sum);
        String shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
        if (product_review!="")
        html=html.replace("{#reviews}",product_review);
        else  html=html.replace("{#reviews}","");
            html=html.replace("{#domain}","");
            //html = html.replace("{#youtube}","");
            //列表
        String json = Getsitemapurl.productJson(productname, des,
                mainimage, product_price, hostName + "/"+secondname+"?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json9",imagedes);
        //产品
//        String json1 = Getsitemapurl.productJson(productname, des,
//                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json8",imagedes);
            html = html.replace("{#json_data}", "<script type=\"application/ld+json\">\n"+json+"\n"+"</script>");

            //获取a连接
        urlCount=Getsitemapurl.urlCount(html,"{#products_url}");
        alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal);
            String [] urls=html.split("\\{#products_url}");
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
                //String temp="\""+hostName+"/?"+url+shellflag+"\""+"title=\""+productname+"\" >"+productname;
                //cc模板
                //String temp="<a href=\"/?"+url+shellflag+"\""+" title=\""+productname+"\" >"+productname+"</a>";
                //mertem
                String temp=hostName+"/"+secondname+"?"+url+shellflag;
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
