package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import com.google.gson.JsonObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:36
 */
public class ExaminationJsonGetController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int examinationId = ServletRequestUtils.getIntParameter(request, "examinationId", -1);
        JsonObject o = clientService.obtainExaminationById(examinationId);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(o.toString());

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
