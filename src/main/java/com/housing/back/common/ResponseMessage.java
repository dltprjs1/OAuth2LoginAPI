package com.housing.back.common;

public interface ResponseMessage {

	String SUCCESS = "Success.";
	String VALIDATION_FAIL = "Vaidation failed.";
	String DUPLICATE_ID = "Duplicate Id.";
	String SIGN_IN_FAIL = "Login information mismatch.";
	String CERTIFICATION_FAIL = "Certification failed.";
	String DATABASE_ERROR = "Database error.";
	String MAIL_FAIL = "Mail send failed.";
}
