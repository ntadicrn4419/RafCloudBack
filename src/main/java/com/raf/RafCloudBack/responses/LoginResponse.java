package com.raf.RafCloudBack.responses;

import com.raf.RafCloudBack.models.UserPermission;

import java.util.List;

public class LoginResponse {
    private String jwt;
    private List<UserPermission> userPermissionList;

    public LoginResponse(String jwt, List<UserPermission> userPermissionList) {
        this.jwt = jwt;
        this.userPermissionList = userPermissionList;
    }

    public String getJwt() {
        return jwt;
    }

    public List<UserPermission> getPermissionList() {
        return userPermissionList;
    }
}
