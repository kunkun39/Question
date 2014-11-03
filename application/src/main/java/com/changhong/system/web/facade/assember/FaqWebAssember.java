package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.JodaUtils;
import com.changhong.system.domain.*;
import com.changhong.system.web.facade.dto.AnswerDTO;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.ObjectiveAnswerDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午10:10
 */
public class FaqWebAssember {

    public static Examination toExaminationDomain(ExaminationDTO examinationDTO) {
        Examination examination = null;
        if (examinationDTO == null) return null;

        if (examinationDTO.getId() > 0) {
            examination = (Examination) EntityLoadHolder.getUserDao().findById(examinationDTO.getId(), Examination.class);
            examination.setTitle(examinationDTO.getTitle());
            examination.setDescription(examinationDTO.getDescription());
            examination.setExaminationType(ExaminationType.valueOf(examinationDTO.getExaminationType()));

        } else {
            examination = new Examination(examinationDTO.getTitle(), examinationDTO.getDescription(), examinationDTO.getExaminationType());
        }
        return examination;
    }

    public static ExaminationDTO toExaminationDTO(Examination examination) {
        final int id = examination.getId();
        final String title = examination.getTitle();
        final String description = examination.getDescription();
        String createTime = JodaUtils.toFullString(examination.getTimestamp());
        final boolean published = examination.isPublished();
        final String examinationType = examination.getExaminationType().name();

        ExaminationDTO dto = new ExaminationDTO(id, title, description, createTime, published, examinationType);
        return dto;
    }

    public static List<ExaminationDTO> toExaminationDTOList(List<Examination> examinations) {
        List<ExaminationDTO> dtos = new ArrayList<ExaminationDTO>();
        if (examinations != null) {
            for (Examination examination : examinations) {
                dtos.add(toExaminationDTO(examination));
            }
        }
        return dtos;
    }

    public static QuestionDTO toQuestionDTO(Question question, boolean addAnswer) {
        final int id = question.getId();
        final int sequence = question.getSequence();
        final String title = question.getTitle();
        final String questionType = question.getQuestionType().name();

        QuestionDTO dto = new QuestionDTO(id, sequence, title, questionType);
        if (addAnswer) {
            List<Answer> answers = question.getAnswers();
            if (addAnswer && answers != null) {
                for (Answer answer : answers) {
                    dto.addAnswer(toAnswerDTO(answer));
                }
            }
        }
        return dto;
    }

    public static List<QuestionDTO> toQuestionDTOList(List<Question> questions) {
        List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();
        if (questions != null) {
            for (Question question : questions) {
                dtos.add(toQuestionDTO(question, true));
            }
        }
        return dtos;
    }

    public static AnswerDTO toAnswerDTO(Answer answer) {
        final int id = answer.getId();
        final String sequence = answer.getSequence();
        final String result = answer.getResult();

        AnswerDTO dto = new AnswerDTO(id, sequence, result);
        return dto;
    }

    /**
     * *************************************统计时用**************************************************
     */

    public static List<QuestionDTO> toQuestionDTOStaList(List<Question> questions) {
        List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();
        if (questions != null) {
            for (Question question : questions) {
                dtos.add(toQuestionStaDTO(question));
            }
        }
        return dtos;
    }

    public static QuestionDTO toQuestionStaDTO(Question question) {
        final int id = question.getId();
        final int sequence = question.getSequence();
        final String title = question.getTitle();
        final String questionType = question.getQuestionType().name();

        QuestionDTO dto = new QuestionDTO(id, sequence, title, questionType);
        List<Answer> answers = question.getAnswers();
        if (answers != null) {
            for (Answer answer : answers) {
                AnswerDTO answerDTO = toAnswerStaDTO(answer);
                dto.addTotalAnswerTimes(answerDTO.getAnswerTimes());
                dto.addAnswer(answerDTO);
            }
        }
        return dto;
    }

    public static AnswerDTO toAnswerStaDTO(Answer answer) {
        final int id = answer.getId();
        final String sequence = answer.getSequence();
        final String result = answer.getResult();
        final int answerTimes = answer.getAnswerTimes();

        AnswerDTO dto = new AnswerDTO(id, sequence, result, answerTimes);
        return dto;
    }

    public static ObjectiveAnswerDTO toObjectiveAnswerDTO(ObjectiveAnswer objectiveAnswer) {
        final String result = objectiveAnswer.getResult();
        final Question question = objectiveAnswer.getQuestion();
        QuestionDTO questionDTO = FaqWebAssember.toQuestionStaDTO(question);
        ObjectiveAnswerDTO dto = new ObjectiveAnswerDTO(questionDTO, result);
        return dto;
    }

    public static List<ObjectiveAnswerDTO> toObjectiveAnswerDTOList(List<ObjectiveAnswer> answers) {
        List<ObjectiveAnswerDTO> dtos = new ArrayList<ObjectiveAnswerDTO>();
        if (answers != null) {
            for (ObjectiveAnswer answer : answers) {
                dtos.add(toObjectiveAnswerDTO(answer));
            }
        }
        return dtos;
    }
}
