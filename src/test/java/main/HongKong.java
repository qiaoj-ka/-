package main;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;

public class HongKong {
    private String userName;  // 香港电信注册账号
    private String passWord;  // 香港电信账号密码
    public HongKong(String userName, String password){
        this.userName=userName;
        this.passWord=password;
    }
    public static void main(String[] args) throws JSONException, IOException {
        HongKong hongKong=new HongKong("CRCCLY14294","pdm705");
        JSONObject body=new JSONObject();
        String content="你好，你的快递已经到达菜鸟驿站，请及时取件";
        body.put("username",hongKong.userName);
        body.put("password",hongKong.passWord);
        body.put("ani","8615336155900");//区号 + 号码
        body.put("message", URLEncoder.encode(content, "GBK"));
        body.put("commend","submit");
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("username="+hongKong.userName);
        stringBuilder.append("&"+"password="+hongKong.passWord);
        stringBuilder.append("&"+"ani="+"8615336155900");
        stringBuilder.append("&message="+URLEncoder.encode(content, "GBK"));
        stringBuilder.append("&commend="+"submit");
        //获得Http客户端
        HttpClientBuilder clientBuilder=HttpClientBuilder.create();
        clientBuilder.setProxy(new HttpHost("10.8.81.20",1080));
        //clientBuilder.setProxy(new HttpHost("127.0.0.1",9002));
        clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
        CloseableHttpClient httpClient = clientBuilder.build();
        String apiGwClientUrl="http://sms.chinatelecom.alarislabs.com:8002";
        //String apiGwClientUrl="http://127.0.0.1:9002/hello";
        String baiduUrl="https://www.baidu.com/";
        HttpGet httpGet=new HttpGet(baiduUrl);
        //HttpPost httpPost=new HttpPost(apiGwClientUrl+"?"+stringBuilder.toString());
        HttpPost httpPost=new HttpPost("https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit");
        //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse respose=httpClient.execute(httpPost);
        System.out.println(respose);
        Connection connection=Jsoup.connect("https://www.baidu.com");
        //Connection connection= Jsoup.connect("https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit");
        connection.timeout(20000);
        Document document=connection.get();
        System.out.println(document);
        //HttpResponse response = httpClient.execute(httpPost);
        //System.out.println(response);
    }
}
