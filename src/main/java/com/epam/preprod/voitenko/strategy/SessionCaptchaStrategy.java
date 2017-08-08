package com.epam.preprod.voitenko.strategy;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCaptchaStrategy implements CaptchaStrategy {
    @Override
    public void setIdCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("idCaptcha", CaptchaRepository.addCaptcha());
    }

    @Override
    public int getIdCaptcha(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute("idCaptcha");
        return object == null ? -1 : (int) object;
    }
}