package com.yaohy.intelligentfarmingbackend.constant;

import lombok.Data;


public enum RoleConstant {

    WEB_USER("WEB_USER","V0VCX1VTRVI="),
    APP_USER("APP_USER","QVBQX1VTRVI=");

    private String role;
    private String code;

    RoleConstant(String role, String code) {
        this.role = role;
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
