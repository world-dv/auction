package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import com.tasksprints.auction.domain.product.exception.ProductImageUploadException;
import com.tasksprints.auction.domain.product.exception.ProductNotFoundException;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.model.ProductImage;
import com.tasksprints.auction.domain.product.repository.ProductImageRepository;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements  ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final ProductImageRepository productImageRepository;
    private static final String UPLOADS_DIR = "src/main/resources/static/uploads/thumbnails/";

    @Override
    public String uploadImage(MultipartFile image) {
        String fileName = generateFileName(image);
        Path filePath = Paths.get(UPLOADS_DIR, fileName);
        saveFile(filePath, image);
        return "/uploads/thumbnails/" + fileName;
    }
    @Override
    public List<String> uploadImageBulk(List<MultipartFile> images) {
        return images.stream() // 병렬 처리를 피하고 일반 Stream 사용
                .map(this::uploadImage)
                .collect(Collectors.toList());
    }
    @Override
    @Deprecated
    public List<ProductResponse> getProductsByUserId(Long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        return convertToDTOList(products);
    }

    @Override
    public ProductResponse getProductByAuctionId(Long auctionId) {
        Product product = productRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductResponse.of(product);
    }

    @Transactional
    @Override
    public ProductResponse register(Long userId, Long auctionId, ProductRequest.Register request, List<MultipartFile> images) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        // 이미지 업로드
        List<String> imageUrls = uploadImageBulk(images);

        List<ProductImage> productImageList= imageUrls.parallelStream()
                .map(ProductImage::create)
                .toList();

        List<ProductImage> savedProductImageList = productImageRepository.saveAll(productImageList);

        // 상품 생성
        Product newProduct = Product.create(
                request.getName(),
                request.getDescription(),
                user,
                auction,
                savedProductImageList
        );

        Product createdProduct = productRepository.save(newProduct);

        return ProductResponse.of(createdProduct);
    }

    @Override
    public ProductResponse update(ProductRequest.Update request) {
        Long productId = request.getProductId();
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        foundProduct.update(request.getName(), request.getDescription());
        Product savedProduct = productRepository.save(foundProduct);
        return ProductResponse.of(savedProduct);
    }

    private List<ProductResponse> convertToDTOList(List<Product> products) {
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
    private void saveFile(Path filePath, MultipartFile image) {
        try {
            Files.createDirectories(filePath.getParent()); // 디렉토리 생성 (필요한 경우)
            Files.copy(image.getInputStream(), filePath); // 파일 저장
        } catch (IOException e) {
            throw new ProductImageUploadException("Failed to upload image: " + image.getOriginalFilename(), e);
        }
    }

    private String generateFileName(MultipartFile image) {
        return UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
    }

}
