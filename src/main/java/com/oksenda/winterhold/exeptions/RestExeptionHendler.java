package com.oksenda.winterhold.exeptions;


import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExeptionHendler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDto<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageDto<Object> dto = ErrorMessageDto.builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }
    @ExceptionHandler(EntityNotFoundExeption.class)
    public ResponseEntity<ErrorMessageDto<Object>> hendlerEntityNotfoundExeption(EntityNotFoundExeption e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorMessageDto dto = ErrorMessageDto.builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto,httpStatus);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ErrorMessageDto<Map<String,String>>> hendlerMethodArgumenNotValidExeption(MethodArgumentNotValidException e){
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        Map<String,String> errorsMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorsMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        ErrorMessageDto dto = ErrorMessageDto.<Map<String,String>>builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(errorsMap)
                .build();
        return new ResponseEntity<>(dto,httpStatus);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<ErrorMessageDto<Object>> usernameNotFoundException(UsernameNotFoundException e){
            HttpStatus httpStatus =  HttpStatus.UNAUTHORIZED;
            ErrorMessageDto dto = ErrorMessageDto.<Object>builder()
                    .httpStatus(httpStatus)
                    .message(e.getMessage())
                    .error(e)
                    .build();
            return new ResponseEntity<>(dto,httpStatus);

        }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorMessageDto<Object>> handlerMethodExixstExeption(EntityExistsException e){
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        ErrorMessageDto dto = ErrorMessageDto.<Object>builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto,httpStatus);

    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDto<Object>> handleAccessDeniedException(AccessDeniedException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorMessageDto<Object> dto = ErrorMessageDto.<Object>builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageDto<Object>> handleBadCredentialsException(BadCredentialsException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorMessageDto<Object> dto = ErrorMessageDto.<Object>builder()
                .httpStatus(httpStatus)
                .message("Invalid username or password")
                .error(e)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException e) {
        return "Invalid username or password";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessageDto<Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageDto<Object> dto = ErrorMessageDto.builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultipartException(MultipartException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageDto<Object> dto = ErrorMessageDto.builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
                .error(e)
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }
}
