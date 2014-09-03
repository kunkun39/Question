package com.changhong.common.web.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * User: Jack Wang
 * Date: 14-7-22
 * Time: 下午2:50
 */
public class PercentageTag extends TagSupport {
    private double value;
    private String parameter;

    @Override
    public int doStartTag() throws JspException {
        try {
            String percentage = generateValue();
            pageContext.getRequest().setAttribute(parameter, percentage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    protected String generateValue() throws Exception {
        return generateFormatNumber(value);
    }

    protected String generateFormatNumber(double price) throws Exception {
        DecimalFormat format1 = new DecimalFormat("#,##0.0");
        String tmp = format1.format(price);
        return tmp;
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    //************ GETTERS / SETTERS *************//


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
