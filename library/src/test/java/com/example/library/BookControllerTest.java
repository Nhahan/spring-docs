package com.example.library;

import com.example.library.dto.AuthorRequestDto;
import com.example.library.dto.AuthorResponseDto;
import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetBook() throws Exception {
        Long authorId1 = createAuthor("Author One");
        Long authorId2 = createAuthor("Author Two");

        // BookRequestDto 생성 및 필드 세팅
        BookRequestDto bookRequest = new BookRequestDto();
        ReflectionTestUtils.setField(bookRequest, "title", "New Book");
        ReflectionTestUtils.setField(bookRequest, "authorIds", Arrays.asList(authorId1, authorId2));

        String requestJson = objectMapper.writeValueAsString(bookRequest);
        String postResponse = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.authors", hasSize(2)))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    // id는 <generated>로 표시하고, 나머지는 하드코딩된 값으로 예상 결과 출력
                    String expected = "{\"id\": <generated>, \"title\": \"New Book\", " +
                            "\"authors\": [{\"id\": <generated>, \"name\": \"Author One\"}, " +
                            "{\"id\": <generated>, \"name\": \"Author Two\"}]}";
                    System.out.println("POST /books → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                })
                .andReturn().getResponse().getContentAsString();

        BookResponseDto bookResponse = objectMapper.readValue(postResponse, BookResponseDto.class);
        Long bookId = bookResponse.getId();

        // GET /books/{id} 호출
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.authors", hasSize(2)))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    String expected = "{\"id\": <generated>, \"title\": \"New Book\", " +
                            "\"authors\": [{\"id\": <generated>, \"name\": \"Author One\"}, " +
                            "{\"id\": <generated>, \"name\": \"Author Two\"}]}";
                    System.out.println("GET /books/" + bookId + " → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                });
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long authorId1 = createAuthor("Initial Author");
        Long authorId2 = createAuthor("New Author");

        // Book 생성 (초기에는 authorId1만 포함)
        BookRequestDto createRequest = new BookRequestDto();
        ReflectionTestUtils.setField(createRequest, "title", "Original Title");
        ReflectionTestUtils.setField(createRequest, "authorIds", Collections.singletonList(authorId1));
        String createJson = objectMapper.writeValueAsString(createRequest);
        String createResponse = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    String expected = "{\"id\": <generated>, \"title\": \"Original Title\", " +
                            "\"authors\": [{\"id\": <generated>, \"name\": \"Initial Author\"}]}";
                    System.out.println("POST /books (for update) → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                })
                .andReturn().getResponse().getContentAsString();
        BookResponseDto created = objectMapper.readValue(createResponse, BookResponseDto.class);
        Long bookId = created.getId();

        // updateBook 요청: title 및 authorIds 변경 (새로 authorId2로 설정)
        BookRequestDto updateRequest = new BookRequestDto();
        ReflectionTestUtils.setField(updateRequest, "title", "Updated Title");
        ReflectionTestUtils.setField(updateRequest, "authorIds", Collections.singletonList(authorId2));
        String updateJson = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.authors", hasSize(1)))
                .andExpect(jsonPath("$.authors[0].id").value(authorId2.intValue()))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    String expected = "{\"id\": <generated>, \"title\": \"Updated Title\", " +
                            "\"authors\": [{\"id\": <generated>, \"name\": \"New Author\"}]}";
                    System.out.println("PUT /books/" + bookId + " → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                });
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long authorId = createAuthor("Author For Delete");
        BookRequestDto createRequest = new BookRequestDto();
        ReflectionTestUtils.setField(createRequest, "title", "Book To Delete");
        ReflectionTestUtils.setField(createRequest, "authorIds", Collections.singletonList(authorId));
        String createResponse = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    String expected = "{\"id\": <generated>, \"title\": \"Book To Delete\", " +
                            "\"authors\": [{\"id\": <generated>, \"name\": \"Author For Delete\"}]}";
                    System.out.println("POST /books (for delete) → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                })
                .andReturn().getResponse().getContentAsString();
        BookResponseDto created = objectMapper.readValue(createResponse, BookResponseDto.class);
        Long bookId = created.getId();

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("DELETE /books/" + bookId + " → Actual response: " + resp);
                    System.out.println("Expected response: (empty response or confirmation message)\n");
                });
    }

    // 헬퍼 메서드: Author 생성 후 id 반환
    private Long createAuthor(String name) throws Exception {
        AuthorRequestDto authorRequest = new AuthorRequestDto();
        ReflectionTestUtils.setField(authorRequest, "name", name);
        String authorJson = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    String expected = "{\"id\": <generated>, \"name\": \"" + name + "\"}";
                    System.out.println("POST /authors → Actual response: " + resp);
                    System.out.println("Expected response: " + expected + "\n");
                })
                .andReturn().getResponse().getContentAsString();
        AuthorResponseDto authorResponse = objectMapper.readValue(authorJson, AuthorResponseDto.class);
        return authorResponse.getId();
    }
}
