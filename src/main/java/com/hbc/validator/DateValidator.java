package com.hbc.validator;

import java.sql.Timestamp;

public class DateValidator {
	
	private DateValidator() {
		
	}

	public static boolean isValidPeriod(Timestamp startDate, Timestamp endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        return startDate.before(endDate);
    }
}
