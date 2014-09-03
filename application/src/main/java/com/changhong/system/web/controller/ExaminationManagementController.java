package com.changhong.system.web.controller;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.paging.ExaminationOverviewPaging;
import org.springframework.util.StringUtils;
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
 * Date: 14-7-21
 * Time: 上午9:57
 */
public class ExaminationManagementController extends AbstractController {

    private FaqService faqService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute(SessionKey.BROSWER_LOCATION, "EXAMINATION");

        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterTitle = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filterTitle", ""));
        request.setAttribute("current", current);
        request.setAttribute("filterTitle", filterTitle);

        ExaminationOverviewPaging paging = new ExaminationOverviewPaging(faqService);
        constructPaging(paging, current, filterTitle);
        List<ExaminationDTO> examinations = paging.getItems();
        model.put("examinations", examinations);
        model.put("paging", paging);
        model.put("currentUser", SecurityUtils.currentUser());

        return new ModelAndView("backend/system/examinationoverview", model);
    }

    private void constructPaging(ExaminationOverviewPaging paging, int current, String filterTitle) {
        paging.setCurrentPageNumber(current);
        paging.setFilterTitle(filterTitle);
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }
}
