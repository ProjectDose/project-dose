package com.estsoft.projectdose.fcm.service;

import com.estsoft.projectdose.fcm.dto.DeviceTokenDto;

public interface DeviceTokenService {
	boolean registerDeviceToken(DeviceTokenDto deviceTokenDto);
	boolean deleteDeviceToken(String email, String token);
}
