package com.service.freeblog_admin.web.error.constants;

public enum ServiceExceptionMessage {
    // 사용자 인증,처리 관련 에러 메시지
    NOT_CHECKED_EMAIL("이메일 중복 검사를 완료하지 않았습니다"),
    AUTH_KEY_NOT_FOUND("인증키가 존재하지 않거나 만료되었습니다. 새로 발급 받아주세요."),
    AUTH_VALID_KEY_MISMATCH("발급 된 인증키 정보가 일치하지 않습니다."),
    RE_PASSWORD_MISMATCH("비밀번호, 재비밀번호 입력 정보가 일치하지 않습니다."),
    COINCIDE_WITH_EACH_PASSWORD("입력한 비밀번호와 변경 전 비밀번호가 일치합니다"),
    SECRET_KEY_MISMATCH("비밀키 정보가 일치하지 않습니다."),
    NOT_CHECKED_ID("ID 중복 검사를 완료하지 않았습니다."),
    ALREADY_SAME_ID("이미 사용중인 ID 입니다."),
    ALREADY_SAME_EMAIL("이미 사용중인 이메일 입니다."),
    ALREADY_AUTHENTICATED_ACCOUNT("이미 이메일 인증 된 계정 입니다."),
    ACCOUNT_INFO_NOT_FOUND("계정 정보가 존재하지 않습니다."),
    MISMATCH_EMAIL("접속 중인 계정의 이메일 정보와 입력 값이 일치하지 않습니다."),
    NOT_LOGIN_STATUS_ACCESS("로그인 되어있지 않은 상태에서 접속하였습니다."),
    MISMATCH_ID("접속 중인 계정의 아이디 정보와 입력 값이 일치하지 않습니다."),
    MISMATCH_PASSWORD("접속 중인 계정의 비밀번호 정보와 입력 값이 일치하지 않습니다."),
    ID_PW_WRONG("아이디 또는 비밀번호가 틀립니다."),
    LOCK_ACCOUNT("잠긴 계정입니다."),
    DEACTIVATE_ACCOUNT("비활성화된 계정입니다."),
    EXPIRED_ACCOUNT("만료된 계정입니다"),
    EXPIRED_PASSWORD("비밀번호가 만료되었습니다."),
    NOT_AUTHENTICATED_ACCOUNT("이메일 인증이 완료되지 않은 계정 입니다."),
    WITHDRAW_ACCOUNT("탈퇴 된 계정입니다."),
    STOP_ACCOUNT("정지 된 계정입니다."),
    NOT_AUTH_ACCESS("인증되지 않은 접속입니다."),
    FAILED_AUTHENTICATION("인증에 실패하였습니다.");
    private final String message;

    ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
