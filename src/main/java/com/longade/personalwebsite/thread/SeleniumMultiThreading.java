package com.longade.personalwebsite.thread;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class SeleniumMultiThreading extends Thread {

    WebDriver driver;
    String url;
    BrowserMobProxy browserMobProxy;

    public SeleniumMultiThreading(String name, String url) {
        super(name);
        this.url = url;
    }

    public SeleniumMultiThreading(String name, String url, BrowserMobProxy browserMobProxy) {
        super(name);
        this.url = url;
        this.browserMobProxy = browserMobProxy;
    }

    @Override
    public void run() {
        System.out.println("Thread Started - " + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
            if (browserMobProxy != null)
                setup(this.url, this.browserMobProxy);
            else
                setup(this.url);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            tearDown();
        }
    }

    public void setup(String url) {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        // options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get(url);

    }

    public void setup(String url, BrowserMobProxy browserMobProxy) {

        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

        Proxy proxy = ClientUtil.createSeleniumProxy(browserMobProxy);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        // options.addArguments("--headless");

        try {
            driver = new ChromeDriver(options);
            driver.get(url);

            Thread.sleep(5000);

            browserMobProxy.newHar(Thread.currentThread().getName());
            Har har = browserMobProxy.getHar();
            File harFile = new File(Thread.currentThread().getName() + ".har");
            har.writeTo(harFile);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
