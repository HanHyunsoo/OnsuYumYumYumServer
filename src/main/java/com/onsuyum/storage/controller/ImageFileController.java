package com.onsuyum.storage.controller;

import com.onsuyum.common.exception.LocalFileNotFoundException;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.storage.domain.service.ImageFileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Api(tags = "Image API")
public class ImageFileController {
    private final ImageFileService imageFileService;

    @GetMapping(path = "/{id}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Operation(
            summary = "해당 ID를 가진 이미지 파일 불러오기",
            description = "ID로 DB에 존재하는 이미지 파일을 불러와서 클라이언트에게 리소스 형태로 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 파일 보여주기 성공"),
                    @ApiResponse(responseCode = "404", description = "이미지 파일 정보 DB에 없거나 로컬 저장소에 이미지 파일이 없는 경우", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<Resource> display(@PathVariable Long id) {
        Resource resource = imageFileService.getResourceById(id);
        HttpHeaders headers = new HttpHeaders();

        try {
            String filePath = resource.getFile().getPath();
            headers.add("Content-type", Files.probeContentType(Path.of(filePath)));
        } catch (IOException e) {
            throw new LocalFileNotFoundException();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}

