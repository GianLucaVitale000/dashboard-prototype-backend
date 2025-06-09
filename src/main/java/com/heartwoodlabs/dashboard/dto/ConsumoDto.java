package com.heartwoodlabs.dashboard.dto;

import java.math.BigDecimal;

public class ConsumoDto {
	private Float quantita;
	private String data;

	public ConsumoDto(Double quantita, String data) {
		this.quantita = quantita == null ? 0.0f : quantita.floatValue();
		this.data = data;
	}

	public Float getQuantita() {
		return quantita;
	}

	public String getData() {
		return data;
	}
}
