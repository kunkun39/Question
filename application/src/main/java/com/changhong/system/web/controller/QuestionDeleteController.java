package com.changhong.system.web.controller;

import com.changhong.system.service.FaqService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午4:37
 */
public class QuestionDeleteController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int current  = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName  = ServletRequestUtils.getStringParameter(request, "filterName", "");

        int questionId = ServletRequestUtils.getIntParameter(request, "questionId", -1);
        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);

        faqService.deleteQuestion(examinationId, questionId);

        return new ModelAndView(new RedirectView("examinationform.html?current=" + current + "&filterTitle=" + filterName + "&examinationId=" + examinationId));
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
