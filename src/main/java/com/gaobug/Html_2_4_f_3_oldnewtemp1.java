package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class Html_2_4_f_3_oldnewtemp1 {
    public static String setHtml(Map dataMap,String url,String nexturl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList,String style,String randurl,String befou){
        try {
            String productname = dataMap.get("product_name") != null ? dataMap.get("product_name").toString() : "";
            String des = dataMap.get("product_description") != null ? dataMap.get("product_description").toString() : "";
            des=des.replaceAll("商品の情報","");
            String mainimage = dataMap.get("product_main_img") != null ? dataMap.get("product_main_img").toString() : "";
           // String cate1 = dataMap.get("product_cate1") != null ? dataMap.get("product_cate1").toString() : "ホーム";
            String cate2 = dataMap.get("product_cate2") != null ? dataMap.get("product_cate2").toString() : "";
            String cate3 = dataMap.get("product_cate3") != null ? dataMap.get("product_cate3").toString() : "";
            String related = dataMap.get("product_related") != null ? dataMap.get("product_related").toString() : "";
            String imagedes = dataMap.get("desimage") != null ? dataMap.get("desimage").toString() : "";
            String googleImage = dataMap.get("google_image_keyword") != null ? dataMap.get("google_image_keyword").toString() : "";
            String product_price = dataMap.get("product_price") != null ? dataMap.get("product_price").toString() : "";
            String youtube_keywords = dataMap.get("youtube_keywords") != null ? dataMap.get("youtube_keywords").toString() : "";
            String yourutbevideo = dataMap.get("ybvideo") != null ? dataMap.get("ybvideo").toString() : "";
            String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//描述词
             yourutbevideo = yourutbevideo.replace("853","420");
             yourutbevideo = yourutbevideo.replace("480","310");
            String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
            //String gimges = "";
            String gtitle = "";
            String cate1url="/" + secondname  + nexturl + ".html";
            String cate2url="/" + secondname  + randurl + ".html";
            String[] imgs=imagedes.split("Φfenge");
            int urlcounta = Textmysql.ASCIcount(befou);
            int secondcount = Textmysql.ASCIcount(secondname);
//            if (!googleImage.equals("")) {
//                String[] goose = googleImage.split("###");
//                if (goose.length > 0) {
//                    gimges = goose[1];
//                    gtitle = goose[0];
//                    gtitle = gtitle.replace("Φfenge", ",");
//                }
//            }
            //String gokeyall="";
            String yakeyall="";
            String yadesall="";
            String youutbekey="";
            String gimges="";
            if (!yahoo_keys.equals("")&&yahoo_keys.indexOf("Φfenge")>0) {
                String yahkes[] =yahoo_keys.split("Φfenge");
                int ga=urlcounta%6+7;
                int ccbb=urlcounta;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+1;
                    int avv=ccbb%yahkes.length;
                    yakeyall+=yahkes[avv];
                }
            }
            if (!yahoo_des.equals("")&&yahoo_des.indexOf("Φfenge")>0) {
                String yahdes[] =yahoo_des.split("Φfenge");
                int ga=urlcounta%5+7;
                int ccbb=urlcounta;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+2;
                    int avv=ccbb%yahdes.length;
                    yadesall+=yahdes[avv];
                }
            }
            if (!googleImage.equals("")&&googleImage.indexOf("###")>0) {
                String temp1[] =googleImage.split("###");
                gimges=temp1[1];
//                String googles[] =temp1[0].split("Φfenge|,");
//                int ga=urlcounta%2+3;
//                int ccbb=urlcounta;
//                for (int i = 0; i < ga; i++) {
//                    ccbb=ccbb+3;
//                    int avv=ccbb%googles.length;
//                    gokeyall+=googles[avv];
//                }
            }

            if (!youtube_keywords.equals("")&&youtube_keywords.indexOf("Φfenge")>0) {
                String youtubess[] =youtube_keywords.split("Φfenge|,");
                int ga=urlcounta%15+5;
                int ccbb=urlcounta;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+4;
                    int avv=ccbb%youtubess.length;
                    youutbekey+=youtubess[avv];
                }
            }
            String hotkey = "";
            if (hotkeyflag) {
                hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, urlcounta);
            } else {
                int randhotkey = (int)(Math.random() * (double)hotkeys.size());
                hotkey = hotkeys.get(randhotkey).toString();
            }
            String serverName = hostName.replaceAll("http://|https://", "");
            String title=productname+" "+serverName;
            String meta_keywords = cate2 + "," + productname + "," + cate3;
            related = related.replace("Ω", "<br>");
//            youtube_keywords = youtube_keywords.replace("Φfenge", "");
            String desstr;
            if (secondcount>63&&!yadesall.equals("")&&(urlcounta%2)==0){
                desstr=yadesall;
            }else if(secondcount<=63&&!yakeyall.equals("")&&(urlcounta%2)==0) {
                desstr=yakeyall;
            }else {
                desstr=youutbekey;
            }
            double pp=Double.parseDouble(product_price);
            int ppin= (int) pp;
            product_price=String.valueOf(ppin);
            String products_description = desstr + "<strong>" + product_price + "円" + productname + "</strong>" + des;
//            products_description=products_description.replaceAll("<([\\s\\S]*?)>", Matcher.quoteReplacement(""));
//            products_description=products_description.replace("<p>","");
//            products_description=products_description.replace("</p>","");
            String classname1 = Getsitemapurl.MD5("class1");
            //String classname2 = Getsitemapurl.MD5("class2");
            String desimge="";
            for (int i = 0; i < imgs.length; i++) {
                String str="<div class=\"imagAdd\"><img loading=\"lazy\" alt=\""+productname+" - "+i+"\" src=\""+imgs[i]+"\" /></div>";
                desimge+=str;
            }
            html = html.replace("{#title}", title);
            html = html.replace("{#product_model}", product_model);
            html = html.replace("{#product_price}", product_price);
            int nromalprice= (int) (ppin*1.33);
            html = html.replace("{#normalprice}", String.valueOf(nromalprice));
            html = html.replace("{#cate1url}",  cate1url);
            html = html.replace("{#cate2url}", cate2url);
            html = html.replace("{#cate1}", cate2);
            //html = html.replace("{#cate3url}", hostName + cate3url);
            html = html.replace("{#cate2}", cate3);
            //html = html.replaceAll("\\{#cate\\d+}", Matcher.quoteReplacement(cate3));
            //html=html.replace("{#cate4url}",cate4url);
            //html=html.replace("{#cate4}",productname);
            html = html.replace("{#classname1}", classname1);
            //html = html.replace("{#classname2}", classname2);
            html = html.replace("{#mainimage}", mainimage);
            html = html.replace("{#meta_title}", productname);
            //html = html.replace("{#h1title1}", productname );
            //html = html.replace("%h1title%",   productname );
            html = html.replace("{#product_name}",   productname );
            String meta_description = product_price + "円" + hotkey + "," + productname + "," + cate2 + cate3 + "-" + serverName;
            html = html.replace("{#meta_keywords}", meta_keywords);
            html = html.replace("{#meta_description}", meta_description);
            html = html.replace("{#products_description}", products_description);
            html = html.replace("{#products_name}", productname);
            html = html.replace("{#current_url}", hostName + "/" + secondname  + url + ".html");
            html = html.replace("{#google_images}", gimges + "<br>" + related);
            html = html.replace("{#youtubevideo}", yourutbevideo);
            html = html.replace("{#googletitle}", gtitle);
            html=html.replace("{#servername}",serverName);
            html=html.replace("{#servernamerss}",serverName.toUpperCase());
            html=html.replace("{#category}",cate2+cate3);
            html=html.replace("{#sitemap}",secondname+"sitemap" + (urlcounta % 30 + 1) + ".xml");
            html=html.replace("{#rss}","/"+secondname+"rss.xml");
            html=html.replace("{#desimge}",desimge);
            int tta = urlcounta%40+6;
            html = html.replace("{#reviews}", String.valueOf(tta));
            tta=tta+4;
            html = html.replace("{#like}", String.valueOf(tta));
            tta=tta+5;
            html = html.replace("{#message}", String.valueOf(tta));
            String shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
            String json11="{\"@context\":\"https://schema.org/\",\"@type\":\"Product\",\"name\":\"{#product_name}\",\"image\":\"{#mainimage}\",\"description\":\"{#products_description}\",\"offers\":{\"@type\":\"Offer\",\"url\":\"{#current_url}\",\"priceCurrency\":\"JPY\",\"price\":\"{#product_price}\",\"availability\":\"https://schema.org/InStock\",\"itemCondition\":\"https://schema.org/NewCondition\"},\"aggregateRating\":{\"@type\":\"AggregateRating\",\"ratingValue\":4.3,\"reviewCount\":30},\"category\":\"{#category}\"}";
            JSONObject googlejson=new JSONObject(json11);
            String descriptionjson=products_description.replaceAll("<([\\s\\S]*?)>", Matcher.quoteReplacement(""));
            descriptionjson=descriptionjson.replace("<p>","");
            descriptionjson=descriptionjson.replace("</p>","");
            googlejson.put("name",productname);
            googlejson.put("image",mainimage);
            googlejson.put("description",descriptionjson);
            googlejson.getJSONObject("offers").put("url",url);
            googlejson.getJSONObject("offers").put("price",ppin);
            int aaa=Textmysql.ASCIcount(url)%10;
            String ratingValue="4."+aaa;
            pp=Double.parseDouble(ratingValue);
            int count=aaa%30+7;
            googlejson.getJSONObject("aggregateRating").put("ratingValue",pp);
            googlejson.getJSONObject("aggregateRating").put("reviewCount",count);
            googlejson.put("category",cate2+cate3);
            html = html.replace("{#json_data}", googlejson.toString());
            String json12="{\"@context\":\"https://schema.org/\",\"@type\":\"Product\",\"name\":\"\",\"image\":\"\",\"description\":\"\",\"sku\":\"aRs9z24904\",\"mpn\":\"aRs9z24904\",\"review\":{\"@type\":\"Review\",\"reviewRating\":{\"@type\":\"Rating\",\"ratingValue\":\"4\",\"bestRating\":\"5\"},\"author\":{\"@type\":\"Person\",\"name\":\"Iotn Ttwxw\"}},\"aggregateRating\":{\"@type\":\"AggregateRating\",\"ratingValue\":\"4.9\",\"reviewCount\":\"25\"}}";
            googlejson=new JSONObject(json12);
            googlejson.put("name",productname);
            googlejson.put("image",mainimage);
            googlejson.put("description",descriptionjson);
            int cct=urlcounta*15;
            int ttc=urlcounta*20;
            googlejson.put("sku",""+urlcounta+cct+ttc);
            googlejson.put("mpn",""+urlcounta+cct+ttc);
            googlejson.getJSONObject("review").getJSONObject("reviewRating").put("ratingValue",ratingValue);
            googlejson.getJSONObject("review").getJSONObject("author").put("name",serverName);
            googlejson.getJSONObject("aggregateRating").put("ratingValue",pp);
            googlejson.getJSONObject("aggregateRating").put("reviewCount",count);
           String googlejson1=Getsitemapurl.formatJson(googlejson.toString());

            // html = html.replace("{#domain}", "");
            //String json = Getsitemapurl.productJson(productname, products_description, mainimage, product_price, hostName + "/" + secondname  + url + shellflag + ".html", hostName, (String)listJson.get(0), cate2url, cate1url, "", "ホーム", cate2, cate3, "json11", imagedes);


            //String json1 = Getsitemapurl.productJson(productname, products_description, mainimage, product_price, hostName + "/" + secondname  + url + shellflag + ".html", hostName, (String)listJson.get(0), cate2url, cate1url, "", "ホーム", cate2, cate3, "json12", imagedes);
            html = html.replace("{#json_data1}", googlejson1);
            int urlCount = Getsitemapurl.urlCount(html, "{#products_url}");
            ArrayList alst = Getsitemapurl.getLastnew(url, idurlmap, urlCount);
            String[] urls = html.split("\\{#products_url}");
            StringBuilder htmlreplaceurls = new StringBuilder();
            int acount = urlcounta % 10 + 10;
            int bcount = urlcounta % 10 + 10;
            for(int j = 0; j < urls.length - 1; ++j) {
                String temp = "";
                int a = (int)(Math.random() * (double)secondarrayList.size());
                String secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
                url = (String)alst.get(j);
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
                if (content.size() >= 2) {
                    url = (String)content.get(1);
                    Map map1 = (Map)JSON.parse((String)content.get(0));
                    String productnameurl="";
                    String imgeurl="";
                    int price = 0;
                    int saleprice = 0;
                    if (map1.get("product_name") != null) {
                        productnameurl=map1.get("product_name").toString();
                        imgeurl=map1.get("product_main_img").toString();
                        saleprice= (int) (ppin*1.25);
                    }
                    if (urls[j].contains("{#relatedimg")) {
                        urls[j] = urls[j].replace("{#relatedimg}", imgeurl);
                        urls[j] = urls[j].replace("{#relatedname}", productnameurl);
                        urls[j] = urls[j].replace("{#normalprice}", String.valueOf(price));
                        urls[j] = urls[j].replace("{#saleprice}", String.valueOf(saleprice));
                    }
                    shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
                    if (acount > 0) {
                        String seconnew=secondname;
                        if (secondname.length()==1) {
                             seconnew = secondname.replace("?", "");
                        }
                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag + ".html\""+" title=\""+productnameurl;
                        --acount;
                        htmlreplaceurls.append(urls[j]).append(temp);
                    } else if (bcount > 0) {
                        temp = "/" + secondname  + url + ".html\""+" title=\""+productnameurl;
                        htmlreplaceurls.append(urls[j]).append(temp);
                        --bcount;
                    } else {
                        temp = "/" + secondname + url + shellflag + ".html\""+" title=\""+productnameurl;
                        htmlreplaceurls.append(urls[j]).append(temp);
                    }
                }

            }

            html = htmlreplaceurls + urls[urls.length - 1];

//            for (int i = 5; i > 0; i--) {
//                url= (String) alst.get(alst.size()-i);
//                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
//                if (content == null || content.size() < 2)
//                    continue;
//                url = (String) content.get(1);
//                Map map1 = (Map) JSON.parse((String) content.get(0));
//                productname = map1.get("product_name") != null ? map1.get("product_name").toString() : "";
//                int aa=5-i;
//                html=html.replace("{#newproducts_url"+aa+"}","<a title=\"" + Matcher.quoteReplacement(productname) + "\" href=\"" + "/" + secondname + url + ".html" + "\" style=\"color:#" + color + "\">" + Matcher.quoteReplacement(productname) + "</a>");
//            }
//            Document doc = Jsoup.parse(html);
//            return doc.toString();
//            String [] hhh=html.split("\\{#style}");
//            if (hhh.length<2){
//                html=html.replace("{#style}","");
//                return html;
//            }else {
//                html = hhh[0]  + style + hhh[1];
//                return html;
//            }
            return html;
        } catch (Exception var63) {
            //Document doc = Jsoup.parse(html);
            var63.printStackTrace();
            return html.toString();
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
