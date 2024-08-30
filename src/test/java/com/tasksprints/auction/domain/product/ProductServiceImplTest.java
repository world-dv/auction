package com.tasksprints.auction.domain.product;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import com.tasksprints.auction.domain.product.exception.ProductNotFoundException;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
import com.tasksprints.auction.domain.product.service.ProductServiceImpl;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    private User user;
    private Auction auction;
    private Product product;

    private static final String UPLOADS_DIR = "src/main/resources/static/uploads/thumbnails/";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .name("testUser")
                .nickName("test")
                .password("password")
                .email("test@naver.com")
                .build();
        auction = Auction.builder()
                .id(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .auctionStatus(AuctionStatus.ACTIVE)
                .startingBid(BigDecimal.ONE)
                .auctionCategory(AuctionCategory.PRIVATE_FREE)
                .seller(user)
                .build();
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Description")
                .owner(user)
                .auction(auction)
                .build();
    }

    @Nested
    @DisplayName("제품 등록 기능")
    class RegisterTests {
        @AfterEach
        public void cleanUp() throws IOException {
            deleteAllImagesInDirectory(UPLOADS_DIR);
        }

        private void deleteAllImagesInDirectory(String directoryPath) throws IOException {
            Path dirPath = Paths.get(directoryPath);

            if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
                try (var paths = Files.walk(dirPath)) {
                    paths.filter(Files::isRegularFile) // 정규 파일만 필터링
                            .forEach(filePath -> {
                                try {
                                    Files.delete(filePath); // 파일 삭제
                                    System.out.println("Deleted: " + filePath.toString()); // 삭제된 파일 로그 출력
                                } catch (IOException e) {
                                    System.err.println("Error deleting file: " + filePath.toString() + " - " + e.getMessage()); // 삭제 실패 로그 출력
                                }
                            });
                }
            } else {
                System.out.println("Directory not found: " + directoryPath); // 디렉토리 없음 로그 출력
            }
        }
        @Test
        @DisplayName("새로운 제품 등록")
        public void testRegister() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
            when(productRepository.save(any(Product.class))).thenReturn(product);

            ProductRequest.Register request = new ProductRequest.Register("Test Product", "Description");

            ProductResponse createdProductResponse = productService.register(1L, 1L, request,createMockImages());

            assertEquals(createdProductResponse.getName(),"Test Product");
            verify(productRepository).save(any(Product.class));
        }

        @Test
        @DisplayName("등록 시 사용자 미발견 예외 발생")
        public void testRegisterUserNotFound() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            ProductRequest.Register request = new ProductRequest.Register("Test Product", "Description");

            assertThrows(UserNotFoundException.class, () -> productService.register(1L, 1L, request,createMockImages()));
        }

        @Test
        @DisplayName("등록 시 경매 미발견 예외 발생")
        public void testRegisterAuctionNotFound() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(auctionRepository.findById(1L)).thenReturn(Optional.empty());

            ProductRequest.Register request = new ProductRequest.Register("Test Product", "Description");

            assertThrows(RuntimeException.class, () -> productService.register(1L, 1L,request, createMockImages()));
        }
        private List<MultipartFile> createMockImages() {
            MockMultipartFile image1 = new MockMultipartFile("image1", "test1.jpg", "image/jpeg", "test image content 1".getBytes());
            MockMultipartFile image2 = new MockMultipartFile("image2", "test2.jpg", "image/jpeg", "test image content 2".getBytes());
            return List.of(image1, image2); // 이미지 리스트 반환
        }

    }

    @Nested
    @DisplayName("제품 조회 기능")
    class GetTests {

        @Test
        @DisplayName("사용자 ID로 제품 조회")
        public void testGetProductsByUserId() {
            when(productRepository.findAllByUserId(1L)).thenReturn(Collections.singletonList(product));

            List<ProductResponse> products = productService.getProductsByUserId(1L);

            assertEquals(products.size(),1);
            assertEquals(products.get(0).getName(),"Test Product");
        }

        @Test
        @DisplayName("경매 ID로 제품 조회")
        public void testGetProductByAuctionId() {
            when(productRepository.findByAuctionId(1L)).thenReturn(Optional.of(product));

            ProductResponse foundProductResponse = productService.getProductByAuctionId(1L);

            assertEquals(foundProductResponse.getName(),"Test Product");
        }

        @Test
        @DisplayName("경매 ID로 제품 미발견 시 예외 발생")
        public void testGetProductByAuctionIdNotFound() {
            when(productRepository.findByAuctionId(1L)).thenReturn(Optional.empty());

            assertThrows(ProductNotFoundException.class, () -> productService.getProductByAuctionId(1L));
        }
    }

    @Nested
    @DisplayName("제품 수정 기능")
    class UpdateTests {

        @Test
        @DisplayName("수정할 제품 미발견 시 예외 발생")
        public void testUpdateProductNotFound() {
            ProductRequest.Update updateRequest = new ProductRequest.Update(1L, "Updated Product", "Updated Description");

            when(productRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(ProductNotFoundException.class, () -> productService.update(updateRequest));
        }

        @Test
        @DisplayName("기존 제품 수정")
        public void testUpdateProduct() {
            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            when(productRepository.save(any(Product.class))).thenReturn(product);

            ProductRequest.Update updateRequest = new ProductRequest.Update(1L, "Updated Product", "Updated Description");

            ProductResponse updatedProductResponse = productService.update(updateRequest);

            assertEquals(updatedProductResponse.getName(),"Updated Product");
            verify(productRepository).save(any(Product.class));
        }
    }
}
