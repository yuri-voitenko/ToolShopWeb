package com.epam.preprod.voitenko.strategy;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieCaptchaStrategy implements CaptchaStrategy {
    @Override
    public void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String nameCookie = "idCaptcha";
        String valueCookie = String.valueOf(CaptchaRepository.addCaptcha());
        Cookie cookie = new Cookie(nameCookie, valueCookie);
        httpServletResponse.addCookie(cookie);
    }

    @Override
    public int getIdCaptcha(HttpServletRequest httpServletRequest) {
        String strIdCaptcha = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("idCaptcha")) {
                strIdCaptcha = cookie.getValue();
            }
        }
        return strIdCaptcha == null ? -1 : Integer.parseInt(strIdCaptcha);
    }

    @Override
    public String getTagContext(HttpServletRequest httpServletRequest) {
        StringBuilder code = new StringBuilder();
        code.append("<div>");
        code.append("<img id=\"captcha_img\" src=\"/getCaptchaImage\" alt=\"\"/>");
        code.append(" </div>");
        return code.toString();
    }
}