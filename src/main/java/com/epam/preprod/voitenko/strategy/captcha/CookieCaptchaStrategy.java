package com.epam.preprod.voitenko.strategy.captcha;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.ID_CAPTCHA;

public class CookieCaptchaStrategy implements CaptchaStrategy {
    @Override
    public void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String valueCookie = String.valueOf(CaptchaRepository.addCaptcha());
        Cookie cookie = new Cookie(ID_CAPTCHA, valueCookie);
        cookie.setSecure(true);
        httpServletResponse.addCookie(cookie);
    }

    @Override
    public int getIdCaptcha(HttpServletRequest httpServletRequest) {
        String strIdCaptcha = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals(ID_CAPTCHA)) {
                strIdCaptcha = cookie.getValue();
            }
        }
        return strIdCaptcha == null ? -1 : Integer.parseInt(strIdCaptcha);
    }
}