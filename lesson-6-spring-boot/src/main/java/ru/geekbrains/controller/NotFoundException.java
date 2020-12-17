package ru.geekbrains.controller;

import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.entity.User;

public class NotFoundException extends RuntimeException {
    String mes;

    public NotFoundException(String mes) {
        this.mes = mes;
    }
}
