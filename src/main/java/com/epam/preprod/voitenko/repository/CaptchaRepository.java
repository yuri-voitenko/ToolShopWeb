package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.captcha.Captcha;

import java.util.HashMap;
import java.util.Map;

public class CaptchaRepository {
    private static final Map<Integer, Captcha> captchaMap = new HashMap<>();

    private CaptchaRepository() {
    }

    public static int addCaptcha() {
        Captcha captcha = new Captcha();
        captchaMap.put(captcha.getId(), captcha);
        return captcha.getId();
    }

    public static Captcha getCaptcha(int idCaptcha) {
        return captchaMap.get(idCaptcha);
    }
}