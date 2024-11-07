package com.hbc.service;

import java.io.IOException;

import com.hbc.dto.pdf.PdfInputDto;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

public interface PdfExporterService {
	
	void exportPdf(HttpServletResponse response, PdfInputDto input) throws DocumentException, IOException;
}
