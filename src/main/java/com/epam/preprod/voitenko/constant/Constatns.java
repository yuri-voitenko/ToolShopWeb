package com.epam.preprod.voitenko.constant;

public class Constatns {
    private Constatns() {
    }
    public class Keys {
        private Keys() {
        }

        public static final String STRATEGY = "strategy";
        public static final String CAPTCHA_STRATEGY = "CaptchaStrategy";
        public static final String TIMEOUT = "Timeout";

        public static final String REG_BEAN = "regBean";
        public static final String ERRORS = "errors";

        public static final String FULL_NAME = "fullName";
        public static final String ADDRESS = "address";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PASSWORD_CHECK = "passwordCheck";
        public static final String CAPTCHA = "captcha";
        public static final String ID_CAPTCHA = "idCaptcha";
        public static final String SUCCESS_REGISTRATION = "successRegistration";
    }

    public class Message {
        private Message() {
        }

        public static final String HINT_FULL_NAME = "Hint: 'FirstName LastName'";
        public static final String HINT_PHONE_NUMBER = "Hint: Use international format!";
        public static final String HINT_EMAIL = "You input incorrect email!";
        public static final String HINT_PASSWORD = "Password requirements:" +
                "<br>\t* At least one upper case English letter" +
                "<br>\t* At least one lower case English letter" +
                "<br>\t* At least one digit" +
                "<br>\t* At least one special character" +
                "<br>\t* Minimum eight in length";
        public static final String HINT_PASSWORD_CHECK = "Is not equal to the password";
        public static final String HINT_CAPTCHA_LIFETIME = "The lifetime of captcha has expired!";
        public static final String HINT_CAPTCHA_CODE = "You input incorrect code for Captcha!";
        public static final String HINT_CAPTCHA_NOT_DIGITS = "Hint: Must be only 10 digits.";
        public static final String HINT_NOT_EMPTY_FIELD = "This field can not be empty!";
        public static final String HINT_SAME_EMAIl = "Registration fail!<br>User with such login(email) already exists!";
        public static final String SUCCESS_REGISTRATION = "Registration completed successfully!";
    }

    public class Exceptions {
        private Exceptions() {
        }

        public static final String INTERRUPTED_EXCEPTION = "InterruptedException has occurred in CaptchaCleaner";
    }
}