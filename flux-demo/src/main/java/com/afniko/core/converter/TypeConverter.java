package com.afniko.core.converter;

public interface TypeConverter<S, T> {

    Class<S> getSourceClass();

    Class<T> getTargetClass();

    T convert(S source);

}
