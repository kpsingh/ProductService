package com.lld4.productservice.advices;


import com.lld4.productservice.dtos.ExceptionAdviceDto;
import com.lld4.productservice.exceptions.InvalidProductException;
import com.lld4.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionAdviceDto> handleUserNotFoundException(ProductNotFoundException e) {
        ExceptionAdviceDto exceptionAdviceDto = new ExceptionAdviceDto();
        exceptionAdviceDto.setMessage(e.getMessage());
        exceptionAdviceDto.setCode(101);
        return new ResponseEntity<>(exceptionAdviceDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<ExceptionAdviceDto> handleInvalidProductException(InvalidProductException e) {
        ExceptionAdviceDto exceptionAdviceDto = new ExceptionAdviceDto();
        exceptionAdviceDto.setMessage(e.getMessage());
        exceptionAdviceDto.setCode(102);
        return  new ResponseEntity<>(exceptionAdviceDto, HttpStatus.BAD_REQUEST);
    }

}
