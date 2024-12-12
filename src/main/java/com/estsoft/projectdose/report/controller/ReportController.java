package com.estsoft.projectdose.report.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
	private final ReportService reportService;

	@GetMapping("/pdf")
	public ResponseEntity<byte[]> generatePdfReport(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Users users = (Users)authentication.getPrincipal();

		byte[] pdfContent = reportService.generateMedicationReport(1L, startDate, endDate);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("filename", "medication_report.pdf");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
	}

	@PostMapping("/email")
	public ResponseEntity<String> sendPdfReportByEmail(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Users users = (Users)authentication.getPrincipal();

		reportService.sendPdfReportByEmail(1L, startDate, endDate);
		return ResponseEntity.ok("보고서가 이메일로 전송되었습니다.");
	}

}
