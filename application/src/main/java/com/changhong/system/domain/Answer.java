package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:51
 */
public class Answer extends EntityBase {

    /**
     * Such as A,B,C,D
     */
    private String sequence;

    private String result;

    private int answerTimes;

    public Answer() {
    }

    public Answer(String sequence, String result) {
        this.sequence = sequence;
        this.result = result;
        this.answerTimes = 0;
    }

    public void handleClientResultStatistic() {
        this.answerTimes = this.answerTimes + 1;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getAnswerTimes() {
        return answerTimes;
    }

    public void setAnswerTimes(int answerTimes) {
        this.answerTimes = answerTimes;
    }
}
