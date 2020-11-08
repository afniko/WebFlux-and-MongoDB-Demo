package com.afniko.core.converter.exception;

import com.afniko.exception.FluxDemoApplicationException;

import static java.lang.String.format;

public class TypeConversionException extends FluxDemoApplicationException {

    public TypeConversionException(String msgTemplate, Object... args) {
        super(format(msgTemplate, args));
    }

    public TypeConversionException(Throwable cause, String msgTemplate, Object... args) {
        super(format(msgTemplate, args), cause);
    }
}
