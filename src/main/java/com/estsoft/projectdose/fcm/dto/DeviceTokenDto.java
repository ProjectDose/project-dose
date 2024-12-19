package com.estsoft.projectdose.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTokenDto {
	private Long userId;
	private String email;
	private String token;
}
