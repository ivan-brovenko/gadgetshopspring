package com.epam.istore.security;


import java.util.List;

public class AccessProvider {
    private List<Constraint> constraints;

    public AccessProvider(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public boolean isURLInConstraints(String url) {
        return constraints.stream().anyMatch(constraint -> url.matches(constraint.getUrlPattern()));
    }

    public boolean isRoleBindsWithCurrentURL(String role, String pagUrl) {
        for (Constraint constraint : constraints) {
            if (constraint.getRole().equals(role) && pagUrl.matches(constraint.getUrlPattern())) {
                return true;
            }
        }
        return false;
    }
}
