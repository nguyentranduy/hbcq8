package com.hbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.entity.AboutUs;
import com.hbc.repo.AboutUsRepo;
import com.hbc.service.AboutUsService;

@Service
public class AboutUsServiceImpl implements AboutUsService {

	@Autowired
	AboutUsRepo repo;

	@Override
	public AboutUs get() {
		List<AboutUs> result = repo.findAll();
		return result.isEmpty() ? null : result.get(0);
	}

}
