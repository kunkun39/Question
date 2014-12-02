package com.changhong.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by Jack Wang
 */
public class WebUtils {

    public static String httpPostRequest(String url) {
        PostMethod postMethod = new PostMethod(url);
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(5000);
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

    public static String httpPostRequest(PostMethod postMethod) {
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(5000);
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
