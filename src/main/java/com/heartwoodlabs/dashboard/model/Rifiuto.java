package com.heartwoodlabs.dashboard.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "rifiuto")
public class Rifiuto {

	public enum Tipo {
		CARTA, CARTONE, PLASTICA, IMBALLAGGIO, LEGNO,
		VETRO, POLISTIROLO, VERNICE, INDIFFERENZIATO;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Rifiuto.Tipo tipo;

	// costo di smaltimento per quintale
	@Column(nullable = false)
	private BigDecimal costoSmaltimento;

	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rifiuto.Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Rifiuto.Tipo tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getCostoSmaltimento() {
		return costoSmaltimento;
	}

	public void setCostoSmaltimento(BigDecimal costoSmaltimento) {
		this.costoSmaltimento = costoSmaltimento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Rifiuto rifiuto = (Rifiuto) o;
		return Objects.equals(id, rifiuto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
