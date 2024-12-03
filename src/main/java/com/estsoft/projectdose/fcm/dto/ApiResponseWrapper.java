package com.estsoft.projectdose.fcm.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponseWrapper<T> {
	private int result;

	private String resultCode;

	private String resultMsg;

	private T data;
}
