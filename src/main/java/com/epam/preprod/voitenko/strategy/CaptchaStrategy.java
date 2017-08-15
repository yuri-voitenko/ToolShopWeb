package com.epam.preprod.voitenko.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaStrategy {
    void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    int getIdCaptcha(HttpServletRequest httpServletRequest);

    default String getTagContext(HttpServletRequest httpServletRequest){
        StringBuilder code = new StringBuilder();
        code.append("<div>");
        code.append("<img id=\"captcha_img\" src=\"/getCaptchaImage\" alt=\"\"/>");
        code.append("</div>");
        return code.toString();
    }
}