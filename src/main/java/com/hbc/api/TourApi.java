package com.hbc.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.dto.tournament.TournamentInfoDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.tournament.submit.InvalidSubmitInfoException;
import com.hbc.exception.tournament.submit.InvalidSubmitPointKeyException;
import com.hbc.exception.tournament.submit.OutOfTimeException;
import com.hbc.exception.tournament.submit.SubmitInfoNotFoundException;
import com.hbc.service.PdfExporterService;
import com.hbc.service.TournamentDetailService;
import com.hbc.service.TournamentInfoService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/tour")
public class TourApi {

	@Autowired
	TournamentInfoService tourInfoService;

	@Autowired
	TournamentDetailService tourDetailService;
	
	@Autowired
	PdfExporterService pdfExporterService;

	@GetMapping("/list")
	public ResponseEntity<?> doGetList(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetList(currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> doGetListMe(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetListMe(currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	// /api/v1/tour/detail?tourId={tourId}
	@GetMapping("/detail")
	public ResponseEntity<?> doGetDetail(@RequestParam("tourId") long tourId, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TourDetailResponseDto> response = tourDetailService.findByTourIdAndUserId(tourId, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PostMapping("/submit")
	public ResponseEntity<?> doGetDetail(@RequestBody TourSubmitTimeRequestDto requestDto, HttpSession session,
			HttpServletResponse response) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			PdfInputDto pdfInputDto = tourDetailService.doSubmitTime(requestDto, currentUser.getId());

			response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=phieughinhan_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	        pdfExporterService.exportPdf(response, pdfInputDto);
			return ResponseEntity.ok().build();
		} catch (SubmitInfoNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (OutOfTimeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (InvalidSubmitPointKeyException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (InvalidSubmitInfoException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
