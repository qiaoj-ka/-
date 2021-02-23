package main;


import com.alibaba.fastjson.JSON;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultiSendMsg {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException {
        test1();
    }
    public static void test1() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&messageId=alss-6011f510-552a92d9-0c81abe5&command=query";
        String url1="https://sms.chinatelecom.alarislabs.com:8002/api?password=cajuyomk&username=CTGRLPREM&messageId=alss-6011f510-3c111092-0045b304-64637178&command=query";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(), "UTF-8");

        System.out.println(response);
    }

    /**
     * 转换一个Json字符串
     * @throws ParseException
     */
    public static void test2() throws ParseException {
        String res="{\"message_id\":\"alss-a1b2c3d4-e5f67890\",\n \"dnis\":\"791003044222\"}";
        //JSONObject jsonObject=(JSONObject)(new JSONParser().parse(res));
        JSONObject json = net.sf.json.JSONObject.fromObject(res);
        System.out.println();
    }

    public static void test3() throws ParseException {
        String res="[{\"dnis\":\"791003044222\",\n\"message_id\":\"5b4c46a8-8dc9-44b4-f55f-" +
                "3bef56819305\"\n," +
                "\"segment_num\":\"1\"\n},{\"dnis\":\"791003044222\", \n" +
                "\"message_id\":\"5b4c46a8-46bc-7ee6-4a16-7d4e5a0d14af\",\n\"segment_num\":\"2\"}] ";
        //JSONObject jsonObject=(JSONObject)(new JSONParser().parse(res));
        String str="{\"dnis\":\"791003044222\",\n \"message_id\":\"5b4c46a8-8dc9-44b4-f55f-3bef56819305\"}";
        JSONObject k=net.sf.json.JSONObject.fromObject(str);
        String[] strings=res.replace("[","").replace("]","").split("},");
        for (int i=0;i<strings.length-1;i++){
            strings[i]+="}";
        }
        JSONObject[] jsonObject=new JSONObject[strings.length];
        for(int i=0;i<strings.length;i++){
            jsonObject[i]=net.sf.json.JSONObject.fromObject(strings[i]);
        }
        System.out.println();
    }

    //单个发送获得json串
    public static void test4() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178&message=TEST&command=submit";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(), "UTF-8");
        JSONObject json = JSONObject.fromObject(response);
//        JSONObject json=new JSONObject();
//        json.put("massage_id","alss-6011f510-3b9551f8-df5d9da1");
        Iterator iter = json.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry= (Map.Entry) iter.next();
            if(entry.getValue()!=null){
                System.out.println(entry.getValue());
            }
        }
        String res=json.getString("message_id");
        System.out.println();
    }
    //多条消息发送得到json串
    public static void test5() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178,85262974397&message=TEST&command=submit";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(), "UTF-8");
        String[] strings=response.replace("[","").replace("]","").split("},");
        for (int i=0;i<strings.length-1;i++){
            strings[i]+="}";
        }
        JSONObject[] jsonObject=new JSONObject[strings.length];
        for(int i=0;i<strings.length;i++){
            jsonObject[i]=net.sf.json.JSONObject.fromObject(strings[i]);
        }
        JSONObject json = net.sf.json.JSONObject.fromObject(response);
        System.out.println();
    }

    public static void test6() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178,85262974397&message=TEST&command=submit";
        String response=HttpClientUtil1.getWithProxy(url,new HashMap<>(), "UTF-8");
        System.out.println(response);
    }

}
