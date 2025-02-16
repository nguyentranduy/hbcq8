package com.hbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.entity.AboutUs;
import com.hbc.repo.AboutUsRepo;
import com.hbc.service.AdminAboutUsService;

@Service
public class AdminAboutUsServiceImpl implements AdminAboutUsService {

	@Autowired
	AboutUsRepo repo;

	@Override
	public AboutUs get() {
		List<AboutUs> result = repo.findAll();
		return result.isEmpty() ? null : result.get(0);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insert(AboutUs aboutUs) throws Exception {
		if (aboutUs == null) {
			throw new Exception("Thông tin about-us không được null.");
		}

		repo.deleteAll();
		
		repo.insert(aboutUs.getContent(), aboutUs.getPerson1(), aboutUs.getRole1(), aboutUs.getImg1(),
				aboutUs.getPerson2(), aboutUs.getRole2(), aboutUs.getImg2(), aboutUs.getPerson3(), aboutUs.getRole3(),
				aboutUs.getImg3(), aboutUs.getPerson4(), aboutUs.getRole4(), aboutUs.getImg4(), aboutUs.getPerson5(),
				aboutUs.getRole5(), aboutUs.getImg5(), aboutUs.getPerson6(), aboutUs.getRole6(), aboutUs.getImg6());
	}
}
