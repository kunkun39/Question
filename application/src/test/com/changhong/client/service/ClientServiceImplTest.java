package com.changhong.client.service;


import com.changhong.system.web.facade.dto.AnswerDTO;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import junit.framework.TestCase;
//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.node.ArrayNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.parser.Feature;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-26
 * Time: 上午9:30
 */
public class ClientServiceImplTest extends TestCase {

    private static String json = "{\"id\":1,\"title\":\"德国黑森林罕见暴雨把路面冲走\",\n" +
            "    \"questions\":[\n" +
            "        {\"sequence\":1,\"title\":\"你是那个?\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":2,\"title\":\"德国黑森林罕见暴雨把路面冲走\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":3,\"title\":\"德国黑森林罕见暴雨把路面冲走\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":4,\"title\":\"你是那个\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":5,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":6,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":7,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":8,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":9,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":10,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":11,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":12,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":13,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":14,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":15,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":16,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":17,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":18,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":19,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]},\n" +
            "        {\"sequence\":20,\"title\":\"美海豚伤痕累累疑曾遭虎鲸整只吞食\",\"answers\":[{\"result\":\"A:姓名\"},{\"result\":\"B:六哈\"},{\"result\":\"C:积分\"},{\"result\":\"D:金佛额\"}]}\n" +
            "        ]\n" +
            "}";

    /**
     * 11-12s, JAR FILE SIZE 42K
     */
    public void testParseJsonWith_org_json_jar() throws Exception {
//        long start = System.currentTimeMillis();
//
//        for (int loop = 0; loop < 10000; loop++) {
//
//            ExaminationDTO examinationDTO = new ExaminationDTO();
//            JSONObject jExamination = new JSONObject(json);
//
//            //handle examination info
//            int id = jExamination.optInt("id");
//            String title = jExamination.optString("title");
//            examinationDTO.setId(id);
//            examinationDTO.setTitle(title);
//
//            //hanle question
//            JSONArray jQuestions = jExamination.getJSONArray("questions");
//            for (int i = 0; i < jQuestions.length(); i++) {
//                JSONObject jQuestion = jQuestions.getJSONObject(i);
//                int sequence = jQuestion.optInt("sequence");
//                String questionTitle = jQuestion.optString("title");
//                QuestionDTO questionDTO = new QuestionDTO();
//                questionDTO.setSequence(sequence);
//                questionDTO.setTitle(questionTitle);
//
//                //handle answer
//                JSONArray jAnswers = jQuestion.getJSONArray("answers");
//                for (int j = 0; j < jAnswers.length(); j++) {
//                    JSONObject jAnswer = jAnswers.getJSONObject(j);
//                    String result = jAnswer.optString("result");
//                    String[] tokens = StringUtils.delimitedListToStringArray(result, ":");
//                    AnswerDTO answerDTO = new AnswerDTO();
//                    answerDTO.setSequence(tokens[0]);
//                    answerDTO.setResult(tokens[1]);
//                    questionDTO.addAnswer(answerDTO);
//                }
//
//                examinationDTO.addQuestion(questionDTO);
//            }
//        }
//
//        long end = System.currentTimeMillis();
//        long during = end - start;
//        System.out.println("10000 times taken time for org.json is " + during);
    }

    //8s-9s JAR FILE SIZE 255K
    public void testParseJsonWith_fast_json_jar() throws Exception {
        long start = System.currentTimeMillis();

        for (int loop = 0; loop < 10000; loop++) {

            ExaminationDTO examinationDTO = new ExaminationDTO();
            JSONObject jExamination = JSON.parseObject(json);

            //handle examination info
            int id = jExamination.getInteger("id");
            String title = jExamination.getString("title");
            examinationDTO.setId(id);
            examinationDTO.setTitle(title);

            //hanle question
            JSONArray jQuestions = jExamination.getJSONArray("questions");
            for (int i = 0; i < jQuestions.size(); i++) {
                JSONObject jQuestion = jQuestions.getJSONObject(i);
                int sequence = jQuestion.getInteger("sequence");
                String questionTitle = jQuestion.getString("title");
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setSequence(sequence);
                questionDTO.setTitle(questionTitle);

                //handle answer
                JSONArray jAnswers = jQuestion.getJSONArray("answers");
                for (int j = 0; j < jAnswers.size(); j++) {
                    JSONObject jAnswer = jAnswers.getJSONObject(j);
                    String result = jAnswer.getString("result");
                    String[] tokens = StringUtils.delimitedListToStringArray(result, ":");
                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setSequence(tokens[0]);
                    answerDTO.setResult(tokens[1]);
                    questionDTO.addAnswer(answerDTO);
                }

                examinationDTO.addQuestion(questionDTO);
            }
        }

        long end = System.currentTimeMillis();
        long during = end - start;
        System.out.println("10000 times taken time for alibaba fast json is " + during);
    }

    //15-17s
    public void testParseJsonWith_jackson_jar() throws Exception {
//        long start = System.currentTimeMillis();
//
//        for (int loop = 0; loop < 10000; loop++) {
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            ExaminationDTO examinationDTO = new ExaminationDTO();
//            JsonNode jExamination = objectMapper.readTree(json);
//
//            //handle examination info
//            int id = jExamination.get("id").getIntValue();
//            String title = jExamination.get("title").getTextValue();
//            examinationDTO.setId(id);
//            examinationDTO.setTitle(title);
//
//            //hanle question
//            ArrayNode jQuestions = (ArrayNode) jExamination.get("questions");
//            for (int i = 0; i < jQuestions.size(); i++) {
//                JsonNode jQuestion = jQuestions.get(i);
//                int sequence = jQuestion.get("sequence").getIntValue();
//                String questionTitle = jQuestion.get("title").getTextValue();
//                QuestionDTO questionDTO = new QuestionDTO();
//                questionDTO.setSequence(sequence);
//                questionDTO.setTitle(questionTitle);
//
//                //handle answer
//                ArrayNode jAnswers = (ArrayNode) jQuestion.get("answers");
//                for (int j = 0; j < jAnswers.size(); j++) {
//                    JsonNode jAnswer = jAnswers.get(j);
//                    String result = jAnswer.get("result").getTextValue();
//                    String[] tokens = StringUtils.delimitedListToStringArray(result, ":");
//                    AnswerDTO answerDTO = new AnswerDTO();
//                    answerDTO.setSequence(tokens[0]);
//                    answerDTO.setResult(tokens[1]);
//                    questionDTO.addAnswer(answerDTO);
//                }
//
//                examinationDTO.addQuestion(questionDTO);
//            }
//        }
//
//        long end = System.currentTimeMillis();
//        long during = end - start;
//        System.out.println("10000 times taken time for jackson is " + during);

    }

    private static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.authenticate.coship.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:activate>\n" +
            "         <!--Optional:-->\n" +
            "         <userInfo>\n" +
            "            <!--Optional:-->\n" +
            "            <mac>00144975374F</mac>\n" +
            "            <!--Optional:-->\n" +
            "            <smcID>8120010155900043</smcID>\n" +
            "            <!--Optional:-->\n" +
            "            <stbModel>?</stbModel>\n" +
            "            <!--Optional:-->\n" +
            "            <stbProvider>coship</stbProvider>\n" +
            "            <!--Optional:-->\n" +
            "            <version>3.1</version>\n" +
            "         </userInfo>\n" +
            "      </ser:activate>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


//    public static void main(String[] args) {
//        SoapObject request = new SoapObject("http://service.authenticate.coship.com/", "activate");
//
//        SoapObject o = new SoapObject();
//        o.addProperty("mac", "1");
//        o.addProperty("smcID", "1");
//        o.addProperty("stbProvider", "1");
//        o.addProperty("version", "1");
//
//        request.addProperty("userInfo", o);
//        System.out.println(1);
//    }

}
