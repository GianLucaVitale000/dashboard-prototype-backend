package com.heartwoodlabs.dashboard.dto;

import java.math.BigDecimal;

public class FatturatoDto {
	private BigDecimal importo;
	private String data;

	public FatturatoDto(BigDecimal importo, String data) {
		this.importo = importo;
		this.data = data;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public String getData() {
		return data;
	}
}
