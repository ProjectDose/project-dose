package com.estsoft.projectdose.report.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.report.service.ReportService;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
	private final ReportService reportService;
	private final CustomUserDetailsService userDetailsService;

	@GetMapping("/pdf")
	public ResponseEntity<byte[]> generatePdfReport(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
		byte[] pdfContent = reportService.generateMedicationReport(startDate, endDate);

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
		reportService.sendPdfReportByEmail(startDate, endDate);
		return ResponseEntity.ok("보고서가 이메일로 전송되었습니다.");
	}

}
