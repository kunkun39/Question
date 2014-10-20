package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:51
 */
public class Examination extends EntityBase {

    private String title;

    private String description;

    private boolean published;

    private ExaminationType examinationType;

    private List<Question> questions;

    public Examination() {
    }

    public Examination(String title, String description, String examinationType) {
        this.title = title;
        this.description = description;
        this.published = false;
        this.examinationType = ExaminationType.valueOf(examinationType);
    }

    public int getQuestionSize() {
        if (questions == null) {
            return 0;
        }
        return questions.size();
    }

    public void changeStatus() {
        this.published = this.published ? false : true;
    }

    public void handleClientResultStatistic(String result) {
        Map<Integer, String> sequenceMapping = new HashMap<Integer, String>();
        String[] answers = StringUtils.delimitedListToStringArray(result, "|");
        for (int i = 0; i < answers.length; i++) {
            String answer = answers[i];
            sequenceMapping.put(i + 1, answer);
        }

        for (Question question : questions) {
            String answer = sequenceMapping.get(question.getSequence());
            question.handleClientResultStatistic(answer);
        }
    }

    /***************************************************GETTER/SETTER*************************************************/

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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
