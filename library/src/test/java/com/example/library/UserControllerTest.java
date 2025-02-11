package com.example.library;

import com.example.library.dto.UserRequestDto;
import com.example.library.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetUser() throws Exception {
        // UserRequestDto 생성 및 email 필드 세팅
        UserRequestDto requestDto = new UserRequestDto();
        ReflectionTestUtils.setField(requestDto, "email", "user@example.com");
        String requestJson = objectMapper.writeValueAsString(requestDto);

        String postResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /users → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"email\": \"user@example.com\"}\n");
                })
                .andReturn().getResponse().getContentAsString();

        UserResponseDto created = objectMapper.readValue(postResponse, UserResponseDto.class);
        Long userId = created.getId();

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("GET /users/" + userId + " → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": " + userId + ", \"email\": \"user@example.com\"}\n");
                });
    }

    @Test
    public void testGetUsers() throws Exception {
        // 여러 사용자 생성
        UserRequestDto user1 = new UserRequestDto();
        ReflectionTestUtils.setField(user1, "email", "user1@example.com");
        UserRequestDto user2 = new UserRequestDto();
        ReflectionTestUtils.setField(user2, "email", "user2@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /users (user1) → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"email\": \"user1@example.com\"}\n");
                });
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("POST /users (user2) → Actual response: " + resp);
                    System.out.println("Expected response: {\"id\": <generated>, \"email\": \"user2@example.com\"}\n");
                });

        // GET /users → 전체 사용자 목록 조회
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(result -> {
                    String resp = result.getResponse().getContentAsString();
                    System.out.println("GET /users → Actual response: " + resp);
                    System.out.println("Expected response: [ {\"id\": <generated>, \"email\": \"user1@example.com\"}, {\"id\": <generated>, \"email\": \"user2@example.com\"} ]\n");
                });
    }
}
