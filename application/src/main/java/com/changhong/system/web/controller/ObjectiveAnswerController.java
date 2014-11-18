package com.changhong.system.web.controller;

import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.ObjectiveAnswer;
import com.changhong.system.domain.Question;
import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.ObjectiveAnswerDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import com.changhong.system.web.paging.ObjectiveAnswerOverViewPaging;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: maren
 * Date: 14-10-25
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class ObjectiveAnswerController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new Hashtable<String, Object>();
        int questionId = ServletRequestUtils.getIntParameter(request, "questionId", -1);
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("questionId", questionId);
        request.setAttribute("current", current);

        ObjectiveAnswerOverViewPaging paging = new ObjectiveAnswerOverViewPaging(faqService);
        constructPaging(paging, current, questionId);
        List<ObjectiveAnswerDTO> objectiveAnswerList = paging.getItems();
        QuestionDTO questionDTO = faqService.obtainQuestionById(questionId);

        model.put("question", questionDTO);
        model.put("paging", paging);
        model.put("objectiveAnswerList", objectiveAnswerList);
        return new ModelAndView("backend/system/objectiveansweroverview", model);
    }

    public void constructPaging(ObjectiveAnswerOverViewPaging paging, int current, int questionId) {
        paging.setCurrentPageNumber(current);
        paging.setQuestionId(questionId);
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;

    }

}
