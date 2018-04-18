package br.com.timer.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class PersistenceObject extends AuditObject implements Cloneable {

	private static final long serialVersionUID = -2387520509257475867L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
}