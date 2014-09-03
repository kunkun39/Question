package com.changhong.view;

import com.changhong.domain.Answer;
import com.changhong.domain.Examination;
import com.changhong.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private Examination examination;

    /**
     * 对应我们的问题
     */
	private VoteSubmitItem item;

    /**
     * 对应我们问题的答案
     */
    private ArrayList<VoteSubmitItem> items;

    public DataLoader(Examination examination) {
        this.examination = examination;
    }

    public ArrayList<VoteSubmitItem> getData() {
        List<Question> questions = examination.getQuestions();
        items = new ArrayList<VoteSubmitItem>();

        for (int i = 0; i < examination.getQuestions().size(); i++) {
            Question question = questions.get(i);
            item = new VoteSubmitItem();
            item.itemId = i;
            item.questiontype = question.getQuestionType();

            item.voteQuestion = "问题" + question.getSequence() + ": " + question.getTitle();
            List<Answer> answers = question.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                item.voteAnswers.add(answers.get(j).getSequence() + " : " + answers.get(j).getResult());
            }
            items.add(item);
        }
        return items;
	}

}
