package com.changhong.system.web.facade.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午10:09
 */
public class ExaminationDTO {

    private int id;

    private String title;

    private String description;

    private String createTime;

    private boolean published;

    private List<QuestionDTO> questions;

    public ExaminationDTO() {
    }

    public ExaminationDTO(int id, String title, String description, String createTime, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createTime = createTime;
        this.published = published;
    }

    public void addQuestion(QuestionDTO question) {
        if (questions == null) {
            questions = new ArrayList<QuestionDTO>();
        }
        questions.add(question);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
