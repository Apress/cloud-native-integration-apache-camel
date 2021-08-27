package com.apress.integration;

public class AnotherObject {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AnotherObject{value='" + value + "'}";
    }
}
