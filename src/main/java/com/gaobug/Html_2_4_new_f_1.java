package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.regex.Matcher;


public class Html_2_4_new_f_1 {
    public static String  setHtml(Map dataMap,String url,String nexturl,String hostName,String html,
                                         ArrayList listJson,Map readConfigWeb, int idTotal,ArrayList hotkeys
            ,ArrayList shellflaglist,boolean hotkeyflag,String secondname,Map idurlmap,ArrayList secondarrayList,String style,String randurl){
        try{
            //获取数据
            String productname=dataMap.get("product_name")!=null?dataMap.get("product_name").toString():"";
            //String googlerand=dataMap.get("google_img_sum")!=null?dataMap.get("google_img_sum").toString():"";
            String des=dataMap.get("product_description")!=null?dataMap.get("product_description").toString():"";
            String mainimage=dataMap.get("product_main_img")!=null?dataMap.get("product_main_img").toString():"";
            String cate1=dataMap.get("product_cate1")!=null?dataMap.get("product_cate1").toString():"ホーム";
            String cate2=dataMap.get("product_cate2")!=null?dataMap.get("product_cate2").toString():"";
            String cate3=dataMap.get("product_cate3")!=null?dataMap.get("product_cate3").toString():"";
            String related=dataMap.get("product_related")!=null?dataMap.get("product_related").toString():"";
            related=related.replace("Ω","<br>");
            String product_review=dataMap.get("product_review")!=null?dataMap.get("product_review").toString():"";
            String product_url=dataMap.get("product_url")!=null?dataMap.get("product_url").toString():"";
            String product_model=dataMap.get("product_model")!=null?dataMap.get("product_model").toString():"";
            String product_price=dataMap.get("product_price")!=null?dataMap.get("product_price").toString():"";
            String youtube_keywords=dataMap.get("youtube_keywords")!=null?dataMap.get("youtube_keywords").toString():"";
            String yahoo_keys=dataMap.get("keytitles")!=null?dataMap.get("keytitles").toString():"";//关键词
            String yahoo_des=dataMap.get("destitle")!=null?dataMap.get("destitle").toString():"";//关键词
            String yourutbevideo = dataMap.get("ybvideo") != null ? dataMap.get("ybvideo").toString() : "";
            int secondnamecount=Textmysql.ASCIcount(secondname);
            int shellcount=Textmysql.ASCIcount(hostName);
            String serverName=hostName.replaceAll("http://|https://",Matcher.quoteReplacement(""));
            String secondnumber;
            String title=productname+ " - "+serverName;
            String h1name=productname+" "+cate2+" "+cate3;
            String h2name0=productname+" "+cate2+" "+cate3;
            String h2name1=product_price+"円"+productname+" "+cate2+" "+cate3;
            String ptitle=productname+" - "+serverName;
            String meta_keywords=cate2+"&"+productname+cate3;
            int urlcounta=Textmysql.ASCIcount(url);
            int hucountall=shellcount+urlcounta+secondnamecount;
            String color=Textmysql.colorid(hucountall);
            //int titlecount=Getsitemapurl.urlCount(html,"{#yahootitle}");

            String id="";

            String imagedes=dataMap.get("desimage")!=null?dataMap.get("desimage").toString():"";
            String [] imges = new String[0];
            String allimgesdes="";
            if (!imagedes.equals("")&&imagedes.indexOf("Φfenge")>0){
                imges=imagedes.split("Φfenge");
            }
            for (int i = 0; i < imges.length; i++) {
                allimgesdes+="<div class=\"mmeds\"><img src=\""+imges[i]+"\"></div>";
            }
            allimgesdes="<div class=\"slider-pro\">"+allimgesdes+"</div>";
            String googleImage=dataMap.get("google_image_keyword")!=null?dataMap.get("google_image_keyword").toString():"";
            String gimges="";
            String gokeyall="";
            String yakeyall="";
            if (!googleImage.equals("")&&googleImage.indexOf("###")>0) {
                String temp1[] =googleImage.split("###");
                if (temp1.length>=2) {
                    gimges = temp1[1];
                    String googles[] = temp1[0].split("Φfenge|,");
                    int ga = hucountall % 2 + 3;
                    int ccbb = hucountall;
                    for (int i = 0; i < ga; i++) {
                        ccbb = ccbb + 6;
                        int avv = ccbb % googles.length;
                        gokeyall += googles[avv]+",";
                    }
                }
            }

            if (!yahoo_keys.equals("")&&yahoo_keys.indexOf("Φfenge")>0) {
                String yahkes[] =yahoo_keys.split("Φfenge");
                int ga=hucountall%3+3;
                int ccbb=hucountall;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+6;
                    int avv=ccbb%yahkes.length;
                    yakeyall+=yahkes[avv]+",";
                }
            }

            String youutbekey="";
            if (!youtube_keywords.equals("")&&youtube_keywords.indexOf("Φfenge")>0) {
                String youtubess[] =youtube_keywords.split("Φfenge|,");
                int ga=hucountall%10+5;
                int ccbb=hucountall;
                for (int i = 0; i < ga; i++) {
                    ccbb=ccbb+9;
                    int avv=ccbb%youtubess.length;
                    youutbekey+=youtubess[avv]+",";
                }
            }
            String cate2url="";
            String cate3url="";
            String cate4url="";
            String jsonurl="";
             cate2url = "/" + secondname  + randurl + ".html";
             cate3url = "/" + secondname  + nexturl + ".html";
             cate4url = "/" + secondname  + url + ".html";
            jsonurl =url;

            if (product_url.indexOf("api.mercari.jp")>0){
                id=product_url.substring(product_url.indexOf("get?id=")+7);
                id=id.replace("\"}","");
            }
            //填充内容
            String hotkey="";
            if (hotkeyflag) {
                hotkey = Getsitemapurl.randGetOne("jptitle.txt", hotkeys, urlcounta);
            }else{
                //随机热销次
                int randhotkey= (int) (Math.random()*hotkeys.size());
                hotkey=hotkeys.get(randhotkey).toString();
            }
            //随机热销词
            // int randhotkey= (int) (Math.random()*hotkeys.size());
            //String hotkey=hotkeys.get(randhotkey).toString();
            product_model=id+"-"+product_model;
            //int i1 = randint % discountnum.length;
            //int discountrand= discountnum[i1];
            //String title=discountrand+"% OFF"+hotkey+" "+productname+cate2+" "+cate3+"&"+hostName;

            html=html.replace("{#color}",color);
            html=html.replace("{#hosturl}",hostName+"/"+secondname);

            String classname2=Getsitemapurl.MD5("classss2");
            String classname1=Getsitemapurl.MD5("casssss1");
            html=html.replace("{#mainimage}",mainimage);
            html=html.replace("{#title}",title);
            html=html.replace("{#product_model}",product_model);
            html=html.replace("{#product_price}",product_price);
            ArrayList alst;
            int urlCount;
            html=html.replace("{#products_image_url}", mainimage);
            html=html.replace("{#meta_title}", title);
            html=html.replace("%h1title%",h1name);
            html=html.replace("%h1title1%",h1name);
            html=html.replace("{#h2title0}",h2name0);
            html=html.replace("{#h2title1}",h2name1);
            html=html.replace("{#ptitle}",ptitle);
            html=html.replace("{#uperhostname}",serverName.toUpperCase());
            html=html.replace("{#secondname}",secondname);
            html=html.replace("{#cate1}",cate1);
            html=html.replace("{#cate1url}",hostName);
            html=html.replace("{#cate2url}",cate2url);
            html=html.replace("{#cate2}",cate2);
            html=html.replace("{#cate3url}",cate3url);
            html=html.replace("{#cate3}",cate3);
            html=html.replace("{#cate4url}",cate4url);
            html=html.replace("{#cate4}",productname);

            //选两个h标签
            String yys;


//        //填充yahoo
//        //随机标签
//            String [] taghead={"<li>", "<span>", "<div>",  "<div><P>", "<div><span>", "<div><strong>", "<div><br>", "<div><td>", "<div><hr>", "<div><ul><li>",  "<div><span><p><strong>"};
//            String [] tagend={"</li>", "</span>", "</div>",  "</p></div>", "</span></div>", "</strong></div>", "</br></div>", "</td></div>", "</hr></div>", "</li></ul></div>", "</strong></p></span></div>"};
//        String metades="";
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
//                html = html.replaceFirst("\\{#h\\d+title\\d+}", "yahootitle"+Matcher.quoteReplacement(tite));
//
//                html = html.replaceFirst("\\{#googleDes}", "yahoodes"+taghead[shellcount%5]+Matcher.quoteReplacement(yahoodes)+tagend[shellcount%5]);
//            }
//
//        }
            int yhcount=urlcounta%10;
            //填充yahoo
            //随机标签
            String [] taghead={"<li>", "<span>", "<div>",  "<div><P>", "<div><span>", "<div><br>", "<div><td>", "<div><hr>", "<div><ul><li>"};
            String [] tagend={"</li>", "</span>", "</div>",  "</p></div>", "</span></div>", "</br></div>", "</td></div>", "</hr></div>", "</li></ul></div>"};
            String metades="";
            if (yahoo_keys!="") {
                String [] keyarray=   yahoo_keys.split("Φfenge");
                List<String>  keys = Arrays.asList(keyarray);
                String [] desarray=   yahoo_des.split("Φfenge");
                List<String>  dess = Arrays.asList(desarray);
                HashSet set =new HashSet();
                for (int i = 0; i < yhcount; i++) {
                    Collections.shuffle(keys);
                    Collections.shuffle(dess);

                    if (keys.size()>0&&dess.size()>0) {
                        if (!set.add(keys.get(0))) {
                            continue;
                        }
                        String temp1=keys.get(0);
                        String temp2=dess.get(0);
                        html = html.replaceFirst("\\{#h\\d+title\\d+}", Matcher.quoteReplacement(temp1));
                        html = html.replaceFirst("\\{#googleDes}", taghead[shellcount % 5] + Matcher.quoteReplacement(temp2) + tagend[shellcount % 5]);
                    }
                }
                set.clear();
            }
            //填充youutbe
//        if (youtube_keywords!="") {
//            String [] keyvideo=youtube_keywords.split("###");
//            if (keyvideo.length==2) {
//                String[] youtubekeys = keyvideo[0].split("Φfenge");
//                if (youtubekeys.length > 0) {
//                    int forcount = (shellcount * 7 + 12) % youtubekeys.length;
//                   if (forcount>10)
//                       forcount-=4;
//                    int temp=forcount;
//                    for (int i = 0; i < forcount; i++) {
//                        temp = (temp + 1) % youtubekeys.length;
//                        html = html.replaceFirst("\\{#h\\d+title\\d+}", Matcher.quoteReplacement(youtubekeys[temp]));
//
//                    }
//                }
//                yys = keyvideo[0].replace("Φfenge", ".");
//                   html = html.replace("{#youtube}", taghead[shellcount%6] + yys + tagend[shellcount%6]);
//                //String[] youtubevideos = keyvideo[1].split("Φfenge");
////                if (youtubevideos.length > 0) {
////                    int forcount = (shellcount * 7 + 12) % youtubevideos.length;
////                    yys = keyvideo[0].replace("Φfenge", ".");
////                    html = html.replace("{#remain_tag_1}", "<p>" + yys + "</p>");
////                    youtubevideos[forcount]=youtubevideos[forcount].replaceAll("watch\\?v=","embed/");
////                    html = html.replace("{#youtube}<br>", "<iframe src=\"https://www.youtube.com" + youtubevideos[forcount] + "\" width=\"853\" height=\"480\"></iframe>");
////                }
//            }
//        }
//
//            if (youutbekey!=""){
//                youutbekey=taghead[shellcount%6] +youutbekey + tagend[shellcount%6];
//            }
//            html = html.replace("{#youtube}", youutbekey);
//        String subproductnam=des.replaceAll("<[\\s\\S]*?>","");
//
//        if (des.indexOf("商品の説明")>0&&des.indexOf("商品の情報")>0) {
//            subproductnam = des.substring(des.indexOf("商品の説明") + 5, des.indexOf("商品の情報"));
//            subproductnam=subproductnam.replaceAll("<[\\s\\S]*?>","");
//        }

            String meta_description=product_price+"円 "+hotkey+metades+","+productname+","+cate3+"-"+serverName;
            html = html.replace("{#youtube}", "");
            html = html.replace("{#googleDes}", "");
            //html=html.replace("<meta name=\"keywords\" content=\"{#meta_keywords}\" />","");
            html=html.replace("{#meta_keywords}",meta_keywords);
            html=html.replace("{#meta_description}",meta_description);
            //des=des.replaceAll("<[\\s\\S]*?>",Matcher.quoteReplacement(""));
            String goyball="";
            if (!yakeyall.equals("")) {
                goyball = yakeyall + "<em>" + product_price + productname + "</em>" + youutbekey ;
            }else {
                goyball = gokeyall + "<em>" + product_price+ productname + "</em>" + youutbekey ;
            }
            html = html.replace("{#products_description}",des+allimgesdes);
            html = html.replace("{#goyb}", goyball);
            //html = html.replace("{#products_image}", products_imagehtml);
            //html = html.replace("{#breadcrumbs}",breadcrumbshtml);
            html=html.replace("{#products_name}",productname);
            html=html.replace("{#current_url}",url);
            html=html.replace("{#img_title}",hotkey+productname);
            html=html.replace("{#google_images}",gimges+"<br>"+related);
            html=html.replace("{#classname1}",classname1);
            html=html.replace("{#classname2}",classname2);
            html=html.replace("{#product_name}",productname);
            html=html.replace("{#category}",cate2+cate3);
            html=html.replace("{#youtubevideo}",yourutbevideo);
            html = html.replace("{#domain}", "");
            html = html.replace("{#shell_url}", "");
            html = html.replace("{#h1title}", h1name);

//            for (int i = 0; i < titlecount; i++) {
//                String [] yahootitle;
//                if (yahoo_keys!=""&&yahoo_keys.indexOf("Φfenge")>1) {
//                    yahootitle =yahoo_keys.split("Φfenge");
//                    ++hucountall;
//                    String temtitle=yahootitle[hucountall%yahootitle.length];
//                    temtitle=temtitle.replace("- メルカリ","");
//                    temtitle=temtitle.replace("メルカリ","");
//                    html=html.replaceFirst("\\{#yahootitle}",Matcher.quoteReplacement(temtitle));
//                    html=html.replace("{#atl"+i+"}",temtitle+"のサムネイル");
//                    html=html.replace("{#atl"+i+"}",temtitle+"のサムネイル");
//                    html=html.replace("{#label"+i+"}",temtitle+"の画像 "+product_price+"円");
//                    html=html.replace("{#item-name"+i+"}",temtitle);
//                }else if (!googleImage.equals("")&&googleImage.indexOf("###")>0) {
//                    yahootitle =googleImage.split("###");
//                    yahootitle =yahootitle[0].split("Φfenge|,");
//                    ++hucountall;
//                    String temtitle=yahootitle[hucountall%yahootitle.length];
//                    temtitle=temtitle.replace("- メルカリ","");
//                    temtitle=temtitle.replace("メルカリ","");
//                    html=html.replaceFirst("\\{#yahootitle}",Matcher.quoteReplacement(temtitle));
//                    html=html.replace("{#atl"+i+"}",temtitle+"のサムネイル");
//                    html=html.replace("{#atl"+i+"}",temtitle+"のサムネイル");
//                    html=html.replace("{#label"+i+"}",temtitle+"の画像 "+product_price+"円");
//                    html=html.replace("{#item-name"+i+"}",temtitle);
//                }else {
//                    html=html.replaceFirst("\\{#yahootitle}",Matcher.quoteReplacement(productname));
//                    html=html.replace("{#atl"+i+"}",productname+"のサムネイル");
//                    html=html.replace("{#atl"+i+"}",productname+"のサムネイル");
//                    html=html.replace("{#label"+i+"}",productname+"の画像 "+product_price+"円");
//                    html=html.replace("{#item-name"+i+"}",productname);
//                }
//            }

//        String shellflag;
//        shellflag=Getsitemapurl.getRandflag(hostName, shellflaglist,url);
            if (product_review!="")
                html=html.replace("{#reviews}",product_review);
            else  html=html.replace("{#reviews}","");
            //列表
            String json = Getsitemapurl.productJson(productname, des,
                    mainimage, product_price,   jsonurl, hostName, (String) listJson.get(0),cate2url,cate3url,"",cate1,cate2,cate3,"json10",imagedes);
//        //产品
//        String json1 = Getsitemapurl.productJson(productname, des,
//                mainimage, price, hostName + "/?" + url+shellflag, hostName, (String) listJson.get(0),cate2url,cate3url,cate4url,cate1,cate2,cate3,"json8");

            html = html.replace("{#json_data}", "<script type=\"application/ld+json\">\n"+json+"\n"+"</script>");


            //获取a连接
            //获取a连接
            urlCount=Getsitemapurl.urlCount(html,"{#products_url}");
            alst = Getsitemapurl.getLastnew(url, idurlmap, urlCount);
            String [] urls=html.split("href=\\{#products_url}>");
            String htmlreplaceurls="";
            //<a href="https://gvmplc.com?otqi651b632" title="SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L" >SHARP シャープ ノンフロン 冷凍 冷蔵庫 SJ-PD27A-T　271L</a>
            // 非redis 存储
            int acount=urlcounta%10+10;
            int bcount=urlcounta%10+10;
            String temp;


            for (int j = 0; j < urls.length-1; j++)
            {

                int a=(urlcounta+1)%secondarrayList.size();
                secondnumber = secondarrayList.get(a % secondarrayList.size()).toString();
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

                   String  shellflag = Getsitemapurl.getRandflag(hostName, shellflaglist, url);
                    if (acount > 0) {
                        String seconnew=secondname;
                        if (secondname.length()==1) {
                            seconnew = secondname.replace("?", "");
                        }
                        temp="title=\""+productname+"\" "+"href=\"/"+seconnew+secondnumber+"/"+url+shellflag+".html"+"\" style=\"color:#"+color+"\">"+productname;
                        --acount;
                        htmlreplaceurls = htmlreplaceurls + urls[j] + temp;
                    } else if (bcount > 0) {
                        temp="title=\""+productname+"\" "+"href=\"/"+secondname+url+".html"+"\" style=\"color:#"+color+"\">"+productname;
                        htmlreplaceurls = htmlreplaceurls + urls[j] + temp;
                        --bcount;
                    } else {
                        temp="title=\""+productname+"\" "+"href=\"/"+secondname+url+shellflag+".html"+"\" style=\"color:#"+color+"\">"+productname;
                        htmlreplaceurls = htmlreplaceurls + urls[j] + temp;
                    }
                }

                //lr模板
                // String temp="\"/?"+url+shellflag+"\""+"title=\""+productname+"\" >"+productname;
                //cc模板
                //temp="<a href=\"/?"+url+shellflag+"\""+" title=\""+productname+"\" >"+productname+"</a>";
                //htmlreplaceurls+=urls[j]+temp;
//                if (acount>0){
//                    temp="<a title=\""+productname+"\" href=\""+"/"+secondname+secondnumber+"/"+url+shellflag+"\" style=\"color: #"+color+"\">"+productname+"</a>";
//                    //temp="/"+secondname+"?"+secondnumber+"/"+url+shellflag;
//                    acount--;
//                    htmlreplaceurls+=urls[j]+temp;
//                    secondname="";
//                    continue;
//                }
//                if (bcount>0){
//                    temp="<a title=\""+productname+"\" href=\""+"/"+secondname+url+"\" style=\"color: #"+color+"\">"+productname+"</a>";
//                    htmlreplaceurls+=urls[j]+temp;
//                    bcount--;
//                    continue;
//                }




            }
            html = htmlreplaceurls+urls[urls.length-1];


//            urlCount=Getsitemapurl.urlCount(html,"{#href#}");
//            alst=Getsitemapurl.getLast(url,mapId,readConfigWeb,urlCount,idTotal,secondnamecount);
//            for (int i = 0; i < alst.size(); i++) {
//                html=html.replaceFirst("\\{#href#}","/"+secondname+secondnumber+"/"+alst.get(i)+".html");
//            }
//            html=html.replace("{#href#}",hostName);
            html=html.replaceAll("<div>\\{#h([\\s\\S]*?)}</div>",Matcher.quoteReplacement(""));
            html=html.replaceAll("\\{#h([\\s\\S]*?)}",Matcher.quoteReplacement(""));
            //Document doc = Jsoup.parse(html);
            //return doc.toString();
            return html;
        } catch (Exception e){
            Document doc = Jsoup.parse(html);
            e.printStackTrace();
            // return doc.toString();
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
