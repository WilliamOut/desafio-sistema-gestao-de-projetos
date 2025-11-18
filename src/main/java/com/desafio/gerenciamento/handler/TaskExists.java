package com.desafio.gerenciamento.handler;

public class TaskExists extends RuntimeException {
    public TaskExists(String message) {
        super(message);
    }
}
