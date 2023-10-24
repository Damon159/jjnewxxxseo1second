package com.gaobug.controller;

import org.springframework.stereotype.Controller;

public class user {
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private String hostName;

    public String getGeturl() {
        return geturl;
    }

    public void setGeturl(String geturl) {
        this.geturl = geturl;
    }

    private String geturl;

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    private String remoteAddress;

    public String getServer() {
        return serverh;
    }

    public void setServer(String serverh) {
        this.serverh = serverh;
    }

    private String serverh;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private String userAgent;

    public String getProductIdTotal() {
        return productIdTotal;
    }

    public void setProductIdTotal(String productId) {
        this.productIdTotal = productId;
    }

    private String productIdTotal;
    private String ht;

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public String getUrlrefer() {
        return urlrefer;
    }

    public void setUrlrefer(String urlrefer) {
        this.urlrefer = urlrefer;
    }

    private String urlrefer;
}
