package com.lld4.productservice.advices;


import com.lld4.productservice.dtos.ExceptionAdviceDto;
import com.lld4.productservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionAdviceDto> handleUserNotFoundException(UserNotFoundException e) {
        ExceptionAdviceDto exceptionAdviceDto = new ExceptionAdviceDto();
        exceptionAdviceDto.setMessage(e.getMessage());
        exceptionAdviceDto.setCode(101);
        return new ResponseEntity<>(exceptionAdviceDto, HttpStatus.NOT_FOUND);

    }

}
