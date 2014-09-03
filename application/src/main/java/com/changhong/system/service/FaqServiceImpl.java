package com.changhong.system.service;

import com.changhong.client.service.ClientService;
import com.changhong.system.domain.AppDescription;
import com.changhong.system.domain.Examination;
import com.changhong.system.domain.Question;
import com.changhong.system.domain.QuestionType;
import com.changhong.system.repository.FaqDao;
import com.changhong.system.web.facade.assember.FaqWebAssember;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:58
 */
@Service("faqService")
public class FaqServiceImpl implements FaqService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FaqDao faqDao;

    public List<ExaminationDTO> obtainAllExaminations() {
        List<Examination> examinations = faqDao.loadAllExamination();
        return FaqWebAssember.toExaminationDTOList(examinations);
    }

    public int obtainExaminationSize(String filterTitle) {
        return faqDao.loadExaminationSize(filterTitle);
    }

    public List<ExaminationDTO> obtainExaminations(String filterTitle, int startPosition, int pageSize) {
        List<Examination> examinations = faqDao.loadExaminations(filterTitle, startPosition, pageSize);
        return FaqWebAssember.toExaminationDTOList(examinations);
    }

    public ExaminationDTO obtainExaminationById(int examinationId) {
        Examination examination = (Examination) faqDao.findById(examinationId, Examination.class);
        return FaqWebAssember.toExaminationDTO(examination);
    }

    public int changeExaminationDetails(ExaminationDTO dto) {

        Examination examination = FaqWebAssember.toExaminationDomain(dto);
        faqDao.persist(examination);
        int examinationId = examination.getId();

        if (examinationId > 0) {
            clientService.cleanExaminationCache(examinationId);
        }
        return examinationId;
    }

    public void changeExaminationStatus(int examinationId) {
        Examination examination = (Examination) faqDao.findById(examinationId, Examination.class);
        examination.changeStatus();

        clientService.cleanExaminationCache(examinationId);
    }

    public List<QuestionDTO> obtainQuestionsByExaminationId(int examinationId) {
        List<Question> questions = faqDao.loadQuestionsByExaminationId(examinationId, -1);
        return FaqWebAssember.toQuestionDTOList(questions);
    }

    public QuestionDTO obtainQuestionById(int questionId) {
        Question question = (Question) faqDao.findById(questionId, Question.class);
        return FaqWebAssember.toQuestionDTO(question, true);
    }

    public void changeQuestionDetails(int examinationId, String questionType, int questionId, String title, String aSelection, String bSelection, String cSelection, String dSelection) {
        Question question = null;
        if(questionId > 0) {
            question = (Question) faqDao.findById(questionId, Question.class);
            question.setTitle(title);
            question.setQuestionType(QuestionType.valueOf(questionType));
        } else {
            Examination examination = (Examination) faqDao.findById(examinationId, Examination.class);
            int sequence = examination.getQuestionSize();
            question = new Question(sequence + 1, title, QuestionType.valueOf(questionType), examination);
        }

        question.updateSelection("A", aSelection);
        question.updateSelection("B", bSelection);
        question.updateSelection("C", cSelection);
        question.updateSelection("D", dSelection);

        faqDao.saveOrUpdate(question);
        clientService.cleanExaminationCache(examinationId);
    }

    public void deleteQuestion(int examinationId, int questionId) {
        Question question = (Question) faqDao.findById(questionId, Question.class);
        int deleteSequence = question.getSequence();
        faqDao.delete(question);

        List<Question> leftQuestions = faqDao.loadQuestionsByExaminationId(examinationId, deleteSequence);
        if (leftQuestions != null) {
            for (Question leftQuestion : leftQuestions) {
                leftQuestion.adjustSequence();
            }
        }

        clientService.cleanExaminationCache(examinationId);
    }

    public List<QuestionDTO> obtainQuestionsByExaminationIdForSta(int examinationId) {
        List<Question> questions = faqDao.loadQuestionsByExaminationId(examinationId, -1);
        return FaqWebAssember.toQuestionDTOStaList(questions);
    }

    public void changeQuestionSequence(int examinationId, int sequence, String sorting) {
        Question wangToChangQuestion = faqDao.loadQuestionBySequence(examinationId, sequence);
        Question needToChangQuestion = null;
        int changeTo = -1;

        if ("up".equals(sorting)) {
            changeTo = sequence - 1;
            needToChangQuestion = faqDao.loadQuestionBySequence(examinationId, changeTo);
            wangToChangQuestion.setSequence(changeTo);
        } else {
            changeTo = sequence + 1;
            needToChangQuestion = faqDao.loadQuestionBySequence(examinationId, changeTo);
        }
        wangToChangQuestion.setSequence(changeTo);
        needToChangQuestion.setSequence(sequence);
    }

    public AppDescription obtainAppDescription() {
        return (AppDescription) faqDao.findById(1, AppDescription.class);
    }

    public void changeAppDescriptionDetails(AppDescription appDescription) {
        faqDao.saveOrUpdate(appDescription);
        clientService.cleanExaminationCache(-1);
    }
}
