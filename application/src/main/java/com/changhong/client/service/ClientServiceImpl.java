package com.changhong.client.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.repository.ClientDao;
import com.changhong.system.domain.Answer;
import com.changhong.system.domain.AppDescription;
import com.changhong.system.domain.Examination;
import com.changhong.system.domain.Question;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:39
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    private Map<Integer, JsonObject> examinationCache = new HashMap<Integer, JsonObject>();

    private JsonObject welcome;

    public void cleanExaminationCache(int examinationId) {
        examinationCache.remove(examinationId);
        welcome = null;
    }

    public JsonObject obtainExaminationList() throws Exception {
        //先试着从缓存中去拿, 保证系统系能
        if (welcome != null) {
            return welcome;
        }

        welcome = new JsonObject();
        AppDescription appDescription = clientDao.loadAppDescription();
        welcome.addProperty("appDescription", appDescription.getDescription());

        List<Examination> examinations = clientDao.loadExaminationCategories();
        JsonArray jExaminations = new JsonArray();
        for (Examination examination : examinations) {
            JsonObject jExamination = new JsonObject();
            jExamination.addProperty("id", examination.getId());
            jExamination.addProperty("title", examination.getTitle());

            jExaminations.add(jExamination);
        }
        welcome.add("categories", jExaminations);
        return welcome;
    }

    public JsonObject obtainExaminationById(int examinationId) throws Exception {
        //先试着从缓存中去拿, 保证系统系能
        JsonObject cachedObject = examinationCache.get(examinationId);
        if (cachedObject != null) {
            return cachedObject;
        }

        Examination examination = clientDao.loadLatestExamination(examinationId);
        JsonObject jExamination = new JsonObject();

        jExamination.addProperty("id", examination.getId());
        jExamination.addProperty("title", examination.getTitle());
        jExamination.addProperty("description", examination.getDescription());

        JsonArray jQuestions = new JsonArray();
        List<Question> questions = examination.getQuestions();
        for (Question question : questions) {
            JsonObject jQuestion = new JsonObject();
            jQuestion.addProperty("sequence", question.getSequence());
            jQuestion.addProperty("questionType", question.getQuestionType().name());
            jQuestion.addProperty("title", question.getTitle());

            JsonArray jAnswers = new JsonArray();
            for (Answer answer : question.getAnswers()) {
                JsonObject jAnswer = new JsonObject();
                if (StringUtils.hasText(answer.getResult())) {
                    jAnswer.addProperty("result", answer.getSequence() + ":" + answer.getResult());
                    jAnswers.add(jAnswer);
                }
            }
            jQuestion.add("answers", jAnswers);

            jQuestions.add(jQuestion);
        }

        jExamination.add("questions", jQuestions);
        examinationCache.put(examination.getId(), jExamination);
        return jExamination;
    }

    public void handleEndUserExaminationResult(String result) throws Exception {
        JSONObject o = JSON.parseObject(result);

        int examinationId = o.getInteger("examinationId");
        String mac = o.getString("mac");
        String answers = o.getString("answers");

        if (StringUtils.hasText(answers)) {
            clientDao.handleUserResult(examinationId, mac, answers);
        }
    }
}
