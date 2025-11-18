package com.desafio.gerenciamento.model;

public enum Status {
    TODO("todo"),
    DOING("doing"),
    DONE("done");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
