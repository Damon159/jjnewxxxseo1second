package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Html_2_4_f_3_old160new {
    public static String setHtml(Map dataMap,String url,String nexturl,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList,String style,String randurl,String brorginurl){
        try {
            String productname = dataMap.get("product_name") != null ? dataMap.get("product_name").toString() : "";
            String des = dataMap.get("product_description") != null ? dataMap.get("product_description").toString() : "";
            String mainimage = dataMap.get("product_main_img") != null ? dataMap.get("product_main_img").toString() : "";
            //String cate1 = dataMap.get("product_cate1") != null ? dataMap.get("product_cate1").toString() : "ホーム";
            String cate2 = dataMap.get("product_cate2") != null ? dataMap.get("product_cate2").toString() : "";
            String cate3 = dataMap.get("product_cate3") != null ? dataMap.get("product_cate3").toString() : "";
            String related = dataMap.get("product_related") != null ? dataMap.get("product_related").toString() : "";
            String google_img_sum = dataMap.get("google_img_sum") != null ? dataMap.get("google_img_sum").toString() : "";
            String product_review = dataMap.get("product_review") != null ? dataMap.get("product_review").toString() : "";
            product_review = product_review.replace("###", ",");
            String imagedes = dataMap.get("desimage") != null ? dataMap.get("desimage").toString() : "";
            String cate2url = "/" + secondname  + nexturl ;
            String cate3url = "/" + secondname  + url ;
            //String cate4url = "/" + secondname  + url + ".html";
            String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//描述词
            String googleImage = dataMap.get("google_image_keyword") != null ? dataMap.get("google_image_keyword").toString() : "";
            String product_price = dataMap.get("product_price") != null ? dataMap.get("product_price").toString() : "";
            String youtube_keywords = dataMap.get("youtube_keywords") != null ? dataMap.get("youtube_keywords").toString() : "";
            String yourutbevideo = dataMap.get("ybvideo") != null ? dataMap.get("ybvideo").toString() : "";
//            String gimges = "";
//            String gtitle = "";
            int urlcounta = Textmysql.ASCIcount(url);
            int shellcount = Textmysql.ASCIcount(hostName);
            int secondcount=Textmysql.ASCIcount(secondname);
//            if (!googleImage.equals("")) {
//                String[] goose = googleImage.split("###");
//                if (goose.length > 0) {
//                    gimges = goose[1];
//                    gtitle = goose[0];
//                    gtitle = gtitle.replace("Φfenge", ",");
//                }
//            }
            int hucountall=Textmysql.ASCIcount(brorginurl);
            String gokeyall="";
            String yakeyall="";
            String yadesall="";
            String youutbekey="";
            String gimges="";
            if (!yahoo_keys.equals("")&&yahoo_keys.indexOf("Φfenge")>0) {
                String yahkes[] =yahoo_keys.split("Φfenge");
                int ga=hucountall%6+7;
                int ccbb=hucountall;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+1;
                    int avv=ccbb%yahkes.length;
                    yakeyall+=yahkes[avv];
                }
            }
            if (!yahoo_des.equals("")&&yahoo_des.indexOf("Φfenge")>0) {
                String yahdes[] =yahoo_des.split("Φfenge");
                int ga=hucountall%5+7;
                int ccbb=hucountall;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+2;
                    int avv=ccbb%yahdes.length;
                    yadesall+=yahdes[avv];
                }
            }
            if (!googleImage.equals("")&&googleImage.indexOf("###")>0) {
                String temp1[] =googleImage.split("###");
                gimges=temp1[1];
                String googles[] =temp1[0].split("Φfenge|,");
                int ga=hucountall%2+3;
                int ccbb=hucountall;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+3;
                    int avv=ccbb%googles.length;
                    gokeyall+=googles[avv];
                }
            }

            if (!youtube_keywords.equals("")&&youtube_keywords.indexOf("Φfenge")>0) {
                String youtubess[] =youtube_keywords.split("Φfenge|,");
                int ga=hucountall%15+5;
                int ccbb=hucountall;
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

            String meta_keywords = cate2 + "," + productname + "," + cate3;
            String serverName = hostName.replaceAll("http://|https://", "");
            related = related.replace("Ω", "<br>");
            //youtube_keywords = youtube_keywords.replace("Φfenge", "");
            String desstr;
            if (secondcount>63&&!yadesall.equals("")&&(hucountall%2)==0){
                    desstr=yadesall;
            }else if(secondcount<=63&&!yakeyall.equals("")&&(hucountall%2)==0) {
                    desstr=yakeyall;
            }else {
                desstr=youutbekey;
            }

            String products_description = desstr + "<strong>" + product_price + "円" + productname + "</strong>" + des;
            String classname1 = Getsitemapurl.MD5("class1");
            String classname2 = Getsitemapurl.MD5("class2");
            html = html.replace("{#mainimage}", mainimage);
            html = html.replace("{#title}", productname);
            html = html.replace("{#product_model}", "");
            html = html.replace("{#product_price}", product_price);
            html = html.replace("{#cate1}", cate2);
            html = html.replace("{#cate1url}", hostName);
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
            html=html.replace("{#servername}",serverName);

            String color = Textmysql.color();
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
            html = html.replace("{#current_url}", hostName + "/" + secondname  + url );
            html = html.replace("{#hosturl}", hostName + "/" + secondname  + url );
            html = html.replace("{#google_images}", gimges + "<br>" + related);
            html = html.replace("{#google_title}", gokeyall);
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

            html = html.replace("{#domain}", "");
            String json = Getsitemapurl.productJson(productname, des, mainimage, product_price, hostName + "/" + secondname  + url + shellflag , hostName, (String)listJson.get(0), cate2url, cate3url, "", "ホーム", cate2, cate3, "json10", imagedes);
            html = html.replace("{#json_data}", json);
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
                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag ;
                        --acount;
                        htmlreplaceurls.append(urls[j]).append(temp);
                    } else if (bcount > 0) {
                        temp = "/" + secondname  + url ;
                        htmlreplaceurls.append(urls[j]).append(temp);
                        --bcount;
                    } else {
                        temp = "/" + secondname + url + shellflag ;
                        htmlreplaceurls.append(urls[j]).append(temp);
                    }
                }
            }

            html = htmlreplaceurls + urls[urls.length - 1];


            urls=html.split("\\{#relatedurl}");
            htmlreplaceurls = new StringBuilder();
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
                        String seconnew=secondname;
                        if (secondname.length()==1) {
                            seconnew = secondname.replace("?", "");
                        }
                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag ;
                        --acount;
                        urls[j+1]= urls[j+1].replace("{#yahootitle"+j+"}",map1.get("product_name").toString());
                        urls[j+1]= urls[j+1].replace("{#relatedpics"+j+"}",map1.get("product_main_img").toString());
                        urls[j+1]= urls[j+1].replace("{#label"+j+"}",map1.get("product_name").toString());
                        urls[j+1]= urls[j+1].replace("{#atl"+j+"}",map1.get("product_name").toString());
                        urls[j+1]= urls[j+1].replace("{#relatedprice"+j+"}",map1.get("product_price").toString());
                        urls[j+1]= urls[j+1].replace("{#item-name"+j+"}",map1.get("product_name").toString());
                        htmlreplaceurls.append(urls[j]).append(temp);

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
            Document doc = Jsoup.parse(html);
            return doc.toString();
//            String [] hhh=html.split("<head>");
//            if (hhh.length<2){
//                return html;
//            }else {
//                html = hhh[0] + "<head>\n" + style + hhh[1];
//                return html;
//            }
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
