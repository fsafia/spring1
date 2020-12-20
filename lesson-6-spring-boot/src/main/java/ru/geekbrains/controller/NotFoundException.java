package ru.geekbrains.controller;


public class NotFoundException extends RuntimeException {
    String mes;

    public NotFoundException(String mes) {
        this.mes = mes;
    }
}
