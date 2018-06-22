package com.epam.istore.security;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Constraint {
    @JacksonXmlProperty(localName = "url-pattern")
    private String urlPattern;
    @JacksonXmlProperty(localName = "role")
    private String role;

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Constraint)) return false;

        Constraint that = (Constraint) o;

        if (!urlPattern.equals(that.urlPattern)){
            return false;
        }
        return role.equals(that.role);
    }

    @Override
    public int hashCode() {
        int result = urlPattern.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "urlPattern='" + urlPattern + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
