package com.heartwoodlabs.dashboard.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "dispositivo")
public class Dispositivo {
	public enum Tipo {
		PRODUZIONE_ELETTRICA_MONOFASE, PRODUZIONE_ELETTRICA_TRIFASE,
		CONSUMO_ELETTRICO_MONOFASE, CONSUMO_ELETTRICO_TRIFASE,
		CONSUMO_ACQUA_BAGNO, CONSUMO_ACQUA_PRODUZIONE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Tipo tipo;

	@Column(nullable = false)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Dispositivo that = (Dispositivo) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
