package com.estsoft.projectdose.fcm.dto;

public enum SuccessCode {

	SELECT_SUCCESS("200", "Successfully fetched data"),
	OPERATION_SUCCESS("200", "Operation was successful");

	private final String status;
	private final String message;

	SuccessCode(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
