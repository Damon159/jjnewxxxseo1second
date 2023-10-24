package com.gaobug;

import com.alibaba.fastjson.JSON;
import com.gaobug.seo.GreatSeo;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.net.URLEncoder;
import java.sql.*;

import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Textmysql {
    public static ScheduledExecutorService pools = Executors.newScheduledThreadPool(500);

    public static void main(String[] args) {
//        fileReadMap("config.txt");
        //System.out.println(colorid(145));
        Map<String, String> readConfigWeb = Textmysql.readConfigSqlTxt("configweb.txt");
        getcates(readConfigWeb);
        //randll();
    }

    public static void ConnectionMysql(String library, String root, String password, String fileName, String ip) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + ":3306/" + library + "?useSSL=false&serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, root, password);
            //创建Statement，执行sql
            Statement st4 = conn.createStatement();
            //获取分类start
            Statement st = conn.createStatement();
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st5 = conn.createStatement();
            ResultSet categories1;
            ResultSet categories2 = null;
            ResultSet categories3 = null;
            ResultSet categories4 = null;
            Map<Object, String> map = new HashMap<>();
            //categories1=st.executeQuery("select categories_id from categories where parent_id=0 and categories_status=1");
            categories1 = st.executeQuery("select a.categories_id ,b.categories_name from categories AS a LEFT JOIN categories_description AS b ON a.categories_id =b.categories_id where a.parent_id=0");
            while (categories1.next()) {
                map.put(categories1.getString("categories_id"), "ホーム");
                String cateSql2 = "select categories_id from categories where parent_id=" + categories1.getString("categories_id") + " and categories_status=1";  //获取二级
                categories2 = st1.executeQuery(cateSql2);
                while (categories2.next()) {
                    map.put(categories2.getString("categories_id"), categories1.getString("categories_id"));
                    String cateSql3 = "select categories_id from categories where parent_id=" + categories2.getString("categories_id") + " and categories_status=1";  //获取二级
                    categories3 = st2.executeQuery(cateSql3);
                    while (categories3.next()) {
                        map.put(categories3.getString("categories_id"), categories2.getString("categories_id"));
                        String cateSql4 = "select categories_id from categories where parent_id=" + categories3.getString("categories_id") + " and categories_status=1";  //获取二级
                        categories4 = st3.executeQuery(cateSql4);
                        while (categories4.next()) {
                            map.put(categories4.getString("categories_id"), categories3.getString("categories_id"));
                        }
                    }
                }
            }
            st.close();
            st1.close();
            st2.close();
            st3.close();
            //查询分类的详情start
            Map<Object, String> cateDesMap = new HashMap<>();
            Statement cateDesSta = conn.createStatement();
            ResultSet cateDesRus = cateDesSta.executeQuery("select categories_id,categories_name from `categories_description`");
            while (cateDesRus.next()) {
                cateDesMap.put(cateDesRus.getString("categories_id"), cateDesRus.getString("categories_name"));
            }
            cateDesSta.close();
            //查询分类的详情end
            //获取分类end
            //开始查询内容
            ResultSet rs5 = st4.executeQuery("SELECT `products_id`,`products_name`,`products_description`,`products_url` FROM `products_description`");
            while (rs5.next()) {
                GreatSeo greatSeo = new GreatSeo();
                //查询指定产品的分类链接star
                String cateDesName1 = "";
                String cateDesName2 = "";
                String cateDesName3 = "";
                String cateDesName4 = "";  //四级分类预留
                String products_image = "";  //产品主图
                String products_model = "";  //产品model号
                String product_reviews = "";
                //ResultSet Reviews=st5.executeQuery("SELECT  B.`products_id`,C.`reviews_text` FROM (`reviews` as A INNER JOIN `products_description` AS B ON A.products_id=B.products_id) INNER JOIN `reviews_description` as C ON A.reviews_id=C.reviews_id WHERE B.products_id="+rs5.getString("products_id")+" GROUP BY B.products_id");
//                while (Reviews.next()){
//                    product_reviews+=Reviews.getString("reviews_text")+",";
//                }
                String sqlCa = "select `master_categories_id`,`products_image`,`products_model` from `products` where `products_id` = " + rs5.getString("products_id");
                //String sqlCa="select `master_categories_id`,`products_image`,`products_model`,`v_products_reviewstui` from `products` where `products_id` = "+rs5.getString("products_id");
                Statement productCateDesSta = conn.createStatement();
                ResultSet productCateDesRes = productCateDesSta.executeQuery(sqlCa);
                while (productCateDesRes.next()) {
                    products_image = productCateDesRes.getString("products_image");
                    products_model = productCateDesRes.getString("products_model");
                    //product_reviews=productCateDesRes.getString("v_products_reviewstui");
                    product_reviews = "";
                    if (productCateDesRes.getString("master_categories_id") != null && productCateDesRes.getString("master_categories_id") != "") { //3级
                        String feiId3 = productCateDesRes.getString("master_categories_id");
                        cateDesName3 = cateDesMap.get(feiId3);
                        if (feiId3 != null && !feiId3.equals("")) { //2级
                            String feiId2 = map.get(feiId3);
                            cateDesName2 = cateDesMap.get(feiId2);
                            if (feiId2 != null && !feiId2.equals("")) { //1级//key是productsid
//                                String feiId1=map.get(feiId2);
//                                cateDesName1=cateDesMap.get(feiId1);
                                cateDesName1 = map.get(feiId2);
                            }
                        }
                    }
                }
                //查询指定产品的分类链接end

                //查询指定产品的特价 start
                String spPriceSql = "select `specials_new_products_price` from `specials` where `products_id` =" + rs5.getString("products_id");
                String spicePrice = "";
                Statement spPriceSta = conn.createStatement();
                ResultSet spPriceRes = spPriceSta.executeQuery(spPriceSql);
                while (spPriceRes.next()) {
                    spicePrice = spPriceRes.getString("specials_new_products_price");
                }
                //查询指定产品的特价 end
                //查询指定产品的属性 start
                String productOptionNameSql = "select `options_values_id` from `products_attributes` where `products_id` =" + rs5.getString("products_id");
                String productOptionName = "";
                Statement productOptionSta = conn.createStatement();
                ResultSet productOptionRes = productOptionSta.executeQuery(productOptionNameSql);
                while (productOptionRes.next()) {
                    String productOptionValueSql = "select `products_options_values_name` from `products_options_values` where `products_options_values_id` = " + productOptionRes.getString("options_values_id");
                    Statement productOptionValueSta = conn.createStatement();
                    ResultSet productOptionValueRes = productOptionValueSta.executeQuery(productOptionValueSql);
                    while (productOptionValueRes.next()) {
                        productOptionName = productOptionName + "|" + productOptionValueRes.getString("products_options_values_name");
                    }
                }
                //查询指定产品的属性 end
                greatSeo.setProduct_id(rs5.getString("products_id"));  //写入文件
                greatSeo.setProduct_name(rs5.getString("products_name"));
                greatSeo.setProduct_model(products_model);
                String images[] = products_image.split(",");
                String tamp = "";
                if (images.length > 0) {
                    greatSeo.setProduct_main_img(images[0]);
                    for (int i = 1; i < images.length; i++) {
                        //<img src=\"https://static.mercdn.net/item/detail/orig/photos/m14795505943_1.jpg?1634299122\"><
                        tamp += "<img src=\"" + images[i] + "\"><br>";
                    }
                }

                greatSeo.setProduct_price(spicePrice);
                greatSeo.setProduct_description(rs5.getString("products_description") + tamp);
                //greatSeo.setProduct_name_two(rs5.getString("products_name_two"));
                greatSeo.setProduct_url(rs5.getString("products_url"));
                //greatSeo.setProduct_related(rs5.getString("products_related"));
                greatSeo.setProduct_cate1(cateDesName1);
                greatSeo.setProduct_cate2(cateDesName2);
                greatSeo.setProduct_cate3(cateDesName3);
                greatSeo.setProduct_review(product_reviews);

                String stringJSON = JSON.toJSONString(greatSeo);
                //System.out.println(fileName+":"+rs5.getString("products_id"));
                //File file=new File("site/"+fileName);
                File file = new File("../site");
                if (!file.exists()) {
                    file.mkdir();
                }
                file = new File("../site/" + fileName);
                if (!file.exists()) {
                    file.mkdir();
                }
                try {
                    FileUtils.write(new File("../site/" + fileName + "/p" + rs5.getString("products_id") + ".txt"), stringJSON);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//关闭连接
            rs5.close();
            st4.close();
            conn.close();
            System.out.println("Get Data Done!!!!");
        } catch (ClassNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 创建文件夹写入文件 ,true 添加 多线程
     */
    public static void creatFlieAndWrite(String path, String context, Boolean append) {

        pools.schedule(new Runnable() {
            @Override
            public void run() {
                File file1 = new File(path);
                if (!file1.exists() && path.contains(".txt")) {
                    file1.getParentFile().mkdirs();
                } else {
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileWriter fw = new FileWriter(file1, append);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(context);
                    bw.flush();
                    bw.close();
                    fw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, 0, TimeUnit.SECONDS);
    }
    //判读是否为整数

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 读取配置文件内容
     **/

    public static Map<String, String> readConfigSqlTxt(String path) {
        Map<String, String> map = new HashMap<>();
        String[] library;
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                library = s.split("=>|\\t");  //获取数据库名
                if (!Objects.equals(library[0], ""))
                    map.put(library[0], library[1]);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return  arrayList;
        return map;
    }

    /**
     * 读取文件名
     */
    public static String[] urlFilename(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        //String[] names = file.list();
        return file.list();
    }

    /**
     * 读取文件返回内容按行读取
     */
    public static ArrayList<String> filedata(String path) {
        BufferedReader br = null;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream((path)), "UTF-8"));//UTF-8
            String str = null;
            int i = 0;
            while ((str = br.readLine()) != null) {
                String[] v1 = str.trim().split("\\s+"); //剔除调前、后、中间所有的空格
                arrayList.add(str);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /**
     *
     */
    public static String rand(int num) {
        String chars = "abcdefghizklmnopqrstuvwxyzabcdefghizklmnopqrstuvwxyz";
        char c;
        String rand = "";
        for (int i = 0; i < num; i++) {
            c = chars.charAt((int) (Math.random() * chars.length()));
            rand += c;
        }
        return rand;
    }

    public static String Getrandfilename(Map map) {
        String[] keys = (String[]) map.keySet().toArray(new String[0]);
        int random = (int) (Math.random() * (keys.length));
        String randomKey = keys[random];
        return randomKey;
    }


    public static void init() {
        File file = new File("logs/urllogs");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 读取整个文件内容,true 按原内容读取,false 不换行
     *
     * @param path
     * @return
     */
    public static ArrayList getFileContent(String path, Boolean line) {
        File file = new File(path);
        ArrayList<Object> list = new ArrayList<>();
        if (!file.exists()) {
            return null;
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                StringBuilder content = new StringBuilder();
                while ((st = br.readLine()) != null) {
                    if (line)
                        content.append(st).append("\n");
                    else content.append(st);
                }
                br.close();
                list.add(content.toString());
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 获取keywords
     *
     * @param
     * @param
     */
    //0 数据库 1名字
    public static void getKeyWordstxt() {
        File file = new File("../site");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File("keywords");
        if (!file2.exists())
            file2.mkdirs();
        int count;
        int filecount = 1;
        long Unix = System.currentTimeMillis() / 1000L;
        count = 0;
        for (Object ob : Objects.requireNonNull(file.listFiles())
        ) {
            try {
                String domain = ob.toString().replace("../site/", "");
                file = new File(ob.toString());
                for (Object ob1 : Objects.requireNonNull(file.listFiles())
                ) {
                    List<String> list = FileUtils.readLines(new File(ob1.toString()));
                    String filenameGoogleImage = "googleimage-" + filecount + "-" + Unix;
                    String filenameYoutubeImage = "youtubeimage-" + filecount + "-" + Unix;
                    String filenameYahookeyword = "yahookeywords-" + filecount + "-" + Unix;
                    if (count > 150000) {
                        filecount++;
                        Unix = System.currentTimeMillis() / 1000L;
                        filenameGoogleImage = "googleimage-" + filecount + "-" + Unix;
                        filenameYoutubeImage = "youtubeimage-" + filecount + "-" + Unix;
                        filenameYahookeyword = "yahookeywords-" + filecount + "-" + Unix;
                        count = 0;
                    }


                    String urlencode = "";
                    String id = "";
                    String products_name = "";
                    String product_model = "";
                    if (list.size() == 0)
                        continue;
                    try {
                        Map dataMap = (Map) JSON.parse(list.get(0));
                        id = (String) dataMap.get("product_id");
                        products_name = (String) dataMap.get("product_name");
                        product_model = (String) dataMap.get("product_model");
                        urlencode = URLEncoder.encode(products_name, "utf-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("faild-->" + ob1);
                        continue;
                    }

                    String yahoourl = "https://br.search.yahoo.com/search?p=" + urlencode + "#" + domain + "-" + id + "kkkk-" + product_model;
                    String contentGoogleImage = domain + "-" + id + "-" + products_name;
                    String youtubekeyword = "https://www.youtube.com/results?search_query=" + urlencode + "#" + domain + "-" + id + "kkkk";
                    Textmysql.creatFlieAndWrite("keywords/" + filenameGoogleImage + ".txt", contentGoogleImage + "\n", true);
                    Textmysql.creatFlieAndWrite("keywords/" + filenameYoutubeImage + ".txt", youtubekeyword + "\n", true);
                    Textmysql.creatFlieAndWrite("keywords/" + filenameYahookeyword + ".txt", yahoourl + "\n", true);
                    //FileUtils.write(new File("keywords/" + filenameGoogleImage + ".txt"), contentGoogleImage + "\n", true);
                    //FileUtils.write(new File("keywords/" + filenameYoutubeImage + ".txt"), youtubekeyword + "\n", true);
                    //FileUtils.write(new File("keywords/" + filenameYahookeyword + ".txt"), yahoourl + "\n", true);
                    count++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Getkeys Done!!!!");
        //创建Statement，执行sql

    }

    public static void putHunterDataGoogleImage(String pathfile, String mapkey) {
        File file = new File(pathfile);
        if (!file.exists()) {
            file.mkdir();
        }
        File[] filenames = file.listFiles();
        if (filenames.length > 0) {
            for (Object filepath : filenames
            ) {

                System.out.println(filepath);
                ArrayList<String> arrayList = Textmysql.filedata(filepath.toString());
                for (Object line : arrayList
                ) {
                    try {
                        String[] contents = line.toString().split("\",\"");
                        if (contents.length < 3) {
                            continue;
                        }
                        if (contents[0].length() < 1) {
                            System.out.println("contents");
                            continue;
                        }
                        String content = contents[0].replace("\"", "");
                        String[] domainId = content.split("-");
                        if (domainId.length != 2) {
                            System.out.println("domainId");
                            continue;
                        }
                        String path = "../site/" + domainId[0] + "/p" + domainId[1] + ".txt";
                        File file2 = new File(path);
                        if (!file2.exists()) {
                            System.out.println("files");
                            continue;
                        }
                        path = path.replace("\uFEFF", "");
                        ArrayList arraycontent = getFileContent(path, false);
                        if (arraycontent == null)
                            continue;
                        Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                        if (dataMap == null)
                            continue;
                        //System.out.println("contt------>>>>>" + contents[0]);
                        contents[2] = contents[2].replace("/>\"", "/>");
                        contents[2] = contents[2].replace("\"\"", "\"");
                        String[] imms = contents[2].split("<br />");
                        Set<String> sett = new HashSet<String>();
                        for (String tp : imms
                        ) {
                            sett.add(tp);
                        }
                        imms = (String[]) sett.toArray(new String[0]);
                        String title = "";
                        Set<String> set = new HashSet<>();
                        Element temp;
                        String all = "";
                        String titles = "";
                        for (Object key : imms
                        ) {
                            Document doc = Jsoup.parse(key.toString());
                            Elements elements = doc.select("img");
                            Elements element1 = doc.getElementsByAttributeStarting("title");
                            if (element1.first() == null)
                                continue;
                            title = elements.first().attr("title");
                            set.add(title);
                            Iterator<String> value = set.iterator();
                            while (value.hasNext()) {
                                titles += value.next();
                            }
                            set.clear();
                            all += elements.first().toString();
                        }
                        titles = titles.replace("...", "");
                        String res = "<p>" + titles + "</p>" + all;

                        dataMap.put(mapkey, res);
                        String stringJSON = JSON.toJSONString(dataMap);

                        FileUtils.write(new File(path), stringJSON, "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }

            }
            System.out.println("GoogleimageDone!!!");
        }
    }

    public static void putHunterDataGoogleImage1(String pathfile, String mapkey) {
        File file = new File(pathfile);
        if (!file.exists()) {
            file.mkdir();
        }
        File[] filenames = file.listFiles();
        if (filenames.length > 0) {
            for (Object filepath : filenames
            ) {
                System.out.println(filepath);
                ArrayList<String> arrayList = Textmysql.filedata(filepath.toString());
                for (Object line : arrayList
                ) {
                    try {
                        String[] contents = line.toString().split("\",\"");
                        if (contents.length < 2) {
                            continue;
                        }
                        if (contents[0].length() < 1) {
                            System.out.println("contents");
                            continue;
                        }
                        String content = contents[0].replace("\"", "");
                        content = content.replaceFirst("-", "Φfenge");
                        content = content.replaceFirst("-", "Φfenge");
                        String domainidname[] = content.split("Φfenge");
                        //System.out.println(content);
                        if (domainidname.length < 2) {
                            System.out.println("domainId-->" + content);
                            continue;
                        }
                        String path = "../site/" + domainidname[0] + "/p" + domainidname[1] + ".txt";
                        File file2 = new File(path);
                        if (!file2.exists()) {
                            System.out.println("files");
                            continue;
                        }
                        path = path.replace("\uFEFF", "");
                        ArrayList arraycontent = getFileContent(path, false);
                        if (arraycontent == null)
                            continue;
                        Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                        if (dataMap == null)
                            continue;
                        //System.out.println("contt------>>>>>" + contents[0]);
                        contents[1] = contents[1].replace("/>\"", "/>");
                        contents[1] = contents[1].replace("\"\"", "\"");
                        String[] imms = contents[1].split("<br />");
                        Set<String> sett = new HashSet<>();
                        for (String tp : imms
                        ) {
                            sett.add(tp);
                        }
                        imms = (String[]) sett.toArray(new String[0]);
                        String title = "";
                        Set<String> set = new HashSet<String>();
                        Element temp;
                        String all = "";
                        String titles = "";
                        for (Object key : imms
                        ) {
                            Document doc = Jsoup.parse(key.toString());
                            Elements elements = doc.select("img");
                            Elements element1 = doc.getElementsByAttributeStarting("title");
                            if (element1.first() == null)
                                continue;
                            title = elements.first().attr("title");
                            set.add(title);
                            Iterator<String> value = set.iterator();
                            while (value.hasNext()) {
                                titles += value.next();
                            }
                            set.clear();

                            all += elements.first().toString();
                        }
                        titles = titles.replace("...", "");
                        String res = "<p>" + titles + "</p>" + all;

                        dataMap.put(mapkey, res);
                        String stringJSON = JSON.toJSONString(dataMap);
                        FileUtils.write(new File(path), stringJSON, "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }

            }
            System.out.println("GoogleimageDone!!!");
        }
    }

    public static void putHunterDataGoogleImage2(String pathfile,String mapkey){
        File file =new File(pathfile);
        if (!file.exists()){
            file.mkdir();
        }
        int count=0;
        File [] filenames=file.listFiles();
        if (filenames.length>0) {
            for (Object filepath : filenames
            ) {
                System.out.println(filepath);
                Boolean flag=true;
                ArrayList arrayList = filedata(filepath.toString());
                for (Object line : arrayList
                ) {
                    int aa = (int) (Math.random() * 2);
                    if (aa == 0)
                    {
                        try {
                            String[] contents = line.toString().split("\",\"");
                            if (contents.length < 2) {
                                //System.out.println("contentsless2");
                                count++;
                                continue;
                            }
                            if (contents[0].length() < 1) {
                                //System.out.println("contents");
                                count++;
                                continue;
                            }
                            String content = contents[0].replace("\"", "");
                            content = content.replaceFirst("-", "Φfenge");
                            content = content.replaceFirst("-", "Φfenge");
                            String domainidname[] = content.split("Φfenge");

                            //System.out.println(content);
                            if (domainidname.length < 2) {
                                //System.out.println("domainId-->"+content);
                                count++;
                                continue;
                            }
                            String path = "../site/" + domainidname[0] + "/p" + domainidname[1] + ".txt";
                            if (flag) {
                                System.out.println(path);
                                flag = false;
                            }
                            File file2 = new File(path);
                            if (!file2.exists()) {
                                //System.out.println("files");
                                count++;
                                continue;
                            }
                            path = path.replace("\uFEFF", "");
                            ArrayList arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                            if (arraycontent == null || arrayList.size() <= 0) {
                                //System.out.println("fileisnull");
                                count++;
                                continue;
                            }

                            Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                            if (dataMap == null) {
                                //System.out.println("jasonwrong");
                                count++;
                                continue;
                            }

                            //System.out.println("contt------>>>>>" + contents[0]);
                            String imgestr = "";
                            for (int i = 0; i < contents.length; i++) {
                                if (contents[i].indexOf("<img") >= 0) {
                                    imgestr = contents[i];
                                    break;
                                }
                            }
                            imgestr = imgestr.replace("/>\"", "/>");
                            imgestr = imgestr.replace("\"\"", "\"");
                            String[] imms = imgestr.split("<br />");
                            Set sett = new HashSet();
                            for (String tp : imms
                            ) {
                                sett.add(tp);
                            }
                            imms = (String[]) sett.toArray(new String[0]);
                            String title = "";
                            Set set = new HashSet();
                            Element temp;
                            String all = "";
                            String titles = "";
                            String str1 = "";
                            for (Object key : imms
                            ) {

                                Document doc = Jsoup.parse(key.toString());
                                Elements elements = doc.select("img");
                                Elements element1 = doc.getElementsByAttributeStarting("title");
                                if (element1.first() == null) {
                                    //System.out.println("Elementsnull");
                                    count++;
                                    continue;
                                }
                                title = elements.first().attr("title");
                                if (str1 == "")
                                    str1 = title;
                                title = title.replace("...", "");
                                //all+=key.toString()+"<li><p>"+title+"</p><li>";
                                all += key.toString() + "<p>" + title + "</p><br>";
                                set.add(title);
                                Iterator value = set.iterator();
                                while (value.hasNext()) {
                                    titles += value.next() + ",";
                                }
                                set.clear();

                            }

                            String cat3 = "";
                            if (dataMap.get("product_cate3") != null || dataMap.get("product_cate3") != "")
                                cat3 = (String) dataMap.get("product_cate3");

                            String sumTitle = str1 + dataMap.get(" ") + cat3;

                            int sumTitleP = (int) (Math.random() * 10.0D);
                            if (sumTitleP == 1 || sumTitleP == 5) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            } else if (sumTitleP == 2 || sumTitleP == 3) {
                                sumTitle = "<span>" + sumTitle + "</span>";
                            } else if (sumTitleP == 4 || sumTitleP == 6) {
                                sumTitle = "<div>" + sumTitle + "</div>";
                            } else if (sumTitleP == 8) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }


                            //all = titles+"###"+"<ul>" + all + "</ul>";
                            all = titles + "###" + all;
                            all = decodeUnicode(all);
                            dataMap.put(mapkey, all);
                            sumTitle = decodeUnicode(sumTitle);
                            dataMap.put("google_img_sum", sumTitle);
                            String stringJSON = JSON.toJSONString(dataMap);
                            FileUtils.write(new File(path), stringJSON, "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                            count++;
                            continue;
                        }
                    }
                    else {
                        try{
                            String[] contents = line.toString().split("\",\"");
                            if (contents.length < 2) {
                                //System.out.println("contentsless2");
                                count++;
                                continue;
                            }
                            if (contents[0].length() < 1) {
                                //System.out.println("contents");
                                count++;
                                continue;
                            }
                            String content = contents[0].replace("\"", "");
                            content=content.replaceFirst("-","Φfenge");
                            content=content.replaceFirst("-","Φfenge");
                            String domainidname []= content.split("Φfenge");

                            //System.out.println(content);
                            if (domainidname.length < 2) {
                                //System.out.println("domainId-->"+content);
                                count++;
                                continue;
                            }
                            String path = "../site/" + domainidname[0] + "/p" + domainidname[1] + ".txt";
                            if (flag) {
                                System.out.println(path);
                                flag = false;
                            }
                            if (flag){
                                System.out.println(path);
                                flag=false;
                            }
                            File file2 = new File(path);
                            if (!file2.exists()) {
                                //System.out.println("files");
                                count++;
                                continue;
                            }
                            path = path.replace("\uFEFF", "");
                            ArrayList arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                            if (arraycontent == null || arrayList.size()<=0){
                                //System.out.println("fileisnull");
                                count++;
                                continue;
                            }

                            Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                            if (dataMap == null) {
                                //System.out.println("jasonwrong");
                                count++;
                                continue;
                            }

                            //System.out.println("contt------>>>>>" + contents[0]);
                            String imgestr="";
                            for (int i = 0; i < contents.length; i++) {
                                if (contents[i].indexOf("<img")>=0) {
                                    imgestr = contents[i];
                                    break;
                                }
                            }
                            imgestr = imgestr.replace("/>\"", "/>");
                            imgestr = imgestr.replace("\"\"", "\"");
                            String[] imms = imgestr.split("<br />");
                            Set sett = new HashSet();
                            for (String tp : imms
                            ) {
                                sett.add(tp);
                            }
                            imms = (String[]) sett.toArray(new String[0]);
                            String title = "";
                            Set set = new HashSet();
                            Element temp;
                            String all = "";
                            String titles = "";
                            String str1="";
                            String head="<div class=\"slider-pro\">";
                            for (Object key : imms
                            ) {
                                Document doc = Jsoup.parse(key.toString());
                                Elements elements = doc.select("img");
                                Elements element1 = doc.getElementsByAttributeStarting("title");
                                if (element1.first() == null) {
                                    //System.out.println("Elementsnull");
                                    count++;
                                    continue;
                                }
                                title = elements.first().attr("title");
                                if (str1=="")
                                    str1=title;
                                title=title.replace("...","");
                                //all+=key.toString()+"<li><p>"+title+"</p><li>";
                                //all+=key.toString()+"<p>"+title+"</p><br>";
                                all+="<div class=\"sp-slide\">"+key+"</div>";
                                set.add(title);
                                Iterator value = set.iterator();
                                while (value.hasNext()) {
                                    titles += value.next()+",";
                                }
                                set.clear();

                            }

                            String cat3="";
                            if (dataMap.get("product_cate3")!=null||dataMap.get("product_cate3")!="")
                                cat3= (String) dataMap.get("product_cate3");

                            String  sumTitle=str1+dataMap.get(" ")+cat3;

                            int sumTitleP = (int)(Math.random() * 10.0D);
                            if (sumTitleP == 1 || sumTitleP == 5) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }

                            else if (sumTitleP == 2 || sumTitleP == 3) {
                                sumTitle = "<span>" + sumTitle + "</span>";
                            }

                            else if (sumTitleP == 4 || sumTitleP == 6) {
                                sumTitle = "<div>" + sumTitle + "</div>";
                            }

                            else if (sumTitleP == 8) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }


                            all=head+all+"</div>";
                            //all = titles+"###"+"<ul>" + all + "</ul>";
                            all = titles+"###"+  all ;
                            all=decodeUnicode(all);
                            dataMap.put(mapkey, all);
                            sumTitle=decodeUnicode(sumTitle);
                            dataMap.put("google_img_sum", sumTitle);
                            String stringJSON = JSON.toJSONString(dataMap);
                            FileUtils.write(new File(path), stringJSON, "UTF-8");
                        }catch (Exception e){
                            e.printStackTrace();
                            count++;
                            continue;
                        }
                    }
                }
                System.out.println("Error--"+count);
                count=0;
            }
            System.out.println("GoogleimageDone!!!");
        }
    }
    public static void putHunterDataGoogleImage3(String pathfile,String mapkey){
        File file =new File(pathfile);
        if (!file.exists()){
            file.mkdir();
        }
        int count=0;
        File [] filenames=file.listFiles();
        if (filenames.length>0) {
            for (Object filepath : filenames
            ) {
                System.out.println(filepath);
                Boolean flag=true;
                ArrayList arrayList = filedata(filepath.toString());
                for (Object line : arrayList
                ) {
                    int aa =0;
                    if (aa == 0)
                    {
                        try {
                            String[] contents = line.toString().split("\",\"");
                            if (contents.length < 2) {
                                //System.out.println("contentsless2");
                                count++;
                                continue;
                            }
                            if (contents[0].length() < 1) {
                                //System.out.println("contents");
                                count++;
                                continue;
                            }
                            String content = contents[1].replace("\"", "");
                            content = content.replaceFirst("-", "Φfenge");
                            content = content.replaceFirst("-", "Φfenge");
                            String domainidname[] = content.split("Φfenge");

                            //System.out.println(content);
                            if (domainidname.length < 2) {
                                //System.out.println("domainId-->"+content);
                                count++;
                                continue;
                            }
                            String path = "../site/" + domainidname[0] + "/p" + domainidname[1] + ".txt";
                            if (flag) {
                                count++;
                                flag = false;
                            }
                            File file2 = new File(path);
                            if (!file2.exists()) {
                                //System.out.println("files");
                                count++;
                                continue;
                            }
                            path = path.replace("\uFEFF", "");
                            ArrayList arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                            if (arraycontent == null || arrayList.size() <= 0) {
                                //System.out.println("fileisnull");
                                count++;
                                continue;
                            }

                            Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                            if (dataMap == null) {
                                //System.out.println("jasonwrong");
                                count++;
                                continue;
                            }

                            //System.out.println("contt------>>>>>" + contents[0]);
                            String imgestr = "";
                            for (int i = 0; i < contents.length; i++) {
                                if (contents[i].indexOf("<img") >= 0) {
                                    imgestr = contents[i];
                                    break;
                                }
                            }
                            imgestr = imgestr.replace("/>\"", "/>");
                            imgestr = imgestr.replace("\"\"", "\"");
                            String[] imms = imgestr.split("<br />");
                            Set sett = new HashSet();
                            for (String tp : imms
                            ) {
                                sett.add(tp);
                            }
                            imms = (String[]) sett.toArray(new String[0]);
                            String title = "";
                            Set set = new HashSet();
                            Element temp;
                            String all = "";
                            String titles = "";
                            String str1 = "";
                            for (Object key : imms
                            ) {

                                Document doc = Jsoup.parse(key.toString());
                                Elements elements = doc.select("img");
                                Elements element1 = doc.getElementsByAttributeStarting("title");
                                if (element1.first() == null) {
                                    //System.out.println("Elementsnull");
                                    count++;
                                    continue;
                                }
                                title = elements.first().attr("title");
                                if (str1 == "")
                                    str1 = title;
                                title = title.replace("...", "");
                                //all+=key.toString()+"<li><p>"+title+"</p><li>";
                                all += key.toString() + "<p>" + title + "</p><br>";
                                set.add(title);
                                Iterator value = set.iterator();
                                while (value.hasNext()) {
                                    titles += value.next() + ",";
                                }
                                set.clear();

                            }

                            String cat3 = "";
                            if (dataMap.get("product_cate3") != null || dataMap.get("product_cate3") != "")
                                cat3 = (String) dataMap.get("product_cate3");

                            String sumTitle = str1 + dataMap.get(" ") + cat3;

                            int sumTitleP = (int) (Math.random() * 10.0D);
                            if (sumTitleP == 1 || sumTitleP == 5) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            } else if (sumTitleP == 2 || sumTitleP == 3) {
                                sumTitle = "<span>" + sumTitle + "</span>";
                            } else if (sumTitleP == 4 || sumTitleP == 6) {
                                sumTitle = "<div>" + sumTitle + "</div>";
                            } else if (sumTitleP == 8) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }


                            //all = titles+"###"+"<ul>" + all + "</ul>";
                            all = titles + "###" + all;
                            all = decodeUnicode(all);
                            dataMap.put(mapkey, all);
                            sumTitle = decodeUnicode(sumTitle);
                            dataMap.put("google_img_sum", sumTitle);
                            String stringJSON = JSON.toJSONString(dataMap);
                            FileUtils.write(new File(path), stringJSON, "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                            count++;
                            continue;
                        }
                    }
                    else {
                        try{
                            String[] contents = line.toString().split("\",\"");
                            if (contents.length < 2) {
                                //System.out.println("contentsless2");
                                count++;
                                continue;
                            }
                            if (contents[0].length() < 1) {
                                //System.out.println("contents");
                                count++;
                                continue;
                            }
                            String content = contents[1].replace("\"", "");
                            content=content.replaceFirst("-","Φfenge");
                            content=content.replaceFirst("-","Φfenge");
                            String domainidname []= content.split("Φfenge");

                            //System.out.println(content);
                            if (domainidname.length < 2) {
                                //System.out.println("domainId-->"+content);
                                count++;
                                continue;
                            }
                            String path = "../site/" + domainidname[0] + "/p" + domainidname[1] + ".txt";
                            if (flag) {
                                System.out.println(path);
                                flag = false;
                            }
                            if (flag){
                                System.out.println(path);
                                flag=false;
                            }
                            File file2 = new File(path);
                            if (!file2.exists()) {
                                //System.out.println("files");
                                count++;
                                continue;
                            }
                            path = path.replace("\uFEFF", "");
                            ArrayList arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                            if (arraycontent == null || arrayList.size()<=0){
                                //System.out.println("fileisnull");
                                count++;
                                continue;
                            }

                            Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                            if (dataMap == null) {
                                //System.out.println("jasonwrong");
                                count++;
                                continue;
                            }

                            //System.out.println("contt------>>>>>" + contents[0]);
                            String imgestr="";
                            for (int i = 0; i < contents.length; i++) {
                                if (contents[i].indexOf("<img")>=0) {
                                    imgestr = contents[i];
                                    break;
                                }
                            }
                            imgestr = imgestr.replace("/>\"", "/>");
                            imgestr = imgestr.replace("\"\"", "\"");
                            String[] imms = imgestr.split("<br />");
                            Set sett = new HashSet();
                            for (String tp : imms
                            ) {
                                sett.add(tp);
                            }
                            imms = (String[]) sett.toArray(new String[0]);
                            String title = "";
                            Set set = new HashSet();
                            Element temp;
                            String all = "";
                            String titles = "";
                            String str1="";
                            String head="<div class=\"slider-pro\">";
                            for (Object key : imms
                            ) {
                                Document doc = Jsoup.parse(key.toString());
                                Elements elements = doc.select("img");
                                Elements element1 = doc.getElementsByAttributeStarting("title");
                                if (element1.first() == null) {
                                    //System.out.println("Elementsnull");
                                    count++;
                                    continue;
                                }
                                title = elements.first().attr("title");
                                if (str1=="")
                                    str1=title;
                                title=title.replace("...","");
                                //all+=key.toString()+"<li><p>"+title+"</p><li>";
                                //all+=key.toString()+"<p>"+title+"</p><br>";
                                all+="<div class=\"sp-slide\">"+key+"</div>";
                                set.add(title);
                                Iterator value = set.iterator();
                                while (value.hasNext()) {
                                    titles += value.next()+",";
                                }
                                set.clear();

                            }

                            String cat3="";
                            if (dataMap.get("product_cate3")!=null||dataMap.get("product_cate3")!="")
                                cat3= (String) dataMap.get("product_cate3");

                            String  sumTitle=str1+dataMap.get(" ")+cat3;

                            int sumTitleP = (int)(Math.random() * 10.0D);
                            if (sumTitleP == 1 || sumTitleP == 5) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }

                            else if (sumTitleP == 2 || sumTitleP == 3) {
                                sumTitle = "<span>" + sumTitle + "</span>";
                            }

                            else if (sumTitleP == 4 || sumTitleP == 6) {
                                sumTitle = "<div>" + sumTitle + "</div>";
                            }

                            else if (sumTitleP == 8) {
                                sumTitle = "<p>" + sumTitle + "</p>";
                            }


                            all=head+all+"</div>";
                            //all = titles+"###"+"<ul>" + all + "</ul>";
                            all = titles+"###"+  all ;
                            all=decodeUnicode(all);
                            dataMap.put(mapkey, all);
                            sumTitle=decodeUnicode(sumTitle);
                            dataMap.put("google_img_sum", sumTitle);
                            String stringJSON = JSON.toJSONString(dataMap);
                            FileUtils.write(new File(path), stringJSON, "UTF-8");
                        }catch (Exception e){
                            e.printStackTrace();
                            count++;
                            continue;
                        }
                    }
                }
                System.out.println("Error--"+count);
                count=0;
            }
            System.out.println("GoogleimageDone!!!");
        }
    }
    public static void putHunterDataYoutube(String filename,String key){
        Connection conn ;
        ResultSet resultSet;
        int count=0;
        File file=new File(filename);
        if (!file.exists())
            file.mkdirs();
        File[] filenames = file.listFiles();
        if (filenames.length>0) {
            try {
                Class.forName("org.sqlite.JDBC");

                if (!file.exists()) {
                    file.mkdir();
                }

                if (filenames.length > 0) {
                    for (Object filepath : filenames
                    ) {
                        try {
                            System.out.println(filepath);
                            filepath = filepath.toString().replace("\\", "/");
                            String dburl = "jdbc:sqlite:" + filepath;
                            conn = DriverManager.getConnection(dburl);
                            Statement statement = conn.createStatement();
                            resultSet = statement.executeQuery("select domainid,keywords,details from content where id=12;");
                            while (resultSet.next()) {
                                System.out.println(resultSet.getString("domainid"));
                            }
                            resultSet = statement.executeQuery("select domainid,keywords,details from content where domainid is not null;");
                            while (resultSet.next()) {
                                String domainid = resultSet.getString("domainid");
                                String keywords = resultSet.getString("keywords");
                                String details = resultSet.getString("details");
                                if (keywords.equals("") && details.equals("")) {
                                    count++;
                                    continue;

                                }
                                String[] nameId = domainid.split("-");
                                String path = "../site/" + nameId[0] + "/p" + nameId[1] + ".txt";
                                File file2 = new File(path);
                                if (!file2.exists()) {
                                    count++;
                                    continue;
                                }
                                ArrayList arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                                if (arraycontent == null) {
                                    count++;
                                    continue;
                                }
                                Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));

                                if (dataMap == null) {
                                    count++;
                                    continue;
                                }
                                String [] temp ;
                                String ybvido = null;
                                if (details.indexOf("Φfenge")>0){
                                    temp =details.split("Φfenge");
                                    String video =temp[0].replaceAll("watch\\?v=","embed/");
                                    ybvido = "<iframe src=\"https://www.youtube.com" + video+ "\" width=\"853\" height=\"480\"></iframe>";
                                }

                                keywords = keywords.replace("...", "");
                                keywords=decodeUnicode(keywords);
                                dataMap.put(key, keywords);
                                dataMap.put("ybvideo", ybvido);
                                String stringJSON = JSON.toJSONString(dataMap);
                                FileUtils.write(new File(path), stringJSON, "UTF-8");
                            }
                            System.out.println("Error--"+count);
                            count=0;
                        }catch (Exception e){
                            e.printStackTrace();
                            count++;
                            continue;
                        }
                    }
                }
                System.out.println("YoutubeDone!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void randId(int count) {

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= count; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Textmysql.creatFlieAndWrite("id.txt", "", false);
        for (Integer integer : list) {
            Textmysql.creatFlieAndWrite("id.txt", integer.toString() + "\n", true);
        }
    }

    public static void putMachData(String pathfile, String key) {
        try {
            File file = new File("../matchdata");
            if (!file.exists()) {
                file.mkdirs();
            }
            File[] filenames = file.listFiles();
            System.out.println(filenames);
            if (file.listFiles().length > 0) {
                for (Object ob : file.listFiles()
                ) {
                    file = new File(ob.toString());
                    for (Object ob1 : file.listFiles()
                    ) {
                        List<String> list = FileUtils.readLines(new File(ob1.toString()));

                        String data = list.get(0).toString();
                        String[] nameId = data.split(",");
                        String path = "../site/" + nameId[0] + "/p" + nameId[1] + ".txt";
                        File file2 = new File(path);
                        if (!file2.exists()) {
                            continue;
                        }
                        Map dataMap = (Map) JSON.parse(list.get(0));
                        if (dataMap == null)
                            continue;
                        String[] temp = (String[]) dataMap.keySet().toArray();
                        String str = "";
                        for (int i = 0; i < temp.length; i++) {
                            str += temp[i];
                        }
                        dataMap.put(key, str);
                        String stringJSON = JSON.toJSONString(dataMap);
                        FileUtils.write(new File(path), stringJSON, "UTF-8");

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void putKeys(String pathfile, String key) {
        Connection conn = null;
        ResultSet resultSet;
        File file = new File(pathfile);
        try {
            Class.forName("org.sqlite.JDBC");
            if (!file.exists()) {
                file.mkdir();
            }
            File[] filenames = file.listFiles();
            if (filenames.length > 0) {
                System.out.println(filenames);
                for (Object filepath : filenames
                ) {
                    try {
                        filepath = filepath.toString().replace("\\", "/");
                        System.out.println(filepath);
                        String dburl = "jdbc:sqlite:" + filepath;
                        conn = DriverManager.getConnection(dburl);
                        Statement statement = conn.createStatement();
                        //resultSet=statement.executeQuery("select domainid,keywords,details from content where domainid is not null;");
                        resultSet = statement.executeQuery("select PageUrl,keywords,details from content where keywords is not null;");
                        while (resultSet.next()) {
                            if (resultSet.getString("keywords").equals("") || resultSet.getString("keywords") == null)
                                continue;
                            //String domainid=resultSet.getString("domainid");
                            String domainid = resultSet.getString("PageUrl");
                            try {
                                domainid = domainid.substring(domainid.lastIndexOf("#") + 1, domainid.indexOf("kkkk"));
                            } catch (Exception e) {
                                System.out.println(domainid);
                            }

                            String keywords = resultSet.getString("keywords");
                            String details = resultSet.getString("details");
                            String[] nameId = domainid.split("-");
                            String path = "../site/" + nameId[0] + "/p" + nameId[1] + ".txt";
                            File file2 = new File(path);
                            if (!file2.exists())
                                continue;
                            ArrayList arraycontent = getFileContent(path, false);
                            if (arraycontent == null)
                                continue;
                            Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));
                            if (dataMap == null)
                                continue;
                            //contents[2]=contents[2].replace("/>\"","/>");
                            //contents[2]=contents[2].replace("\"\"","\"");
                            //keywords = keywords + "Φfenge" + details;
                            if (keywords.trim().equals("") || keywords.trim().length() == 0)
                                continue;
                            dataMap.put("keytitles", keywords);
                            dataMap.put("destitle", details);
                            String stringJSON = JSON.toJSONString(dataMap);
                            //System.out.println(stringJSON);
                            FileUtils.write(new File(path), stringJSON, "UTF-8");
                            // creatFlieAndWrite(path,stringJSON,false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                System.out.println("YahooDone!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int ASCIcount(String str) {
        str = str.replaceAll("http://|https://", "");
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count += str.charAt(i);
        }
        return count;
    }

    public static void randll() {
        Set set = new HashSet();
        try {
            FileUtils.write(new File("ll.txt"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5000; i++) {
            String str = "";
            int aa = (int) (Math.random() * 3 + 2);
            for (int j = 0; j <= aa; j++) {
                int c = 'a' + (int) (Math.random() * 26);
                str += (char) c;
            }
            try {
                FileUtils.write(new File("ll.txt"), str + "\n", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void getData(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] pathes = file.listFiles();
        Map<String, String> map = new HashMap<>();
        assert pathes != null;
        if (pathes.length > 0) {
            for (Object aa : pathes
            ) {
                String fileName = aa.toString().replace("..\\data\\", "");
                fileName = fileName.replace("../data/", "");
                String[] domains = fileName.split("_");
                fileName = domains[0];
                System.out.println("fileName" + fileName);
                List<String> list = Textmysql.filedata(aa.toString());
                String temp = "";
                String des = "";
                String[] datas = null;
                for (Object data : list
                ) {
                    try {
                        data = data.toString().trim();
                        Pattern pattern = Pattern.compile("^</p><p>");
                        Matcher matcher = pattern.matcher(data.toString());
                        if (matcher.find()) {
                            temp += data;
                        }
                        Pattern pattern1 = Pattern.compile("^2-");
                        Matcher matcher1 = pattern1.matcher(data.toString());
                        if (matcher1.find()) {
                            datas = data.toString().split("\\|");
                            String[] cates = datas[0].split(",");
                            cates = cates[1].split("-1-");
                            map.put("product_cate3", cates[0]);
                            map.put("product_cate1", "ホーム");
                            map.put("product_cate2", cates[1]);
                            map.put("product_id", datas[1]);
                            map.put("product_url", "https://api.mercari.jp/items/get?id=" + datas[2]);
                            map.put("product_main_img", datas[3]);
                            String price2 = datas[4];
                            String price1 = datas[5];
                            if (Double.parseDouble(price1) > Double.parseDouble(price2))
                                map.put("product_price", price2);
                            else
                                map.put("product_price", price1);
                            map.put("product_name", datas[6]);
                            map.put("product_review", "");
                            des = datas[7];
                            if (temp != "") {
                                des = des + temp;
                                des = des.replace("|", ",");
                            }
                            map.put("product_description", des);
                            temp = "";
                            String stringJSON = JSON.toJSONString(map);
                            file = new File("../site");
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            file = new File("../site/" + fileName);
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            FileUtils.write(new File("../site/" + fileName + "/p" + datas[1].trim() + ".txt"), stringJSON, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;

                    }

                }

            }

        }
        System.out.println("getdatas Done !!!!");
    }

    public static Map<String, Object> fileReadMap(String path) {
        ArrayList list = getFileContent(path, false);
        assert list != null;
        String config = list.get(0).toString();
        JSONObject googlejson = new JSONObject(config);
        return googlejson.toMap();
    }

    public static String colorid(int counturl) {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成红色颜色代码

        int y = (counturl * 4 ) % 219 + 16;
        int u = (counturl * 3 ) % 224 + 16;
        int v = (counturl * 2 ) % 224 + 16;
        int R = (int) (y + 1.14 * v);
        int G = (int) (y - 0.394 * u - 0.581 * v);
        int B = (int) (y + 2.028 * u);
        if (R < 0) R = 0;
        if (G < 0) G = 0;
        if (B < 0) B = 0;
        if (R > 255) R = 255;
        if (G > 255) G = 255;
        if (B > 255) B = 255;
        red = Integer.toHexString(R).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(G).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(B).toUpperCase();
        String firstone = rand(1).toUpperCase();
        //判断红色代码的位数
        red = red.length() == 1 ? "0" + red : red;
        //判断绿色代码的位数
        green = green.length() == 1 ? "0" + green : green;
        //判断蓝色代码的位数
        blue = blue.length() == 1 ? "0" + blue : blue;
        //生成十六进制颜色值
//
        return red + green + blue;
    }
    public static String colorid1(int counturl){
        String[] colors;
        colors = new String[]{"FFC0CB","DC143C","FFF0F5","DB7093","FF69B4","FF1493","C71585","DA70D6","D8BFD8","DDA0DD","EE82EE","FF00FF","FF00FF","8B008B","800080","BA55D3","9400D3","9932CC","4B0082","8A2BE2","9370DB","7B68EE","6A5ACD","483D8B","E6E6FA","F8F8FF","0000FF","0000CD","191970","00008B","000080","4169E1","6495ED","B0C4DE","778899","708090","1E90FF","F0F8FF","4682B4","87CEFA","87CEEB","00BFFF","ADD8E6","B0E0E6","5F9EA0","F0FFFF","E1FFFF","AFEEEE","00FFFF","D4F2E7","00CED1","2F4F4F","008B8B","008080","48D1CC","20B2AA","40E0D0","7FFFAA","00FA9A","00FF7F","F5FFFA","3CB371","2E8B57","F0FFF0","90EE90","98FB98","8FBC8F","32CD32","00FF00","228B22","008000","006400","7FFF00","7CFC00","ADFF2F","556B2F","F5F5DC","FAFAD2","FFFFF0","FFFFE0","FFFF00","808000","BDB76B","FFFACD","EEE8AA","F0E68C","FFD700","FFF8DC","DAA520","FFFAF0","FDF5E6","F5DEB3","FFE4B5","FFA500","FFEFD5","FFEBCD","FFDEAD","FAEBD7","D2B48C","DEB887","FFE4C4","FF8C00","FAF0E6","CD853F","FFDAB9","F4A460","D2691E","8B4513","FFF5EE","A0522D","FFA07A","FF7F50","FF4500","E9967A","FF6347","FFE4E1","FA8072","FFFAFA","F08080","BC8F8F","CD5C5C","FF0000","A52A2A","B22222","8B0000","800000","FFFFFF","F5F5F5","DCDCDC","D3D3D3","C0C0C0","A9A9A9"};
        return colors[counturl%colors.length];
    }
    public static Map<Integer, Object> idurlmap(ArrayList list){
        Map<Integer, Object> map=new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i,list.get(i));
        }
        return map;
    }
    public static Map<Object, Integer> urlidmap(ArrayList list){
        Map<Object, Integer> map=new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i),i);
        }
        return map;
    }
    public static void getidurl(Map configweb){
        ArrayList<String> arrayList=new ArrayList<String>();
        File file=new File("../site");
        Map map=new HashMap();
        if (!file.exists()){
            file.mkdir();
        }
         file=new File("../cate");
        if (!file.exists()){
            file.mkdir();
        }
        try {
            FileUtils.write(new File("newid.txt"),"");
            String  [] keysdb = (String[]) configweb.keySet().toArray(new String[0]);
            for (int i = 0; i < keysdb.length; i++) {
                File file1=new File("../site/"+configweb.get(keysdb[i]));
                String[] files =file1.list();
                for (String  filename:files
                ) {
                    int id=1;
                    filename=filename.replace("p","");
                    filename=filename.replace(".txt","");
                    String temp=keysdb[i]+filename;
                    arrayList.add(temp);
                }
            }
            Collections.shuffle(arrayList);
            Collections.shuffle(arrayList);
            for (String s : arrayList) {
                FileUtils.write(new File("newid.txt"), s.toString() + "\n", true);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void getcates(Map configweb){

        ArrayList<String> arrayList=new ArrayList<String>();
        Map map=new HashMap();
        File file=new File("../site");
        if (!file.exists()){
            file.mkdir();
        }
        file=new File("../cate");
        if (!file.exists()){
            file.mkdir();
        }
        try {
            FileUtils.write(new File("cate.txt"), "");
            int id=1;
            String  [] keysdb = (String[]) configweb.keySet().toArray(new String[0]);
            for (int i = 0; i < keysdb.length; i++) {
                File file1=new File("../site/"+configweb.get(keysdb[i]));
                String[] files =file1.list();
                for (String  filename:files
                ) {
                    filename=filename.replace("p","");
                    filename=filename.replace(".txt","");
                    String temp=keysdb[i]+filename;
                   List list=FileUtils.readLines(new File(String.valueOf("../site/"+configweb.get(keysdb[i])+"/p"+filename+".txt")),"UTF-8");
                   if (!list.isEmpty()) {
                       Map dataMap = (Map) JSON.parse((String) list.get(0));
                       String catename3 = dataMap.get("product_cate2") != null ? dataMap.get("product_cate2").toString() : "";
                       //System.out.println(catename3);
                       if (!catename3.equals("")) {
                           if (map.get(catename3) == null) {
                               map.put(catename3, id);
                               FileUtils.write(new File("cate.txt"), id + "\t" + catename3 + "\n", true);
                               id++;

                           }
                           FileUtils.write(new File("../cate/cate-" + map.get(catename3) + ".txt"), temp + "\n", true);
                       }
                   }

                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void randsecond(int number,int min,int max){
        try {
            FileUtils.write(new File("second.txt"),"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < number; i++) {
            Random random = new Random();
            int str= random.nextInt(max)%(max-min+1) + min;
            try {
                FileUtils.write(new File("second.txt"),str+"\n",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static int urlcount(Map map){
        String [] domainkeys = (String[]) map.keySet().toArray(new String[0]);
        int uu=0;
        if (domainkeys.length>0&&!domainkeys[0].equals("")){
            for (int i = 0; i < domainkeys[0].length(); i++) {
                uu++;
            }
            return uu;
        }else
            return 3;
    }
    public static String soutsd(int urlcount,int id,int secon){
        System.out.println("urlcount--"+urlcount);
        System.out.println("id--"+id);
        System.out.println("secon--"+secon);
        return "";
    }
    public static String getstyle(String path){
        String st="";
        File file=new File(path);
        if (!file.exists())
            return "";
        try {
            ArrayList list= (ArrayList) FileUtils.readLines(new File(path));
            for (int i = 0; i < list.size(); i++) {
                st+=list.get(i)+"\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
    public static void getfileyh(String filePathog){
        File file=new File(filePathog);
        if (!file.exists()){
            file.mkdirs();
            System.out.println(filePathog+"not exist!");
        }
        int count=0;
        File[] flist=file.listFiles();
        String [] domains=file.list();
        for (int k = 0; k < flist.length; k++) {
            if(!flist[k].isDirectory()){
                System.out.println("not file--->"+flist[k]);
                continue;
            }
            File file1=new File(flist[k].toString());
            String[] list1=file1.list();
            for (int j = 0; j < list1.length; j++) {
                String path=flist[k]+"/"+list1[j];
                ArrayList alcontent=filedata(path);
                String content= (String) alcontent.get(0);
                if (content==null||content==""){
                    count++;
                    continue;
                }

                List<Map> list = (List<Map>) JSON.parse(content);
                String keywords = "";
                String details = "";
                for (int i = 0; i < list.size(); i++) {
                    keywords += list.get(i).get("title") + "Φfenge";
                    details += list.get(i).get("des") + "Φfenge";
                }
                keywords=decodeUnicode(keywords);
                details=decodeUnicode(details);
                keywords = keywords.replaceAll("検索結果", "");
                keywords=keywords.replaceAll("<[\\s\\S]*?>","");
                details=details.replaceAll("<[\\s\\S]*?>","");
                details = details.replaceAll("検索結果", "");
                String domainid = domains[k];
                String id = list1[j];
                path = "../site/" + domainid + "/p" + id + ".txt";
                path=path.replace(".json","");
                File file2 = new File(path);
                if (!file2.exists()) {
                    count++;
                    continue;
                }
                ArrayList arraycontent = null;
                try {
                    arraycontent = (ArrayList) FileUtils.readLines(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (arraycontent == null) {
                    count++;
                    continue;
                }
                Map dataMap = (Map) JSON.parse((String) arraycontent.get(0));

                if (dataMap == null){
                    count++;
                    continue;
                }
                keywords=decodeUnicode(keywords);
                details=decodeUnicode(details);
                dataMap.put("keytitles", keywords);
                dataMap.put("destitle", details);
                String stringJSON = JSON.toJSONString(dataMap);
                try {
                    FileUtils.write(new File(path), stringJSON, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(flist[k]+"Done!!");
            System.out.println("Error--"+count);
            count=0;
        }
        System.out.println("All Done!!!");
    }
    public static String decodeUnicode(String unicodeStr) {
        char aChar;
        int len = unicodeStr.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len;) {
            aChar = unicodeStr.charAt(x++);
            if (aChar == '\\') {
                aChar = unicodeStr.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = unicodeStr.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                System.out.println("UnicodeToUtf8 exception!");
                                return "";
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
    public String pages(int pageNum,int totalPage ,int productnumber,List urls,Map readConfigWeb,int idTotal,String hostName,String secondname,String cateurlhtml){
            String cateurlshtml="";
        if (pageNum*productnumber>totalPage)
            productnumber=totalPage;
        for (int i = (pageNum-1)*productnumber; i <productnumber; i++) {
            List content = Getsitemapurl.getProductTxt((String) urls.get(i), readConfigWeb, idTotal);
            if (content.size() >= 2) {
                String name="";
                String price="";
                String image="";
                String cateurlnn = (String)content.get(1);
                Map map1 = (Map)JSON.parse((String)content.get(0));
                name=map1.get("product_name").toString();
                price=map1.get("product_price").toString();
                int  pricec= (int) (Double.valueOf(price).intValue());
                int  orprice= (int) (Double.valueOf(price).intValue()*1.3);
                cateurlhtml=cateurlhtml.replace("{#url}",hostName+"/"+secondname+cateurlnn);
                cateurlhtml=cateurlhtml.replace("{#productname}",name);
                cateurlhtml=cateurlhtml.replace("{#mainimage}",image);
                cateurlhtml=cateurlhtml.replace("{#price}",String.valueOf(orprice));
                cateurlhtml=cateurlhtml.replace("{#specialprice}",String.valueOf(pricec));
                cateurlshtml+=cateurlhtml;
            }
        }
        return cateurlshtml;
    }
    public static String color(){
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length()==1 ? "0" + red : red ;
        //判断绿色代码的位数
        green = green.length()==1 ? "0" + green : green ;
        //判断蓝色代码的位数
        blue = blue.length()==1 ? "0" + blue : blue ;
        //生成十六进制颜色值
        String color = red+green+blue;

        return color;
    }

}


