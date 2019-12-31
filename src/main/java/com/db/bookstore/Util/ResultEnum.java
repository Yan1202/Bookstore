package com.db.bookstore.Util;

public enum ResultEnum {
    SUCCESS(200, "success"),
    FAIL(400, "fail"),
    UNAUTHORIZED(401, "unauthorized"),
    NOT_FOUND(404, "not found"),
    INTERNAL_SERVER_ERROR(500, "internal server error"),
    INVALID_USER(007, "invalid user or password"),
    OCCUPIED(005,"this ID has been occupied.");
    public int code;
    public String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
