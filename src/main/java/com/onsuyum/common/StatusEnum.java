package com.onsuyum.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum StatusEnum {

    // 200
    SUCCESS_GET_CATEGORIES(OK, "카테고리들 불러오기 성공"),
    SUCCESS_GET_CATEGORY(OK, "카테고리 불러오기 성공"),
    SUCCESS_GET_MENUS(OK, "메뉴들 불러오기 성공"),
    SUCCESS_GET_RESTAURANTS(OK, "음식점들 불러오기 성공"),
    SUCCESS_GET_RESTAURANT(OK, "음식점 불러오기 성공"),
    SUCCESS_GET_RANDOM_RESTAURANT(OK, "임의의 음식점 불러오기 성공"),

    // 201
    SUCCESS_CREATE_MENUS(CREATED, "메뉴들 정보 저장 성공"),
    SUCCESS_CREATE_RESTAURANT(CREATED, "음식점 정보 저장 성공"),
    SUCCESS_CREATE_CATEGORIES_BY_RESTAURANT(CREATED, "해당 음식점의 카테고리들 정보 저장 성공"),

    // 204
    NO_CONTENT_CATEGORIES(NO_CONTENT, "카테고리들 정보 없음"),
    NO_CONTENT_MENUS(NO_CONTENT, "메뉴들 정보 없음"),
    NO_CONTENT_RESTAURANTS(NO_CONTENT, "음식점들 정보 없음"),

    // 400
    NEED_CATEGORY_VALUE(BAD_REQUEST, "카테고리를 한개 이상 요청 보내야 합니다."),

    // 401

    // 403
    RESTAURANT_TIME_NOT_VALID(FORBIDDEN, "음식점에 대해 접근할 수 있는 시간(5분)이 지났습니다."),
    FORBIDDEN_RESTAURANT(FORBIDDEN, "이 음식점에 대해 권한이 없습니다."),

    // 404
    CATEGORY_NOT_FOUND(NOT_FOUND, "해당 ID를 가진 카테고리 없음"),
    RESTAURANT_NOT_FOUND(NOT_FOUND, "해당 ID를 가진 음식점 없음"),
    IMAGE_NOT_FOUND(NOT_FOUND, "해당 ID를 가진 이미지파일 없음"),
    LOCAL_FILE_NOT_FOUND(NOT_FOUND, "로컬 저장소에 파일이 존재하지 않음"),

    // 500
    COULD_NOT_LOAD_IMAGE_FILE(INTERNAL_SERVER_ERROR, "이미지 파일을 읽어오는 중에 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    StatusEnum(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
