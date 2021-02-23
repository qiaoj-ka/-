package main;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class Convert {
    private String name;
    private String password;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Convert(String name,  String sex){
        this.name=name;
        this.sex=sex;
    }
    public static void main(String[] args) {
        test2();
    }

    public static void test(){
        Convert convert=new Convert("jiajia","girl");
        String pojo= JSON.toJSONString(convert);
        System.out.println(pojo);
    }

    public static void test1(){
        boolean flag=true;
        Convert convert=new Convert("jiajia","girl");
        JSONObject pojo= (JSONObject) JSON.toJSON(convert);
        StringBuilder stringBuilder=new StringBuilder();
        Iterator iter = pojo.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            if(entry.getValue()==null)continue;
            if(flag){
                stringBuilder.append(entry.getKey()+"="+entry.getValue());
                flag=false;
            }else {
                stringBuilder.append("&"+entry.getKey()+"="+entry.getValue());
            }
        }
        System.out.println(stringBuilder.toString());
    }

    public static void test2(){
        boolean flag=true;
        Convert convert=new Convert("jiajia","girl");
        JSONObject pojo= (JSONObject) JSON.toJSON(convert);
        StringBuilder stringBuilder=new StringBuilder();
        pojo.forEach((k,v)->{
            if(v!=null)
            stringBuilder.append("&"+k+"="+v);
        });
        System.out.println(stringBuilder.toString());
    }
}
