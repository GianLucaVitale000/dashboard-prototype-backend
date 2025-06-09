package com.heartwoodlabs.dashboard.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "produzione")
public class Produzione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private LocalDate data;

	@Column(name = "pezzi_prodotti")
	private Integer pezziProdotti;

	@Column(name = "pezzi_difettosi")
	private Integer pezziDifettosi;

	@ManyToOne
	@JoinColumn(name = "linea_id")
	private LineaProduttiva linea;

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

	public Integer getPezziProdotti() {
		return pezziProdotti;
	}

	public void setPezziProdotti(Integer pezziProdotti) {
		this.pezziProdotti = pezziProdotti;
	}

	public Integer getPezziDifettosi() {
		return pezziDifettosi;
	}

	public void setPezziDifettosi(Integer pezziDifettosi) {
		this.pezziDifettosi = pezziDifettosi;
	}

	public LineaProduttiva getLinea() {
		return linea;
	}

	public void setLinea(LineaProduttiva linea) {
		this.linea = linea;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Produzione that = (Produzione) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
