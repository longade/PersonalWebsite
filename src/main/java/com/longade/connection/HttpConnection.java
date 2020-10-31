package com.longade.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class HttpConnection {

    URL url;
    HttpsURLConnection connection;

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
    }

    public void getHTML(String strURL) {
        try {
            Document doc = Jsoup.connect(strURL).get();
            Elements list = doc.select("ul > li");
            for (Element single : list) {
                System.out.println(single.text());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
