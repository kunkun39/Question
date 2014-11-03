package com.changhong.system.web.paging;
import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.ObjectiveAnswerDTO;


import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: maren
 * Date: 14-10-27
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class ObjectiveAnswerOverViewPaging extends  AbstractPaging<ObjectiveAnswerDTO>{

    private FaqService faqService;

    private  int questionId;

    public ObjectiveAnswerOverViewPaging(FaqService faqService) {
        this.faqService = faqService;
    }

    @Override
    public List<ObjectiveAnswerDTO> getItems() {
        return faqService.obtainObjectiveAnswers(questionId,getStartPosition(),getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = faqService.obtainObjectiveAnswersSize(questionId);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&questionId=" + getQuestionId();
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
