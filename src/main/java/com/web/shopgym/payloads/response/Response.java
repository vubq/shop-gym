package com.web.shopgym.payloads.response;

import lombok.Getter;

@Getter
public class Response {

    /** The Constant CODE_SEND_TIME_INVALID. */
    public static final int CODE_SEND_TIME_INVALID = 4;

    /** The Constant CODE_SEND_TEXT_INVALID. */
    public static final int CODE_SEND_TEXT_INVALID = 5;

    /** The Constant CODE_EMAIL_INVALID. */
    public static final int CODE_EMAIL_INVALID = 6;

    /** The Constant CODE_PHONENUMBER_INVALID. */
    public static final int CODE_PHONENUMBER_INVALID = 7;

    /** The Constant CODE_AGE_INVALID. */
    public static final int CODE_AGE_INVALID = 8;

    /** The Constant CODE_FIELD_REQUIED. */
    public static final int CODE_FIELD_REQUIED = 9;

    /** The Constant CODE_URL_EXIST. */
    public static final int CODE_URL_EXIST = 10;

    /** The Constant CODE_URL_INVALID. */
    public static final int CODE_URL_INVALID = 11;

    /** The Constant CODE_PASSWORD_REQUIED. */
    public static final int CODE_PASSWORD_REQUIED = 12;

    /** The Constant CODE_PASSWORD_REQUIED. */
    public static final int CODE_LOGIN_REQUIED = 13;

    /** The Constant CODE_INTERNAL_ERROR. */
    public static final int CODE_INTERNAL_ERROR = 9999;

    /** The Constant PASSWORD_INVALID. */
    public static final int CODE_PASSWORD_INVALID = 14;

    /** The Constant CODE_OTP_INCORRECT. */
    public static final int CODE_OTP_INCORRECT = 15;

    /** The Constant CODE_NOT_AUTH. */
    public static final int CODE_NOT_AUTH = 16;

    /** The Constant CODE_PAYMENT_EXITS. */
    public static final int CODE_PAYMENT_EXITS = 17;

    /** The Constant CODE_PAYMENT_PENDING. */
    public static final int CODE_CALLBACK_INVALID = 406;

    public static final int CODE_SUCCESS = 0;

    public static final int CODE_PERMISSION = 1;

    public static final int CODE_NOT_FOUND = 2;

    public static final int CODE_ALREADY_EXIST = 3;

    public static final int CODE_BUSINESS = 4;

    public static final int CODE_INVALID = 19;

    public static final int CODE_BAD_REQUEST = 400;

    public static final int CODE_EXPIRED = 8888;

    protected Integer code;

    public Response code(int code) {
        this.code = code;
        return this;
    }

    private Object data;

    public static Response build() {
        return new Response();
    }

    public Response data(Object data) {
        this.data = data;
        return this;
    }

    public Response ok() {
        Response response = new Response();
        response.code = CODE_SUCCESS;
        return response;
    }
}

