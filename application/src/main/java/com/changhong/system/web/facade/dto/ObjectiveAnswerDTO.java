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



    private  String result;

    public ObjectiveAnswerDTO(String result) {

        this.result = result;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}
