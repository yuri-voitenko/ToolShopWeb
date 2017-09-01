package com.epam.preprod.voitenko.util;

import com.epam.preprod.voitenko.entity.Role;

import java.util.Collection;
import java.util.Map;

public class Authenticator {
    private Map<String, Collection<String>> permissionMap;

    public Authenticator(Map<String, Collection<String>> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public boolean isRoleHaveAccess(Role role, String url) {
        String keyRole = role.toString().toLowerCase();
        return permissionMap.containsKey(keyRole) && verifyRoleAccess(url, keyRole);
    }

    private boolean verifyRoleAccess(String url, String keyRole) {
        for (String regEx : permissionMap.get(keyRole)) {
            if (url.matches(regEx)) {
                return true;
            }
        }
        return false;
    }
}