package com.changhong.system.web.paging;

import com.changhong.system.service.FaqService;
import com.changhong.system.web.facade.dto.ExaminationDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午10:14
 */
public class ExaminationOverviewPaging extends AbstractPaging<ExaminationDTO> {

    private FaqService faqService;

    private String filterTitle;

    public ExaminationOverviewPaging(FaqService faqService) {
        this.faqService = faqService;
    }

    public List<ExaminationDTO> getItems() {
        return faqService.obtainExaminations(filterTitle, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = faqService.obtainExaminationSize(filterTitle);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&filterTitle=" + getFilterTitle();
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }
}