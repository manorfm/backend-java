package br.com.timer.rest.domain.clock.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ClockResource {

	private Long pis;
    private String name;
    private List<DayResource> days;
}