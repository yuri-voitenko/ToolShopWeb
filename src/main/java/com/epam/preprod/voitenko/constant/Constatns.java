package com.epam.preprod.voitenko.constant;

public class Constatns {
    private Constatns() {
    }

    public static final String PATH_TO_AVATARS = "/src/main/webapp/images/avatars/";
    public static final String DEFAULT_AVATAR = "default.png";
    public static final String EMPTY_STRING = "";

    public class RegEx {
        private RegEx() {
        }

        public static final String REGEX_FULL_NAME = "([a-zA-Z]{2,}\\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";
        public static final String REGEX_NOT_DIGIT = "\\D";
        public static final String REGEX_EMAIL = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
        public static final String REGEX_PASSWORD = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}";
        public static final String REGEX_CAPTCHA_CODE = "\\d{10}";
        public static final String REGEX_FOR_PARSE_FILE_NAME = "(?i)^.*filename=\"([^\"]+)\".*$";
        public static final String REGEX_FILE_NAME_IMAGE = "(?i)(.*/)*.+\\.(png|jpg|jpeg)$";
        public static final String REGEX_REPLACE_FILE_NAME_IMAGE = "(?i)(.+)(\\.)(png|jpg|jpeg)";
    }

    public class Keys {
        private Keys() {
        }

        public static final String STRATEGY = "strategy";
        public static final String CAPTCHA_STRATEGY = "CaptchaStrategy";
        public static final String TIMEOUT = "Timeout";

        public static final String REG_ENTITY = "regEntity";
        public static final String LOGIN_ENTITY = "logEntity";
        public static final String USER_ENTITY = "userEntity";
        public static final String ERRORS = "errors";

        public static final String ID = "id";
        public static final String FULL_NAME = "fullName";
        public static final String ADDRESS = "address";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PASSWORD_CHECK = "passwordCheck";
        public static final String CAPTCHA = "captcha";
        public static final String AVATAR = "avatar";
        public static final String ID_CAPTCHA = "idCaptcha";
        public static final String SUCCESS_REGISTRATION = "successRegistration";
        public static final String FAIL_REGISTRATION = "failRegistration";
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
        public static final String HINT_SUCCESS_REGISTRATION = "Registration completed successfully!";
        public static final String HINT_FAIL_REGISTRATION = "Registration failed!";

        public static final String NOT_LOGIN_EMAIL = "There is no registered user with such e-mail!";
        public static final String NOT_LOGIN_PASSWORD = "There is no registered user with such password";
    }

    public class Exceptions {
        private Exceptions() {
        }

        public static final String INTERRUPTED_EXCEPTION = "InterruptedException has occurred in CaptchaCleaner";
        public static final String CANNOT_GET_ALL = "Can not get a list of all users";
        public static final String CANNOT_GET_ENTITY_BY_ID = "Can not get a user with such id";
        public static final String CANNOT_GET_ENTITY_BY_EMAIL = "Can not get a user with such email";
        public static final String CANNOT_UPDATE_ENTITY = "Can not update user";
        public static final String CANNOT_DELETE_ENTITY = "Can not delete user with such id";
        public static final String CANNOT_CREATE_ENTITY = "Can not create a new user";

        public static final String CANNOT_CLOSE_RESULT_SET = "Can not close ResultSet";
        public static final String CANNOT_CLOSE_STATEMENT = "Can not close Statement";
        public static final String CANNOT_CLOSE_CONNECTION = "Can not close Connection";
        public static final String CANNOT_CREATE_DATA_SOURCE = "Can not get DataSource with such name";
        public static final String CANNOT_GET_CONNECTION = "Can not get connection!";
        public static final String CANNOT_EXECUTE_COMMIT = "Can not execute commit! ";
        public static final String CANNOT_EXECUTE_ROLLBACK = "Can not execute rollback! ";
    }
}