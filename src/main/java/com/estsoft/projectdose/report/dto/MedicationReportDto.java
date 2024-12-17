package com.estsoft.projectdose.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MedicationReportDto {
	private String medicationName;  // 약 이름
	private int repeatInterval;     // 반복 간격
	private String dosage;           // 복용량
	private Map<String, Object> repeatData;  // 반복 데이터 (주기 또는 요일)

	// 기본 생성자 추가
	public MedicationReportDto() {}

	// 모든 필드를 포함하는 생성자
	public MedicationReportDto(String medicationName, int repeatInterval,
		String dosage, Map<String, Object> repeatData) {
		this.medicationName = medicationName;
		this.repeatInterval = repeatInterval;
		this.dosage = dosage;
		this.repeatData = repeatData;
	}

	// JPQL에서 사용할 생성자
	public MedicationReportDto(String medicationName, int repeatInterval,
		String dosage,
		Object repeatInterval2,
		Object daysOfWeek) {
		this.medicationName = medicationName;
		this.repeatInterval = repeatInterval;
		this.dosage = dosage;

		// repeatData 맵 생성
		this.repeatData = new HashMap<>();
		if (repeatInterval2 != null) {
			this.repeatData.put("repeatInterval", repeatInterval2);
		}
		if (daysOfWeek != null) {
			this.repeatData.put("daysOfWeek", daysOfWeek);
		}
	}
}