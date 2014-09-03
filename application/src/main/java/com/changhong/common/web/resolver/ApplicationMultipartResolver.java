package com.changhong.common.web.resolver;

import org.apache.commons.fileupload.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-25
 * Time: 下午1:32
 */
public class ApplicationMultipartResolver extends CommonsMultipartResolver {

    private HttpServletRequest request;

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        this.request = request;
        return super.isMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);

        fileUpload.setProgressListener(new ApplicationProgressListener(request));

        return fileUpload;
    }
}
