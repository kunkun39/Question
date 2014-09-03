package com.changhong.client.web.controller;

import com.changhong.client.service.ClientService;
import com.google.gson.JsonObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-8-19
 * Time: 下午1:31
 */
public class ExaminationListJsonGetController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonObject o = clientService.obtainExaminationList();

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(o.toString());

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
