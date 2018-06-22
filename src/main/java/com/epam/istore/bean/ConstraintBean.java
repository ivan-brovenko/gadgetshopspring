package com.epam.istore.bean;


import com.epam.istore.security.Constraint;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ConstraintBean {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "constraint")
    List<Constraint> constraints;

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof ConstraintBean)){
            return false;
        }

        ConstraintBean that = (ConstraintBean) o;

        return constraints != null ? constraints.equals(that.constraints) : that.constraints == null;
    }

    @Override
    public int hashCode() {
        return constraints != null ? constraints.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ConstraintBean{" +
                "constraints=" + constraints +
                '}';
    }
}
