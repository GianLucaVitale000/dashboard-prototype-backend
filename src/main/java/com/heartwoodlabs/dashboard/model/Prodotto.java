package com.heartwoodlabs.dashboard.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@NamedQueries({
		@NamedQuery(name = "countByNome", query = "select count(p) " +
				"from com.heartwoodlabs.dashboard.model.Prodotto p " +
				"where lower(p.nome) = lower(:nome) ")
})
@Entity(name = "prodotto")
public class Prodotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private BigDecimal prezzoVendita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrezzoVendita() {
		return prezzoVendita;
	}

	public void setPrezzoVendita(BigDecimal prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Prodotto prodotto = (Prodotto) o;
		return Objects.equals(id, prodotto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
