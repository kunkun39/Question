package com.changhong.service;

import com.alibaba.fastjson.JSONObject;
import com.changhong.assember.JsonAssember;
import com.changhong.domain.AppDescription;
import com.changhong.domain.Examination;
import com.changhong.utils.WebUtils;
import com.ots.deviceinfoprovide.DeviceInfo;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by Jack Wang
 */
public class HttpClientService {

//    private static String HOSTS = "http://www.ottserver.com:8081/";

   private static String HOSTS = "http://10.9.46.110:8080/";

    private static boolean LOCAL = false;

    public static String getWelcomePageResponse() {
        if (LOCAL) {
            return "{\"appDescription\":\"你好，你是那个\",\"categories\":[{\"id\":3,\"title\":\"你是那个\"},{\"id\":2,\"title\":\"关于美女的问卷调查\"},{\"id\":1,\"title\":\"关于节目喜爱程度的调查访问\"}]}";
        }

        String url = HOSTS + "faq/ott/getexaminationlist.html";
        String response = WebUtils.httpPostRequest(url);
        return response;
    }

    public static String getExaminationById(int examinationId) {
        if (LOCAL) {
            return "{\"id\":6,\"title\":\"川网问卷调查（二） \",\"description\":\"\",\"questions\":[{\"sequence\":1,\"questionType\":\"MUTI\",\"title\":\"你家里面有几个人呢一？\",\"answers\":[{\"result\":\"A:1\"},{\"result\":\"B:2\"},{\"result\":\"C:3\"},{\"result\":\"D:>3\"}]},{\"sequence\":2,\"questionType\":\"SINGLE\",\"title\":\"你家里面有几个人呢二？\",\"answers\":[{\"result\":\"A:1\"},{\"result\":\"B:2\"},{\"result\":\"C:3\"},{\"result\":\"D:>3\"}]},{\"sequence\":3,\"questionType\":\"SINGLE\",\"title\":\"你家里面有几个人呢三？\",\"answers\":[{\"result\":\"A:1\"},{\"result\":\"B:2\"},{\"result\":\"C:3\"},{\"result\":\"D:>3\"}]}]}";
        }

        String url = HOSTS + "faq/ott/getexamination.html?examinationId=" + examinationId;
        String response = WebUtils.httpPostRequest(url);
        return response;
    }

    public static void sendExaminationResult(int examinationId, String result) {
        if (!LOCAL) {
            JSONObject o = new JSONObject();
            o.put("mac", DeviceInfo.DeviceMac);
            o.put("examinationId", examinationId);
            o.put("answers", result);
            PostMethod method = new PostMethod(HOSTS + "faq/ott/sendexamination.html");
            method.addParameter("result", o.toString());
            WebUtils.httpPostRequest(method);
        }
    }

    public static void main(String[] args) throws Exception{
        String response = getWelcomePageResponse();
        AppDescription appDescription = JsonAssember.convertToAppDescription(response);
        System.out.printf("1");

        response = getExaminationById(1);
        Examination examination = JsonAssember.convertToExamination(response);
        System.out.printf("1");
    }
}
