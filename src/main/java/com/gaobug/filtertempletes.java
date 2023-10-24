package com.gaobug;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class filtertempletes {
    public static void main(String[] args) {
        //filterword();
        //filteraurl();
        //ReadTempReNameAll();
        mertep1();
        //filterurl();
        //htmlnew();

    }

    //<meta http-equiv="refresh" content=" 过滤内容新增


    public static void filterurl(){
        File file=new File("E:\\lr\\数据处理内容\\模板下载\\newjp1119.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.write(new File("E:\\lr\\数据处理内容\\模板下载\\newjp1119.txt"),  "",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path="E:\\lr\\数据处理内容\\模板下载\\jpnew1119.txt";
        String [] filtername={"facebook","twitter","amazon","mac.lc","apple.com","zozo.jp","mercari","rakuten","ameblo.jp","google.","app.adjust.com","yahoo.co.jp","yodobashi.com","wikipedia.org","core.ac.uk","codebeautify.org","wiktionary.org","kakaku.com","oracle.com","kotobank.jp","linguee.com","ebay.com","ibm.com","instagram.com","lingq.com","archlinux.org",".edu","datatracker.ietf.org","weblio.jp","goo.ne.jp","youtube.com","github.com","huawei.com","support.","plotly.com","context.reverso.net","jisho.org","search.kakaku.com","fleapedia.com","buyma.com","tr-ex.me","wikiwand.com","sophia-it.com","kurashiru.com","cookpad.com","wiki.xn--rckteqa2e.com","learnwitholiver.com","mojim.com","yugioh-wiki.net","monotaro.com","renso-ruigo.com","msdmanuals.com","tutitatu.com","waraerujd.com","yaoyolog.com","wiki.openstreetmap.org","wiki.dengekionline.com","dmm.com",".com.br",".ac.id",".gov","cleancss.com","git.","wiki.","line.me","fril.jp","baidu.com",".pdf","/document/","/docs/","dictionary"};
        ArrayList list= Textmysql.filedata(path);
        for (int i = 0; i < list.size(); i++) {
            Boolean flag=true;
            for (int j = 0; j < filtername.length; j++) {
                if (list.get(i).toString().indexOf(filtername[j]) > 0) {
                    System.out.println(list.get(j).toString());
                    flag=false;
                    break;
                }
            }
                try {
                    if (flag) {
                        FileUtils.write(new File("E:\\lr\\数据处理内容\\模板下载\\newjp1119.txt"), list.get(i).toString() + "\n", true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    public static void filterword(){
        String [] strtpemlet={"詐欺","偽物","中古","被害","詐欺サイト","钓鱼网站","钓鱼","架空請求","架空","警察","弁護士","騙す","騙"};
        File file=new File("../filterhtml");
        File[] file1=file.listFiles();
        for (Object path:file1){
            ArrayList list= Textmysql.getFileContent(path.toString(),false);
            for (int i = 0; i < strtpemlet.length; i++) {
                if (list.get(0).toString().indexOf(strtpemlet[i])>0){
                    try {
                        FileUtils.write(new File(path.toString()), "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String html=list.get(0).toString();
            if (html.indexOf("http-equiv=\"refresh\"")>0) {
                System.out.println(path);
                html = html.replaceAll("<meta http-equiv=\"refresh\" content=\"([\\s\\S]*?)/>", "");
                try {
                    FileUtils.write(new File(path.toString()), html);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
        public static void filteraurl(){

        File file=new File("../filterhtml");
        if (!file.exists()){
            file.mkdirs();
        }else {
            File[] file1=file.listFiles();
            for (Object path:file1
                 ) {
                ArrayList list= Textmysql.getFileContent(path.toString(),true);
                try {
                    Document doc = Jsoup.parse((String) list.get(0));
                    Elements elements = doc.select("a");
                    String html = "";
                    if (html.indexOf("{#href#}")<0) {
                        if (elements.size() > 20) {
                            int count = 0;
                            String[] strs = ((String) list.get(0)).split("\\{#products_url}");
                            int randnumb = (int) (Math.random() * 8 + 7);
                            for (int i = 0; i < strs.length; i++) {
                                {
                                    if (count <= randnumb) {
                                        int flag = (int) (Math.random() * 2);
                                        if (flag == 0) {
                                            if (i != strs.length - 1) {
                                                html += strs[i] + "{#href#}";
                                            } else {
                                                html += strs[i];
                                            }
                                            count++;
                                        } else {
                                            if (i != strs.length - 1) {
                                                html += strs[i] + "{#products_url}";
                                            } else {
                                                html += strs[i];
                                            }
                                        }
                                    } else {
                                        if (i != strs.length - 1) {
                                            html += strs[i] + "{#products_url}";
                                        } else {
                                            html += strs[i];
                                        }
                                    }

                                }

                            }
                            String Regex = "";
                            Pattern pattern = Pattern.compile(Regex);
                            Matcher match = pattern.matcher(html);
                            // html=html.replaceAll(">(\\s+)</a>"," ");
                            //System.out.println(html);
                            doc = Jsoup.parse(html);
                            elements = doc.select("a");
                            for (int i = 0; i < elements.size(); i++) {
                                String strflaga = elements.get(i).getElementsByAttributeStarting("href").toString();
                                if (strflaga.indexOf("{#products_url}") > 0) {
                                    doc = Jsoup.parse(strflaga);
                                    Elements temp = doc.select("a");
                                    String temps = temp.first().text();
                                    //System.out.println("or-->"+strflaga);
                                    //System.out.println("temp-->"+temps);
                                    html = html.replaceAll("\\{#products_url}>([\\s\\S]*?)</a>", "{#products_url}></a>");
                                }
                            }
                            //doc=Jsoup.parse(html);
                            //html=doc.toString();
                            FileUtils.write(new File(path.toString()), html);
                        }
                    }

                }catch (Exception e){
                    System.out.println(path);
                    try {
                        FileUtils.write(new File(path.toString()), "");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }

            }
        }
    }
    public static void ReadTempReNameAll() {
        File file = new File("../filterhtml");
        File[] array = file.listFiles();
        for(int i = 0; i < array.length; i++) {
            if (array[i].isFile() && array[i].getName().indexOf(".html") > 0) {
                array[i].renameTo(new File("../filterhtml/" + 700+i + ".html"));
            }
        }
        for(int i = 0; i < array.length; i++) {
            if (array[i].isFile() && array[i].getName().indexOf(".html") > 0) {
                array[i].renameTo(new File("../filterhtml/" + i + ".html"));
            }
        }

    }
    public static void mertep() {
        File file = new File("E:\\lr\\数据处理内容\\Javaproduct\\greatSeo\\merhtml\\html3\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] file1 = file.listFiles();
        for (Object path : file1) {
            try{
            ArrayList list = Textmysql.getFileContent(path.toString(), false);
            String html = list.get(0).toString();
//            html=html.replaceAll("<base href=\"https://jp.mercari.com/item\">","");
            Document doc;
//            html = html.replaceAll("<title>([\\s\\S]*?)</title>", "<title>{#title}</title>");
//            html = html.replaceAll("<link rel=\"canonical\" href=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<link rel=\"canonical\" href=\"{#current_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"description\" content=\"{#meta_description}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:url\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:url\" content=\"{#current_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:title\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:title\" content=\"{#meta_title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:description\" content=\"{#meta_description}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:image\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:image\" content=\"{#products_image_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"twitter:description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"twitter:description\" content=\"{#title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"twitter:title\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"twitter:title\" content=\"{#title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<mer-icon-exclamation-with-triangle-outline([\\s\\S]*?)</mer-icon-exclamation-with-triangle-outline>", "");
//            html = html.replaceAll("<script([\\s\\S]*?)</script>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//            doc = Jsoup.parse(html);
//            Elements elements = doc.select("h1");
//            String productname = elements.first().text();
//            if (!productname.equals("{#products_name}"))
//                html = html.replaceAll(productname, "{#products_name}");
//            Elements elements1 = doc.select(".iSvZNI .number");
//            String price = elements1.first().text();
//            if (!price.equals("{#product_price}"))
//                html = html.replaceAll(price, "{#product_price}");
//            doc = Jsoup.parse(html);
//            doc.select(".layout__StyledSection-sc-1lyi7xi-7").get(2).addClass("xxx3");
//            doc.select(".layout__StyledSection-sc-1lyi7xi-7").get(3).addClass("xxx4");
//            doc.select(".style-scope").get(1).addClass("xxxstyle2");
//            html = doc.toString().replaceAll("<section class=\"layout__StyledSection-sc-1lyi7xi-7 jbpkGU xxx3\">([\\s\\S]*?)</section>", "<section class=\"layout__StyledSection-sc-1lyi7xi-7 jbpkGU\">{#breadcrumbs}<br><img src=\"{#products_image_url}\"><br><mer-heading class=\"layout__SectionHeading-sc-1lyi7xi-5 hEoZlH mer-spacing-b-16\" title-label=\"商品の説明\" level=\"2\" title-type=\"section\" mer-defined=\"\">{#products_description}<br>{#google_images}<br>{#youtube}<br>{#reviews}</mer-heading></section>");
//            html = html.replaceAll("<section class=\"layout__StyledSection-sc-1lyi7xi-7 bwRNSO xxx4\">([\\s\\S]*?)</section>", "");
//            html=html.replaceAll("<p class=\"primary body style-scope mer-text xxxstyle2\">([\\s\\S]*?)</p>","");
//            html=html.replaceAll("<h1 class=\"heading page style-scope mer-heading\">\\{#products_name}</h1> ","<h1 class=\"heading page style-scope mer-heading\">{#h1title1}</h1> ");
//            html=html.replaceAll("<a href=\"\\{#products_url}\">ホーム</a>","<a href=\"/\">ホーム</a>");
            html=html.replaceAll("<iframe([\\s\\S]*?)</iframe> ","");
            doc=Jsoup.parse(html);
            Elements elements2=doc.select(".error-icon");
                System.out.println(elements2.size());
                System.out.println("");
            doc = Jsoup.parse(html);
            try {
                FileUtils.write(new File(path.toString()), doc.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
                System.out.println(path);
                try {
                    FileUtils.write(new File(path.toString()), "");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }

    }
    public static void mertep1() {
        File file = new File("E:\\lr\\数据处理内容\\Javaproduct\\greatSeo\\merhtml\\html3\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] file1 = file.listFiles();
        for (Object path : file1) {
            try{
                ArrayList list = Textmysql.getFileContent(path.toString(), false);
                String html = list.get(0).toString();

                //html=html.replaceAll("<base href=\"https://jp.mercari.com/item\">","");
                Document doc;
//            html = html.replaceAll("<title>([\\s\\S]*?)</title>", "<title>{#title}</title>");
//            html = html.replaceAll("<link rel=\"canonical\" href=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<link rel=\"canonical\" href=\"{#current_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"description\" content=\"{#meta_description}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:url\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:url\" content=\"{#current_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:title\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:title\" content=\"{#meta_title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:description\" content=\"{#meta_description}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta property=\"og:image\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta property=\"og:image\" content=\"{#products_image_url}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"twitter:description\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"twitter:description\" content=\"{#title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<meta name=\"twitter:title\" content=\"([\\s\\S]*?)\" data-react-helmet=\"true\">", "<meta name=\"twitter:title\" content=\"{#title}\" data-react-helmet=\"true\">");
//            html = html.replaceAll("<mer-icon-exclamation-with-triangle-outline([\\s\\S]*?)</mer-icon-exclamation-with-triangle-outline>", "");
//            html = html.replaceAll("<script([\\s\\S]*?)</script>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//            html = html.replaceAll("<p class=\"primary body style-scope mer-text\">お使いのブラウザがWebサイトに対応していない、または最新版ではない可能性があります。サポートしている環境をご確認ください。<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"\\{#products_url}\">詳しくはこちら</a></p>", "");
//
//
//            doc = Jsoup.parse(html);
//            Elements elements = doc.select("h1");
//            String productname = elements.first().text();
//            if (!productname.equals("{#products_name}"))
//                html = html.replaceAll(productname, "{#products_name}");
//            Elements elements1 = doc.select(".iSvZNI .number");
//            String price = elements1.first().text();
//            if (!price.equals("{#product_price}"))
//                html = html.replaceAll(price, "{#product_price}");
//            doc = Jsoup.parse(html);
//            doc.select(".layout__StyledSection-sc-1lyi7xi-7").get(2).addClass("xxx3");
//            doc.select(".layout__StyledSection-sc-1lyi7xi-7").get(3).addClass("xxx4");
//            doc.select(".style-scope").get(1).addClass("xxxstyle2");
//            html = doc.toString().replaceAll("<section class=\"layout__StyledSection-sc-1lyi7xi-7 jbpkGU xxx3\">([\\s\\S]*?)</section>", "<section class=\"layout__StyledSection-sc-1lyi7xi-7 jbpkGU\">{#breadcrumbs}<br><img src=\"{#products_image_url}\"><br><mer-heading class=\"layout__SectionHeading-sc-1lyi7xi-5 hEoZlH mer-spacing-b-16\" title-label=\"商品の説明\" level=\"2\" title-type=\"section\" mer-defined=\"\">{#products_description}<br>{#google_images}<br>{#youtube}<br>{#reviews}</mer-heading></section>");
//            html = html.replaceAll("<section class=\"layout__StyledSection-sc-1lyi7xi-7 bwRNSO xxx4\">([\\s\\S]*?)</section>", "");
//            html=html.replaceAll("<p class=\"primary body style-scope mer-text xxxstyle2\">([\\s\\S]*?)</p>","");
//            html=html.replaceAll("<h1 class=\"heading page style-scope mer-heading\">\\{#products_name}</h1> ","<h1 class=\"heading page style-scope mer-heading\">{#h1title1}</h1> ");
//            html=html.replaceAll("<a href=\"\\{#products_url}\">ホーム</a>","<a href=\"/\">ホーム</a>");
               //html=html.replaceAll("<noscript>([\\s\\S]*?)<iframe([\\s\\S]*?)</iframe>([\\s\\S]*?)</noscript> ","");
                html=html.replaceAll("<div class=\"error-icon([\\s\\S]*?)>([\\s\\S]*?)</div>","");

                html=html.replaceAll("https://jp\\.mercari\\.com/item","{#current_url}");
                html=html.replaceAll("<button([\\s\\S]*?)>([\\s\\S]*?)</button>","");
                html=html.replaceAll("<div data-index=\"0\" class=\"slick-slide","<div data-index=\"0\" class=\"{#classname1}");
               doc = Jsoup.parse(html);
                Elements ee=doc.select(".slick-slide");
               while (ee.size()>0){
                    ee.get(0).remove();
                    ee=doc.select(".slick-slide");
                }
                html=doc.toString().replaceAll("<mer-item-thumbnail src=\"([\\s\\S]*?)\" alt=\"([\\s\\S]*?)\" label=\"\" size=\"fluid\" data-testid=\"image-0\" mer-defined=\"\">","<mer-item-thumbnail src=\"{#mainimage}\" alt=\"{#products_name}\" label=\"\" size=\"fluid\" data-testid=\"image-0\" mer-defined=\"\">");
                html=html.replaceAll("<img alt=\"\" class=\"style-scope mer-item-thumbnail\" src=\"([\\s\\S]*?)\" loading=\"lazy\" decoding=\"async\">","<img alt=\"\" class=\"style-scope mer-item-thumbnail\" src=\"{#mainimage}\" loading=\"lazy\" decoding=\"async\">");
                doc = Jsoup.parse(html);
                try {
                    FileUtils.write(new File(path.toString()), doc.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                System.out.println(path);
                try {
                    FileUtils.write(new File(path.toString()), "");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public  static void  htmlnew() {
        File file = new File("E:\\lr\\数据处理内容\\Javaproduct\\greatSeo\\szhtml\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] file1 = file.listFiles();
        for (Object path : file1) {
            try {
                ArrayList list = Textmysql.getFileContent(path.toString(), false);
                String html = list.get(0).toString();
                html=html.replaceAll("<meta property=\"og:title\" content=\"([\\s\\S]*?)\" />","<meta property=\"og:title\" content=\"{#title1}\" />");
                html=html.replaceAll("<meta property=\"og:description\" content=\"([\\s\\S]*?)\"","<meta property=\"og:description\" content=\"{#meta_description}\"");
                html=html.replaceAll("<script([\\s\\S]*?)</script>","<script type=\"application/ld+json\">{#json_data}</script>");
                html=html.replaceAll("<title>([\\s\\S]*?)</title>","<title>{#title1}</title>");
                html=html.replaceAll("<meta property=\"og:site_name\" content=\"([\\s\\S]*?)\">","<meta property=\"og:site_name\" content=\"{#title1}\">");
                html=html.replaceAll("<meta property=\"og:image\" content=\"([\\s\\S]*?)\">","<meta property=\"og:image\" content=\"{#mainimage}\">");
                html=html.replaceAll("<meta property=\"og:url\" content=\"([\\s\\S]*?)\">","<meta property=\"og:url\" content=\"{#current_url}\">");
                html=html.replaceAll("<meta itemprop=\"position\" content=\"1\" /></li><li itemprop=\"itemListElement\" itemscope itemtype=\"https://schema.org/ListItem\"><a itemprop=\"item\" href=\"([\\s\\S]*?)\"><span itemprop=\"name\">([\\s\\S]*?)</span></a>","<meta itemprop=\"position\" content=\"1\" /></li><li itemprop=\"itemListElement\" itemscope itemtype=\"https://schema.org/ListItem\"><a itemprop=\"item\" href=\"{#current_url}\"><span itemprop=\"name\">{#product_name}</span></a>");
                html=html.replaceFirst("<ol class=\"([\\s\\S]*?)-main\">","<ol class=\"{#classname1}-main\">");
                html=html.replaceFirst("-main\"><li><a class=\"([\\s\\S]*?)\"","-main\"><li><a class=\"{#classname1}\"");
                html=html.replaceFirst("sharer\\.php\\?u=([\\s\\S]*?)\"><span>facebook","sharer\\.php\\?u={#hosturl}\"><span>facebook");
                html=html.replaceFirst("facebook</span></a></li><li><a class=\"([\\s\\S]*?)\"","facebook</span></a></li><li><a class=\"{#classname1}\"");
                html=html.replaceFirst("https://twitter\\.com/share\\?url=([\\s\\S]*?)\"><span>twitter<","https://twitter\\.com/share\\?url={#hosturl}\"><span>twitter<");
                html=html.replaceFirst("twitter</span></a></li><li><a class=\"([\\s\\S]*?)\"","twitter</span></a></li><li><a class=\"{#classname1}\"");
                html=html.replaceFirst("https://www\\.linkedin\\.com/shareArticle\\?mini=true&amp;url=([\\s\\S]*?)\"><span>","https://www\\.linkedin\\.com/shareArticle\\?mini=true&amp;url={#hosturl}\"><span>");
                html=html.replaceFirst("linkedin</span></a></li><li><a class=\"([\\s\\S]*?)\"","linkedin</span></a></li><li><a class=\"{#classname1}\"");
                html=html.replaceFirst("https://pinterest\\.com/pin/create/button/\\?url=([\\s\\S]*?)\"><span>pinterest","https://pinterest\\.com/pin/create/button/\\?url={#hosturl}\"><span>pinterest");
                html=html.replaceAll("<h1 id=\"product-name\">([\\s\\S]*?)</h1>","<h1 id=\"product-name\">{#h1title}</h1>");
                html=html.replaceFirst("<td class=\"product-image\">([\\s\\S]*?)</td>","<td class=\"product-image\"><img src=\"{#mainimage}\" title=\"{#title}\" alt=\"{#title}\"<br>{#google_title},{#randname}</td>");
                html=html.replaceFirst("<td class=\"product-info\" style=\"color:#([\\s\\S]*?)\">([\\s\\S]*?)</td>","<td class=\"product-info\" style=\"color:#{#color}\">{#title}</td>");
                html=html.replaceFirst("<td class=\"iku-font\">([\\s\\S]*?)</td>","<td class=\"iku-font\">{#product_model}</td>");
                html=html.replaceFirst("<td class=\"ac-price\">([\\s\\S]*?)円</td>","<td class=\"ac-price\">{#product_price}円</td>");
                html=html.replaceFirst("</table>商品の説明([\\s\\S]*?)<br>","</table>商品の説明{#productrandname}<br>");
                html=html.replaceFirst("</table>商品の説明\\{#productrandname}([\\s\\S]*?)</section>","</table>商品の説明\\{#productrandname}<br>{#products_description}</section>");
                html=html.replaceFirst("<div class=\"product-gallery\">([\\s\\S]*?)</div>","<div class=\"product-gallery\">{#google_images}</div>");
                html=html.replaceAll("<h1>([\\s\\S]*?)</h1>","<h1>{#h1title}</h1>");
                html=html.replaceAll("<h2>([\\s\\S]*?)</h2>","<h2>{#h2title}</h2>");
                html=html.replaceAll("</a><p>([\\s\\S]*?)</p>","</a><p>{#randname1}</p>");
                html=html.replaceAll("<meta name=\"description\" content=\"([\\s\\S]*?)\">","<meta name=\"description\" content=\"{#meta_description}\">");
                html=html.replaceAll("<meta content=\"([\\s\\S]*?)\" name=\"description\">","<meta content=\"{#meta_description}\" name=\"description\">");
                html=html.replaceAll("<meta content=\"([\\s\\S]*?)\" property=\"og:title\">","<meta content=\"{#title}\" property=\"og:title\">");
                html=html.replaceAll("<meta content=\"([\\s\\S]*?)\" name=\"keywords\">","<meta content=\"{#meta_keywords}\" name=\"keywords\">");
                html=html.replaceAll("<a title=([\\s\\S]*?)</a>","{#products_url}");

                Document document=Jsoup.parse(html);
//                System.out.println(document.toString());
//                System.out.println("");
                FileUtils.write(new File(path.toString()), document.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
