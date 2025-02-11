package com.example.library;

import com.example.library.dto.AuthorRequestDto;
import com.example.library.dto.AuthorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetAuthor() throws Exception {
        AuthorRequestDto requestDto = new AuthorRequestDto();
        ReflectionTestUtils.setField(requestDto, "name", "John Doe");
        String requestJson = objectMapper.writeValueAsString(requestDto);

        // POST /authors 호출 → 예상: name은 "John Doe" 포함, id는 생성됨
        String postResponse = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /authors → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"name\": \"John Doe\"}\n");
                })
                .andReturn().getResponse().getContentAsString();

        AuthorResponseDto created = objectMapper.readValue(postResponse, AuthorResponseDto.class);
        Long authorId = created.getId();

        // GET /authors/{id} 호출 → 예상: 동일한 id와 name "John Doe"
        mockMvc.perform(get("/authors/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("GET /authors/" + authorId + " → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": " + authorId + ", \"name\": \"John Doe\"}\n");
                });
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        // Author 생성
        AuthorRequestDto requestDto = new AuthorRequestDto();
        ReflectionTestUtils.setField(requestDto, "name", "Jane Doe");
        String postResponse = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /authors (for delete) → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"name\": \"Jane Doe\"}\n");
                })
                .andReturn().getResponse().getContentAsString();
        AuthorResponseDto created = objectMapper.readValue(postResponse, AuthorResponseDto.class);
        Long authorId = created.getId();

        // DELETE /authors/{id} 호출 → 예상: 빈 응답(또는 성공 메시지)
        mockMvc.perform(delete("/authors/{id}", authorId))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("DELETE /authors/" + authorId + " → Actual response: " + resp);
                    System.out.println("Expected response: (empty response or confirmation message)\n");
                });
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        // Author 생성
        AuthorRequestDto requestDto = new AuthorRequestDto();
        ReflectionTestUtils.setField(requestDto, "name", "Author For Books");
        String postResponse = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /authors (for books) → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"name\": \"Author For Books\"}\n");
                })
                .andReturn().getResponse().getContentAsString();
        AuthorResponseDto created = objectMapper.readValue(postResponse, AuthorResponseDto.class);
        Long authorId = created.getId();

        // GET /authors/{id}/books 호출 → 예상: 빈 배열
        mockMvc.perform(get("/authors/{id}/books", authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("GET /authors/" + authorId + "/books → Actual response: " + resp);
                    System.out.println("Expected response: []\n");
                });
    }
}
