package com.longade.connection;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpConnection {

    URL url;
    HttpsURLConnection connection;
    BrowserMobProxy browserMobProxy;

    public void getRequest(String strURL) {
        try {
            url = new URL(strURL);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println(connection.getResponseCode() + "|" + connection.getResponseMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            connection.disconnect();
        }
    }

    public List<String> getHTML(String strURL) {

        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

        browserMobProxy = new BrowserMobProxyServer();
        browserMobProxy.setTrustAllServers(true);
        browserMobProxy.start(9090);
        System.out.println(browserMobProxy.getPort());

        Proxy proxy = ClientUtil.createSeleniumProxy(browserMobProxy);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        /* java.net Proxy to use for Jsoup connect (SSL problems) */
        // SocketAddress socketAddress = new InetSocketAddress("localhost", browserMobProxy.getPort());
        // Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);

        List<String> urls = new ArrayList<>();

        try {
            browserMobProxy.newHar("onlyfoot");
            driver.get("http://onlyfoot.net/ch3sport.html");
            Thread.sleep(5000);
            Har har = browserMobProxy.getHar();
            List<HarEntry> entries = har.getLog().getEntries();
            FileWriter fileWriter = new FileWriter("clean_requests.txt");
            for (HarEntry entry : entries) {
                String url = entry.getRequest().getUrl();
                if (url.contains(".m3u8")) {
                    System.out.println(url);
                    fileWriter.write(url + System.lineSeparator());
                }
            }
            fileWriter.close();
            File harFile = new File("network_requests.har");
            har.writeTo(harFile);
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            driver.close();
            browserMobProxy.stop();
        }

        // use of Jsoup to connect to the page and get html elements
        /* try {
            browserMobProxy.newHar("davewebsite");
            Document doc = Jsoup.connect("http://onlyfoot.net/ch3sport.html")
                    .proxy(proxy)
                    .get();
            Elements list = doc.select("ul > li");
            for (Element single : list) {
                System.out.println(single.text());
                urls.add(single.text());
            }
            Har har = browserMobProxy.getHar();
            File harFile = new File("network_requests.har");
            har.writeTo(harFile);
            return urls;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            browserMobProxy.stop();
        }*/

    }

}
