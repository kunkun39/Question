package com.changhong.client.web.controller;

import com.google.gson.JsonObject;
import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * User: Jack Wang
 * Date: 14-7-22
 * Time: 上午11:12
 */
public class ExaminationJsonHandleControllerTest extends TestCase {

    public void testGetWelcome() {
        PostMethod method = new PostMethod("http://localhost:8080/faq/ott/getexaminationlist.html");
//        PostMethod method = new PostMethod("http://www.ottserver.com:8081/faq/ott/getexaminationlist.html");
        String s = httpPostRequest(method);
        System.out.println(s);
    }


    public void testSendExamnination() {
        JsonObject o = new JsonObject();
        o.addProperty("mac", "QA:WD:ER:ED:ED:EB");
        o.addProperty("examinationId", "1");
        o.addProperty("answers", "D|B|C|A,B|C");
//        PostMethod method = new PostMethod("http://localhost:8080/faq/ott/sendexamination.html");
        PostMethod method = new PostMethod("http://www.ottserver.com:8081/faq/ott/sendexamination.html");
        method.addParameter("result", o.toString());
        String s = httpPostRequest(method);
    }

    public static String httpPostRequest(PostMethod postMethod) {
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(60000);
        client.getParams().setContentCharset("UTF-8");

        int status = 0;
        String response = "";
        try {
            status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            } else if (status == HttpStatus.SC_MOVED_TEMPORARILY) {
                response = postMethod.getResponseHeader("Location").getValue();
            } else {
                throw new RuntimeException("can't find update data for this request");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            postMethod.releaseConnection();
        }

        return response;
    }
}
