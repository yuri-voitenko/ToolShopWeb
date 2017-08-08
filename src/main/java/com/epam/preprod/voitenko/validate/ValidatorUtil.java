package com.epam.preprod.voitenko.validate;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.captcha.Captcha;
import com.epam.preprod.voitenko.repository.CaptchaRepository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

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

    private static boolean validateFullName(String fullName) {
        return validate(fullName, "([a-zA-Z]{2,}\\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)",
                "fullName", "Hint: 'FirstName LastName'");
    }

    private static boolean validateAddress(String address) {
        return validate(address, "address");
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        final String key = "phoneNumber";
        String digits = phoneNumber.replaceAll("\\D", "");
        if (validate(phoneNumber, key) && !(digits.length() == 11 || digits.length() == 12)) {
            errorMessages.put(key, "Hint: Use international format!");
            return false;
        }
        return true;
    }

    private static boolean validateEmail(String email) {
        return validate(email, "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))",
                "email", "Hint: 'FirstName LastName'");
    }

    private static boolean validatePassword(String password) {
        String hintMessage = "Password requirements:" +
                "<br>\t* At least one upper case English letter" +
                "<br>\t* At least one lower case English letter" +
                "<br>\t* At least one digit" +
                "<br>\t* At least one special character" +
                "<br>\t* Minimum eight in length";
        return validate(password, "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}",
                "password", hintMessage);
    }

    private static boolean validateRepeatedPassword(String password, String repeatedPassword) {
        final String key = "repeatedPassword";
        if (validate(repeatedPassword, key) && !password.equals(repeatedPassword)) {
            errorMessages.put(key, "Is not equal to the password");
            return false;
        }
        return true;
    }

    public static boolean validateCaptcha(String codeCaptcha, int idCaptcha) {
        if (validate(codeCaptcha, "\\d{10}",
                "codeCaptcha", "Hint: Must be only 10 digits.")) {
            Captcha objCaptcha = CaptchaRepository.getCaptcha(idCaptcha);
            if (!codeCaptcha.equals(String.valueOf(objCaptcha.getSecretCode()))) {
                errorMessages.put("Captcha fail!", "You input incorrect code for Captcha!");
                return false;
            }
        }
        return true;
    }

    public static boolean isActualCaptcha(int idCaptcha, long timeout) {
        Captcha objCaptcha = CaptchaRepository.getCaptcha(idCaptcha);

        LocalDateTime creationDate = objCaptcha.getCreationDate();
        creationDate = creationDate.plusSeconds(timeout);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(creationDate)) {
            errorMessages.put("Captcha fail!", "The lifetime of captcha has expired!");
            return false;
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    private static boolean validate(String verify, String regexPattern, String key, String hintMessage) {
        if (validate(verify, key) && !isMatchedRegex(verify, regexPattern)) {
            errorMessages.put(key, hintMessage);
            return false;
        }
        return true;
    }

    private static boolean validate(String verify, String key) {
        if (isNullOrEmpty(verify)) {
            errorMessages.put(key, "This field can not be empty!");
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