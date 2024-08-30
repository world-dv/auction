package com.tasksprints.auction.api.product;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import com.tasksprints.auction.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Register Product", description = "Register a new product for an auction by user ID and auction ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product registered successfully."),
            @ApiResponse(responseCode = "404", description = "Auction not found.")
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResult<ProductResponse>> registerProduct(
            @RequestParam Long userId,
            @RequestParam Long auctionId,
            @RequestPart("productRequest") ProductRequest.Register productRequest,
            @RequestPart("images") List<MultipartFile> images) {
        ProductResponse ProductResponse = productService.register(userId, auctionId, productRequest, images);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.PRODUCT_NOT_FOUND, ProductResponse));
    }
    @Operation(summary = "Get Products by Auction ID", description = "Retrieve products based on user ID or auction ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product(s) retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Product(s) not found for the given user ID or auction ID.")
    })
    @GetMapping
    public ResponseEntity<ApiResult<?>> getProducts(
            @RequestParam Long auctionId) {

            ProductResponse ProductResponse = productService.getProductByAuctionId(auctionId);
            return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.PRODUCT_NOT_FOUND, ProductResponse));
    }
    @Operation(summary = "Update Product", description = "Update an existing product with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @PutMapping
    public ResponseEntity<ApiResult<ProductResponse>> updateProduct(@RequestBody ProductRequest.Update productRequest) {
        ProductResponse updatedProduct = productService.update(productRequest);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.REVIEW_RETRIEVED, updatedProduct));
    }

    @Operation(summary = "Delete Product", description = "Delete a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResult<String>> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.PRODUCT_NOT_FOUND));
    }
//    @PostMapping("/uploadImage")
//    public ResponseEntity<ApiResult<String>> uploadImage() {
//        productService.uploadImage();
//        return ResponseEntity.ok(ApiResult.success("Image uploaded successfully."));
//    }
//
//    @PostMapping("/uploadImageBulk")
//    public ResponseEntity<ApiResult<String>> uploadImageBulk() {
//        productService.uploadImageBulk();
//        return ResponseEntity.ok(ApiResult.success("Bulk images uploaded successfully."));
//    }
}


//}
