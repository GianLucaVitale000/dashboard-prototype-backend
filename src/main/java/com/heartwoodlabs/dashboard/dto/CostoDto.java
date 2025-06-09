package com.heartwoodlabs.dashboard.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CostoDto {
	private BigDecimal importo;
	private String data;

	public CostoDto(Double importo, String data) {
		this.importo = importo == null ? BigDecimal.ZERO : BigDecimal.valueOf(importo.floatValue()).setScale(2, RoundingMode.HALF_UP);
		this.data = data;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public String getData() {
		return data;
	}
}
