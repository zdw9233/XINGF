package com.uyi.xinf;

public abstract class Constens {
    public static int START_ACTIVITY_FOR_RESULT = 10000;//跳转界面1
    public static int START_ACTIVITY_FOR_RESULT_TWO = 10005;//跳转界面2
    public static int START_ACTIVITY_FOR_RESULT_THREE = 10006;//跳转界面3
    public static int BACK_LOGIN = 10001;//登陆
    public static int PHOTO_REQUEST_GALLERY = 10020;//相册
    public static int PHOTO_REQUEST_TAKEPHOTO = 10021;//相机
    public static int PHOTO_REQUEST_CUT = 10022;//裁剪
    public static int START_ALIPAY_FOR_RESULT = 10003;//阿里支付跳转界面


    public static final String CHAR_ENCODING = "UTF-8";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String USERNAME_REGEX = "[a-zA-Z0-9_-]+";
    public static final String USERNAME_REGEX_AND_LEN = "[a-zA-Z0-9_-]{4,32}";
    public static final int USERNAME_MIN_LEN = 6;
    public static final int USERNAME_MAX_LEN = 32;
    public static final int REAL_NAME_LEN = 20;

    public static final String PASSWORD_REGEX = "(?=.*?[A-Z]+.*?)(?=.*?[a-z]+.*?)(?=.*?[0-9]+.*?)(?=.*?[\\p{Punct}]+.*?).*";
    public static final String PASSWORD_NEW_REGEX = "(?=.*?[a-zA-Z]+.*?)(?=.*?[0-9]+.*?).*";
    public static final int PASSWORD_MIN_LEN = 6;
    public static final int PASSWORD_MAX_LEN = 32;

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    public static final String ID_CARD_REGEX = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    public static final String PHONE_REGEX = "(^(\\d{3,4}[-]?)?\\d{7,8})$|^((1[0-9][0-9]\\d{8}$))";


    /**
     * 测试服地址
     */
//    public final static String SERVER_URL = "http://192.168.0.201:8080";
    public final static String SERVER_URL = "http://121.42.142.228:8090";

    /**
     * 生产服地址
     */
//	public final static String SERVER_URL = "http://www.uyidoctor.com";
    //登陆
    public final static String LOGIN_URL = SERVER_URL + "/app/api/account/login";
    //上传基本资料
    public final static String POST_BASIC_INFORMATION = SERVER_URL + "/app/api/customer/save";
    //查询用户信息列表
    public final static String GET_BASIC_INFORMATION = SERVER_URL + "/app/api/customer/queryAllByUser?name=%s&page=%s&id=5&pageSize=%s";
    //3.根据id查询用户信息
    public final static String GET_BASIC_INFORMATION_DETAILS = SERVER_URL + "/app/api/customer/queryUserById?id=%s";
}
