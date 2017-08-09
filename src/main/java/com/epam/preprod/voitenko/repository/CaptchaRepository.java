package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.captcha.Captcha;
import com.epam.preprod.voitenko.validate.ValidatorUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CaptchaRepository {
    private static final Map<Integer, Captcha> captchaMap = new HashMap<>();

    private CaptchaRepository() {
    }

    public static int addCaptcha() {
        synchronized (captchaMap) {
            Captcha captcha = new Captcha();
            captchaMap.put(captcha.getId(), captcha);
            return captcha.getId();
        }
    }

    public static void deleteOutdatedCaptcha(long timeout) {
        synchronized (captchaMap) {
            captchaMap.values().removeIf(objCaptcha -> !ValidatorUtil.isActualCaptcha(objCaptcha, timeout));
        }
    }

    public static Captcha getCaptcha(int idCaptcha) {
        synchronized (captchaMap) {
            return captchaMap.get(idCaptcha);
        }
    }
}