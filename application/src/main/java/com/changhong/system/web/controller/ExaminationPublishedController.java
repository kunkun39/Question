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
 * Date: 14-7-22
 * Time: 上午10:16
 */
public class ExaminationPublishedController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterTitle = ServletRequestUtils.getStringParameter(request, "filterTitle", "");
        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);

        faqService.changeExaminationStatus(examinationId);

        return new ModelAndView(new RedirectView("examinationoverview.html?examinationId=" + examinationId + "&current=" + current + "&filterTitle=" + filterTitle));
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
