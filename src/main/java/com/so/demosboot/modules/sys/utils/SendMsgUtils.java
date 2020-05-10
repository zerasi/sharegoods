package com.so.demosboot.modules.sys.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendMsgUtils {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public static Result sendMsgCode(String tel, String msgCode){
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");


        String content = new String("您的验证码是：" + msgCode + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C46506783"), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "22928857c78e96b028d2811f1e440895"),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", tel),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);
        String msg = "";
        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            msg = root.elementText("msg");
            //String smsid = root.elementText("smsid");

            if("2".equals(code)){
                return new Result(true, msg);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            // Release connection
            method.releaseConnection();
            //client.getConnectionManager().shutdown();
        }
        return new Result(false, msg);
    }
}
