package com.estsoft.projectdose.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicationReportDto {
	private String medicationName;
	private Integer repeatInterval;
	private String dosage;
}
