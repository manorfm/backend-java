package br.com.timer.rest.components.converters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class StringToLocalDateTimeConverter extends BidirectionalConverter<String, LocalDateTime> {

	private static final String DD_MM_YYYY = "dd/MM/yyyy";

	@Override
	public String convertFrom(LocalDateTime source, Type<String> dataType, MappingContext contex) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY);
		return source.format(formatter);
	}

	@Override
	public LocalDateTime convertTo(String source, Type<LocalDateTime> dataType, MappingContext contex) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY);
		LocalDate localDate = LocalDate.parse(source, formatter);
		
		return localDate.atTime(LocalTime.now());
	}
	
	/*private String getPattern(String date) {
		if (date.matches("\\d{4}(-\\d{2}){2}")) {
			if (date.matches("(:?\\d{2}){3}")) {
				
			}
			
			return "yyyy-MM-dd";
		}
		
		return DD_MM_YYYY;
	}*/
}
