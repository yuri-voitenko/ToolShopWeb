package com.epam.preprod.voitenko.captcha;

import com.epam.preprod.voitenko.strategy.CaptchaStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CaptchaTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        CaptchaStrategy strategy = (CaptchaStrategy) pageContext.getServletContext().getAttribute("strategy");
        out.println(strategy.getTagContext(request));
    }
}