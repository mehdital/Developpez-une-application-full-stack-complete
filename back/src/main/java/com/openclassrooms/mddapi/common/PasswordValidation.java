package com.openclassrooms.mddapi.common;

public final class PasswordValidation {

	private PasswordValidation() {}

	public static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";
}

