package com.hibecode.beerstore.core.enums;

public enum BeerType
{
    LARGER((short) 0),
    PILSEN((short) 1),
    IPA((short) 2);

    private  short code;
    BeerType(short code)
    {
        this.code = code;
    }
}