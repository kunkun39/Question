package com.changhong.system.web.controller;

import com.changhong.system.service.FaqService;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import com.changhong.system.web.facade.dto.UserDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午10:45
 */
public class ExaminationFormController extends SimpleFormController {

    private FaqService faqService;

    public ExaminationFormController() {
        setCommandClass(ExaminationDTO.class);
        setCommandName("examination");
        setFormView("/backend/system/examinationform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterTitle = ServletRequestUtils.getStringParameter(request, "filterTitle", "");
        request.setAttribute("current", current);
        request.setAttribute("filterTitle", filterTitle);

        if (examinationId > 0) {
            List<QuestionDTO> questions = faqService.obtainQuestionsByExaminationId(examinationId);
            request.setAttribute("questions", questions);
            return faqService.obtainExaminationById(examinationId);
        }
        return new ExaminationDTO();
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        if (!StringUtils.hasText(title)) {
            errors.rejectValue("title", "examination.title.empty");
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterTitle = ServletRequestUtils.getStringParameter(request, "filterTitle", "");

        ExaminationDTO examinationDTO = (ExaminationDTO) command;
        int examinationId = faqService.changeExaminationDetails(examinationDTO);

        return new ModelAndView(new RedirectView("examinationform.html?examinationId=" + examinationId + "&current=" + current + "&filterTitle=" + filterTitle));
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
