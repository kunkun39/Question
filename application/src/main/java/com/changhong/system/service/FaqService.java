package com.changhong.system.service;


import com.changhong.system.domain.AppDescription;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.ObjectiveAnswerDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:58
 */
public interface FaqService {

    List<ExaminationDTO> obtainAllExaminations();

    List<ExaminationDTO> obtainExaminations(String filterTitle, int startPosition, int pageSize);

    int obtainExaminationSize(String title);

    ExaminationDTO obtainExaminationById(int examinationId);

    int changeExaminationDetails(ExaminationDTO dto);

    void changeExaminationStatus(int examinationId);

    List<QuestionDTO> obtainQuestionsByExaminationId(int examinationId);

    QuestionDTO obtainQuestionById(int questionId);

    void changeQuestionDetails(int examinationId, String questionType, int questionId, String title, String aSelection, String bSelection, String cSelection, String dSelection,String eSelection,String fSelection,String gSelection);

    void deleteQuestion(int examinationId, int questionId);

    List<QuestionDTO> obtainQuestionsByExaminationIdForSta(int examinationId);

    int obtainClientResultSize(int examinationId);

    void changeQuestionSequence(int examinationId, int sequence, String sorting);

    AppDescription obtainAppDescription();

    void changeAppDescriptionDetails(AppDescription appDescription);

    List<ObjectiveAnswerDTO> obtainObjectiveAnswers(int questionId, int startPosition, int pageSize);

    int obtainObjectiveAnswersSize(int questionId);

}
