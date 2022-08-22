package dsw.CarDealership.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends AbstractEntity<Long> {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false, unique = true, length = 19)
	private String email;
	
	@NotNull
	@Column(nullable = false, length = 64)
	private String senha;
	
	@NotNull
	@Column(nullable = false, length = 19)
	private String papel;

	public String getEmail() {
		return email;
	}

	public void setLogin(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String password) {
		this.senha = password;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}
}
