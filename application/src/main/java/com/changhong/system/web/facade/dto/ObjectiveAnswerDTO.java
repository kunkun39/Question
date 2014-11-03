package com.changhong.system.web.facade.dto;

import com.changhong.system.domain.Question;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: maren
 * Date: 14-10-27
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class ObjectiveAnswerDTO {

    private  QuestionDTO questionDTO;

    private  String result;

    public ObjectiveAnswerDTO(QuestionDTO questionDTO,  String result) {
        this.questionDTO = questionDTO;
        this.result = result;
    }

    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}
