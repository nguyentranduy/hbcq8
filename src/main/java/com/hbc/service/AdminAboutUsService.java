package com.hbc.service;

import com.hbc.entity.AboutUs;

public interface AdminAboutUsService {

	AboutUs get();
	void insert(AboutUs aboutUs) throws Exception;
}
