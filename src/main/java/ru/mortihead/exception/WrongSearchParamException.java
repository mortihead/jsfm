package ru.mortihead.exception;

public class WrongSearchParamException extends Exception {
    public WrongSearchParamException(long param) {
        super(String.format("Wrong search param exception : '%s'", param));
    }
}
