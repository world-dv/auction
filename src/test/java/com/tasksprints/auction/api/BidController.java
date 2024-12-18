package com.tasksprints.auction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.api.bid.BidController;
import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.chat.service.ChatService;
import com.tasksprints.auction.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BidController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BidService bidService;

    @MockBean
    ChatService chatService;

    @MockBean
    UserService userService;
    @MockBean
    private SimpMessageSendingOperations simpMessageSendingOperations;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }
    @Test
    @DisplayName("입찰 제출 성공")
    public void testSubmitBid_Success() throws Exception {
        BidResponse bidDTO = new BidResponse(); // Populate with necessary fields
        when(bidService.submitBid(anyLong(), anyLong(), any())).thenReturn(bidDTO);

        mockMvc.perform(post("/api/v1/bid")
                .param("auctionId", "1")
                .param("userId", "1")
                .param("amount", "100.00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.BID_SUBMITTED_SUCCESS));
    }
    @Test
    @DisplayName("입찰 금액 업데이트 성공")
    public void testUpdateBid_Success() throws Exception {
        BidResponse updatedBidDTO = new BidResponse(); // Populate with necessary fields
        when(bidService.updateBidAmount(anyLong(), anyLong(), any())).thenReturn(updatedBidDTO);

        mockMvc.perform(put("/api/v1/bid")
                .param("auctionId", "1")
                .param("userId", "1")
                .param("amount", "150.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBidDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.BID_UPDATED_SUCCESS));
    }
}
