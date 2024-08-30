package com.tdevred.bouteek.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import com.tdevred.bouteek.business.DTO.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryTest {

    @Autowired
    private MockMvc mockMvc;

    private final Faker faker = new Faker();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CategoryCreationDTO getCategory() {
        return new CategoryCreationDTO(faker.commerce().department(), faker.lorem().characters(50, 250));
    }

    public ProductCreationDTO getProduct() {
        return new ProductCreationDTO(faker.commerce().productName(), Double.parseDouble(faker.commerce().price().replaceAll(",", ".")), faker.lorem().characters(50, 250), 1);
    }

    public ProductDTO createProduct() throws Exception {
        ProductCreationDTO productCreationDTO = getProduct();
        String json = objectMapper.writeValueAsString(productCreationDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
    }

    public CategoryDTO createCategory() throws Exception {
        CategoryCreationDTO categoryDTO = getCategory();
        String json = objectMapper.writeValueAsString(categoryDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CategoryDTO.class);
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryCreationDTO categoryDTO = getCategory();
        String json = objectMapper.writeValueAsString(categoryDTO);
        this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("name").value(categoryDTO.getName()))
                .andExpect(jsonPath("description").value(categoryDTO.getDescription()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddProductToCategory() throws Exception {
        CategoryCreationDTO categoryDTO = getCategory();
        String json = objectMapper.writeValueAsString(categoryDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();

        long category_id = Integer.toUnsignedLong(JsonPath.read(mvcResult.getResponse().getContentAsString(), "id"));
        long product_id = createProductInCategory(category_id);

        this.mockMvc.perform(get(STR."/products/\{product_id}").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("category").value(categoryDTO.getName()))
                .andExpect(status().isOk()).andReturn();
    }

    public long createProductInCategory(long categoryId) throws Exception {
        ProductCreationDTO productCreationDTO = getProduct();
        productCreationDTO.setCategory(categoryId);
        String json = objectMapper.writeValueAsString(productCreationDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class).getId();
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        long category_id = createCategory().getId();
        this.mockMvc.perform(delete(STR."/categories/\{category_id}"))
                .andExpect(status().isOk());
        this.mockMvc.perform(get(STR."/categories/\{category_id}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldModifyCategory() throws Exception {
        CategoryDTO categoryDTO = createCategory();
        long category_id = categoryDTO.getId();

        CategoryModificationDTO categoryModificationDTO = new CategoryModificationDTO();
        categoryModificationDTO.setName(faker.commerce().department());
        categoryModificationDTO.setDescription(faker.lorem().characters(50, 250));
        this.mockMvc.perform(patch(STR."/categories/\{category_id}").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryModificationDTO)))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STR."/categories/\{category_id}"))
                .andExpect(jsonPath("name").value(categoryModificationDTO.getName()))
                .andExpect(jsonPath("description").value(categoryModificationDTO.getDescription()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldModifyNothingCategory() throws Exception {
        CategoryDTO category = createCategory();
        long category_id = category.getId();

        CategoryModificationDTO categoryModificationDTO = new CategoryModificationDTO();
        this.mockMvc.perform(patch(STR."/categories/\{category_id}").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryModificationDTO)))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STR."/categories/\{category_id}"))
                .andExpect(jsonPath("name").value(category.getName()))
                .andExpect(jsonPath("description").value(category.getDescription()))
                .andExpect(status().isOk());
    }

    /*
    Product is deleted on category deletion (if wanted)
    Product is in category products
    Category unique name
    Operations (POST PATCH DELETE) require to be authenticated AS ADMIN
     */
}
