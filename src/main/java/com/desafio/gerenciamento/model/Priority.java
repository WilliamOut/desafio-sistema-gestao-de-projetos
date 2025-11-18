package com.desafio.gerenciamento.model;

public enum Priority {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private String value;

    Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
