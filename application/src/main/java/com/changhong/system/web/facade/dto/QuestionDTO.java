package com.changhong.system.web.facade.dto;

import com.changhong.system.domain.QuestionType;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午1:24
 */
public class QuestionDTO {

    private int id;

    private int sequence;

    private String title;

    private String questionType;

    private List<AnswerDTO> answers;

    /**
     * 统计时需要使用的属性
     */
    private int totalAnswerTimes = 0;

    public QuestionDTO() {
        this.id = -1;
        this.questionType = QuestionType.SINGLE.name();
        AnswerDTO answerA = new AnswerDTO(-1, "A", "");
        AnswerDTO answerB = new AnswerDTO(-1, "B", "");
        AnswerDTO answerC = new AnswerDTO(-1, "C", "");
        AnswerDTO answerD = new AnswerDTO(-1, "D", "");
        AnswerDTO answerE = new AnswerDTO(-1, "E", "");
        AnswerDTO answerF = new AnswerDTO(-1, "F", "");
        addAnswer(answerA);
        addAnswer(answerB);
        addAnswer(answerC);
        addAnswer(answerD);
        addAnswer(answerE);
        addAnswer(answerF);
    }

    public QuestionDTO(int id, int sequence, String title, String questionType) {
        this.id = id;
        this.sequence = sequence;
        this.title = title;
        this.questionType = questionType;
    }

    public void addAnswer(AnswerDTO answer) {
        if (answers == null) {
            answers = new ArrayList<AnswerDTO>();
        }
        answers.add(answer);
    }

    public String getAnswerDescription() {
        StringBuilder buffer = new StringBuilder();
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (StringUtils.hasText(answer.getResult())) {
                    buffer.append(answer.getAnswer() + "&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }
        }
        return buffer.toString();
    }

    public String getSelectionA() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("A")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }

    public String getSelectionB() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("B")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }

    public String getSelectionC() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("C")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }

    public String getSelectionD() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("D")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }
      public String getSelectionE() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("E")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }
      public String getSelectionF() {
        if (answers != null) {
            for (AnswerDTO answer : answers) {
                if (answer.getSequence().equals("F")) {
                    return answer.getResult();
                }
            }
        }
        return "";
    }

    public void addTotalAnswerTimes(int answerTimes) {
        this.totalAnswerTimes = this.totalAnswerTimes + answerTimes;
    }

    /**
     * *****************************************************GETTER/SETTER*******************************************
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public int getTotalAnswerTimes() {
        return totalAnswerTimes;
    }

    public void setTotalAnswerTimes(int totalAnswerTimes) {
        this.totalAnswerTimes = totalAnswerTimes;
    }
}
