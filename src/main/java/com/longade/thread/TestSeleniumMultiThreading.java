package com.longade.thread;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

public class TestSeleniumMultiThreading {

    public static void main(String[] args) {

        BrowserMobProxy browserMobProxy = new BrowserMobProxyServer();
        browserMobProxy.setTrustAllServers(true);
        browserMobProxy.start(8585);

        Thread t1 = new SeleniumMultiThreading("ThreadOne", "https://www.google.it", browserMobProxy);
        Thread t2 = new SeleniumMultiThreading("ThreadTwo", "https://www.facebook.com", browserMobProxy);
        Thread t3 = new SeleniumMultiThreading("ThreadThree", "https://www.twitter.com", browserMobProxy);
        Thread t4 = new SeleniumMultiThreading("ThreadFour", "https://www.instagram.com", browserMobProxy);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
