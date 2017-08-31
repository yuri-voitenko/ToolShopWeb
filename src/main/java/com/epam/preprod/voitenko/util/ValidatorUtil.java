package com.epam.preprod.voitenko.util;

import com.epam.preprod.voitenko.captcha.Captcha;
import com.epam.preprod.voitenko.entity.FilterEntity;
import com.epam.preprod.voitenko.entity.LoginEntity;
import com.epam.preprod.voitenko.entity.RegisterEntity;
import com.epam.preprod.voitenko.repository.CaptchaRepository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ADDRESS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ASC;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.CAPTCHA;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.COST;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.DESC;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.FULL_NAME;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.NAME;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PASSWORD;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PASSWORD_CHECK;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PHONE_NUMBER;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_CAPTCHA_CODE;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_CAPTCHA_LIFETIME;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_CAPTCHA_NOT_DIGITS;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_FULL_NAME;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_NOT_EMPTY_FIELD;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_PASSWORD;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_PASSWORD_CHECK;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_PHONE_NUMBER;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_CAPTCHA_CODE;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_DIGITS;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_FULL_NAME;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_NOT_DIGIT;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.REGEX_PASSWORD;

public class ValidatorUtil {
    private static Map<String, String> errorMessages = new LinkedHashMap<>();

    private ValidatorUtil() {
    }

    public static Map<String, String> validate(RegisterEntity regBean) {
        errorMessages = new LinkedHashMap<>();
        if (!validateFullName(regBean.getFullName())) {
            regBean.setFullName(EMPTY_STRING);
        }
        if (!validateAddress(regBean.getAddress())) {
            regBean.setAddress(EMPTY_STRING);
        }
        if (!validatePhoneNumber(regBean.getPhoneNumber())) {
            regBean.setPhoneNumber(EMPTY_STRING);
        }
        if (!validateEmail(regBean.getEmail())) {
            regBean.setEmail(EMPTY_STRING);
        }
        if (!validatePassword(regBean.getPassword())) {
            regBean.setPassword(EMPTY_STRING);
        }
        if (!validateRepeatedPassword(regBean.getPassword(), regBean.getRepeatedPassword())) {
            regBean.setRepeatedPassword(EMPTY_STRING);
        }
        return errorMessages;
    }

    public static Map<String, String> validate(LoginEntity loginEntity) {
        errorMessages = new LinkedHashMap<>();
        if (!validateEmail(loginEntity.getEmail())) {
            loginEntity.setEmail(EMPTY_STRING);
        }
        if (!validatePassword(loginEntity.getPassword())) {
            loginEntity.setPassword(EMPTY_STRING);
        }
        return errorMessages;
    }

    public static boolean validate(FilterEntity filterEntity) {
        if (!isNullOrEmpty(filterEntity.getLowPrice()) && !isMatchedRegex(filterEntity.getLowPrice(), REGEX_DIGITS)) {
            return false;
        }
        if (!isNullOrEmpty(filterEntity.getHighPrice()) && !isMatchedRegex(filterEntity.getHighPrice(), REGEX_DIGITS)) {
            return false;
        }
        return (NAME.equals(filterEntity.getOrderKey()) || COST.equals(filterEntity.getOrderKey())) &&
                (ASC.equals(filterEntity.getOrderDirection()) || DESC.equals(filterEntity.getOrderDirection()));
    }

    private static boolean validateFullName(String fullName) {
        return validate(fullName, REGEX_FULL_NAME, FULL_NAME, HINT_FULL_NAME);
    }

    private static boolean validateAddress(String address) {
        return validate(address, ADDRESS);
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        if (validate(phoneNumber, PHONE_NUMBER)) {
            String digits = phoneNumber.replaceAll(REGEX_NOT_DIGIT, EMPTY_STRING);
            if (validate(phoneNumber, PHONE_NUMBER) && !(digits.length() == 11 || digits.length() == 12)) {
                errorMessages.put(PHONE_NUMBER, HINT_PHONE_NUMBER);
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean validateEmail(String email) {
        return validate(email, REGEX_EMAIL, EMAIL, HINT_EMAIL);
    }

    private static boolean validatePassword(String password) {
        return validate(password, REGEX_PASSWORD, PASSWORD, HINT_PASSWORD);
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
        return validate(codeCaptcha, REGEX_CAPTCHA_CODE, CAPTCHA, HINT_CAPTCHA_NOT_DIGITS)
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

    public static boolean isNullOrEmpty(String verify) {
        return verify == null || verify.isEmpty();
    }

    public static boolean isNullOrEmpty(String[] array) {
        return array == null || array.length == 0;
    }
}