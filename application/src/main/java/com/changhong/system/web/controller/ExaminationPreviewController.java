package com.changhong.system.web.controller;

import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-22
 * Time: 上午9:21
 */
public class ExaminationPreviewController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);
        ExaminationDTO examination = faqService.obtainExaminationById(examinationId);
        List<QuestionDTO> questions = faqService.obtainQuestionsByExaminationId(examinationId);

        model.put("examination", examination);
        model.put("questions", questions);

        return new ModelAndView("backend/system/examinationpreview", model);
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
