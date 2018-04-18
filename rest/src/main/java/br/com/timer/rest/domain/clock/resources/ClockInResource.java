package br.com.timer.rest.domain.clock.resources;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ClockInResource {

	private Long pis;
	private LocalDateTime dateTime;
}
