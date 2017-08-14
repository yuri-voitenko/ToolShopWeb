package com.epam.preprod.voitenko.strategy;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.ID_CAPTCHA;

public class SessionCaptchaStrategy implements ICaptchaStrategy {
    @Override
    public void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(ID_CAPTCHA, CaptchaRepository.addCaptcha());
    }

    @Override
    public int getIdCaptcha(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute(ID_CAPTCHA);
        return object == null ? -1 : (int) object;
    }
}