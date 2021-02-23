package main;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.io.IOException;

public class Proxy1 {
    public static void main(String[] args) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( new AuthScope("39.96.86.4", 80),new UsernamePasswordCredentials("root", "123654wang") );
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.useSystemProperties();
        clientBuilder.setProxy(new HttpHost("39.96.86.4", 80));
        clientBuilder.setDefaultCredentialsProvider(credsProvider);
        clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
        CloseableHttpClient client = clientBuilder.build();
        String baiduUrl="https://www.baidu.com/";
        HttpGet httpGet=new HttpGet(baiduUrl);
        CloseableHttpResponse response=client.execute(httpGet);
        System.out.println(response);
    }
}
