package com.hbc.service.impl;

import java.awt.Color;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.service.PdfExporterService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdfExporterServiceImpl implements PdfExporterService {

	@Override
	public void exportPdf(HttpServletResponse response, PdfInputDto input) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.defaultEncoding);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("PHIẾU GHI NHẬN THÔNG TIN", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(70f);
		table.setWidths(new float[] { 3f, 3f});
		table.setSpacingBefore(10);

		writeTable(table, input);

		document.add(table);
		document.close();
	}
	
	private void writeTable(PdfPTable table, PdfInputDto input) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.defaultEncoding);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Mã giải đua", font));
		table.addCell(cell);
		table.addCell(String.valueOf(input.getTourId()));

		cell.setPhrase(new Phrase("Mã căn cứ", font));
		table.addCell(cell);
		table.addCell(input.getPointCode());

		cell.setPhrase(new Phrase("Mã kiềng", font));
		table.addCell(cell);
        table.addCell(input.getBirdCode());

		cell.setPhrase(new Phrase("Mã bí mật", font));
		table.addCell(cell);
        table.addCell(input.getPointKey());

		cell.setPhrase(new Phrase("Thời gian ghi nhận", font));
		table.addCell(cell);
        table.addCell(input.getPointSubmitTime());
	}
}
