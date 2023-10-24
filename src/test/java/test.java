import com.gaobug.Getsitemapurl;
import com.gaobug.Textmysql;
import com.gaobug.utils.RedisUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class test extends RedisUtils {

    public static void main(String[] args) {

        // STOPSHIP: 2023/10/10
//        String des="商品の説明BE@RBRICK田臥勇太宇都宮ブレックス100%&400%ジャンル（スポーツ選手）···バスケットボールフィギュア種類···コレクションフィギュア※配送は佐川急便でのお届けになります。※すり替え防止の観点より、受取り後の返品はご遠慮下さい。ベアブリックBE＠RBRICK田臥勇太BREX100%400%NBAスラムダンク世界中で愛されているブロックベアフィギュアの「BE＠RBRICK」日本人バスケットボールプレーヤー単独モデルの発売は初めてです。宇都宮ブレックス公式ジャンル（スポーツ選手）···バスケットボールフィギュア種類···コレクションフィギュア<img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_1.jpg?1689047990\"><br><img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_2.jpg?1689047990\"><br><img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_3.jpg?1689047990\"><br><img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_4.jpg?1689047990\"><br><img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_5.jpg?1689047990\"><br><img src=\"https://static.mercdn.net/item/detail/orig/photos/m64320930988_6.jpg?1689047990\">";
//        String description=des.substring(0,des.indexOf("<img"));
//        String imges=des.substring(des.indexOf("<img"),des.lastIndexOf(">")+1);
//        System.out.println(description);
//        String [] ss= imges.split("\"><br><img src=\"");
//        for (int i = 0; i < ss.length; i++) {
//            String aa=ss[i].replace("<img src=\"","");
//            aa=aa.replace("\">","");
//            System.out.println(aa);
//        }
//        String productname="再投稿　zzr250 ネイキッド仕様 カワサキ 出産祝い";
//        String [] tags=productname.split(" |,|，|\\.|。|　");
//        System.out.println(tags.length);
//        for (String sa:
//             tags) {
//            System.out.println(sa);
//        }
//        String json1="{\"@context\": \"https://schema.org\", \"@type\": \"NewsArticle\", \"mainEntityOfPage\": { \"@type\": \"WebPage\", \"@id\": \"\" }, \"headline\": \"\", \"image\": [], \"datePublished\": \"2023-09-05T20:28\", \"dateModified\": \"2023-09-16T06:55\", \"author\": {\"@type\": \"Person\", \"name\": \"みんふあ\" }, \"publisher\": { \"@type\": \"Organization\", \"name\": \"goodmaskco.com\" } }";
//        JSONObject googlejson=new JSONObject(json1);
//        googlejson.put("headline","fdsafsd");

        Map map=new HashMap<>();
        map.put("エプロン","1");
        map.put("エプロン","2");
        if (map.get("エプロン")!=null) {
            System.out.println(map.get("エプロンj"));
        }

        //System.out.println(map.get("エプロン"));





//    String str="{\"@context\":\"https://schema.org\",\"@type\":\"NewsArticle\",\"headline\":\"ロードバイク ビアンキ Infinito c2c ultegra\",\"image\":[\"https://static.mercdn.net/item/detail/orig/photos/m43753999182_1.jpg?1587196026\",\"https://static.mercdn.net/item/detail/orig/photos/m43753999182_1.jpg\",\"https://bianchi.ocnk.net/phone/data/bianchi/product/20170604_b4c060.jpg\",\"http://www.climbbikes.com/wp-content/uploads/091225climb1.jpg\",\"https://www.81496.com/jouhou/2012/bianchi/infinitoultegrache.jpg\",\"https://blog-imgs-32.fc2.com/m/i/t/mitilu2525dorutie/img10634672772_convert_20091010142007.jpg\"],\"datePublished\":\"2022-11-09T10:05:33+09:00\",\"dateModified\":\"2022-11-09T10:05:33+09:00\",\"author\": [{\"@type\": \"Person\",\"name\": \"gopulster.com\",\"url\": \"https://gopulster.com/?5728wnds1408seq772410.html\"},{\"@type\": \"Person\",\"name\": \"gopulster.com\",\"url\": \"https://gopulster.com/?5728wnds1408seq772410.html\"}]}";
//
//        JSONObject json1=new JSONObject(str);
//        json1.put("headline","fdsaf");
//        String imgedes="fdsadΦfengesa";
//        String [] iamges=imgedes.split("Φfenge");
//        for (int i = 0; i < iamges.length; i++) {
//
//        }
//        json1.getJSONArray("image").putAll(iamges);
//        System.out.println(json1);
//       String ss="\"> <li class=\"feaprdLi\"> \n" +
//               "         <div class=\"feaprdbk\"> \n" +
//               "          <div class=\"feaprdImg\"> \n" +
//               "           <picture> \n" +
//               "            <source type=\"image/webp\" srcset=\"{#relatedimg0}\"> \n" +
//               "            <img alt=\"{#relatedname0}\" src=\"{#relatedimg0}\" loading=\"lazy\" decoding=\"async\"> \n" +
//               "           </picture> \n" +
//               "          </div> \n" +
//               "          <div class=\"feaprdPrice\"> \n" +
//               "           <span class=\"feanormal\" data=\"{#normalprice0}\">{#normalprice0}</span> \n" +
//               "           <span class=\"feaspecial\" data=\"{#saleprice0}\">{#saleprice0}</span> \n" +
//               "          </div> \n" +
//               "          <div class=\"feaprdName\">\n" +
//               "           {#relatedname0}\n" +
//               "          </div> \n" +
//               "         </div> </li></a>\n" +
//               "       <a href=\"";
//        ss = ss.replace("{#relatedimg" + 0 + "}", "xxxxxxxxxxxxxxxxxxxxx");

    }


//
//    public static void ttf{
//        Map readConfigWeb = Textmysql.readConfigSqlTxt("configweb.txt");
//        ArrayList domains=Textmysql.filedata("changeDomain.txt");
//        String regex="\t";
//        String [] readConfigWebkey= (String[]) readConfigWeb.keySet().toArray(new String[0]);
//        for (int i = 0; i < domains.size(); i++) {
//            String[] arr = domains.get(i).toString().split(regex);
//            for (int j = 0; j < readConfigWebkey.length; j++) {
//                if (readConfigWeb.get(readConfigWebkey[j]).equals(arr[0])){
//                    readConfigWeb.put(readConfigWebkey[j], arr[1]);
//                    System.out.println(readConfigWebkey[j]+"--->"+arr[1]);
//                    File file=new File("../site/"+arr[0]);
//                    if (!file.exists()){
//                        System.out.println("file not exist");
//                    }else {
//                        file.renameTo(new File("../site/"+arr[1]));
//                    }
//                }
//            }
//
//        }
//        Textmysql.creatFlieAndWrite("configweb.txt","",false);
//        for (int j = 0; j < readConfigWebkey.length; j++) {
//            String str=readConfigWebkey[j]+"=>"+readConfigWeb.get(readConfigWebkey[j]);
//            try {
//                FileUtils.write(new File("configweb.txt"),str+"\n",true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        String str="fdsafja";
//        if (str.indexOf("ja")>0){
//            System.out.println("11111111");
//        }else {
//            System.out.println("2222222");
//        }


//        Getsitemapurl getsitemapurl = new Getsitemapurl();
//        ArrayList listId = Textmysql.filedata("id.txt");
//        int idTotal = listId.size();
//        Map mapId = getsitemapurl.initid(listId, idTotal);
//        //Map<Object, Object> readConfigWeb;
//        Map readConfigWeb = Textmysql.readConfigSqlTxt("configweb.txt");
//        ArrayList listJson = Textmysql.getFileContent("googlejson.txt", false);
//        ArrayList hotkeys = Textmysql.filedata("jptitle.txt");
//        ArrayList shellflag = Textmysql.filedata("ll.txt");
//        Map configmap=Textmysql.fileReadMap("config.txt");
//
//        String urlorigin="sitemap10-25.xml";
//        Map domainIndentifier= Getsitemapurl.getDbnameId(urlorigin);
//        System.out.println(domainIndentifier.get("db"));
//        if (readConfigWeb.get(domainIndentifier.get("db"))==null){
//            urlorigin = getsitemapurl.randurl(urlorigin, readConfigWeb, idTotal, "javashell.com",true);
//            System.out.println(urlorigin);
//        }

  //  }
    public static void getyahoo(){
        File file=new File("tt");
        File[] file1=file.listFiles();
        for (Object str:file1
             ) {
            ArrayList list= Textmysql.filedata(str.toString());
            for (int i = 0; i < list.size(); i++) {
                String urlencode = null;
                try {
                    urlencode = URLEncoder.encode(list.get(i).toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String str1="&pz=7";
                String str2="&b=";
                String str3="&pz=7&xargs=0";
                int a=7;

                try {
                    FileUtils.write(new File("tt/tt.txt"),"",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 10; j++) {
                    String st=str1+str2+a*j+7+str3;
                    String yahoourl = "https://br.search.yahoo.com/search?p=" + urlencode+st;

                    try {
                        FileUtils.write(new File("tt/tt.txt"),yahoourl+"\n",true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }
}
