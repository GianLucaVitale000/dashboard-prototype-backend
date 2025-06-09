package com.heartwoodlabs.dashboard.dto;

import java.math.BigDecimal;

public class ProdottoDto {
	private String nome;
	private BigDecimal prezzo;

	public ProdottoDto() {}

	public ProdottoDto(String nome, BigDecimal prezzo) {
		this.nome = nome;
		this.prezzo = prezzo;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
}
