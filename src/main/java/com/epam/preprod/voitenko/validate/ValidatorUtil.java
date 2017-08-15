package com.epam.preprod.voitenko.validate;

import com.epam.preprod.voitenko.bean.LoginBean;
import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.captcha.Captcha;
import com.epam.preprod.voitenko.repository.CaptchaRepository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.*;

public class ValidatorUtil {
    private static Map<String, String> errorMessages = new LinkedHashMap<>();

    private ValidatorUtil() {
    }

    public static Map<String, String> validate(RegisterBean regBean) {
        errorMessages = new LinkedHashMap<>();
        if (!validateFullName(regBean.getFullName())) {
            regBean.setFullName("");
        }
        if (!validateAddress(regBean.getAddress())) {
            regBean.setAddress("");
        }
        if (!validatePhoneNumber(regBean.getPhoneNumber())) {
            regBean.setPhoneNumber("");
        }
        if (!validateEmail(regBean.getEmail())) {
            regBean.setEmail("");
        }
        if (!validatePassword(regBean.getPassword())) {
            regBean.setPassword("");
        }
        if (!validateRepeatedPassword(regBean.getPassword(), regBean.getRepeatedPassword())) {
            regBean.setRepeatedPassword("");
        }
        return errorMessages;
    }

    public static Map<String, String> validate(LoginBean loginBean) {
        errorMessages = new LinkedHashMap<>();
        if (!validateEmail(loginBean.getEmail())) {
            loginBean.setEmail("");
        }
        if (!validatePassword(loginBean.getPassword())) {
            loginBean.setPassword("");
        }
        return errorMessages;
    }

    private static boolean validateFullName(String fullName) {
        return validate(fullName, "([a-zA-Z]{2,}\\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)",
                FULL_NAME, HINT_FULL_NAME);
    }

    private static boolean validateAddress(String address) {
        return validate(address, ADDRESS);
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        if (validate(phoneNumber, PHONE_NUMBER)) {
            String digits = phoneNumber.replaceAll("\\D", "");
            if (validate(phoneNumber, PHONE_NUMBER) && !(digits.length() == 11 || digits.length() == 12)) {
                errorMessages.put(PHONE_NUMBER, HINT_PHONE_NUMBER);
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean validateEmail(String email) {
        return validate(email, "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))",
                EMAIL, HINT_EMAIL);
    }

    private static boolean validatePassword(String password) {
        return validate(password, "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}",
                PASSWORD, HINT_PASSWORD);
    }

    private static boolean validateRepeatedPassword(String password, String repeatedPassword) {
        if (validate(repeatedPassword, PASSWORD_CHECK)) {
            if (!password.equals(repeatedPassword)) {
                errorMessages.put(PASSWORD_CHECK, HINT_PASSWORD_CHECK);
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validateCaptcha(int idCaptcha, String codeCaptcha, long timeout) {
        Captcha objCaptcha = CaptchaRepository.getCaptcha(idCaptcha);
        if (objCaptcha == null || !isActualCaptcha(objCaptcha, timeout)) {
            errorMessages.put(CAPTCHA, HINT_CAPTCHA_LIFETIME);
            return false;
        }
        if (!isCorrectCodeCaptcha(objCaptcha, codeCaptcha)) {
            errorMessages.put(CAPTCHA, HINT_CAPTCHA_CODE);
            return false;
        }
        return true;
    }

    public static boolean isActualCaptcha(Captcha objCaptcha, long timeout) {
        if (objCaptcha == null || timeout < 0) {
            throw new IllegalArgumentException();
        }
        LocalDateTime creationDate = objCaptcha.getCreationDate();
        creationDate = creationDate.plusSeconds(timeout);
        LocalDateTime now = LocalDateTime.now();

        return !now.isAfter(creationDate);
    }

    private static boolean isCorrectCodeCaptcha(Captcha objCaptcha, String codeCaptcha) {
        if (objCaptcha == null) {
            throw new IllegalArgumentException();
        }
        final String secretCode = String.valueOf(objCaptcha.getSecretCode());
        return validate(codeCaptcha, "\\d{10}", CAPTCHA, HINT_CAPTCHA_NOT_DIGITS)
                && secretCode.equals(codeCaptcha);
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean validate(String verify, String regexPattern, String key, String hintMessage) {
        if (validate(verify, key)) {
            if (!isMatchedRegex(verify, regexPattern)) {
                errorMessages.put(key, hintMessage);
                return false;
            }
            return true;
        }
        return false;
    }


    private static boolean validate(String verify, String key) {
        if (isNullOrEmpty(verify)) {
            errorMessages.put(key, HINT_NOT_EMPTY_FIELD);
            return false;
        }
        return true;
    }

    private static boolean isMatchedRegex(String verify, String regexPattern) {
        return verify != null && verify.matches(regexPattern);
    }

    private static boolean isNullOrEmpty(String verify) {
        return verify == null || verify.isEmpty();
    }
}