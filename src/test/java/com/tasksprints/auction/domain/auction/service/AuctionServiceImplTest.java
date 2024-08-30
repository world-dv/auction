package com.tasksprints.auction.domain.auction.service;

import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.exception.AuctionAlreadyClosedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.exception.InvalidAuctionTimeException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionServiceImpl auctionService;

    private User seller;

    @BeforeEach
    void setUp() {
        seller = User.builder()
                .id(1L)
                .name("testUser")
                .nickName("testNick")
                .password("testPassword")
                .email("test@example.com")
                .build();
    }

    @Nested
    @DisplayName("경매 생성 테스트")
    class CreateAuctionTests {

        @Test
        @DisplayName("성공")
        void testCreateAuction_Success() {
            AuctionRequest.Create auctionRequest = new AuctionRequest.Create(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(7),
                    BigDecimal.valueOf(100.00),
                    AuctionCategory.PRIVATE_FREE,
                    AuctionStatus.ACTIVE
            );

            when(userRepository.findById(1L)).thenReturn(Optional.of(seller));
            when(auctionRepository.save(any(Auction.class))).thenAnswer(invocation -> invocation.getArgument(0));

            AuctionResponse createdAuction = auctionService.createAuction(1L, auctionRequest);

            assertThat(createdAuction.getSellerId()).isEqualTo(seller.getId());
            assertThat(createdAuction.getCategory()).isEqualTo(AuctionCategory.PRIVATE_FREE.name());
            assertThat(createdAuction.getStatus()).isEqualTo(AuctionStatus.ACTIVE.name());

            verify(auctionRepository).save(any(Auction.class));
        }

        @Test
        @DisplayName("사용자 미존재 예외")
        void testCreateAuction_UserNotFound() {
            AuctionRequest.Create auctionRequest = new AuctionRequest.Create(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(7),
                    BigDecimal.valueOf(100.00),
                    AuctionCategory.PRIVATE_FREE,
                    AuctionStatus.ACTIVE
            );

            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                    auctionService.createAuction(1L, auctionRequest));

            assertThat(exception.getMessage()).isEqualTo("User not found");
            verify(auctionRepository, never()).save(any(Auction.class));
        }

        @Test
        @DisplayName("종료 시간이 시작 시간보다 이전인 경우 예외")
        void testCreateAuction_EndTimeBeforeStartTime() {
            AuctionRequest.Create auctionRequest = new AuctionRequest.Create(
                    LocalDateTime.now().plusDays(7),
                    LocalDateTime.now(),
                    BigDecimal.valueOf(100.00),
                    AuctionCategory.PRIVATE_FREE,
                    AuctionStatus.ACTIVE
            );

            when(userRepository.findById(1L)).thenReturn(Optional.of(seller));

            InvalidAuctionTimeException exception = assertThrows(InvalidAuctionTimeException.class, () ->
                    auctionService.createAuction(1L, auctionRequest));

            assertThat(exception.getMessage()).isEqualTo("End time must be after start time");
            verify(auctionRepository, never()).save(any(Auction.class));
        }
    }

    @Nested
    @DisplayName("경매 종료 테스트")
    class CloseAuctionTests {

        @Test
        @DisplayName("성공")
        void testCloseAuction_Success() {
//            Auction auction = createAuction(1L, seller, AuctionCategory.PRIVATE_FREE, AuctionStatus.ACTIVE);
//
//            when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
//            when(auctionRepository.save(any(Auction.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//            Auctio closedAuction = auctionService.closeAuction(1L);
//
//            assertThat(closedAuction.getAuctionStatus()).isEqualTo(AuctionStatus.CLOSED);
//            verify(auctionRepository).save(auction);
        }

        @Test
        @DisplayName("경매 미존재 예외")
        void testCloseAuction_AuctionNotFound() {
            when(auctionRepository.findById(1L)).thenReturn(Optional.empty());

            AuctionNotFoundException exception = assertThrows(AuctionNotFoundException.class, () ->
                    auctionService.closeAuction(1L));

            assertThat(exception.getMessage()).isEqualTo("Auction not found");
            verify(auctionRepository, never()).save(any(Auction.class));
        }

        @Test
        @DisplayName("이미 종료된 경매 예외")
        void testCloseAuction_AuctionAlreadyClosed() {
            Auction auction = Auction.builder()
                    .id(1L)
                    .auctionCategory(AuctionCategory.PRIVATE_FREE)
                    .auctionStatus(AuctionStatus.CLOSED)
                    .seller(seller)
                    .build();

            when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));

            AuctionAlreadyClosedException exception = assertThrows(AuctionAlreadyClosedException.class, () ->
                    auctionService.closeAuction(1L));

            assertThat(exception.getMessage()).isEqualTo("Auction is already closed");
            verify(auctionRepository, never()).save(any(Auction.class));
        }
    }

    @Nested
    @DisplayName("경매 상태 조회 테스트")
    class GetAuctionStatusTests {

        @Test
        @DisplayName("성공")
        void testGetAuctionStatus_Success() {
            Auction auction = createAuction(1L, seller, AuctionStatus.ACTIVE);

            when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));

            String auctionStatus = auctionService.getAuctionStatus(1L);

            assertThat(auctionStatus).isEqualTo(AuctionStatus.ACTIVE.toString());
        }

        @Test
        @DisplayName("경매 미존재 예외")
        void testGetAuctionStatus_AuctionNotFound() {
            when(auctionRepository.findById(1L)).thenReturn(Optional.empty());

            AuctionNotFoundException exception = assertThrows(AuctionNotFoundException.class, () ->
                    auctionService.getAuctionStatus(1L));

            assertThat(exception.getMessage()).isEqualTo("Auction not found");
        }
    }

    @Nested
    @DisplayName("사용자별 경매 목록 조회 테스트")
    class GetAuctionsByUserTests {

        @Test
        @DisplayName("성공")
        void testGetAuctionsByUser_Success() {
            Auction auction1 = createAuction(1L, seller, AuctionStatus.PENDING);
            Auction auction2 = createAuction(2L, seller, AuctionStatus.PENDING);

            // Mocking: 사용자 정보 반환
            when(userRepository.findById(1L)).thenReturn(Optional.of(seller));
            // Mocking: 경매 목록 반환
            when(auctionRepository.findAuctionsByUserId(1L)).thenReturn(List.of(auction1, auction2));

            // Act: 사용자가 있는지 확인하고 경매 목록 가져오기
            List<AuctionResponse> auctions = auctionService.getAuctionsByUser(1L);

            // Assert: 반환된 경매 목록 확인
            assertThat(auctions).hasSize(2);
            assertThat(auctions.get(0).getCategory()).isEqualTo(AuctionCategory.PUBLIC_PAID.name());
            assertThat(auctions.get(1).getCategory()).isEqualTo(AuctionCategory.PUBLIC_PAID.name());
        }

        @Test
        @DisplayName("사용자 미존재 예외")
        void testGetAuctionsByUser_UserNotFound() {
            // Arrange: 사용자가 존재하지 않을 때 findById를 모의 설정
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert: UserNotFoundException이 발생할 것으로 예상
            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                    auctionService.getAuctionsByUser(1L));

            assertThat(exception.getMessage()).isEqualTo("User not found");
        }
    }

    @Nested
    @DisplayName("모든 경매 목록 조회 테스트")
    class GetAllAuctionsTests {

        @Test
        @DisplayName("성공")
        void testGetAllAuctions_Success() {
            Auction auction1 = createAuction(1L, seller, AuctionStatus.PENDING);
            Auction auction2 = createAuction(2L, seller, AuctionStatus.PENDING);

            when(auctionRepository.findAll()).thenReturn(List.of(auction1, auction2));

            List<AuctionResponse> auctions = auctionService.getAllAuctions();

            assertThat(auctions).hasSize(2);
            assertThat(auctions.get(0).getCategory()).isEqualTo(AuctionCategory.PUBLIC_PAID.name());
            assertThat(auctions.get(1).getCategory()).isEqualTo(AuctionCategory.PUBLIC_PAID.name());
        }
    }

    @Nested
    @DisplayName("경매 ID로 조회 테스트")
    class GetAuctionByIdTests {

        @Test
        @DisplayName("성공")
        void testGetAuctionById_Success() {
            Auction auction = createAuction(1L, seller, AuctionStatus.PENDING);

            when(auctionRepository.findAuctionById(1L)).thenReturn(Optional.of(auction));

            AuctionResponse foundAuction = auctionService.getAuctionById(1L);

            assertThat(foundAuction.getCategory()).isEqualTo(AuctionCategory.PUBLIC_PAID.name());
        }

        @Test
        @DisplayName("경매 미존재 예외")
        void testGetAuctionById_AuctionNotFound() {
            when(auctionRepository.findAuctionById(1L)).thenReturn(Optional.empty());

            AuctionNotFoundException exception = assertThrows(AuctionNotFoundException.class, () ->
                    auctionService.getAuctionById(1L));

            assertThat(exception.getMessage()).isEqualTo("Auction not found");
        }
    }

    private Auction createAuction(Long auctionId, User seller, AuctionStatus status) {
        return Auction.builder()
                .id(auctionId)
                .auctionCategory(AuctionCategory.PUBLIC_PAID)
                .auctionStatus(status)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(7))
                .startingBid(BigDecimal.valueOf(100.00))
                .seller(seller)
                .build();
    }
}
