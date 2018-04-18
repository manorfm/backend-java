package br.com.timer.rest.domain.clock.resources;

import java.util.List;

import lombok.Data;

@Data
public class DayResource {

	private int day;
	private List<String> hours;
	private String total;
	private List<String> warnings;
}
