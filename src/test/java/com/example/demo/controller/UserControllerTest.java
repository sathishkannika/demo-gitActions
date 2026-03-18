package com.example.demo.controller;

import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ─────────────────────────────────────────────
    // GET /api/users
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/users — should return all seeded users")
    void getAllUsers_returnsOkWithUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("sathish")))
                .andExpect(jsonPath("$[1].name", is("mahizhan")));
    }
    // ─────────────────────────────────────────────
    // GET /api/users/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/users/1 — should return user with id 1")
    void getUserById_existingId_returnsUser() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("sathish1")))
                .andExpect(jsonPath("$.email", is("sathish@example.com")));
    }

    @Test
    @DisplayName("GET /api/users/999 — should return 404 for unknown id")
    void getUserById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // POST /api/users
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/users — should create user and return 201")
    void createUser_validPayload_returnsCreated() throws Exception {
        User newUser = new User(null, "Charlie", "charlie@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Charlie")))
                .andExpect(jsonPath("$.email", is("charlie@example.com")));
    }

    @Test
    @DisplayName("POST /api/users — should return 400 when name is blank")
    void createUser_blankName_returns400() throws Exception {
        User invalid = new User(null, "", "charlie@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/users — should return 400 when email is invalid")
    void createUser_invalidEmail_returns400() throws Exception {
        User invalid = new User(null, "Charlie", "not-an-email");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    // ─────────────────────────────────────────────
    // PUT /api/users/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/users/1 — should update existing user")
    void updateUser_existingId_returnsUpdatedUser() throws Exception {
        User updated = new User(null, "Alice Updated", "alice.updated@example.com");

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice Updated")))
                .andExpect(jsonPath("$.email", is("alice.updated@example.com")));
    }

    @Test
    @DisplayName("PUT /api/users/999 — should return 404 for unknown id")
    void updateUser_unknownId_returns404() throws Exception {
        User updated = new User(null, "Ghost", "ghost@example.com");

        mockMvc.perform(put("/api/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // DELETE /api/users/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/users/2 — should delete existing user and return 204")
    void deleteUser_existingId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/users/999 — should return 404 for unknown id")
    void deleteUser_unknownId_returns404() throws Exception {
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());
    }
}
