package main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public class Jsontest {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        test1();
    }
    public static void test1() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?&password=cajuyomk&dnis=85264637178&username=CTGRLPREM&password=pdm705&dnis=85264637178&ani=CRCResolink&username=CRCCLY14294&command=submit";
        String add="【润联科技】尊敬的移动用户：\n" +
                "       你好，杜海威先生给你充了6666元话费，请立刻给他转账吧，谢谢！";
        String res=add.replace(" ","%20").replace("\n","%0A");
        String response=HttpClientUtil1.getWithProxy(url+"&message="+res,new HashMap<>(),"utf-8");
        System.out.println();
    }
    public static void test() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178,85262974397&message=TEST&command=submit";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(),"utf-8");
        List<JSONObject> jsonObjects= JSON.parseArray(response,JSONObject.class);
        System.out.println();
    }
}
