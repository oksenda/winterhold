package com.oksenda.winterhold.exeptions;

public class EntityNotFoundExeption extends RuntimeException{

    public EntityNotFoundExeption(String message) {
        super(message);
    }
}
