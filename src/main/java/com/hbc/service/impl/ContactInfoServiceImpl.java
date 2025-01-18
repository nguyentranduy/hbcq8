package com.hbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.dto.contactinfo.UpdateContactRequestDto;
import com.hbc.entity.ContactInfo;
import com.hbc.repo.ContactInfoRepo;
import com.hbc.service.ContactInfoService;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {
	
	@Autowired
	ContactInfoRepo repo;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doUpdate(UpdateContactRequestDto dto) throws Exception {
		repo.doUpdate(dto.getAddress(), dto.getPhone1(), dto.getName1(), dto.getPhone2(), dto.getName2(), dto.getEmail());
	}

	@Override
	public ContactInfo doGet() {
		return repo.findById(1L).get();
	}
}
