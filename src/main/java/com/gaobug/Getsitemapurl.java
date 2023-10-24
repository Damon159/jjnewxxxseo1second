package com.gaobug;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Getsitemapurl {

    public String echoSitemap(String sitemapName,String [] urlidmapkey,String flag,String secondname){
        LocalDateTime localDateTime = LocalDateTime.now();
        String data = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        String sitemap_header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n                <urlset\n        xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n        xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n        http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">";
        String sitemap_header = "<urlset xmlns=\"https://www.sitemaps.org/schemas/sitemap/0.9\">";
//        sitemap_header += "<url><loc>"+sitemapName+"</loc> <lastmod>"+data+"</lastmod>"+"<changefreq>daily</changefreq>\n" +
//                "    <priority>0.1</priority>\n" +
//                "  </url>";
//        sitemap_header += "<url><loc>"+sitemapName+"</loc>" +
//                "  </url>";
        String lastSitemap="</urlset>";
        String url =  randGetUrl(2000,data,sitemapName,urlidmapkey,flag,secondname);
        sitemap_header=sitemap_header+url+lastSitemap;
        return sitemap_header;
    }
    public String maps(int count,String hostname,String secondname){
        LocalDateTime localDateTime = LocalDateTime.now();
        String data = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        String sitemap_header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n                <urlset\n        xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n        xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n        http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">";
        String sitemap_header = "<urlset xmlns=\"https://www.sitemaps.org/schemas/sitemap/0.9\">";
        StringBuilder sitemap_content= new StringBuilder();
        for (int i = 1; i <= count; i++) {
            String sitemapName=hostname+"/"+secondname+"sitemap231013"+i+".xml";
            //sitemap_content.append("<url><loc>").append(sitemapName).append("</loc> <lastmod>").append(data).append("</lastmod>").append("<changefreq>daily</changefreq>\n").append("    <priority>0.1</priority>\n").append("  </url>");
            sitemap_content.append("<url><loc>").append(sitemapName).append("</loc>").append("  </url>");
        }
        String lastSitemap="</urlset>";
        return sitemap_header+sitemap_content+lastSitemap;
    }
//    /**
//     * 生成sitemap 随机生成两千条url
//     * */
//    public static String randGetUrl(int count, String data,String sitemapName,Map configWeb,String flag,int idTotal,String secondname){
//        String sumUlr="";
//        for (int i=0;i<count;i++){
//            String url="<url><loc>"+sitemapName+"/"+secondname+"?"+randLastDb(configWeb,idTotal)+flag+"</loc><lastmod>"+data+"</lastmod>" +
//                    "<priority>0.8</priority>" +
//                    "<changefreq>hourly</changefreq></url>";
//            sumUlr+=url;
//        }
//        return sumUlr;
//    }
public String rsssitemap(String sitemapName, Map configWeb, int idTotal, String secondname, ArrayList<String> secondnumber, ArrayList<String> shellflag, Map idurlmap){
    StringBuilder hh= new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<rss version=\"2.0\">\n" +
            "<channel>\n");
    String all ="";
    String sername=sitemapName.replaceAll("http://|https://","");
    LocalDateTime localDateTime = LocalDateTime.now();
    String data = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String time = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    data=data+"T"+time+"+08:00";
    for (int i = 0; i < 290; i++) {
        int id=(int)(Math.random()*idTotal);
        String url= (String) idurlmap.get(id);
        String flag=getRandflag(sitemapName, shellflag,url);
        List<String> content = Getsitemapurl.getProductTxt(url, configWeb,idTotal);
        Map map1 = (Map) JSON.parse(content.get(0));
        String productname = map1.get("product_name")!=null?map1.get("product_name").toString():"";
        productname=productname.replace("&","");
        int numb= (int) (Math.random()*secondnumber.size());
        String temp="<item>\n" +
                "    <title>"+productname+"</title>\n" +
                "    <link>"+sitemapName+"/"+secondname+secondnumber.get(numb)+"/"+url+flag+"</link>\n" +
                "    <author>"+sername+"</author>\n" +
                "    <pubDate>"+data+"</pubDate>\n" +
                "  </item>\n";
        hh.append(temp);


    }
    String end="</channel>\n" +
            "</rss>\n";
    return hh+end;
}
    /**
     * 生成sitemap 随机生成n条url
     * */
    public static String randGetUrl(int count, String data,String sitemapName,String [] urlidmapkey,String flag,String secondname){
        StringBuilder sumUlr= new StringBuilder();
        for (int i=0;i<count;i++){
           String tturl= urlidmapkey[(int) (Math.random()*urlidmapkey.length)];
            String url="<url><loc>"+sitemapName+"/"+secondname+tturl+"</loc></url>";
            sumUlr.append(url);
        }
        return sumUlr.toString();
    }
    /**
     * 随机获取sitemap网站链接
     * */
    public static String randLastDb(Map configWeb,int idTotal){
        String[] keys = (String[]) configWeb.keySet().toArray(new String[0]);
        Random random = new Random();
        String randomKey = keys[random.nextInt(keys.length)];
        int num=(int)(Math.random()*idTotal+1);
        String url=randomKey+num;
        return  url;
    }

    /**
     * 随机生成一条链接
     * @param url  xevm200
     * @return
     */
    public static String  randOneurl(String url,Map configWeb,int idTotal){
        Map<String, String> map = getDbnameId(url);
        int count=0;
        String integ= map.get("db");
        int id=Integer.parseInt(map.get("id"));
        for (int i = 0; i < integ.length(); i++) {
            count+=(int)integ.charAt(i);
        }
        int newindex=count%configWeb.size();
        String [] temp= (String[]) configWeb.keySet().toArray(new String[0]);
        String newdb=temp[newindex];
        int newid=id%idTotal+1;
        return newdb+newid;
    }

    /**
     * 读取configweb.txt  第一
     * */
    public  static Map<String, Object> readConfigWeb(){
        String path = "configweb.txt";
        File file = new File(path);
        Map<String,Object> map=new HashMap<>();
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));//构造一个BufferedReader类来读取文件

            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                String[] library= s.split("=>");  //获取数据库名
                map.put(library[0],library[1]);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    //0获取数据 1 url
    public static List<String> getProductTxt(String content, Map configWeb, int idToal){
            List<String> list = null;
        //System.out.println("content"+content);
            Map<String, String> map = getDbnameId(content);
            String id= map.get("id");
            String integ= map.get("db");
       // System.out.println("id"+id);
        //System.out.println("db"+integ);
            String domain = (String) configWeb.get(map.get("db"));
            String path = "../site/" + domain + "/p" + map.get("id") + ".txt";
            File file=new File(path);
            //不存在
            if (domain==null || !file.exists()) {
                content=randOneurl(content,configWeb,idToal);
                map = getDbnameId(content);
                domain = (String) configWeb.get(map.get("db"));
                integ= map.get("db");
                id= map.get("id");
                path = "../site/" + domain + "/p" + id+ ".txt";
                //file=new File(path);
            }
        //list=Textmysql.filedata(path);
        try {
            list=FileUtils.readLines(new File(path),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert list != null;
        list.add(integ+id);
            return list;
    }


    /**
     *
     * @param path
     * @return
     * 按照原文本内容读取
     */
    public static String getFileContent(String path){
        File file=new File(path);
        String st = "";
        String content="";
        if (!file.exists()){
            return "templetes not exit!";
        }else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader
                        (new FileInputStream(new File
                                (path)
                        ), StandardCharsets.UTF_8));
                while ((st=br.readLine())!=null){
                    content+=st+"\n";
                }
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
    public static Map<String, String> getDbnameId(String str){
        Map<String, String> map=new HashMap<>();
        String reg1="^[a-z]+";
        String reg2="\\d+";
        Pattern pattern=Pattern.compile(reg1);
        Matcher match=pattern.matcher(str);
        if (match.find()){
            map.put("db",match.group(0));
        }
        Pattern pattern1=Pattern.compile(reg2);
        Matcher match1=pattern1.matcher(str);
        if (match1.find()){
            map.put("id",match1.group(0));
        }
        return map;
    }

    /**
     * 随机获取n条链接
     * @param url
     * @return
     */

    public static ArrayList<String> getLast(String url, Map mapID, Map configWeb, int urlcount, int idcount){
        Map<String, String> mapurl=getDbnameId(url);
        ArrayList<String> arrayList=new ArrayList<>();
        //代号
        String[] dbnames=dbnamesall(configWeb, mapurl.get("db"),urlcount);
        //转化为下角标
        int idex=Integer.parseInt(String.valueOf(mapurl.get("id")))-1;
        for (int i = 0; i < urlcount; i++) {
                if (i < urlcount/2) {
                    arrayList.add(dbnames[i] + mapID.get(idex));
                    idex = Integer.parseInt(mapID.get(idex).toString())-1;
                } else {
                    idex = (idex + 1) % idcount;
                    arrayList.add(dbnames[i] + mapID.get(idex));
                }
        }
        return arrayList;
    }
    public static ArrayList<Object> getLastnew(String url, Map idurlmap, int urlcount){
        ArrayList<Object> arrayList=new ArrayList<>();
        int aid=Textmysql.ASCIcount(url)%idurlmap.size();
        int temp=1;
        ArrayList<Integer> te=new ArrayList<Integer>();
        for (int i = 0; i < urlcount; i++) {
            if (i < urlcount/2) {
                arrayList.add(idurlmap.get(aid%idurlmap.size()));
                te.add(aid);
                aid++;
            } else {
                //随机链接
//             int aa= (int) (Math.random()*idurlmap.size());
//             arrayList.add(idurlmap.get(aa));
             //随机固定链接
             arrayList.add(idurlmap.get((aid+2000*temp)%idurlmap.size()));
             aid++;
             temp++;
            }
        }
        return arrayList;
    }


    public static Map<Integer, Object> initid(ArrayList listId, int idTotal){
        Map<Integer, Object> map=new HashMap<Integer, Object>();
        for (int i = 0; i < idTotal; i++) {
            map.put(i,listId.get(i));
        }
        return map;
    }
    //找到位置获取下面
    public static String [] dbnamesall(Map map,String content,int count){
        String [] dbs = new String[count];
        String [] keys= (String[]) map.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            if(keys[i].equals(content)){
                int kk=i;
                for (int j = 0; j < count; j++) {
                    kk=(kk+1)%map.size();//111
                    dbs[j]=keys[kk];
                }
                break;
            }
        }
        return dbs;
    }
    public static void writelog1(String url,String ip,String hostname,Map confiWeb){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dat=formatter.format(calendar.getTime());
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(calendar.getTime());
        String shellUrl=hostname+url;
        File file =new File("logs/urllogs/"+dat+".txt");
    }
    public static String  writeLog(String url, String ip, String hostname, Map<String, String> confiWeb, String urlRefer){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dat=formatter.format(calendar.getTime());
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(calendar.getTime());
        String shellUrl=hostname+"/index.php?"+url;
        //String shellUrl=hostname+"/?"+url;
        String realUrl="";
        Map<String, String> domainIndentifier=Getsitemapurl.getDbnameId(url);
        realUrl="https://"+confiWeb.get(domainIndentifier.get("db"))+"/index.php?main_page=product_info&products_id="+domainIndentifier.get("id");
        String result=time+"\t"+ip+"\t"+shellUrl+"\t"+realUrl+"\t"+urlRefer;
        File file =new File("logs/urllogs/"+dat+".txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (ip!=null)
            FileUtils.write(new File("logs/urllogs/" + dat + ".txt"),result+"\n",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return realUrl;
    }
    public static String productJson(String productName,String description,String mainimage,String price,String url,String hostname,
                                     String json,String cate2url,String cate3url,String cate4url,String cate1,String cate2,String cate3,String jj,String imgedes){
        json=json.replace(" ","");
        JSONObject googlejson=new JSONObject(json);
        int jsonid=0;
        JSONObject json1 = null;
        String ss;
        String [] keys= googlejson.keySet().toArray(new String[0]);
        if (jj.equals("")) {
            for (int i = 0; i < url.length(); i++) {
                jsonid += url.charAt(i);
            }
            jsonid = jsonid % googlejson.length();
            ss=keys[jsonid];
        }else
            ss=jj;
        LocalDateTime localDateTime = LocalDateTime.now();
        String data = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time=localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        data=data+"T"+time;
        String modifdata = localDateTime.plusDays(13).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time1=localDateTime.plusSeconds(6000).format(DateTimeFormatter.ofPattern("HH:mm"));
        modifdata=modifdata+"T"+time1;
        String servername=hostname.replaceAll("http://|https://","");
        //int jsonid=4;
        //String ss=keys[jsonid];
        switch (ss){
            case "json1" : //product
                json1= googlejson.getJSONObject(ss);
                json1.put("name",productName);
                json1.put("description",description);
                json1.getJSONArray("image").put(0,mainimage);
                //随机固定评分
                double randrating=Double.parseDouble(price);
                int intrandrating=((int) randrating *3+15)%10;
                String rating="4."+intrandrating;
                String [] aa={"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
                double randreviews=Double.parseDouble(price);
                int intrandreviews=((int) randreviews *4+16)%aa.length;
                if (mainimage.indexOf("static.mercdn.net")>0) {
                    String sku = mainimage.substring(mainimage.indexOf("photos/") +7,mainimage.indexOf("_1"));
                    json1.put("sku",sku);
                }
                json1.getJSONObject("offers").put("price",price);
                json1.getJSONObject("offers").put("url",url);
                json1.getJSONObject("offers").put("offers","JPY");
                json1.getJSONObject("review").getJSONObject("author").put("name",hostname);
                json1.getJSONObject("review").getJSONObject("reviewRating").put("ratingValue",rating);
                json1.getJSONObject("aggregateRating").put("ratingValue",rating);
                json1.getJSONObject("aggregateRating").put("reviewCount",aa[intrandreviews]);
                break;
            case "json2": //NewsArticle1

                json1= googlejson.getJSONObject(ss);
                json1.put("headline",productName);
                json1.getJSONArray("image").put(0,mainimage);
                json1.put("datePublished",data);
                json1.put("dateModified",modifdata);

                json1.getJSONObject("author").put("name",servername);
                json1.getJSONObject("author").put("jobTitle",servername);
                json1.getJSONObject("author").put("url",hostname);
                json1.getJSONObject("publisher").put("name",servername);
                json1.getJSONObject("publisher").put("publisher",servername);
                json1.put("description",description);
                break;
                case "json3": //Event
                    json1= googlejson.getJSONObject(ss);
                json1.put("name",productName);
                json1.put("startDate",data);
                json1.put("endDate",modifdata);
                json1.getJSONObject("location").put("url",hostname);
                json1.getJSONArray("image").put(0,mainimage);
                json1.put("description",description);
                json1.getJSONObject("offers").put("url",hostname);
                json1.getJSONObject("offers").put("price",price);
                json1.getJSONObject("offers").put("offers","JPY");
                json1.getJSONObject("offers").put("validFrom","2099-12-29");
                json1.getJSONObject("organizer").put("name",servername);
                json1.getJSONObject("organizer").put("url",hostname);
                break;
                case "json4": //ItemList
                    json1= googlejson.getJSONObject(ss);
                    json1.getJSONArray("itemListElement").getJSONObject(0).getJSONObject("item").put("url",hostname);
                    json1.getJSONArray("itemListElement").getJSONObject(1).getJSONObject("item").put("url",cate2url);
                    json1.getJSONArray("itemListElement").getJSONObject(2).getJSONObject("item").put("url",cate3url);
                    for (int i = 0; i <json1.getJSONArray("itemListElement").length() ; i++) {
                        JSONObject temp=json1.getJSONArray("itemListElement").getJSONObject(i);
                        temp.getJSONObject("item").put("name",productName);
                        temp.getJSONObject("item").put("description",description);
                        temp.getJSONObject("item").getJSONObject("provider").put("name",servername);
                        temp.getJSONObject("item").getJSONObject("provider").put("sameAs",hostname);
                    }
                break;
                case "json5": //BreadcrumbListold

                    json1= googlejson.getJSONObject(ss);
                    json1.getJSONArray("itemListElement").getJSONObject(0).put("item",hostname);
                    json1.getJSONArray("itemListElement").getJSONObject(1).put("item",cate2url);
                   //2).put("item",cate3url);
                    for (int i = 0; i <json1.getJSONArray("itemListElement").length() ; i++) {
                        JSONObject temp=json1.getJSONArray("itemListElement").getJSONObject(i);
                        temp.put("name",hostname);
                    }
                break;
            case "json6": //NewsArticle2

                json1= googlejson.getJSONObject(ss);
                json1.put("headline",productName);
                json1.getJSONObject("mainEntityOfPage").put("@id",cate3url);
                json1.getJSONArray("image").put(0,mainimage);
                json1.put("datePublished",data);
                json1.put("dateModified",modifdata);
                json1.getJSONObject("author").put("name",servername);
                json1.getJSONObject("publisher").put("name",servername);
                json1.getJSONObject("publisher").put("url",cate2url);
                break;
            case "json7": //BreadcrumbList
                    json1= googlejson.getJSONObject(ss);
                    JSONObject temp=json1.getJSONArray("itemListElement").getJSONObject(0);
                    temp.put("name",cate1);
                    temp.put("item",hostname);
                    temp=json1.getJSONArray("itemListElement").getJSONObject(1);
                    temp.put("name",cate2);
                    temp.put("item",hostname+cate2url);
                    temp=json1.getJSONArray("itemListElement").getJSONObject(2);
                    temp.put("name",cate3);
                    temp.put("item",hostname+cate3url);
                    temp=json1.getJSONArray("itemListElement").getJSONObject(3);
                    temp.put("name",productName);
                    temp.put("item",hostname+cate4url);
                    break;
            case "json8"://productnew
                json1=googlejson.getJSONObject(ss);
                description=description.replace("<p>","");
                description=description.replaceAll("<\\/p>","");
                json1.put("description",description);
                if (mainimage.indexOf("static.mercdn.net")>0) {
                    String sku = mainimage.substring(mainimage.indexOf("photos/") +7,mainimage.indexOf("_1"));
                    json1.put("sku",sku);
                    String mpn=sku.substring(0,4);
                    json1.put("mpn",mpn);
                }else {//煤炉数据
                    json1.put("sku","");
                }
                json1.put("image",mainimage);
                json1.put("name",productName);
                String [] reviewsname={"三富英樹","乗松伸幸","濱野彰士","増田眞治","福崎桂一郎","前島一弓","小林衣里","坂本晴彦","阿保君枝","平井博之","青柳仁司","行方宏至","松下直美","土門大幸","大堀達則","中原賢治","酒井幸太郎","篠原剛","池谷義紀","山崎一男","淀川美和","新井正巳","渡邊聖也","清水和洋","早川哲二","浅見博美","寺田政登","谷澤篤","有働順子","倭秀樹","森永義正","齊藤淳","林忠雄","伊藤弘宣","冨澤忠","北澤聖吾","寺尾秀司","日野剛","増田茂","田中淳一","若月賢一","渡辺剛史","鈴木和美","小澤太一","高橋英太","中嶋進","高江洲義経","石川信二","池田遵","岡一美"};
                String [] bb={"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};

                int tt=0;
                for (int i = 0; i < url.length(); i++) {
                    tt += url.charAt(i);
                }
                 randrating=Double.parseDouble(price);
                 intrandrating=((int) randrating *3+15)%10;
                 rating="4."+intrandrating;
                 json1.getJSONArray("review").getJSONObject(0).getJSONObject("reviewRating").put("ratingValue",rating);
                 randrating=Double.parseDouble(price);
                 intrandrating=((int) randrating *3+15)%10;
                 rating="4."+intrandrating;
                json1.getJSONArray("review").getJSONObject(1).getJSONObject("reviewRating").put("ratingValue",rating);

                json1.getJSONArray("review").getJSONObject(0).getJSONObject("author").put("name",reviewsname[tt%reviewsname.length]);
                json1.getJSONArray("review").getJSONObject(1).getJSONObject("author").put("name",reviewsname[(tt+1)%reviewsname.length]);
                String [] cc={"90","91","92","93","94","95","96","97","98","99"};
                json1.getJSONObject("aggregateRating").put("ratingValue",cc[tt%cc.length]);
                json1.getJSONObject("aggregateRating").put("ratingCount",bb[tt%bb.length]);
                json1.getJSONObject("offers").put("price",price);
                json1.getJSONObject("offers").put("url",url);
                json1.getJSONObject("offers").put("offers","JPY");
                json1.getJSONObject("offers").put("priceValidUntil","2024-01-15");
                json1.getJSONObject("offers").getJSONObject("seller").put("name",servername);
                break;
            case "json9":
                json1=googlejson.getJSONObject(ss);
                json1.put("headline",productName);
                String [] iamges=imgedes.split("Φfenge");
                json1.getJSONArray("image").putAll(iamges);
                json1.put("datePublished",data+"+09:00");
                json1.put("dateModified",modifdata+"+09:00");
                json1.getJSONArray("author").getJSONObject(0).put("url",hostname+"/?"+url);
                json1.getJSONArray("author").getJSONObject(0).put("name",servername);
                json1.getJSONArray("author").getJSONObject(1).put("url",hostname+"/?"+url);
                json1.getJSONArray("author").getJSONObject(1).put("name",servername);
                break;
            case "json10":
                json1=googlejson.getJSONObject(ss);
                json1.put("headline",productName);
                json1.getJSONObject("mainEntityOfPage").put("@id",hostname);
                json1.getJSONArray("image").put(0,mainimage);
                json1.put("datePublished",data+"+09:00");
                json1.put("dateModified",modifdata+"+09:00");
                json1.getJSONObject("author").put("name",servername);
                json1.getJSONObject("publisher").put("name",servername);
                json1.getJSONObject("publisher").put("url",hostname);
                break;
            case "json11":
                description=description.replaceAll("<([\\s\\S]*?)>", Matcher.quoteReplacement(""));
                description=description.replace("<p>","");
                description=description.replace("</p>","");
                json1=googlejson.getJSONObject(ss);
                json1.put("name",productName);
                json1.put("image",mainimage);
                json1.put("description",description);
                json1.getJSONObject("offers").put("url",url);
                double pp=Double.parseDouble(price);
                int ppin= (int) pp;
                json1.getJSONObject("offers").put("price",ppin);
                int aaa=Textmysql.ASCIcount(url)%10;
                String ratingValue="4."+aaa;
                pp=Double.parseDouble(ratingValue);
                int count=aaa%10+20;
                json1.getJSONObject("aggregateRating").put("ratingValue",pp);
                json1.getJSONObject("aggregateRating").put("reviewCount",count);
                json1.put("category",cate2+cate3);
                break;
            case "json12":
                description=description.replaceAll("<([\\s\\S]*?)>", Matcher.quoteReplacement(""));
                description=description.replace("<p>","");
                description=description.replace("</p>","");
                json1=googlejson.getJSONObject(ss);
                json1.put("name",productName);
                json1.put("image",mainimage);
                json1.put("description",description);
                int urlcc=Textmysql.ASCIcount(url);
                int cct=urlcc*15;
                int ttc=urlcc*20;
                json1.put("sku",""+urlcc+cct+ttc);
                json1.put("mpn",""+urlcc+cct+ttc);
                break;

        }
        String pretty = formatJson(json1.toString());
        //return json1.toString();
        return pretty;
    }

    public static String getNexturlnew(String url, Map<Object, Integer> urlidmap, Map<Integer, Object> idurlmap, boolean flag) {
        String nexturl="";
        if (flag) { //下一个id
           int id= (urlidmap.get(url) +1)%idurlmap.size();
            nexturl= idurlmap.get(id).toString();
           return nexturl;
        } else { //下一个db 下5个id
            int id= urlidmap.get(url);
            int tem=(id+5)%idurlmap.size();
             nexturl= idurlmap.get(tem).toString();
            return nexturl;
        }
    }
    public static String getNexturl(String url,Map webConfig,int productIdTotal,boolean flag){
        String [] keys= (String[]) webConfig.keySet().toArray(new String[0]);
        Map<String, String> map=Getsitemapurl.getDbnameId(url);
        int id=Integer.parseInt(String.valueOf(map.get("id")));
        String db=map.get("db").toString();
        if (flag){ //下一个id
            if (id==productIdTotal) {
                for (int i = 0; i < keys.length; i++) {
                    if (keys[i].equals(db)) {
                        int temp=(i + 1)%keys.length;
                        db = keys[temp];
                        break;
                    }
                }
                id=id%productIdTotal+1;
                return db+id;
            }else {
                id+=1;
                return db+id;
            }
        }else { //下一个db 下5个id
            for (int i = 0; i < keys.length; i++) {
                if (keys[i].equals(db)) {
                    int temp=(i + 2)%keys.length;
                    db = keys[temp];
                    break;
                }
            }
            id=id%productIdTotal+5;
            id+=1;
            return db+id;

        }
    }
    public static int urlCount(String html,String rgex){
        int oldCount = html.length();
        // 将 ab 替换为空之后字符串的长度
        int newCount = html.replace(rgex, "").length();
        // 由于统计的字符串长度是2，所以出现的次数要除以要统计字符串的长度
        return (oldCount - newCount) / rgex.length();
    }
    public static String randGetOne(String path,ArrayList data,int randnum){
        if(data!=null) {
            int newindex=(randnum+11)%data.size();
            return data.get(newindex).toString();
        }else {
            try {
                data= (ArrayList) FileUtils.readLines(new File(path),"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int newindex=(randnum+11)%data.size();
            return data.get(newindex).toString();
        }
    }
    public static String getRandflag(String hostName,ArrayList shellflag,String url){
        //首页内容
        hostName=hostName.replace("www.","");
        if (hostName.equals("/")){
            int temp=0;
            for (int i = 0; i < hostName.length(); i++) {
                temp+=hostName.charAt(i);
            }
            temp=temp%shellflag.size();
            return shellflag.get(temp)+String.valueOf(temp);
        }else {//非首页内容
            int count = 0;
            for (int i = 0; i < url.length(); i++) {
                count += url.charAt(i);
            }
            int newindex = count% shellflag.size();
            String res = shellflag.get(newindex) + String.valueOf(newindex);
            return res;
        }
    }
    public static String MD5(String str){
        String slat = "&%5123***&&%%$$#@";
        String base = str +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    public static String checkurl(String urlorigin,Map readConfigWeb,int idTotal,String hostname,Boolean flag,String [] keys) {
        String Rexg = "^([a-z]){7}(\\d+)(.*)";
        Pattern pattern = Pattern.compile(Rexg);
        Matcher match = pattern.matcher(urlorigin);
        //默认随机db id
        int randkeyid= (int) (Math.random()*keys.length);
        String db = keys[randkeyid];
        int id= (int) (Math.random()*idTotal+1);
        if (!match.find()){
            //hostname = hostname.replaceAll("http://|https://", "");
            // "" 非 ""
            int counthostname = 0;
            int counturl = 0;
            if (hostname.length() > 0 ) { //域名存在
                for (int i = 0; i < hostname.length(); i++) {
                    counthostname += hostname.charAt(i);
                }
                for (int i = 0; i < urlorigin.length(); i++) {
                    counturl += urlorigin.charAt(i);
                }
                db= keys[counthostname / 10 % keys.length];
                id= counturl % idTotal+1;
                urlorigin = db + id;
                if (flag)
                System.out.println( "hostname available set url-->" + urlorigin);
            }
            else { //域名不存在
                if (flag)
                    System.out.println( "hot notavailable set url-->" + urlorigin);
                urlorigin = db + id;
            }
        }
        Map<String, String> map=getDbnameId(urlorigin);
        int idlenght=map.get("id").toString().length();
        int tempid=1;
        if (idlenght<5) {
            tempid = Integer.parseInt(map.get("id"));
        }
         if (readConfigWeb.get(map.get("db"))==null|| idlenght>5 ||tempid<0){
             randkeyid= (int) (Math.random()*keys.length);
             db = keys[randkeyid];
             id= (int) (Math.random()*idTotal+1);
             urlorigin=db+id;
        }
        return urlorigin;
    }

    public static String checkurlnew(String urlorigin, Map<String, String> readConfigWeb, String hostname, Boolean flag, Map idurlmap, Integer [] keysid, int urlcount, String befou) {
        urlorigin=urlorigin.replaceAll("^(\\d+)","");
        urlorigin=urlorigin.replace("-","");
        String Rexg = "^([a-z]){"+urlcount+"}(\\d+)(.*)";
        Pattern pattern = Pattern.compile(Rexg);
        Matcher match = pattern.matcher(urlorigin);
        String url=urlorigin;
        //链接不匹配
        if (!match.find()){
            //hostname = hostname.replaceAll("http://|https://", "");
            // "" 非 ""
            if (hostname.length() > 0 ) { //域名存在
                int id= Textmysql.ASCIcount(befou)%idurlmap.size()+2000;
                //int id= (int) (Math.random()*keysid.length);
                urlorigin=idurlmap.get(id).toString();
                if (flag)
                    System.out.println( "hostname available set url-->"+url+"-----" + urlorigin);
            }
            else { //域名不存在
                int randid=Textmysql.ASCIcount(befou);
                //int randid= (int) (Math.random()*keysid.length);
                urlorigin= (String) idurlmap.get(randid);
                if (flag)
                    System.out.println( "hot notavailable set url-->"+url+"-----" + urlorigin);
            }
        }
        Map<String, String> map=getDbnameId(urlorigin);
        int idlenght=map.get("id").toString().length();
        int tempid=1;
        if (idlenght<5) {
            tempid = Integer.parseInt(map.get("id"));
        }
        if (readConfigWeb.get(map.get("db"))==null|| idlenght>5 ||tempid<0){
            int randid= (int) (Math.random()*keysid.length);
            urlorigin= (String) idurlmap.get(randid);
        }
        return urlorigin;
    }
    public static String randurl(String urlorigin,Map readConfigWeb,int idTotal,String hostname,Boolean flag) {
            //hostname = hostname.replaceAll("http://|https://", "");
            // "" 非 ""
            int counthostname = 0;
            int counturl = 0;
            if (hostname.length() <=0 ) {
                hostname=Textmysql.rand(7);
            }
            for (int i = 0; i < hostname.length(); i++) {
                counthostname += hostname.charAt(i);
            }
            if (urlorigin.length()<=0) {
                urlorigin=Textmysql.rand(7);

            }
            for (int i = 0; i < urlorigin.length(); i++) {
                counturl += urlorigin.charAt(i);
            }

                String[] keys = (String[]) readConfigWeb.keySet().toArray(new String[0]);
                String db = keys[counthostname / 10 % keys.length];
                String id = String.valueOf(counturl % idTotal+1);
                urlorigin = db + id;
                if (flag)
                    System.out.println( "randurl-->" + urlorigin);
        return urlorigin;
    }
    public static Map<String, Object> inittempletes(){
        Map<String, Object> map=new HashMap<String, Object>();
        String key;
        String value;
        String[] templetes = Textmysql.urlFilename("../html");
        for (int i = 0; i < Objects.requireNonNull(templetes).length; i++) {
            key = templetes[i];
            ArrayList datas = Textmysql.getFileContent("../html/" + templetes[i],true);
            assert datas != null;
            map.put(key, datas.get(0));
        }
        return map;
    }
    public static Map<String, Object> getcatetem(){
        Map<String, Object> map=new HashMap<String, Object>();
        String key;
        String value;
        String[] templetes = Textmysql.urlFilename("../catehtml");
        for (int i = 0; i < Objects.requireNonNull(templetes).length; i++) {
            key = templetes[i];
            ArrayList datas = Textmysql.getFileContent("../catehtml/" + templetes[i],true);
            assert datas != null;
            map.put(key, datas.get(0));
        }
        return map;
    }
    public static String breadcrumbshtml(int count){
        String breadcrumbshtmlh= "<ol itemscope itemtype=\"https://schema.org/BreadcrumbList\">\n" +
                "      <li itemprop=\"itemListElement\" itemscope\n" +
                "          itemtype=\"https://schema.org/ListItem\">\n" +
                "        <a itemprop=\"item\" href=\"/\">\n" +
                "            <span itemprop=\"name\">{#cate1}</span></a>\n" +
                "        <meta itemprop=\"position\" content=\"1\" />\n" +
                "      </li>\n";
        String breadcrumbshtmle="</ol>\n" +
                "{#domain}\n";
        StringBuilder temp= new StringBuilder();
        for (int i = 2; i <= count; i++) {
            String str= "<li itemprop=\"itemListElement\" itemscope\n" +
                    "          itemtype=\"https://schema.org/ListItem\">\n" +
                    "        <a itemprop=\"item\" href=\"{#cate"+i+"url}\">\n" +
                    "        <span itemprop=\"name\">{#cate"+i+"}</span></a>\n" +
                    "    <meta itemprop=\"position\" content=\""+i+"\" />\n" +
                    "    </li>\n";
            temp.append(str);
        }
        return breadcrumbshtmlh+temp+breadcrumbshtmle;
    }

    // 去首页面包屑
    public static String breadcrumbshtml1(int count){
        String breadcrumbshtmlh= "<ol itemscope itemtype=\"https://schema.org/BreadcrumbList\">\n";
        String breadcrumbshtmle="</ol>\n" +
                "{#domain}\n";
        String temp="";
        for (int i = 1; i <= count; i++) {
            String str= "<li itemprop=\"itemListElement\" itemscope\n" +
                    "          itemtype=\"https://schema.org/ListItem\">\n" +
                    "        <a itemprop=\"item\" href=\"{#cate"+i+"url}\">\n" +
                    "        <span itemprop=\"name\">{#cate"+i+"}</span></a>" +
                    "    <meta itemprop=\"position\" content=\""+i+"\"/>\n" +
                    "    </li>\n";
            temp+=str;
        }
        return breadcrumbshtmlh+temp+breadcrumbshtmle;
    }
    public static String breadcrumbshtml2(){
     String str="<span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
             "<a href=\"/\" itemprop=\"url\" style=\"color:#0045cc\">" + "<span itemprop=\"title\">{#cate1}</span>&raquo</a></span><span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
             "<a href=\"{#cate2url}\" itemprop=\"url\" style=\"color:#0045cc\">" + "<span itemprop=\"title\">{#cate2}</span>&raquo</a></span><span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
             "<a href=\"{#cate3url}\" itemprop=\"url\" style=\"color:#0045cc\"><span itemprop=\"title\">{#cate3}</span></a></span>";
     return str;

    }
    public static String breadcrumbshtml3(){
        String str="<span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
                "<a href=\"{#cate1url}\" itemprop=\"url\" style=\"color:#{#color}\">" + "<span itemprop=\"title\">{#cate1}</span>&raquo</a></span><span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
                "<a href=\"{#cate2url}\" itemprop=\"url\" style=\"color:#{#color}\">" + "<span itemprop=\"title\">{#cate2}</span>&raquo</a></span><span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\">" +
                "<a href=\"{#cate3url}\" itemprop=\"url\" style=\"color:#{#color}\"><span itemprop=\"title\">{#cate3}</span></a></span>";
        return str;

    }
    public static String breadcrumbshtml4(){
        String str="<a href=\"{#cate2url}\">{#cate2}</a> > <a href=\"{#cate3url}\">{#cate3}</a> > ";
        return str;

    }
    public static String breadcrumbshtml5(){
        String str="<span class=\"pifright\"> <a href=\"{#hosturl}\"> <ol itemscope itemtype=\"https://schema.org/BreadcrumbList\"><li itemprop=\"itemListElement\" itemscope itemtype=\"https://schema.org/ListItem\"><a href=\"{#hosturl}\" itemprop=\"item\"><span itemprop=\"name\">ホーム</span></a><meta itemprop=\"position\" content=\"1\"></li>\n" +
                "<li itemprop=\"itemListElement\" itemscope itemtype=\"https://schema.org/ListItem\"><a href=\"{#cate2url}\" itemprop=\"item\"><span itemprop=\"name\">{#cate2}</span></a><meta itemprop=\"position\" content=\"2\"></li>\n" +
                "<li itemprop=\"itemListElement\" itemscope itemtype=\"https://schema.org/ListItem\"><a href=\"{#cate3url}\" itemprop=\"item\"><span itemprop=\"name\">{#cate3}</span></a><meta itemprop=\"position\" content=\"3\"></li>\n" +
                "</li></ol></a> </span> ";
        return str;

    }
    public static String cateurls(){
        String str="<li id=\"catyprdli\" class=\"catyprdprdLi\">\n" +
                "        <div class=\"catyprdprdbk\"><a href=\"{#url}\" title=\"{#productname}\">\n" +
                "        <div class=\"catyprdImg\"><img loading=\"lazy\" alt=\"{#productname}\" src=\"{#mainimage}\"/></div>\n" +
                "        <div class=\"catyprdPrice\">\n" +
                "        <span class=\"catyprdnormal\">¥{#price}</span>\n" +
                "        <span class=\"catyprdspecial\">¥{#specialprice}</span>\n" +
                "        </div>\n" +
                "        <div class=\"catyprdName\">{#productname}</div></a>\n" +
                "        </div>\n" +
                "    </li>";
        return str;
    }
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\'){
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
