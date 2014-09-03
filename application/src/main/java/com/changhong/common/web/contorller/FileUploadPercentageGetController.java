package com.changhong.common.web.contorller;

import com.changhong.common.web.session.SessionKey;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 14-4-25
 * Time: 下午3:38
 */
public class FileUploadPercentageGetController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int percentage = (Integer) request.getSession().getAttribute(SessionKey.UPLAOD_FILE_PROCESS);

        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("percentage", percentage);
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();

        return null;
    }

}
