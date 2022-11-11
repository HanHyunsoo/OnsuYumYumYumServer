package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BabfulMenuDateAlreadyExistsException extends CustomAbstractException {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BabfulMenuDateAlreadyExistsException(LocalDate localDate) {
        super(StatusEnum.BABFUL_MENU_DATE_ALREADY_EXISTS
                .setDetailAndReturn(
                        String.format("이미 해당 날짜(%s)에 밥풀 식단 데이터가 존재합니다.",
                                localDate.format(formatter))
                ));
    }
}
