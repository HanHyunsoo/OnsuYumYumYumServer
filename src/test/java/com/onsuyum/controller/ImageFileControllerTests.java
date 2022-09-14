package com.onsuyum.controller;

import com.onsuyum.storage.controller.ImageFileController;
import com.onsuyum.storage.domain.service.ImageFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ImageFileController.class)
public class ImageFileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageFileService imageFileService;

    @Test
    @DisplayName("display 성공")
    void successDisplay() throws Exception {
        // given
        Resource resource = new FileSystemResource("diagram.png");

        given(imageFileService.getResourceById(anyLong()))
                .willReturn(resource);

        // when
        ResultActions perform = mockMvc.perform(get("/images/{id}", anyLong()));

        // then
        perform.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_PNG));
    }

    // TODO : 2022.09.14 Global Exception 정의 후 테스트 진행해야함.
//    @Test
//    @DisplayName("display 실패(db 테이블에 해당하는 id의 데이터가 존재하지 않을 때)")
//    void failureDisplayWhenImageNotFound() {
//    }

//    @Test
//    @DisplayName("display 실패(로컬에 파일이 존재하지 않을 때)")
//    void failureDisplayWhenImageNotExistsInLocal() {
//    }
}
