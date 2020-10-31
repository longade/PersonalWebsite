package com.longade.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getHTML(String strURL) {

        List<String> urls = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(strURL).get();
            Elements list = doc.select("ul > li");
            for (Element single : list) {
                System.out.println(single.text());
                urls.add(single.text());
            }
            return urls;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
