package com.heartwoodlabs.dashboard.dto;

import com.heartwoodlabs.dashboard.model.Rifiuto;

public class RifiutoDto {
	private Float quantita;
	private Float percentuale;
	private Rifiuto.Tipo tipo;

	public RifiutoDto(Double quantita, Rifiuto.Tipo tipo) {
		this.quantita = quantita == null ? 0.0f : quantita.floatValue();
		this.tipo = tipo;
	}

	public Float getQuantita() {
		return quantita;
	}
	public Rifiuto.Tipo getTipo() {
		return tipo;
	}
	public Float getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(Float percentuale) {
		this.percentuale = percentuale;
	}
}
