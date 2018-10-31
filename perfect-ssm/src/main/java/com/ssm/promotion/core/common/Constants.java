package com.ssm.promotion.core.common;

/**
 * Created by 13 on 2017/6/26.
 */
public class Constants {

    public static final int RESULT_CODE_SUCCESS = 200;  // 成功处理请求
    public static final int RESULT_CODE_BAD_REQUEST = 412;  // bad request
    public static final int RESULT_CODE_SERVER_ERROR = 500;  // 没有对应结果

    public static final String ARTICLE_CACHE_KEY = "perfect-ssm:article:";//文章key
    public static final String PICTURE_CACHE_KEY = "perfect-ssm:picture:";//图片key
    
    /** 权限  **/
    public static final String SESSION_USER = "session_user";//session
    public static final String AUTHORITY_NORMAL = "普通管理员";
    public static final String AUTHORITY_SUPER = "系统管理员";
    public static final String AUTHORITY_SYS = "超级管理员";
    
    /** 用户状态 */
    public static final int USER_STATUS_NORMAL = 0;//正常状态
    public static final int USER_STATUS_FORBIDDEN = 1;//封停状态
    public static final int USER_STATUS_DEL = 2;//（逻辑）删除状态
    public static final String USER_DEFAULT_PWD = "123456";//重置默认密码;
    
    /** 操作系统 */
    public static final String OS_WINDOWS = "Windows";
    public static final String OS_LINUX = "Linux";
    
}
