package com.dbank.ist.referencedata.nace.exceptions;

public class ParentNotFoundExecption extends RuntimeException {

    public ParentNotFoundExecption(String parent) {
        super(parent);
    }
}