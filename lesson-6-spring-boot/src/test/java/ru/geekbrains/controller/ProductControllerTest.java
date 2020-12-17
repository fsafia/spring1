package ru.geekbrains.controller;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.UserRepository;

import static org.mockito.ArgumentMatchers.any;

//@AutoConfigureMockMvc
//public class ProductControllerTest extends Lesson6SpringBootApplicationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    ProductRepository productRepository;
//
////    public void checkMethodallProducts() throws Exception {
////        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
////                .param("page", 1 )
////                .param("size", 4 )
////    }
//
//
////     public void checkMethodallProducts() throws Exception {
//////        Mockito.when(productRepository.findAll(any(), any())).thenReturn(new Page<>());
////        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
////                .param("page", 1 )
////                .param("size", 4 )
////                .andExpect(MockMvcResultMatchers.model().attribute("productsPage", ))
////                .andExpect(M);
//
//
//}
