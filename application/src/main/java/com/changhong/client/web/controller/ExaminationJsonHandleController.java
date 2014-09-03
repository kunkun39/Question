package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:37
 */
public class ExaminationJsonHandleController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = ServletRequestUtils.getStringParameter(request, "result", "");

        if (StringUtils.hasText(result)) {
            clientService.handleEndUserExaminationResult(result);
        }

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
