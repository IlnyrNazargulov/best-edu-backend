package ru.ilnyrdiplom.bestedu.facade.model.enums;

public enum Role {
    ROLE_ANONYMOUS,
    ROLE_EMAIL_VERIFIED,
    ROLE_ADMIN,
    ROLE_TEACHER,
    ROLE_STUDENT;

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String EMAIL_VERIFIED = "ROLE_EMAIL_VERIFIED";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String TEACHER = "ROLE_TEACHER";
    public static final String STUDENT = "ROLE_STUDENT";
}
