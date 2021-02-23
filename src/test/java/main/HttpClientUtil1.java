package main;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpClientUtil1 {
    // 依次是代理地址，代理端口号，用户密码
    private static String proxyHost="10.8.81.20";
    private static int proxyPort=1080;
    private static String proxyName="user";
    private static String proxyPwd="123456";


    public static String getWithProxy(String url, Map<String, String> headers, String charset) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        //用户名和密码验证
//        Authenticator.setDefault(new Authenticator(){
//            protected  PasswordAuthentication  getPasswordAuthentication(){
//                PasswordAuthentication p=new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
//                return p;
//            }
//        });
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new MyConnectionSocketFactory())
                .register("https", new MySSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
       // CloseableHttpClient httpclient = createSSLInsecureClient(cm);


        try {
            InetSocketAddress socksaddr = new InetSocketAddress(proxyHost,proxyPort);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            HttpGet httpget = new HttpGet(url);
            if(headers != null) {
                for(String key:headers.keySet()) {
                    httpget.setHeader(key, headers.get(key));
                }
            }
            CloseableHttpResponse response = httpclient.execute(httpget,context);
            System.out.println();
            try {
                return new String(EntityUtils.toByteArray(response.getEntity()), charset);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            // Return some fake DNS record for every request, we won't be using it
            return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
        }
    }

    static class MyConnectionSocketFactory extends PlainConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

    static class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

        public MySSLConnectionSocketFactory(final SSLContext sslContext) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

            // You may need this verifier if target site's certificate is not secure

            super(targetSSLContext(), ALLOW_ALL_HOSTNAME_VERIFIER);
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);

        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                    .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }

    }
    public static CloseableHttpClient createSSLInsecureClient( PoolingHttpClientConnectionManager cm) {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 默认信任所有证书
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setConnectionManager(cm).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
    public static SSLContext targetSSLContext() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return   new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 默认信任所有证书
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
    }
}
