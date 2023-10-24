package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Html_231010 {
    public static String setHtml(Map dataMap,String url,String cate2url,String hostName,String html,
                                 ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList,String style,String cate3url,String brorginurl){
        try {
            String productname = dataMap.get("product_name") != null ? dataMap.get("product_name").toString() : "";
            String des = dataMap.get("product_description") != null ? dataMap.get("product_description").toString() : "";
            String mainimage = dataMap.get("product_main_img") != null ? dataMap.get("product_main_img").toString() : "";
            String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
            //String cate1 = dataMap.get("product_cate1") != null ? dataMap.get("product_cate1").toString() : "ホーム";
            String cate2 = dataMap.get("product_cate2") != null ? dataMap.get("product_cate2").toString() : "";
            String cate3 = dataMap.get("product_cate3") != null ? dataMap.get("product_cate3").toString() : "";
            String related = dataMap.get("product_related") != null ? dataMap.get("product_related").toString() : "";
            String google_img_sum = dataMap.get("google_img_sum") != null ? dataMap.get("google_img_sum").toString() : "";
            String product_review = dataMap.get("product_review") != null ? dataMap.get("product_review").toString() : "";
            product_review = product_review.replace("###", ",");
            String imagedes = dataMap.get("desimage") != null ? dataMap.get("desimage").toString() : "";
             cate2url = "/" + secondname  + cate2url ;
             cate3url = "/" + secondname  + cate3url ;
            String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//描述词
            String googleImage = dataMap.get("google_image_keyword") != null ? dataMap.get("google_image_keyword").toString() : "";
            String product_price = dataMap.get("product_price") != null ? dataMap.get("product_price").toString() : "";
            String youtube_keywords = dataMap.get("youtube_keywords") != null ? dataMap.get("youtube_keywords").toString() : "";
            String yourutbevideo = dataMap.get("ybvideo") != null ? dataMap.get("ybvideo").toString() : "";
            String gimges = "";
            String gtitle = "";
            //int urlcounta = Textmysql.ASCIcount(url);
            int hucountall=Textmysql.ASCIcount(brorginurl);
            int shellcount = Textmysql.ASCIcount(hostName);
            //int secondcount=Textmysql.ASCIcount(secondname);
            int priceint= (int) (Double.valueOf(product_price).intValue());
            int orprice= (int) (Double.valueOf(product_price).intValue()*1.3);
            DecimalFormat decimalFormat=new DecimalFormat("0");
            String formalintprice=decimalFormat.format(new BigDecimal(priceint));
            String formalintorprice=decimalFormat.format(new BigDecimal(orprice));
            String serverName = hostName.replaceAll("http://|https://", "");
            String [] zones={"三富英樹","乗松伸幸","濱野彰士","増田眞治","福崎桂一郎","前島一弓","小林衣里","坂本晴彦","阿保君枝","平井博之","青柳仁司","行方宏至","松下直美","土門大幸","大堀達則","中原賢治","酒井幸太郎","篠原剛","池谷義紀","山崎一男","淀川美和","新井正巳","渡邊聖也","清水和洋","早川哲二","浅見博美","寺田政登","谷澤篤","有働順子","倭秀樹","森永義正","齊藤淳","林忠雄","伊藤弘宣","冨澤忠","北澤聖吾","寺尾秀司","日野剛","増田茂","田中淳一","若月賢一","渡辺剛史","鈴木和美","小澤太一","高橋英太","中嶋進","高江洲義経","石川信二","池田遵","岡一美"};
            String [] tags=productname.split(" |,|，|\\.|。|　");
            if (!googleImage.equals("")) {
                String[] goose = googleImage.split(",###");
                if (goose.length > 0) {
                    gimges = goose[1];
                    //gtitle = goose[0];
                    //gtitle = gtitle.replace("Φfenge", ",");
                }
            }
            String hotkey = "";
            if (hotkeyflag) {
                hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, hucountall);
            } else {
                int randhotkey = (int)(Math.random() * (double)hotkeys.size());
                hotkey = hotkeys.get(randhotkey).toString();
            }

            String gokeyall="";
            String yakeyall="";
            String yadesall="";
            String youutbekey="";
            String title=productname+" "+hotkey ;
            String metatitle=productname+" > "+cate2+" > "+cate3;
            String meta_description = title + " " + productname + " " + cate2 + cate3 + " " + serverName;
            String description=des.substring(0,des.indexOf("<img"));
            String imges=des.substring(des.indexOf("<img"),des.lastIndexOf(">")+1);

//
//            if (!yahoo_keys.equals("")&&yahoo_keys.indexOf("Φfenge")>0) {
//                String yahkes[] =yahoo_keys.split("Φfenge");
//                int ga=hucountall%6+7;
//                int ccbb=hucountall;
//                for (int i = 0; i < ga; i++) {
//                    ccbb=ccbb+1;
//                    int avv=ccbb%yahkes.length;
//                    yakeyall+=yahkes[avv];
//                }
//            }
//            if (!yahoo_des.equals("")&&yahoo_des.indexOf("Φfenge")>0) {
//                String yahdes[] =yahoo_des.split("Φfenge");
//                int ga=hucountall%5+7;
//                int ccbb=hucountall;
//                for (int i = 0; i < ga; i++) {
//                    ccbb=ccbb+2;
//                    int avv=ccbb%yahdes.length;
//                    yadesall+=yahdes[avv];
//                }
//            }
//            if (!googleImage.equals("")&&googleImage.indexOf("###")>0) {
//                String temp1[] =googleImage.split("###");
//                gimges=temp1[1];
//                String googles[] =temp1[0].split("Φfenge|,");
//                int ga=hucountall%2+3;
//                int ccbb=hucountall;
//                for (int i = 0; i < ga; i++) {
//                    ccbb=ccbb+3;
//                    int avv=ccbb%googles.length;
//                    gokeyall+=googles[avv];
//                }
//            }

//            if (!youtube_keywords.equals("")&&youtube_keywords.indexOf("Φfenge")>0) {
//                String youtubess[] =youtube_keywords.split("Φfenge|,");
//                int ga=hucountall%15+5;
//                int ccbb=hucountall;
//                for (int i = 0; i < ga; i++) {
//                    ccbb=ccbb+4;
//                    int avv=ccbb%youtubess.length;
//                    youutbekey+=youtubess[avv];
//                }
//            }


           // String meta_keywords = title +" "+" > " + cate2+" > "+cate3;

            //related = related.replace("Ω", "<br>");
            //youtube_keywords = youtube_keywords.replace("Φfenge", "");
//            String desstr;
//            if (secondcount>63&&!yadesall.equals("")&&(hucountall%2)==0){
//                    desstr=yadesall;
//            }else if(secondcount<=63&&!yakeyall.equals("")&&(hucountall%2)==0) {
//                    desstr=yakeyall;
//            }else {
//                desstr=youutbekey;
//            }

            //String classname1 = Getsitemapurl.MD5("class1");
            //String classname2 = Getsitemapurl.MD5("class2");
            html = html.replace("{#mainimage}", mainimage);
            html = html.replace("{#title}", title);
            html = html.replace("{#product_model}", "");
            html = html.replace("{#price}", formalintprice);
//            html = html.replace("{#cate1}", cate2);
//            html = html.replace("{#cate1url}", hostName);
            html = html.replace("{#cate2url}", hostName + cate2url);
            html = html.replace("{#cate2}", cate2);
            html = html.replace("{#cate3url}", hostName + cate3url);
            html = html.replace("{#cate3}", cate3);
            html = html.replace("{#productname}", productname);
            //html = html.replace("{#classname1}", classname1);
            //html = html.replace("{#classname2}", classname2);
            html = html.replace("{#products_image_url}", mainimage);
            html = html.replace("{#meta_title}", metatitle);
            html = html.replace("{#h1title}", title );
            html = html.replace("{#orprice}", formalintorprice);
            html = html.replace("{#number}", String.valueOf(hucountall%20+20) );
            html=html.replace("{#zone}",zones[hucountall%zones.length]);
            //html = html.replace("%h1title%",   productname );
            html = html.replace("{#product_name}",   productname );
            html=html.replace("{#servername}",serverName);
            String tagsstr="";
            if (tags.length>0){
                for (String ss:
                     tags) {
                    tagsstr+="<li class=\"n_common_TagStyle\"><p>"+ss+"</p></li>"+"\n";
                }

            }
            html=html.replace("{#tags}",tagsstr);
            String color = Textmysql.color();
            int cc = shellcount % 20 + 1;
            String randname = Textmysql.rand(cc % 4 + 1);
           // html = html.replace("{#youtube}", yourutbevideo);
            html=html.replace("{#sername}",serverName);
            //html = html.replace("メルカリ", serverName);
            html = html.replace("{#sitemap}", secondname+"sitemap20231010" + (hucountall % 30 + 1) + ".xml");
            html = html.replace("{#color}", color);
            html = html.replace("{#meta_description}", meta_description);
            html = html.replace("{#products_description}", description+"<br>"+gimges);
            html = html.replace("{#products_name}", productname);
            html = html.replace("{#hosturl}", hostName + "/" + secondname );
            html = html.replace("{#google_images}", gimges + "<br>" + related);
            html = html.replace("{#google_title}", gokeyall);
            html = html.replace("{#randnumber1}", String.valueOf(cc));
            html = html.replace("{#randname}", randname);
            html = html.replace("{#google_img_sum}", google_img_sum);
            html=html.replace("{#cates}",cate2+" > "+cate3);

            String imgestr="";
            String shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
            if (product_review != "") {
                html = html.replace("{#reviews}", product_review);
            } else {
                html = html.replace("{#reviews}", "");
            }
            JSONObject json1new;
            html = html.replace("{#domain}", "");
            String json1="{\"@context\": \"https://schema.org\", \"@type\": \"NewsArticle\", \"mainEntityOfPage\": { \"@type\": \"WebPage\", \"@id\": \"{#current_url}\" }, \"headline\": \"\", \"image\": [], \"datePublished\": \"2023-09-05T20:28\", \"dateModified\": \"2023-09-16T06:55\", \"author\": {\"@type\": \"Person\", \"name\": \"みんふあ\" }, \"publisher\": { \"@type\": \"Organization\", \"name\": \"goodmaskco.com\" } }";
            JSONObject googlejson=new JSONObject(json1);
            LocalDateTime localDateTime = LocalDateTime.now();
            String data = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time=localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String datetime=data+"T"+time;
            String modifdata = localDateTime.plusDays(-13).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time1=localDateTime.plusSeconds(6000).format(DateTimeFormatter.ofPattern("HH:mm"));
            modifdata=modifdata+"T"+time1;
            //googlejson.getJSONObject("mainEntityOfPage").put("@id",hostName + "/" + secondname  + url);
            googlejson.getJSONObject("publisher").put("name",serverName);
            googlejson.put("headline",title);
            googlejson.put("datePublished",modifdata);
            googlejson.put("dateModified",datetime);
            googlejson.getJSONArray("image").put(0,mainimage);
            html = html.replace("{#json_data}", googlejson.toString());
            String json2="{\"@context\":\"https://schema.org\",\"@type\":\"Product\",\"name\":\"\",\"image\":[],\"description\":\"\",\"mpn\":\"m473998027-976-72268\",\"sku\":\"99802723-00976-68\",\"brand\":{\"@type\":\"Brand\",\"name\":\"セガ\"},\"review\":{\"@type\":\"Review\",\"reviewRating\":{\"@type\":\"Rating\",\"ratingValue\":\"1\",\"bestRating\":\"5\"},\"author\":{\"@type\":\"Organization\",\"name\":\"goodmaskco.com\"}},\"aggregateRating\":{\"@type\":\"AggregateRating\",\"ratingValue\":\"5\",\"reviewCount\":\"6849\"},\"offers\":{\"@type\":\"Offer\",\"priceCurrency\":\"JPY\",\"price\":\"13288\",\"priceValidUntil\":\"2024-01-16\",\"url\":\"{#current_url}\",\"itemCondition\":\"http://schema.org/UsedCondition\",\"availability\":\"http://schema.org/InStock\",\"seller\":{\"@type\":\"Organization\",\"name\":\"みんふあ\"},\"shippingDetails\":{\"@type\":\"OfferShippingDetails\",\"shippingRate\":{\"@type\":\"MonetaryAmount\",\"value\":0,\"currency\":\"JPY\"},\"shippingDestination\":{\"@type\":\"DefinedRegion\",\"addressCountry\":\"JA\"},\"deliveryTime\":{\"@type\":\"ShippingDeliveryTime\",\"handlingTime\":{\"@type\":\"QuantitativeValue\",\"minValue\":0,\"maxValue\":1,\"unitCode\":\"DAY\"},\"transitTime\":{\"@type\":\"QuantitativeValue\",\"minValue\":1,\"maxValue\":5,\"unitCode\":\"DAY\"}}}}}";

             googlejson=new JSONObject(json2);
            googlejson.put("name",title);
            String [] ss= imges.split("\"><br><img src=\"");
            for (int i = 0; i < ss.length; i++) {
                String aa=ss[i].replace("<img src=\"","");
                aa=aa.replace("\">","");
                googlejson.getJSONArray("image").put(i,aa);
                int tt=i+1;
                imgestr+="<div class=\"imagAdd\"><img loading=\"lazy\" alt=\""+productname+" "+tt+"\"  src=\""+aa+"\" onClick=\"zImg(this)\"/></div>";
            }
            googlejson.put("description",description);
            googlejson.put("mpn",product_model);
            googlejson.put("sku",product_model);
            int ratingValue=hucountall%5+1;
            int reviewCount=(hucountall*15+32)%1000+200;
            googlejson.getJSONObject("review").getJSONObject("reviewRating").put("ratingValue",String.valueOf(ratingValue));
            googlejson.getJSONObject("aggregateRating").put("reviewCount",reviewCount);
            googlejson.getJSONObject("offers").put("price",formalintprice);

            modifdata = localDateTime.plusDays(-160).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            googlejson.getJSONObject("offers").put("priceValidUntil",modifdata);
            //googlejson.getJSONObject("offers").put("url",hostName + "/" + secondname  + url);
            html=html.replace("{#json_data1}",Getsitemapurl.formatJson(googlejson.toString()));
            html=html.replace("{#descriptionimg}",imgestr);
            //System.out.println(html);
            int urlCount = Getsitemapurl.urlCount(html, "{#productoneurl");
            if (brorginurl.endsWith("/")||brorginurl.endsWith("?")){
                html = html.replace("{#current_url}", hostName + "/" + secondname);
            }else {
                html = html.replace("{#current_url}", hostName + "/" + secondname + url);
            }
            ArrayList alst = Getsitemapurl.getLastnew(url, idurlmap, urlCount);
//            int acount = hucountall % 10 + 10;
//            int bcount = hucountall % 10 + 10;
            for (int i = 0; i < alst.size(); i++) {
                String temp;
                //int a = (int)(Math.random() * (double)secondarrayList.size());
                //String secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
                url=(String) alst.get(i);
//                int tempcount=Textmysql.ASCIcount(url);
//                String  secondnumber=secondarrayList.get(tempcount%secondarrayList.size()).toString();
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
                if (content.size() >= 2) {
                    String name="";
                    String price="";
                    String image="";
                    url = (String)content.get(1);
                    Map map1 = (Map)JSON.parse((String)content.get(0));
                        name=map1.get("product_name").toString();
                    price=map1.get("product_price").toString();
                    int  pricec= (int) (Double.valueOf(price).intValue());
                    orprice= (int) (Double.valueOf(price).intValue()*1.3);
                    image=map1.get("product_main_img").toString();
                    html=html.replace("{#productoneurl"+i+"}",secondname+url);
                    html=html.replace("{#productonename"+i+"}",name);
                    html=html.replace("{#productoneimage"+i+"}",image);
                    html=html.replace("{#productoneprice"+i+"}",String.valueOf(pricec));
                    html=html.replace("{#productoneorprice"+i+"}",String.valueOf(orprice));



                    //shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
                    //String seconnew=secondname;



//                    if (acount > 0) {
//                        String seconnew=secondname;
//                        if (secondname.length()==1) {
//                             seconnew = secondname.replace("?", "");
//                        }
//                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag ;
//                        --acount;
//                        htmlreplaceurls.append(urls[j]).append(temp);
//                    } else if (bcount > 0) {
//                        temp = "/" + secondname  + url ;
//                        htmlreplaceurls.append(urls[j]).append(temp);
//                        --bcount;
//                    } else {
//
//                    }
                }
            }
             urlCount = Getsitemapurl.urlCount(html, "{#producttwour_l");
             alst = Getsitemapurl.getLastnew(url, idurlmap, urlCount+5);
//            int acount = hucountall % 10 + 10;
//            int bcount = hucountall % 10 + 10;
            for (int i = 0; i < alst.size(); i++) {
                String temp;
                //int a = (int)(Math.random() * (double)secondarrayList.size());
                //String secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
                url=(String) alst.get(i);
//                int tempcount=Textmysql.ASCIcount(url);
//                String  secondnumber=secondarrayList.get(tempcount%secondarrayList.size()).toString();
                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
                if (content.size() >= 2) {
                    String name="";
                    String price="";
                    String image="";
                    url = (String)content.get(1);
                    Map map1 = (Map)JSON.parse((String)content.get(0));
                    name=map1.get("product_name").toString();
                    price=map1.get("product_price").toString();
                    image=map1.get("product_main_img").toString();
                    int pricec= (int) (Double.valueOf(price).intValue());

                    html=html.replace("{#producttwour_l"+i+"}",secondname+url);
                    html=html.replace("{#producttwoname"+i+"}",name);
                    html=html.replace("{#producttwoimage"+i+"}",image);
                    html=html.replace("{#producttwoprice"+i+"}",String.valueOf(pricec));



                    //shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
                    //String seconnew=secondname;



//                    if (acount > 0) {
//                        String seconnew=secondname;
//                        if (secondname.length()==1) {
//                             seconnew = secondname.replace("?", "");
//                        }
//                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag ;
//                        --acount;
//                        htmlreplaceurls.append(urls[j]).append(temp);
//                    } else if (bcount > 0) {
//                        temp = "/" + secondname  + url ;
//                        htmlreplaceurls.append(urls[j]).append(temp);
//                        --bcount;
//                    } else {
//
//                    }
                }
            }

//            urls=html.split("\\{#relatedurl}");
//            htmlreplaceurls = new StringBuilder();
//            for(int j = 0; j < urls.length - 1; ++j) {
//                String temp = "";
//                int a = (int)(Math.random() * (double)secondarrayList.size());
//                String secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
//                url = (String)alst.get(j);
//                List content = Getsitemapurl.getProductTxt(url, readConfigWeb, idTotal);
//
//                if (content.size() >= 2) {
//                    url = (String)content.get(1);
//                    Map map1 = (Map)JSON.parse((String)content.get(0));
//                    if (map1.get("product_name") != null) {
//                        map1.get("product_name").toString();
//                    } else {
//                        String var10000 = "";
//                    }
//
//                    shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
//                        String seconnew=secondname;
//                        if (secondname.length()==1) {
//                            seconnew = secondname.replace("?", "");
//                        }
//                        temp = "/" +seconnew+ secondnumber + "/" + url + shellflag ;
//                        urls[j+1]= urls[j+1].replace("{#yahootitle"+j+"}",map1.get("product_name").toString());
//                        urls[j+1]= urls[j+1].replace("{#relatedpics"+j+"}",map1.get("product_main_img").toString());
//                        urls[j+1]= urls[j+1].replace("{#label"+j+"}",map1.get("product_name").toString());
//                        urls[j+1]= urls[j+1].replace("{#atl"+j+"}",map1.get("product_name").toString());
//                        urls[j+1]= urls[j+1].replace("{#relatedprice"+j+"}",map1.get("product_price").toString());
//                        urls[j+1]= urls[j+1].replace("{#item-name"+j+"}",map1.get("product_name").toString());
//                        htmlreplaceurls.append(urls[j]).append(temp);
//
//                }
//            }
//            html = htmlreplaceurls + urls[urls.length - 1];
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
            //Document doc = Jsoup.parse(html);
            //return doc.toString();
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
