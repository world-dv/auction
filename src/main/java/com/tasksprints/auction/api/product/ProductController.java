package com.tasksprints.auction.api.product;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResponse;
import com.tasksprints.auction.domain.product.dto.ProductDTO;
import com.tasksprints.auction.domain.product.dto.ProductRequest;
import com.tasksprints.auction.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/{userId}/{auctionId}")
    public ResponseEntity<ApiResponse<ProductDTO>> registerProduct(
            @PathVariable Long userId,
            @PathVariable Long auctionId,
            @RequestBody ProductRequest.Register productRequest) {
        ProductDTO productDTO = productService.register(userId, auctionId, productRequest);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.PRODUCT_NOT_FOUND, productDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDTO> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.REVIEWS_RETRIEVED, products));
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductByAuctionId(@PathVariable Long auctionId) {
        ProductDTO productDTO = productService.getProductByAuctionId(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.PRODUCT_NOT_FOUND, productDTO));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@RequestBody ProductRequest.Update productRequest) {
        ProductDTO updatedProduct = productService.update(productRequest);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.REVIEW_RETRIEVED, updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.PRODUCT_NOT_FOUND));
    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<ApiResponse<String>> uploadImage() {
//        productService.uploadImage();
//        return ResponseEntity.ok(ApiResponse.success("Image uploaded successfully."));
//    }
//
//    @PostMapping("/uploadImageBulk")
//    public ResponseEntity<ApiResponse<String>> uploadImageBulk() {
//        productService.uploadImageBulk();
//        return ResponseEntity.ok(ApiResponse.success("Bulk images uploaded successfully."));
//    }
}
