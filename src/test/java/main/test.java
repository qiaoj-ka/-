package main;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Properties;

public class test {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        test6();
    }

    public static void test6() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //HttpClientUtil util=new HttpClientUtil();
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178,85262974397,&message=TEST&command=submit";
        String url1="https://sms.chinatelecom.alarislabs.com:8002/api?message=TEST&password=cajuyomk&dnis=85264637178,85262974397&ani=CRCResolink&username=CTGRLPREM&command=submit";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(), "UTF-8");

        System.out.println(response);

    }
    //解析Json数据
    public static void parseJsonGet(String jsonString){

    }
    public static void test5() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        //HttpClientUtil util=new HttpClientUtil();
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&dnis=85264637178&ani=CRCResolink&message=【润连科技】你好&command=submit";
        String res=HttpClientUtil1.getWithProxy(url,null,null);
        System.out.println();

    }
    public static void test3() throws IOException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit";
        String url1="https://www.baidu.com/";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.96.86.4", 80));
        Connection.Response response =   Jsoup.connect(url1).proxy(proxy).execute();
        System.out.println(response);
    }


    /**
     * java用socket5实现内网和外网的连接
     * 参考博客：https://blog.csdn.net/q15102780705/article/details/111352565
     * 控制台有报错原因
     * @throws IOException
     */
    public static void test2() throws IOException {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new MyConnectionSocketFactory())
                .register("https", new MySSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg,new FakeDnsResolver());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress("10.8.81.20", 1080);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            //String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit";
//            HttpHost target = new HttpHost("www.baidu.com", 443, "https");
//            HttpGet request = new HttpGet("/");
            HttpHost target=new HttpHost("sms.chinatelecom.alarislabs.com",8002,"https");
            HttpPost request = new HttpPost("/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit");
            System.out.println("Executing request " + request + " to " + target + " via SOCKS proxy " + socksaddr);
            CloseableHttpResponse response = httpclient.execute(target, request, context);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity entity1 = response.getEntity();
                //EntityUtils.consume(entity1);
                System.out.println(EntityUtils.toString(entity1,"utf-8"));
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            httpclient.close();
        }

    }

    /**
     * jsoup通过代理访问目标地址
     * 借鉴博客：https://blog.csdn.net/n009ww/article/details/90267411
     * 报错博客：https://bbs.csdn.net/topics/391080780
     * @throws IOException
     */
    public static void test1() throws IOException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CTG&dnis=85264637178&message=TEST&command=submit";
        String url1="http://39.96.86.4/";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.8.81.20", 1080));
        Connection.Response response =   Jsoup.connect(url).proxy(proxy).execute();
        System.out.println(response);
    }

    public void  test4() throws IOException {
        String url="https://www.baidu.com/";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.96.86.4", 80));
        Connection connection=Jsoup.connect(url);
        String headerKey = "Proxy-Authorization";
//        String headerValue = "Basic " + Base64.encode("root:123654wang");
//        connection
//        Connection.Response response =   Jsoup.connect(url).proxy(proxy).execute();
//        System.out.println(response);
    }

}
 class MyAuthenticator extends Authenticator {
    private String user = "";
    private String password = "";
    public MyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}

 class FakeDnsResolver implements DnsResolver {
    @Override
    public InetAddress[] resolve(String host) throws UnknownHostException {
        // Return some fake DNS record for every request, we won't be using it
        return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
    }
}
