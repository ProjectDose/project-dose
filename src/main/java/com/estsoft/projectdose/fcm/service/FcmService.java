package com.estsoft.projectdose.fcm.service;

import java.io.IOException;

import com.estsoft.projectdose.fcm.dto.FcmSendDto;

public interface FcmService {

	int sendMessageTo(FcmSendDto fcmSendDto) throws IOException;
}
