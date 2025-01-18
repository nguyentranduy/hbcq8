package com.hbc.service;

import com.hbc.dto.contactinfo.UpdateContactRequestDto;
import com.hbc.entity.ContactInfo;

public interface ContactInfoService {

	ContactInfo doGet();
	void doUpdate(UpdateContactRequestDto dto) throws Exception;
}
