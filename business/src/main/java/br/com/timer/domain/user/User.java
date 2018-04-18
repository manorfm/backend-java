package br.com.timer.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.timer.domain.AuditObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper=false)
@Getter @Setter
public class User extends AuditObject {
	
	private static final long serialVersionUID = 4439931903656975507L;

	@Id
	@Column(name = "usr_pis")
	private Long pis;

	@Column(name = "usr_nm")
	private String name;
	
	@Column(name = "usr_pw")
	private String password;
}
