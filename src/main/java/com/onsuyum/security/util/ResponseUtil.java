package com.onsuyum.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
            .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
            .build();

    public static void setResponse(HttpServletResponse response, StatusEnum statusEnum) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusEnum.getHttpStatus().value());

        FailureResponseBody body = FailureResponseBody.builder()
                .status(statusEnum.getHttpStatus().value())
                .statusDetail(statusEnum.getHttpStatus().name())
                .code(statusEnum.name())
                .message(statusEnum.getDetail())
                .build();

        String jsonBody = objectMapper.writeValueAsString(body);

        response.getWriter().print(jsonBody);
    }
}
