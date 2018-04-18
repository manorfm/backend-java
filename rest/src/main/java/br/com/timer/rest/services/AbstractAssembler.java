package br.com.timer.rest.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.timer.rest.components.converters.StringToLocalDateTimeConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public abstract class AbstractAssembler<T, V> {
	
	protected MapperFacade mapper;
	
    public AbstractAssembler() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        mapperFactory.getConverterFactory().registerConverter(new StringToLocalDateTimeConverter());
        mapper = mapperFactory.getMapperFacade();
    }
    
    
    public abstract List<V> convert(List<T> users);
    
    public abstract V convert(T user);
}