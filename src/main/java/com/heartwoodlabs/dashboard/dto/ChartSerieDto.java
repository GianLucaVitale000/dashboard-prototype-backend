package com.heartwoodlabs.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartSerieDto {

	private String label;
	private List<Object> data = new ArrayList<>();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Object> getData() {
		return data;
	}
}
