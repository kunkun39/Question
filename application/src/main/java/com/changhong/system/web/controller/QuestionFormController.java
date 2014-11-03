package com.changhong.system.web.controller;

import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.QuestionDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午2:30
 */
public class QuestionFormController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int current  = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName  = ServletRequestUtils.getStringParameter(request, "filterName", "");

        String method = ServletRequestUtils.getStringParameter(request, "method");
        int questionId = ServletRequestUtils.getIntParameter(request, "questionId", -1);
        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);

        if ("save".equals(method)) {
            String title = ServletRequestUtils.getStringParameter(request, "questionTitle", "");
            String questionType = ServletRequestUtils.getStringParameter(request, "questionType", "SINGLE");
            String ASelection = ServletRequestUtils.getStringParameter(request, "ASelection", "");
            String BSelection = ServletRequestUtils.getStringParameter(request, "BSelection", "");
            String CSelection = ServletRequestUtils.getStringParameter(request, "CSelection", "");
            String DSelection = ServletRequestUtils.getStringParameter(request, "DSelection", "");

            faqService.changeQuestionDetails(examinationId, questionType, questionId, title, ASelection, BSelection, CSelection, DSelection);

            return new ModelAndView(new RedirectView("examinationform.html?current=" + current + "&filterTitle=" + filterName + "&examinationId=" + examinationId));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            QuestionDTO question = new QuestionDTO();
            if (questionId > 0) {
                question = faqService.obtainQuestionById(questionId);
            }
            model.put("question", question);
            model.put("current", current);
            model.put("filterName", filterName);
            model.put("examinationId", examinationId);
            return new ModelAndView("backend/system/questionform", model);
        }
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
