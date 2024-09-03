package com.tasksprints.auction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.api.product.ProductController;
import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import com.tasksprints.auction.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMultipartFile multipartFile1;
    private MockMultipartFile multipartFile2;
    private MockMultipartFile productRequestMultiPart;

    @BeforeEach
    public void setup() throws Exception {
        multipartFile1 = new MockMultipartFile(
                "images",
                "image1.png",
                "image/png",
                "fake image content 1".getBytes(StandardCharsets.UTF_8)
        );

        multipartFile2 = new MockMultipartFile(
                "images",
                "image2.jpg",
                "image/jpeg",
                "fake image content 2".getBytes(StandardCharsets.UTF_8)
        );

        ProductRequest.Register productRequest = new ProductRequest.Register("Sample Product", "This is a sample product.","여성의류");
        String valueAsString = objectMapper.writeValueAsString(productRequest);

        productRequestMultiPart = new MockMultipartFile(
                "productRequest",
                "productRequest",
                MediaType.APPLICATION_JSON_VALUE,
                valueAsString.getBytes(StandardCharsets.UTF_8)
        );
    }
    @DisplayName("제품 등록 성공")
    @Test
    public void testRegisterProduct_Success() throws Exception {
        ProductResponse productResponse = new ProductResponse(); // Populate with necessary fields
        when(productService.register(anyLong(), anyLong(), any(), any())).thenReturn(productResponse);

        mockMvc.perform(multipart("/api/v1/product?userId=1&auctionId=1")
                        .file(multipartFile1)
                        .file(multipartFile2)
                        .file(productRequestMultiPart)
                )
                .andExpect(status().isOk()) // Expect status OK
                .andExpect(jsonPath("$.message").value(ApiResponseMessages.PRODUCT_FOUND_SUCCESS ));
    }

    @DisplayName("제품 등록 실패")
    @Test
    public void testRegisterProduct_Failure() throws Exception {
        when(productService.register(anyLong(), anyLong(), any(), any())).thenThrow(new RuntimeException("Error registering product"));

        mockMvc.perform(multipart("/api/v1/product?userId=1&auctionId=1")
                        .file(multipartFile1)
                        .file(multipartFile2)
                        .file(productRequestMultiPart)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error registering product"));
    }

    @DisplayName("auctionId를 통해 제품 조회 성공")
    @Test
    public void testGetProductByAuctionId_Success() throws Exception {
        ProductResponse ProductResponse = new ProductResponse(); // Populate with necessary fields
        when(productService.getProductByAuctionId(anyLong())).thenReturn(ProductResponse);

        mockMvc.perform(get("/api/v1/product?auctionId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ApiResponseMessages.PRODUCT_FOUND_SUCCESS)); // Adjust as needed
    }

    @DisplayName("제품 수정 성공")
    @Test
    public void testUpdateProduct_Success() throws Exception {
        ProductRequest.Update productRequest = new ProductRequest.Update(); // Populate with necessary fields
        ProductResponse updatedProductResponse = new ProductResponse(); // Populate with necessary fields
        when(productService.update(any())).thenReturn(updatedProductResponse);

        mockMvc.perform(put("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ApiResponseMessages.REVIEW_RETRIEVED)); // Adjust as needed
    }

}
