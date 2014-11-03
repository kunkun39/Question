package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-10-22
 * Time: 下午5:01
 */
public class ObjectiveAnswer extends EntityBase {

    private String result;

    private Question question;

    public ObjectiveAnswer() {
    }

    public ObjectiveAnswer(String result) {
        this.result = result;
    }

    /**************************************************GETTER/SETTER**************************************************/

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
