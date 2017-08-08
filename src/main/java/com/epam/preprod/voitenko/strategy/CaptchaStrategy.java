package com.epam.preprod.voitenko.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaStrategy {
    void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    int getIdCaptcha(HttpServletRequest httpServletRequest);

    default String getTagContext() {
        return null;
    }

    String getTagContext(HttpServletRequest httpServletRequest);
}