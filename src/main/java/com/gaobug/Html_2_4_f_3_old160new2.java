package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class Html_2_4_f_3_old160new2 {
    public static String setHtml(Map dataMap,String url,String nexturl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList,String style,String randurl){
        try {
            String productname = dataMap.get("product_name") != null ? dataMap.get("product_name").toString() : "";
            String des = dataMap.get("product_description") != null ? dataMap.get("product_description").toString() : "";
            String mainimage = dataMap.get("product_main_img") != null ? dataMap.get("product_main_img").toString() : "";
            String cate1 = dataMap.get("product_cate1") != null ? dataMap.get("product_cate1").toString() : "ホーム";
            String cate2 = dataMap.get("product_cate2") != null ? dataMap.get("product_cate2").toString() : "";
            String cate3 = dataMap.get("product_cate3") != null ? dataMap.get("product_cate3").toString() : "";
            String related = dataMap.get("product_related") != null ? dataMap.get("product_related").toString() : "";
            String google_img_sum = dataMap.get("google_img_sum") != null ? dataMap.get("google_img_sum").toString() : "";
            String product_review = dataMap.get("product_review") != null ? dataMap.get("product_review").toString() : "";
            String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
            product_review = product_review.replace("###", ",");
            String imagedes = dataMap.get("desimage") != null ? dataMap.get("desimage").toString() : "";
            String cate1url = "/" + secondname  + randurl + ".html";
            String cate2url = "/" + secondname  + nexturl + ".html";
            String cate3url = "/" + secondname  + url + ".html";
            //String cate4url = "/" + secondname  + url + ".html";
            String googleImage = dataMap.get("google_image_keyword") != null ? dataMap.get("google_image_keyword").toString() : "";
            String product_price = dataMap.get("product_price") != null ? dataMap.get("product_price").toString() : "";
            String youtube_keywords = dataMap.get("youtube_keywords") != null ? dataMap.get("youtube_keywords").toString() : "";
            String yourutbevideo = dataMap.get("ybvideo") != null ? dataMap.get("ybvideo").toString() : "";
            String gimges = "";
            String gtitle = "";
            int urlcounta = Textmysql.ASCIcount(url);
            if (!googleImage.equals("")) {
                String[] goose = googleImage.split("###");
                if (goose.length > 0) {
                    gimges = goose[1];
                    gtitle = goose[0];
                    gtitle = gtitle.replace("Φfenge", ",");
                }
            }

            String hotkey = "";
            if (hotkeyflag) {
                hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, urlcounta);
            } else {
                int randhotkey = (int)(Math.random() * (double)hotkeys.size());
                hotkey = hotkeys.get(randhotkey).toString();
            }

            String meta_keywords = cate2 + "," + productname + "," + cate3;
            String serverName = hostName.replaceAll("http://|https://", "");
            related = related.replace("Ω", "<br>");
            youtube_keywords = youtube_keywords.replace("Φfenge", "");
            String products_description = youtube_keywords + "<em>" + product_price + "円" + productname + "</em>" + des;
            String classname1 = Getsitemapurl.MD5("class1");
            String classname2 = Getsitemapurl.MD5("class2");
            html = html.replace("{#mainimage}", mainimage);
            html = html.replace("{#title}", productname);
            html = html.replace("{#product_model}", "");
            html = html.replace("{#product_price}", product_price);
            html = html.replace("{#cate1}", cate2);
            html = html.replace("{#cate1url}", cate1url);
            html = html.replace("{#cate2url}", hostName + cate2url);
            html = html.replace("{#cate2}", cate3);
            html = html.replace("{#cate3url}", hostName + cate3url);
            html = html.replace("{#cate3}", productname);
            html = html.replace("{#classname1}", classname1);
            html = html.replace("{#classname2}", classname2);
            html = html.replace("{#products_image_url}", mainimage);
            html = html.replace("{#meta_title}", productname);
            html = html.replace("{#h1title1}", productname );
            //html = html.replace("%h1title%",   productname );
            html = html.replace("{#product_name}",   productname );
            int shellcount = Textmysql.ASCIcount(hostName);
            String color = Textmysql.colorid(urlcounta+shellcount);
            int cc = shellcount % 20 + 1;
            String randname = Textmysql.rand(cc % 4 + 1);
            html = html.replace("{#youtube}", yourutbevideo);
            String meta_description = product_price + "円" + hotkey + "," + productname + "," + cate2 + cate3 + "-" + serverName;
            html = html.replace("{#meta_keywords}", meta_keywords);
            html = html.replace("メルカリ", serverName);
            html = html.replaceAll("opensearch\\.xml", secondname+"sitemap" + (shellcount % 30 + 1) + ".xml");
            html = html.replace("{#color}", color);
            html = html.replace("{#meta_description}", meta_description);
            html = html.replace("{#products_description}", products_description);
            html = html.replace("{#products_name}", productname);
            html = html.replace("{#current_url}", hostName + "/" + secondname  + url + ".html");
            html = html.replace("{#hosturl}", hostName + "/" + secondname  + url + ".html");
            html = html.replace("{#google_images}", gimges + "<br>" + related);
            html = html.replace("{#google_title}", gtitle);
            html = html.replace("{#randnumber1}", String.valueOf(cc));
            html = html.replace("{#randname}", randname);
            html = html.replace("{#google_img_sum}", google_img_sum);
            html=html.replace("{#category}",cate2+cate3);
            String shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
            if (product_review != "") {
                html = html.replace("{#reviews}", product_review);
            } else {
                html = html.replace("{#reviews}", "");
            }
            String json="{\"@context\":\"https://schema.org/\",\"@type\":\"Product\",\"productID\":\"\",\"image\":[],\"name\":\"\",\"description\":\"\",\"brand\":\"\",\"offers\":{\"@type\":\"Offer\",\"url\":\"\",\"availability\":\"https://schema.org/InStock\",\"price\":57000,\"priceCurrency\":\"JPY\"}}";
            JSONObject googlejson=new JSONObject(json);
            String [] arrayimge=imagedes.split("Φfenge");
            String descriptionjson=products_description.replaceAll("<([\\s\\S]*?)>", Matcher.quoteReplacement(""));
            descriptionjson=descriptionjson.replace("<p>","");
            descriptionjson=descriptionjson.replace("</p>","");
            googlejson.put("productID",product_model);
            googlejson.put("name",productname);
            googlejson.put("brand",cate2);
            googlejson.getJSONArray("image").putAll(arrayimge);
            googlejson.put("description",descriptionjson);
            googlejson.getJSONObject("offers").put("url",url);
            double pp=Double.parseDouble(product_price);
            int ppin= (int) pp;
            googlejson.getJSONObject("offers").put("price",ppin);
            html = html.replace("{#domain}", "");
            //String json = Getsitemapurl.productJson(productname, des, mainimage, product_price, hostName + "/" + secondname  + url + shellflag + ".html", hostName, (String)listJson.get(0), cate2url, cate3url, "", cate1, cate2, cate3, "json11", imagedes);
            html = html.replace("{#json_data}", googlejson.toString());
            int urlCount = Getsitemapurl.urlCount(html, "{#products_url}");
            ArrayList alst = Getsitemapurl.getLastnew(url, idurlmap, urlCount+5);
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
                    if (map1.get("product_name") != null) {
                        map1.get("product_name").toString();
                    } else {
                        String var10000 = "";
                    }

                    shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
                    if (acount > 0) {
                        String seconnew=secondname;
                        if (secondname.length()==1) {
                             seconnew = secondname.replace("?", "");
                        }
                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag + ".html";
                        --acount;
                        htmlreplaceurls.append(urls[j]).append(temp);
                    } else if (bcount > 0) {
                        temp = "/" + secondname  + url + ".html";
                        htmlreplaceurls.append(urls[j]).append(temp);
                        --bcount;
                    } else {
                        temp = "/" + secondname + url + shellflag + ".html";
                        htmlreplaceurls.append(urls[j]).append(temp);
                    }
                }
            }

            html = htmlreplaceurls + urls[urls.length - 1];
            for (int i = 5; i > 0; i--) {
                url= (String) alst.get(alst.size()-i);
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
                if (content == null || content.size() < 2)
                    continue;
                url = (String) content.get(1);
                Map map1 = (Map) JSON.parse((String) content.get(0));
                productname = map1.get("product_name") != null ? map1.get("product_name").toString() : "";
                int aa=5-i;
                html=html.replace("{#newproducts_url"+aa+"}","<a title=\"" + Matcher.quoteReplacement(productname) + "\" href=\"" + "/" + secondname + url + ".html" + "\" style=\"color:#" + color + "\">" + Matcher.quoteReplacement(productname) + "</a>");
            }
//            Document doc = Jsoup.parse(html);
//            return doc.toString();
//            String [] hhh=html.split("<head>");
//            if (hhh.length<2){
//                return html;
//            }else {
//                html = hhh[0] + "<head>\n" + style + hhh[1];
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
