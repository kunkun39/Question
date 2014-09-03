package com.changhong.system.web.controller;

import com.changhong.common.utils.JodaUtils;
import com.changhong.system.service.FaqService;
import com.changhong.system.web.excel.ExaminationStatisticExcelView;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import org.joda.time.LocalDate;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-28
 * Time: 上午9:43
 */
public class ExaminationStatisticDownloadController extends AbstractController {

    private FaqService faqService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String currentDay = JodaUtils.toString(new LocalDate());
        String filename = new String(("川网问卷调查统计" + currentDay + ".xls").getBytes("GBK"), "ISO_8859_1");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);
        ExaminationDTO examination = faqService.obtainExaminationById(examinationId);
        List<QuestionDTO> questions = faqService.obtainQuestionsByExaminationIdForSta(examinationId);

        ExaminationStatisticExcelView view = new ExaminationStatisticExcelView(examination, questions);
        view.render(null, request, response);

        return null;
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
