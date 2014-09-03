package com.changhong.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.changhong.domain.Answer;
import com.changhong.domain.Examination;
import com.changhong.domain.Question;

import java.util.*;

/**
 * Created by Jack Wang
 */
public class PreferenceService {

    private Context context;

    public PreferenceService(Context context) {
        this.context = context;
    }

    public void saveAnswers(String examinationId, String questionId, Set<String> answers) {
        SharedPreferences preferences = context.getSharedPreferences("changhong_wenjuan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(examinationId + "_" + questionId, answers);
        editor.commit();
    }

    public Set<String> getAnswers(String examinationId, String questionId) {
        SharedPreferences preferences = context.getSharedPreferences("changhong_wenjuan", Context.MODE_PRIVATE);

        Set<String> answers = preferences.getStringSet(examinationId + "_" + questionId, new HashSet<String>());
        return answers;
    }

    public String getAllAnswers(Examination examination) {
        boolean answerAll = true;
        int examinationId = examination.getId();
        List<Question> questions = examination.getQuestions();

        SharedPreferences preferences = context.getSharedPreferences("changhong_wenjuan", Context.MODE_PRIVATE);
        StringBuffer buffer = new StringBuffer();

        for (Question question : questions) {
            Set<String> answers = preferences.getStringSet(examinationId + "_" + (question.getSequence() - 1), new HashSet<String>());
            if (answers == null || answers.isEmpty()) {
                answerAll = false;
            }
            buffer.append(Answer.getAnswer(answers) + "|");
        }

        if (!answerAll) {
            return "";
        }
        return buffer.toString();
    }

    public void cleanAllAnswers(Examination examination) {
        int examinationId = examination.getId();
        List<Question> questions = examination.getQuestions();

        SharedPreferences preferences = context.getSharedPreferences("changhong_wenjuan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Question question : questions) {
            editor.remove(examinationId + "_" + (question.getSequence() - 1));
        }
        editor.commit();
    }

    public String getAllAnswersShow(Examination examination) {
        int examinationId = examination.getId();
        List<Question> questions = examination.getQuestions();

        SharedPreferences preferences = context.getSharedPreferences("changhong_wenjuan", Context.MODE_PRIVATE);
        StringBuffer buffer = new StringBuffer();

        for (Question question : questions) {
            Set<String> answers = preferences.getStringSet(examinationId + "_" + (question.getSequence() - 1), new HashSet<String>());
            buffer.append(question.getAnswerShow(answers));
        }

        return buffer.toString();
    }
}
