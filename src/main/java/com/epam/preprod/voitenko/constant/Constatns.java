package com.epam.preprod.voitenko.constant;

public class Constatns {
    private Constatns() {
    }

    public static final String PATH_TO_AVATARS = "/src/main/webapp/images/avatars/";
    public static final String PATH_TO_TOOL_IMAGES = "/src/main/webapp/images/tools/";
    public static final String DEFAULT_AVATAR = "default.png";
    public static final String DEFAULT_TOOL_IMAGE = "default.png";
    public static final String EMPTY_STRING = "";

    public class RegEx {
        private RegEx() {
        }

        public static final String REGEX_FULL_NAME = "([a-zA-Z]{2,}\\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";
        public static final String REGEX_NOT_DIGIT = "\\D";
        public static final String REGEX_DIGITS = "\\d+";
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
        public static final String NAME_TOOL = "nameTool";
        public static final String LOW_PRICE = "lowPrice";
        public static final String HIGH_PRICE = "highPrice";
        public static final String ORDER_KEY = "orderKey";
        public static final String ORDER_DIRECTION = "orderDirection";
        public static final String NUMBER_TOOLS_ON_PAGE = "numberToolsOnPage";
        public static final String NUMBER_PAGE = "numberPage";
        public static final String CATEGORIES = "categories";
        public static final String MANUFACTURERS = "manufacturers";
        public static final String TOOLS = "tools";
        public static final String NUMBER_SUITABLE_TOOLS = "numberSuitableTools";
        public static final String AMOUNT_PAGES = "amountPages";
        public static final String CART = "cart";
        public static final String CART_TOTAL = "cartTotal";
        public static final String CART_QUANTITY = "cartQuantity";
        public static final String TOTAL_COST_SPECIFIC_TOOL = "totalCostSpecificTool";
        public static final String LIST_ORDERED_TOOLS = "listOrderedTools";

        public static final String REG_ENTITY = "regEntity";
        public static final String LOGIN_ENTITY = "logEntity";
        public static final String USER_ENTITY = "userEntity";
        public static final String ORDER_ENTITY = "orderEntity";
        public static final String FILTER_ENTITY = "filterEntity";
        public static final String ERRORS = "errors";

        public static final String PASSWORD_CHECK = "passwordCheck";
        public static final String CAPTCHA = "captcha";
        public static final String ID_CAPTCHA = "idCaptcha";
        public static final String SUCCESS_REGISTRATION = "successRegistration";
        public static final String FAIL_REGISTRATION = "failRegistration";
        public static final String ORDER_STATE = "orderState";

        // common
        public static final String ID = "id";
        public static final String ADDRESS = "address";
        public static final String COST = "cost";
        public static final String ASC = "ASC";
        public static final String DESC = "DESC";
        // users
        public static final String FULL_NAME = "fullName";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String AVATAR = "avatar";
        // tools
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
        public static final String MANUFACTURER = "manufacturer";
        public static final String POWER = "power";
        public static final String MAX_ROTATION_SPEED = "maxRotationSpeed";
        public static final String WEIGHT = "weight";
        public static final String MAIN_IMAGE = "mainImage";
        public static final String ADDITIONAL_IMAGE = "additionalImage";
        // orders
        public static final String STATUS = "status";
        public static final String STATUS_DETAIL = "statusDetail";
        public static final String DATE_TIME = "dateTime";
        public static final String USER_ID = "userID";
        // info_ordered_tools
        public static final String TOOL_ID = "toolID";
        public static final String AMOUNT = "amount";
        // order_bunch
        public static final String ORDER_ID = "orderID";
        public static final String INFO_ORDERED_TOOL_ID = "infoOrderedToolID";
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
        public static final String UNAUTHORIZED_USER = "You need to sign in or register to place an order";
        public static final String SUCCESS_ORDER = "";
        public static final String FAIL_ORDER = "";
    }

    public class Exceptions {
        private Exceptions() {
        }

        public static final String INTERRUPTED_EXCEPTION = "InterruptedException has occurred in CaptchaCleaner";
        // user
        public static final String CANNOT_GET_ALL_USERS = "Can not get a list of all users";
        public static final String CANNOT_GET_USER_BY_ID = "Can not get a user with such id";
        public static final String CANNOT_GET_USER_BY_EMAIL = "Can not get a user with such email";
        public static final String CANNOT_UPDATE_USER = "Can not update user";
        public static final String CANNOT_DELETE_USER = "Can not delete user with such id";
        public static final String CANNOT_CREATE_USER = "Can not create a new user";
        // tool
        public static final String CANNOT_GET_ALL_TOOLS = "Can not get a list of all tools";
        public static final String CANNOT_GET_ALL_CATEGORIES = "Can not get a list of all categories";
        public static final String CANNOT_GET_ALL_MANUFACTURERS = "Can not get a list of all manufacturers";
        public static final String CANNOT_GET_TOOL_BY_ID = "Can not get a tool with such id";
        public static final String CANNOT_UPDATE_TOOL = "Can not update tool";
        public static final String CANNOT_DELETE_TOOL = "Can not delete tool with such id";
        public static final String CANNOT_CREATE_TOOL = "Can not create a new tool";
        // order
        public static final String CANNOT_GET_ALL_ORDERS = "Can not get a list of all orders";
        public static final String CANNOT_GET_ORDER_BY_ID = "Can not get a order with such id";
        public static final String CANNOT_UPDATE_ORDER = "Can not update order";
        public static final String CANNOT_DELETE_ORDER = "Can not delete order with such id";
        public static final String CANNOT_CREATE_ORDER = "Can not create a new order";
        // info_ordered_tools
        public static final String CANNOT_GET_ALL_INFO_ORDERED_TOOLS = "Can not get a list of all infoOrderedTools";
        public static final String CANNOT_GET_INFO_ORDERED_TOOL_BY_ID = "Can not get an infoOrderedTool with such id";
        public static final String CANNOT_UPDATE_INFO_ORDERED_TOOL = "Can not update infoOrderedTool";
        public static final String CANNOT_DELETE_INFO_ORDERED_TOOL = "Can not delete infoOrderedTool with such id";
        public static final String CANNOT_CREATE_INFO_ORDERED_TOOL = "Can not create a new infoOrderedTool";
        // order_bunch
        public static final String CANNOT_GET_ORDER_BUNCH_BY_ID = "Can not get an order_bunch with such id";
        public static final String CANNOT_DELETE_ORDER_BUNCH = "Can not delete order_bunch with such id";
        public static final String CANNOT_CREATE_ORDER_BUNCH = "Can not create a new order_bunch";

        public static final String CANNOT_CLOSE_RESULT_SET = "Can not close ResultSet";
        public static final String CANNOT_CLOSE_STATEMENT = "Can not close Statement";
        public static final String CANNOT_CLOSE_CONNECTION = "Can not close Connection";
        public static final String CANNOT_CREATE_DATA_SOURCE = "Can not get DataSource with such name";
        public static final String CANNOT_GET_CONNECTION = "Can not get connection!";
        public static final String CANNOT_EXECUTE_COMMIT = "Can not execute commit! ";
        public static final String CANNOT_EXECUTE_ROLLBACK = "Can not execute rollback! ";
    }
}