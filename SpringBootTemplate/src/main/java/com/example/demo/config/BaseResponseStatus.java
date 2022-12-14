package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_POST_INVALID_TITLE(false, 2018, "상품명을 2글자 이상 입력해주세요."),
    POST_POST_EMPTY_CATEGORY(false, 2019, "카테고리를 선택해주세요."),
    POST_POST_EMPTY_PRICE(false, 2020, "가격을 입력해주세요."),
    POST_POST_EMPTY_POST_IMG(false, 2021, "상품 사진을 등록해주세요."),
    POST_POST_EMPTY_TAG(false, 2022, "태그를 입력해주세요."),
    POST_POST_NOT_ENOUGH_PRICE(false, 2023, "100원 이상 입력해주세요."),
    POST_POST_OVER_POST_IMG(false, 2024, "입력 가능한 사진 갯수를 초과하였습니다."),
    POST_BRAND_EMPTY_IMG(false, 2026, "브랜드 사진을 등록해주세요."),
    POST_BRAND_EMPTY_NAME(false, 2027, "브랜드 한국어 이름을 입력해주세요."),
    POST_BRAND_EMPTY_ENG_NAME(false, 2028, "브랜드 영어 이름을 입력해주세요."),
    POST_REVIEW_INVALID_STAR_NUM(false, 2031, "별 개수를 정해주세요."),
    POST_REVIEW_EMPTY(false, 2032, "리뷰를 작성해주세요."),
    POST_ACCOUNT_EMPTY_ACCOUNT_HOLDER(false, 2033, "예금주를 입력해주세요."),
    POST_ACCOUNT_EMTPY_BANK(false, 2034, "은행을 선택해주세요."),
    POST_ACCOUNT_EMPTY_ACCOUNT_NUM(false, 2035, "계좌번호를 입력해주세요."),
    POST_AUTHENTICATION_FAILURE_PHONENUM(false, 2036, "전화번호를 입력해주세요."),
    POST_AUTHENTICATION_FAILURE(false, 2037, "인증에 실패하였습니다."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    PATCH_EMPTY_BAN(false, 3015, "차단 목록에 존재하지 않는 상점입니다."),
//    PAGE_LIMIT(false, 3016, "존재하지 않는 페이지입니다."),
    POST_OVER_ACCOUNT_QUANTITY(false, 3017, "계좌는 두개까지 입력가능합니다."),
    PATCH_ALREADY_DELETED_ACCOUNT(false, 3018, "이미 삭제된 계좌입니다."),
    POST_BAN_SECESSION_USER(false, 3019, "존재하지 않는 상점입니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    PATCH_DELETE_FAIL_BRAND(false, 4016, "브랜드 삭제에 실패하였습니다."); // 브랜드 삭제는 관리자 부분이므로 무시



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
