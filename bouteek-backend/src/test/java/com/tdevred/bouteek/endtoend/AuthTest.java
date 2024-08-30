package com.tdevred.bouteek.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import com.tdevred.bouteek.authentication.DTOs.LoginResponse;
import com.tdevred.bouteek.authentication.DTOs.LoginUserDto;
import com.tdevred.bouteek.authentication.DTOs.RegisterUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    private final Faker faker = new Faker();

    private final ObjectMapper objectMapper = new ObjectMapper();


    private String getAuthenticatedToken() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(faker.internet().safeEmailAddress());
        registerUserDto.setPassword(faker.internet().password(10, 15));
        registerUserDto.setFullName(faker.internet().uuid());

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        String username = JsonPath.read(responseJson, "username");
        String password = JsonPath.read(responseJson, "password");

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword(registerUserDto.getPassword());

        json = objectMapper.writeValueAsString(loginUserDto);

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        String loginResponseJson = mvcResultLogin.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(loginResponseJson, LoginResponse.class);
        return loginResponse.getToken();
    }

    @Test
    public void registersUser() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(faker.internet().safeEmailAddress());
        registerUserDto.setPassword(faker.internet().password(10, 15));
        registerUserDto.setFullName(faker.internet().uuid());

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("fullName").value(registerUserDto.getFullName()))
                .andExpect(jsonPath("email").value(registerUserDto.getEmail()))
                .andExpect(jsonPath("password").hasJsonPath())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void authenticates() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(faker.internet().safeEmailAddress());
        registerUserDto.setPassword(faker.internet().password(10, 15));
        registerUserDto.setFullName(faker.internet().uuid());

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword(registerUserDto.getPassword());

        json = objectMapper.writeValueAsString(loginUserDto);

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").hasJsonPath())
                .andExpect(jsonPath("expiresIn").hasJsonPath())
                .andReturn();
    }

    @Test
    public void refusesAuthNoEmail() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setPassword(faker.internet().password(10, 15));
        registerUserDto.setFullName(faker.internet().uuid());

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(jsonPath("fullName").doesNotHaveJsonPath())
                .andExpect(jsonPath("email").hasJsonPath())
                .andReturn();
    }

    @Test
    public void refusesAuthNoFullName() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(faker.internet().safeEmailAddress());
        registerUserDto.setPassword(faker.internet().password(10, 15));

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(jsonPath("fullName").hasJsonPath())
                .andExpect(jsonPath("email").doesNotHaveJsonPath())
                .andReturn();
    }

    @Test
    public void refusesAuthNoPassword() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(faker.internet().safeEmailAddress());
        registerUserDto.setFullName(faker.internet().uuid());

        String json = objectMapper.writeValueAsString(registerUserDto);

        MvcResult mvcResult = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("password").hasJsonPath())
                .andExpect(jsonPath("fullName").doesNotHaveJsonPath())
                .andExpect(jsonPath("email").doesNotHaveJsonPath())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
