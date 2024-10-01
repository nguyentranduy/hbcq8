package com.hbc.util;

public class MathUtil {
	
	private MathUtil() {
		
	}
	
	public static double rounded3(double value) {
		return (double) Math.round(value * 1000) / 1000;
	}
}
