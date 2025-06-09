package com.heartwoodlabs.dashboard.dto;

public class ProduzioneDto {
	private Long pezziProdotti, pezziDifettosi;
	private String data;

	public ProduzioneDto(Long pezziProdotti, Long pezziDifettosi, String data) {
		this.pezziProdotti = pezziProdotti;
		this.pezziDifettosi = pezziDifettosi;
		this.data = data;
	}

	public Long getPezziProdotti() {
		return pezziProdotti;
	}
	public Long getPezziDifettosi() {
		return pezziDifettosi;
	}
	public String getData() {
		return data;
	}
}
