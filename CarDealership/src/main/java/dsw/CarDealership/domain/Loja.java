package dsw.CarDealership.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
public class Loja extends Usuario{
	
	@NotNull
	@Column(nullable = false, length = 19)
	private String nome;
	@NotNull
	@Column(nullable = false, length = 19)
	private String descricao;
	@NotNull
	@Column(nullable = false, length = 19)
	private String cnpj;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
