package com.tdevred.bouteek.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import com.tdevred.bouteek.authentication.DTOs.LoginResponse;
import com.tdevred.bouteek.authentication.DTOs.LoginUserDto;
import com.tdevred.bouteek.authentication.DTOs.RegisterUserDto;
import com.tdevred.bouteek.business.DTO.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductTest {

    @Autowired
    private MockMvc mockMvc;

    private final Faker faker = new Faker();

    private final ObjectMapper objectMapper = new ObjectMapper();


    private long firstCategoryId;
    private String firstCategoryName;
    private long secondCategoryId;
    private String secondCategoryName;

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

    // Ajouter une catégorie spéciale pour ces tests, histoire d'assurer la reproductibilité des tests
    // et ne pas se baser sur des données qui existent actuellement en base
    @BeforeEach
    public void createTwoCategories() throws Exception {
        CategoryDTO firstCategory = createCategory();
        CategoryDTO secondCategory = createCategory();

        firstCategoryId = firstCategory.getId();
        firstCategoryName = firstCategory.getName();
        secondCategoryId = secondCategory.getId();
        secondCategoryName = secondCategory.getName();
    }

    public Pair<LoginUserDto, String> createAdminAccount() {
        return new ImmutablePair<>(new LoginUserDto(), "a");
    }

    public void deleteAccount(long account_id) {

    }

    public CategoryCreationDTO getCategory() {
        return new CategoryCreationDTO(faker.commerce().department(), faker.lorem().characters(50, 250));
    }

    private CategoryDTO createCategory() throws Exception {
        String token = getAuthenticatedToken();

        CategoryCreationDTO categoryDTO = getCategory();
        String json = objectMapper.writeValueAsString(categoryDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CategoryDTO.class);
    }

    public ProductCreationDTO getProduct() {
        return new ProductCreationDTO(faker.commerce().productName(), Double.parseDouble(faker.commerce().price().replaceAll(",", ".")), faker.lorem().characters(50, 250), firstCategoryId);
    }

    public ProductDTO createProduct() throws Exception {
        String token = getAuthenticatedToken();

        ProductCreationDTO productCreationDTO = getProduct();
        String json = objectMapper.writeValueAsString(productCreationDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        String token = getAuthenticatedToken();

        ProductCreationDTO product = getProduct();
        String json = objectMapper.writeValueAsString(product);
        this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("price").value(product.getPrice()))
                .andExpect(jsonPath("description").value(product.getDescription()))
                .andExpect(jsonPath("category").value(firstCategoryName))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        String token = getAuthenticatedToken();
        ProductDTO product = createProduct();
        long product_id = product.getId();
        this.mockMvc.perform(delete(STR."/products/\{product_id}").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        this.mockMvc.perform(get(STR."/products/\{product_id}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldModifyProduct() throws Exception {
        String token = getAuthenticatedToken();
        long product_id = createProduct().getId();

        ProductModificationDTO productModificationDTO = new ProductModificationDTO();
        productModificationDTO.setName(faker.commerce().productName());
        productModificationDTO.setPrice(Double.parseDouble(faker.commerce().price().replaceAll(",", ".")));
        productModificationDTO.setDescription(faker.lorem().characters(50, 250));
        productModificationDTO.setCategory(secondCategoryId);
        this.mockMvc.perform(patch(STR."/products/\{product_id}").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productModificationDTO)).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STR."/products/\{product_id}"))
                .andExpect(jsonPath("name").value(productModificationDTO.getName()))
                .andExpect(jsonPath("price").value(productModificationDTO.getPrice()))
                .andExpect(jsonPath("description").value(productModificationDTO.getDescription()))
                .andExpect(jsonPath("category").value(secondCategoryName))
                .andExpect(status().isOk());
    }

    @Test
    void shouldModifyNothingProduct() throws Exception {
        String token = getAuthenticatedToken();
        ProductDTO product = createProduct();
        long product_id = product.getId();

        ProductModificationDTO productModificationDTO = new ProductModificationDTO();
        this.mockMvc.perform(patch(STR."/products/\{product_id}").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productModificationDTO)).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STR."/products/\{product_id}"))
                .andExpect(jsonPath("name").value(product.getName()))
                .andExpect(jsonPath("price").value(product.getPrice()))
                .andExpect(jsonPath("description").value(product.getDescription()))
                .andExpect(jsonPath("category").value(product.getCategory()))
                .andExpect(status().isOk());

    }

    /*
    Created product is available in all products
    Product name is unique
    Product creation has to be validated (like Login)
    Product operations (POST, PATCH, DELETE) require to be authenticated AS ADMIN
     */
}
