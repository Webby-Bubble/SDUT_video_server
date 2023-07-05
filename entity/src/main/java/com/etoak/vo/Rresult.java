package com.etoak.vo;

public enum Rresult {
    SUCCESS(20000,"success"),
    ERROR(20001,"error"),
    LOGIN_ERORR(20002,"login_error"),
    USERNAME_OR_PWD_ERROR(20003,"username_or_pwd_error"),
    TIMEOUT(20004,"timeout....");

    private int code;
    private String msg;

    Rresult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
