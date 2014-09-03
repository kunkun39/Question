package com.changhong.common.web.resolver;

import com.changhong.common.web.session.SessionKey;
import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Jack Wang
 * Date: 14-4-25
 * Time: 下午1:33
 */
public class ApplicationProgressListener implements ProgressListener {

    private HttpServletRequest request;

    public ApplicationProgressListener() {
    }

    public ApplicationProgressListener(HttpServletRequest request) {
        this.request = request;
    }

    public void update(long byteRead, long contentLength, int itemSequence) {
        if (request != null) {
            int processRate = ((Long) ((byteRead * 100) / contentLength)).intValue();
            request.getSession().setAttribute(SessionKey.UPLAOD_FILE_PROCESS, processRate);
        }
    }
}
