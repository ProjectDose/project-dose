package com.estsoft.projectdose.report.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.repository.DoseLogRepository;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.report.dto.DailyStatisticsResponse;
import com.estsoft.projectdose.report.dto.LogDetails;
import com.estsoft.projectdose.report.dto.MedicationReportDto;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final DoseLogRepository doseLogRepository;
	private final DoseScheduleRepository doseScheduleRepository;
	private final JavaMailSender mailSender;

	public Map<LocalDateTime, Integer> getMonthlyAchievementRates(Long userId) {
		List<Object[]> achievementRates = doseLogRepository.findMonthlyAchievementRates(userId);

		return achievementRates.stream()
			.collect(Collectors.toMap(
				result -> (LocalDateTime)result[0],  // 날짜
				result -> ((Number)result[1]).intValue()  // 달성률
			));
	}

	public DailyStatisticsResponse getDailyStatistics(Long userId, LocalDate selectedDate) {

		Double achievementRate = doseLogRepository.calculateDailyAchievementRate(userId, selectedDate);
		List<LogDetails> doseLogDetails = doseLogRepository.findDailyDoseLogs(userId, selectedDate);

		return new DailyStatisticsResponse(achievementRate, doseLogDetails);
	}


	public byte[] generateMedicationReport(Long userId, LocalDate startDate, LocalDate endDate) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			PdfWriter writer = new PdfWriter(baos);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);

			PdfFont koFont = PdfFontFactory.createFont("/static/fonts/NanumGothic.ttf", PdfEncodings.IDENTITY_H);
			document.setFont(koFont);

			document.add(new Paragraph("투약 통계 보고서")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(20)
				.setBold()
				.setFontColor(new DeviceRgb(0, 0, 128))); // 짙은 파란색

			document.add(new Paragraph(String.format("기간: %s ~ %s",
				startDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
				endDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))))
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(12)
				.setFontColor(new DeviceRgb(100, 100, 100))); // 회색

			document.add(new LineSeparator(new SolidLine(1f))
				.setMarginTop(10)
				.setMarginBottom(20));

			// 약 스케줄 테이블
			List<MedicationReportDto> medicationSchedules =
				doseScheduleRepository.findMedicationSchedules(userId, startDate, endDate);

			Table scheduleTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
			scheduleTable.setFont(koFont);
			scheduleTable.addHeaderCell(new Cell().add(new Paragraph("약 이름"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250)) // 연한 파란 배경
				.setTextAlignment(TextAlignment.CENTER));
			scheduleTable.addHeaderCell(new Cell().add(new Paragraph("복용 주기"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250))
				.setTextAlignment(TextAlignment.CENTER));
			scheduleTable.addHeaderCell(new Cell().add(new Paragraph("복용량"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250))
				.setTextAlignment(TextAlignment.CENTER));

			for (MedicationReportDto schedule : medicationSchedules) {
				String scheduleInfo = "";
				Map<String, Object> repeatData = schedule.getRepeatData();

				if (repeatData.containsKey("repeatInterval") && repeatData.get("repeatInterval") != null) {
					scheduleInfo = schedule.getRepeatInterval() + "일마다";
				} else if (repeatData.containsKey("daysOfWeek") && repeatData.get("daysOfWeek") != null) {
					Map<String, String> daysMap = (Map<String, String>) repeatData.get("daysOfWeek");
					scheduleInfo = daysMap.values().stream()
						.map(this::convertDayToKorean)
						.collect(Collectors.joining(", "));
				}

				scheduleTable.addCell(schedule.getMedicationName());
				scheduleTable.addCell(scheduleInfo);
				scheduleTable.addCell(schedule.getDosage());
			}

			document.add(new Paragraph("약물 스케줄")
				.setFontSize(16)
				.setFontColor(new DeviceRgb(0, 0, 128)));
			document.add(scheduleTable);

			// 일일 복용 현황 테이블
			List<Object[]> dailyMedicationStatus =
				doseLogRepository.findDailyMedicationStatus(userId, startDate, endDate);

			Table dailyStatusTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
			dailyStatusTable.setFont(koFont);
			dailyStatusTable.addHeaderCell(new Cell().add(new Paragraph("날짜"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250))
				.setTextAlignment(TextAlignment.CENTER));
			dailyStatusTable.addHeaderCell(new Cell().add(new Paragraph("약 이름"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250))
				.setTextAlignment(TextAlignment.CENTER));
			dailyStatusTable.addHeaderCell(new Cell().add(new Paragraph("복용 현황"))
				.setBackgroundColor(new DeviceRgb(230, 230, 250))
				.setTextAlignment(TextAlignment.CENTER));

			LocalDate currentDate = null;
			for (Object[] status : dailyMedicationStatus) {
				LocalDate date;
				if (status[0] instanceof java.sql.Date) {
					date = ((java.sql.Date) status[0]).toLocalDate();
				} else if (status[0] instanceof LocalDate) {
					date = (LocalDate) status[0];
				} else {
					continue;
				}

				// 날짜 변경 시 구분선 추가
				if (currentDate == null || !currentDate.equals(date)) {
					if (currentDate != null) {
						document.add(new LineSeparator(new SolidLine(0.5f))
							.setMarginTop(10)
							.setMarginBottom(10));
					}
					currentDate = date;
				}

				String medicationName = (String)status[1];
				boolean isTaken = (Boolean)status[2];

				dailyStatusTable.addCell(date.toString());
				dailyStatusTable.addCell(medicationName);
				dailyStatusTable.addCell(isTaken ? "복용 완료" : "미복용");
			}


			document.add(new Paragraph("일일 복용 현황")
				.setFontSize(16)
				.setFontColor(new DeviceRgb(0, 0, 128)));
			document.add(dailyStatusTable);

			document.close();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("PDF 생성 중 오류 발생", e);
		}
	}

	private String convertDayToKorean(String engDay) {
		switch (engDay) {
			case "MONDAY":
				return "월";
			case "TUESDAY":
				return "화";
			case "WEDNESDAY":
				return "수";
			case "THURSDAY":
				return "목";
			case "FRIDAY":
				return "금";
			case "SATURDAY":
				return "토";
			case "SUNDAY":
				return "일";
			default:
				return engDay;
		}
	}

	public void sendPdfReportByEmail(Long userId, LocalDate startDate, LocalDate endDate) {
		try {
			byte[] pdfContent = generateMedicationReport(userId, startDate, endDate);

			// // 사용자 이메일 조회
			// String userEmail = getUserEmail(userId);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo("arc552@naver.com");
			helper.setSubject("삐약이에서 발송한 투약 통계 보고서 입니다.");
			helper.setText("첨부된 PDF 파일을 확인해 주세요.");

			helper.addAttachment("medication_report.pdf",
				new ByteArrayResource(pdfContent));

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송 중 오류 발생", e);
		}
	}

}