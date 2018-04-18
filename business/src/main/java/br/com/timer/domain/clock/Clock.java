package br.com.timer.domain.clock;

import java.beans.Transient;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import br.com.timer.domain.PersistenceObject;
import br.com.timer.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "clock")
@AttributeOverride(name="id", column=@Column(name="tmr_id"))
@EqualsAndHashCode(callSuper=false)
public @Data class Clock extends PersistenceObject {

	private static final long serialVersionUID = -2943136365144245908L;
	
	public Clock() { }
	
	public Clock(User user, LocalDateTime dateTime) {
		this.timer = dateTime;
		this.user = user;
	}
	
	@OneToOne
	private User user;
	
	@Column(name = "tmr_dh_timer")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime timer;
	
	@Transient
	public int getDay() {
		return timer.getDayOfMonth();
	}
	
}