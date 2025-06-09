package com.heartwoodlabs.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartDto {

	public enum ChartType {
		TABLE, BASIC_LINE, DUAL_AXIS, STACKED_COLUMNS, PIE
	}

	public ChartDto(ChartType type) {
		this.type = type;
	}

	private ChartType type;
	private String title, subtitle;
	private List<String> labels = new ArrayList<>();
	private List<ChartSerieDto> series = new ArrayList<>();

	public ChartType getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<String> getLabels() {
		return labels;
	}

	public List<ChartSerieDto> getSeries() {
		return series;
	}

}
