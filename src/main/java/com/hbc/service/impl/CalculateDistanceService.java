package com.hbc.service.impl;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hbc.dto.tourlocation.CalDistanceRequestDto;
import com.hbc.dto.tourlocation.CalDistanceResponseDto;
import com.hbc.exception.calculatedistance.InvalidCoorFormatException;
import com.hbc.util.MathUtil;

@Service
public class CalculateDistanceService {
	
	private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM
	
	private static final String REGEX = "^\\d{1,3}\\.\\d{1,3};\\d{1,3}\\.\\d{1,3}$";
	private static final Pattern COORDINATE_PATTERN = Pattern.compile(REGEX);

	public CalDistanceResponseDto calculateDistance(CalDistanceRequestDto calDistanceRequestDto)
			throws InvalidCoorFormatException {
		validateFormat(calDistanceRequestDto);
		CalDistanceResponseDto calDistanceResponseDto = new CalDistanceResponseDto();
		calDistanceResponseDto.setStartPoint(0D);
		String[] startPoint = calDistanceRequestDto.getStartPoint().split(";");
		String[] endPoint = calDistanceRequestDto.getEndPoint().split(";");
		
		if (ObjectUtils.isEmpty(calDistanceRequestDto.getPoint1())) {
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(startPoint[0]), Double.parseDouble(startPoint[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
			return calDistanceResponseDto;
		} else {
			String[] point1 = calDistanceRequestDto.getPoint1().split(";");
			calDistanceResponseDto.setPoint1(distance(Double.parseDouble(startPoint[0]), Double.parseDouble(startPoint[1]),
					Double.parseDouble(point1[0]), Double.parseDouble(point1[1])));
		}
		
		if (ObjectUtils.isEmpty(calDistanceRequestDto.getPoint2())) {
			String[] point1 = calDistanceRequestDto.getPoint1().split(";");
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(point1[0]), Double.parseDouble(point1[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
			return calDistanceResponseDto;
		} else {
			String[] point1 = calDistanceRequestDto.getPoint1().split(";");
			String[] point2 = calDistanceRequestDto.getPoint2().split(";");
			calDistanceResponseDto.setPoint2(distance(Double.parseDouble(point1[0]), Double.parseDouble(point1[1]),
					Double.parseDouble(point2[0]), Double.parseDouble(point2[1])));
		}
		
		if (ObjectUtils.isEmpty(calDistanceRequestDto.getPoint3())) {
			String[] point2 = calDistanceRequestDto.getPoint2().split(";");
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(point2[0]), Double.parseDouble(point2[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
			return calDistanceResponseDto;
		} else {
			String[] point2 = calDistanceRequestDto.getPoint2().split(";");
			String[] point3 = calDistanceRequestDto.getPoint3().split(";");
			calDistanceResponseDto.setPoint3(distance(Double.parseDouble(point2[0]), Double.parseDouble(point2[1]),
					Double.parseDouble(point3[0]), Double.parseDouble(point3[1])));
		}
		
		if (ObjectUtils.isEmpty(calDistanceRequestDto.getPoint4())) {
			String[] point3 = calDistanceRequestDto.getPoint3().split(";");
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(point3[0]), Double.parseDouble(point3[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
			return calDistanceResponseDto;
		} else {
			String[] point3 = calDistanceRequestDto.getPoint3().split(";");
			String[] point4 = calDistanceRequestDto.getPoint4().split(";");
			calDistanceResponseDto.setPoint4(distance(Double.parseDouble(point3[0]), Double.parseDouble(point3[1]),
					Double.parseDouble(point4[0]), Double.parseDouble(point4[1])));
		}
		
		if (ObjectUtils.isEmpty(calDistanceRequestDto.getPoint5())) {
			String[] point4 = calDistanceRequestDto.getPoint4().split(";");
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(point4[0]), Double.parseDouble(point4[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
			return calDistanceResponseDto;
		} else {
			String[] point4 = calDistanceRequestDto.getPoint4().split(";");
			String[] point5 = calDistanceRequestDto.getPoint5().split(";");
			calDistanceResponseDto.setPoint5(distance(Double.parseDouble(point4[0]), Double.parseDouble(point4[1]),
					Double.parseDouble(point5[0]), Double.parseDouble(point5[1])));
			calDistanceResponseDto.setEndPoint(distance(Double.parseDouble(point5[0]), Double.parseDouble(point5[1]),
					Double.parseDouble(endPoint[0]), Double.parseDouble(endPoint[1])));
		}
		
		return calDistanceResponseDto;
	}
	
	private void validateFormat(CalDistanceRequestDto calDistanceRequestDto) throws InvalidCoorFormatException {
		for (String coordinate : new String[] {calDistanceRequestDto.getStartPoint(),
				calDistanceRequestDto.getPoint1(), calDistanceRequestDto.getPoint2(), calDistanceRequestDto.getPoint3(),
				calDistanceRequestDto.getPoint4(), calDistanceRequestDto.getPoint5(), calDistanceRequestDto.getEndPoint()}) {
			if (coordinate != null && !coordinate.isEmpty() && !COORDINATE_PATTERN.matcher(coordinate).matches()) {
				throw new InvalidCoorFormatException("400", "Định dạng tọa độ không hợp lệ: " + convertPointName(coordinate));
			}
		}
	}
	
	private double distance(double startLat, double startLong, double endLat, double endLong) {
		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return MathUtil.rounded3(EARTH_RADIUS * c);
	}

	private double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}
	
	private String convertPointName(String pointName) {
		switch (pointName) {
			case "startPoint":
				return "Điểm khởi đầu";
			case "point1":
				return "Chặng 1";
			case "point2":
				return "Chặng 2";
			case "point3":
				return "Chặng 3";
			case "point4":
				return "Chặng 4";
			case "point5":
				return "Chặng 5";
			default:
				return "Điểm kết thúc";
		}
	}
}
