package com.tecnics.einvoice.lang;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;

public class Assert {

    public static void isNotNull(Object param , String message) {
        if(param == null)
            throw new InternalException(Ex.INVALID_API_REQUEST.getKey(),Ex.formatMessage(Ex.INVALID_API_REQUEST.getKeyMessage(), new Object[]{message}));
    }

    public static void isNotNull(Object param[] , String message) {
        if(param == null)
            throw new InternalException(Ex.INVALID_API_REQUEST.getKey(),Ex.formatMessage(Ex.INVALID_API_REQUEST.getKeyMessage(), new Object[]{message}));
    }
}
