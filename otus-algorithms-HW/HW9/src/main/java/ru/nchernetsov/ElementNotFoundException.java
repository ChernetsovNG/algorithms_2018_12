package ru.nchernetsov;

public class ElementNotFoundException extends IllegalArgumentException {

    public ElementNotFoundException(String message) {
        super(message);
    }
}
