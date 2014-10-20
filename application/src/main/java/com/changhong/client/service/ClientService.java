package com.changhong.client.service;

import com.google.gson.JsonObject;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:39
 */
public interface ClientService {

    void cleanExaminationCache(int examinationId);

    JsonObject obtainExaminationList(String examinationType) throws Exception;

    JsonObject obtainExaminationById(int examinationId)  throws Exception;

    void handleEndUserExaminationResult(String result) throws Exception ;
}
