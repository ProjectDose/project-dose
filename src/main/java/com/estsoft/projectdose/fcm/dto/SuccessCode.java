package com.estsoft.projectdose.fcm.dto;

import lombok.Getter;

@Getter
public enum SuccessCode {

	SELECT_SUCCESS("200", "Successfully fetched data"),
	OPERATION_SUCCESS("200", "Operation was successful");

	private final String status;
	private final String message;

	SuccessCode(String status, String message) {
		this.status = status;
		this.message = message;
	}

}
