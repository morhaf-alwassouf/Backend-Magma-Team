package com.magma.main.Exceptions;

import com.magma.main.Utils.AppConstants;

public class BusinessException extends Exception {
    public String code;

    public BusinessException(String key , String language)
    {
        super(language.toLowerCase().equals(AppConstants.languageEn) || language.toLowerCase().equals("0") ? ExceptionMessages.en.get(key): ExceptionMessages.ar.get(key) );
        this.code = ExceptionMessages.codes.get(key);
    }
    public BusinessException(int code , String message)
    {
        super(message);
        this.code = code + "";
    }
}
