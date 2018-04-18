package br.com.timer.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class AuditObject implements Serializable {

	private static final long serialVersionUID = -1836260047204259147L;

	@Column(name="aud_create")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime create;

	@Column(name="aud_update")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime update;
	
	@PrePersist
	void onPersist() {
		create = LocalDateTime.now();
	}
	
	@PreUpdate
	void onUpdate() {
		update = LocalDateTime.now();
	}
}
