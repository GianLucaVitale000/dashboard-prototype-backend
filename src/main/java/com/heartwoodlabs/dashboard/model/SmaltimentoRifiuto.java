package com.heartwoodlabs.dashboard.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "smaltimento_rifiuto")
public class SmaltimentoRifiuto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private LocalDate data;

	// Quintali
	@Column(nullable = false)
	private Double quantita;

	@ManyToOne
	@JoinColumn(name = "rifiuto_id", nullable = false)
	private Rifiuto rifiuto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getQuantita() {
		return quantita;
	}

	public void setQuantita(Double quantita) {
		this.quantita = quantita;
	}

	public Rifiuto getRifiuto() {
		return rifiuto;
	}

	public void setRifiuto(Rifiuto rifiuto) {
		this.rifiuto = rifiuto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SmaltimentoRifiuto that = (SmaltimentoRifiuto) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
