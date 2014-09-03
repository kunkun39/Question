package com.changhong.system.web.controller;

import com.changhong.system.domain.AppDescription;
import com.changhong.system.service.FaqService;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-8-19
 * Time: 上午11:21
 */
public class AppDescriptionFormController extends SimpleFormController {

    private FaqService faqService;

    public AppDescriptionFormController() {
        setCommandClass(AppDescription.class);
        setCommandName("appDescription");
        setFormView("/backend/system/appdescriptionform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return faqService.obtainAppDescription();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        AppDescription appDescription = (AppDescription) command;
        faqService.changeAppDescriptionDetails(appDescription);

        return new ModelAndView(new RedirectView("appdescription.html"));
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
