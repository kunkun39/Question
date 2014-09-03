package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午1:25
 */
public class AnswerDTO {

    private int id;

    private String sequence;

    private String result;

    /**
     * 统计时需要使用的属性
     */
    private int answerTimes;

    public AnswerDTO() {
    }

    public AnswerDTO(int id, String sequence, String result) {
        this.id = id;
        this.sequence = sequence;
        this.result = result;
    }

    public AnswerDTO(int id, String sequence, String result, int answerTimes) {
        this.id = id;
        this.sequence = sequence;
        this.result = result;
        this.answerTimes = answerTimes;
    }

    public String getAnswer() {
        return sequence + ":" + result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
