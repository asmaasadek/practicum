package com.big.assessment.utilities;

public enum UserType {
    RECEIVER("Receiver"),
    SENDER("Sender");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
