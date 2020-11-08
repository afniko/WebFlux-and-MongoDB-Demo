package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.converter.TypeConverterFacade;
import com.afniko.core.converter.exception.TypeConversionException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class TypeConverterFacadeImpl implements TypeConverterFacade {

    private final List<TypeConverter<?, ?>> converters;

    private final Map<ConversionDescriptor, TypeConverter<?, ?>> converterRegistry = new HashMap<>();

    public TypeConverterFacadeImpl(List<TypeConverter<?, ?>> converters) {
        this.converters = converters;
    }

    @PostConstruct
    protected void populateConverterRegistry() {
        for (TypeConverter<?, ?> converter : converters) {
            ConversionDescriptor descriptor = new ConversionDescriptor(converter.getSourceClass(), converter.getTargetClass());
            TypeConverter<?, ?> alreadyRegistered = converterRegistry.put(descriptor, converter);

            if (alreadyRegistered != null) {
                throw new TypeConversionException(
                        "Duplicate type converter found:[%s]->[%s], already registered:[%s], register candidate:[%s]",
                        descriptor.srcClz, descriptor.targetClz, alreadyRegistered.getClass().getSimpleName(),
                        converter.getClass().getSimpleName());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S, T> T convert(S source, Class<T> targetClz) {
        assertNotNull(source, targetClz);
        TypeConverter<S, T> targetConverter = (TypeConverter<S, T>) getConverterFor(source.getClass(), targetClz);
        return targetConverter.convert(source);
    }

    private TypeConverter<?, ?> getConverterFor(Class<?> sourceClz, Class<?> targetClz) {
        TypeConverter<?, ?> result = converterRegistry.get(new ConversionDescriptor(sourceClz, targetClz));

        assertConverterExists(sourceClz, targetClz, result);

        return result;
    }

    private void assertConverterExists(Class<?> sourceClz, Class<?> targetClz, TypeConverter<?, ?> result) {
        if (result == null) {
            throw new TypeConversionException("Failed to find type converter for:[%s]->[%s] conversion",
                    sourceClz, targetClz);
        }
    }

    private void assertNotNull(Object source, Class<?> targetClz) {
        if (source == null) {
            throw new TypeConversionException("Fail to convert value: source object can not be null");
        }
        if (targetClz == null) {
            throw new TypeConversionException("Fail to convert value: target class can not be null");
        }
    }

    private static class ConversionDescriptor {

        private final Class<?> srcClz;

        private final Class<?> targetClz;

        public ConversionDescriptor(Class<?> srcClz, Class<?> targetClz) {
            this.srcClz = srcClz;
            this.targetClz = targetClz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ConversionDescriptor that = (ConversionDescriptor) o;

            return Objects.equals(srcClz, that.srcClz)
                    && Objects.equals(targetClz, that.targetClz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(srcClz, targetClz);
        }
    }
}
