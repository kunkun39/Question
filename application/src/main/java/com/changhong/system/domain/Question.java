package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.web.facade.dto.AnswerDTO;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:51
 */
public class Question extends EntityBase {

    /**
     * such as 1,2,3,4...
     */
    private int sequence;

    private String title;

    private QuestionType questionType;

    private Examination examination;

    private List<Answer> answers;

    private List<ObjectiveAnswer> objectiveAnswers;

    public Question() {
    }

    public Question(int sequence, String title, QuestionType questionType, Examination examination) {
        this.sequence = sequence;
        this.title = title;
        this.questionType = questionType;
        this.examination = examination;

        Answer answerA = new Answer("A", "");
        Answer answerB = new Answer("B", "");
        Answer answerC = new Answer("C", "");
        Answer answerD = new Answer("D", "");
        Answer answerE = new Answer("E", "");
        Answer answerF = new Answer("F", "");
        Answer answerG = new Answer("G", "");
        addAnswer(answerA);
        addAnswer(answerB);
        addAnswer(answerC);
        addAnswer(answerD);
        addAnswer(answerE);
        addAnswer(answerF);
        addAnswer(answerG);
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<Answer>();
        }
        answers.add(answer);
    }

    public void addObjectiveAnswer(ObjectiveAnswer answer) {
        if (objectiveAnswers == null) {
            objectiveAnswers = new ArrayList<ObjectiveAnswer>();
        }
        objectiveAnswers.add(answer);
    }

    public void updateSelection(String sequence, String result) {
        if (answers != null) {
            for (Answer answer : answers) {
                if (answer.getSequence().equals(sequence)) {
                    answer.setResult(result);
                }
            }
        }
    }

    public void adjustSequence() {
        int i = this.sequence = this.sequence - 1;
    }

    public void handleClientResultStatistic(String answer) {
        if (questionType.equals(QuestionType.OBJECTIVE)) {
            if (StringUtils.hasText(answer)) {
                addObjectiveAnswer(new ObjectiveAnswer(answer));
            }
        } else {
            Set<String> selectionMapping = new HashSet<String>();
            String[] selections = StringUtils.delimitedListToStringArray(answer, ",");
            for (int i = 0; i < selections.length; i++) {
                String selection = selections[i];
                selectionMapping.add(selection);
            }

            for (Answer loop : answers) {
                if (selectionMapping.contains(loop.getSequence())) {
                    loop.handleClientResultStatistic();
                }
            }
        }
    }

    /******************************************GETTER/SETTER***********************************************************/

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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<ObjectiveAnswer> getObjectiveAnswers() {
        return objectiveAnswers;
    }

    public void setObjectiveAnswers(List<ObjectiveAnswer> objectiveAnswers) {
        this.objectiveAnswers = objectiveAnswers;
    }
}
