package com.epam.istore.converter;


public interface Converter<S, T> {
    T convert(S parameterToConvert);
}
