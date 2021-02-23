package main;


import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import sun.misc.BASE64Encoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ProxyTest {
    private static String proxyHost = "10.8.81.20";
    private static int proxyPort = 1080;
//    private static String proxyUser = "root";
//    private static String proxyPass = "123654wang";
    public static void main(String[] args) {
        String url = "https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit";
        String content = doProxy(url);
        System.out.println("Result :===================\n " + content);
    }
    /**
     * 通过系统变量方式实现代理
     *
     * @param url
     * @return
     */
    public static String doProxy(String url) {
        // 设置系统变量

        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", "" + proxyPort);
        // 针对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", "" + proxyPort);
        // 设置默认校验器
      //  setDefaultAuthentication();

        //开始请求
        try {
            URL u = new URL(url);
            SslUtils.ignoreSsl();
            URLConnection conn = u.openConnection();
//            String headerKey = "Proxy-Authorization";
//            BASE64Encoder encoder = new BASE64Encoder();
//            String pass="root:123654wang";
//            String headerValue = "Basic " + pass;
//            conn.setRequestProperty(headerKey, headerValue);
            HttpsURLConnection httpsCon = (HttpsURLConnection) conn;
            httpsCon.setFollowRedirects(true);
            String encoding = conn.getContentEncoding();
            if (StringUtils.isEmpty(encoding)) {
                encoding = "UTF-8";
            }
            InputStream is = conn.getInputStream();
            Scanner scanner = new Scanner(is, encoding);
            String text = scanner.useDelimiter("\\A").next();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

//    /**
//     * 设置全局校验器对象
//     */
//    public static void setDefaultAuthentication() {
//        MyAuthenticator myAuthenticator=new MyAuthenticator(proxyUser,proxyPass);
//        Authenticator.setDefault(myAuthenticator);
//    }
}
