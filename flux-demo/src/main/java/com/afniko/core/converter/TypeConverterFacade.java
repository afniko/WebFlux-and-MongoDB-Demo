package com.afniko.core.converter;

public interface TypeConverterFacade {

    <S, T> T convert(S source, Class<T> targetClz);

}
