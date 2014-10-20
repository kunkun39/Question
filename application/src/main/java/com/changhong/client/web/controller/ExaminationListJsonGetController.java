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
 * Date: 14-8-19
 * Time: 下午1:31
 */
public class ExaminationListJsonGetController extends AbstractController {

    private ClientService clientService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examinationType = ServletRequestUtils.getStringParameter(request, "type", "TVBOX_ONLY");

        JsonObject o = clientService.obtainExaminationList(examinationType);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(o.toString());

        return null;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
