package ru.geekbrains.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.geekbrains.Lesson6SpringBootApplicationTests;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest extends Lesson6SpringBootApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductsWithoutParametersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("bicycle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cost").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(15));
    }

    @Test
    public void getProductsBySizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("size", "6")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("bicycle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cost").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(15));
    }

    @Test
    public void getProductsBySizedAndMinCostsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("min_cost", "300")
                .param("size", "6")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("bicycle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cost").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(10));
    }

    @Test
    public void getProductsBySizedAndMaxCostsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("max_cost", "800")
                .param("size", "6")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("boll"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(11));
    }

    @Test
    public void getProductsByMaxCostsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("max_cost", "800")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("boll"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cost").value("300"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(11));
    }

    @Test
    public void getProductsByMinCostAndMaxCostsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("min_cost", "300")
                .param("max_cost", "800")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("boll"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cost").value("300"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(6));
    }

    @Test
    public void getProductsByTitleAndMinCostAndMaxCostsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/products")
                .param("title", "du")
                .param("min_cost", "300")
                .param("max_cost", "800")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(0));
    }
}
