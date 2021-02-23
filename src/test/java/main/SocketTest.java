package main;

public class SocketTest {
    public static void main(String[] args) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("username="+"CTGRLPREM");
        stringBuilder.append("&"+"password="+"cajuyomk");
        stringBuilder.append("&"+"ani="+"CRCResolink");
        stringBuilder.append("&"+"dnis="+"852"+"64637178");
        stringBuilder.append("&message="+"TEST");
        stringBuilder.append("&command="+"submit");
        String url="https://sms.chinatelecom.alarislabs.com:8002/api?"+stringBuilder.toString();
        String url1="https://sms.chinatelecom.alarislabs.com:8002/api?username=CTGRLPREM&password=cajuyomk&ani=CRCResolink&dnis=85264637178&message=TEST&command=submit";
        boolean flag=url1.equals(url);
        System.out.println(flag);
    }
}
