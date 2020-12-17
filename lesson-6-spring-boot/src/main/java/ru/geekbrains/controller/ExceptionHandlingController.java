package ru.geekbrains.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice  //методы этого класса применятся ко всем методам всех контролллеров(где генерятся исключения)

public class ExceptionHandlingController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView NotFoundExseptionHandler(NotFoundException notFoundExseption) {
        ModelAndView modelAndView = new ModelAndView("not_found");
            modelAndView.getModel().put("mes", notFoundExseption.mes);
        return modelAndView;
    }
}
