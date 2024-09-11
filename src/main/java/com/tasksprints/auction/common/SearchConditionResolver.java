package com.tasksprints.auction.common;

import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SearchConditionResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 메서드 파라미터 타입이 AuctionRequest.SearchCondition일 때 처리
        return parameter.getParameterType().equals(AuctionRequest.SearchCondition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        // QueryString에서 값을 추출
        String auctionCategory = webRequest.getParameter("auctionCategory");
        String productCategory = webRequest.getParameter("productCategory");
        String startTime = webRequest.getParameter("startTime");
        String endTime = webRequest.getParameter("endTime");
        String minPrice = webRequest.getParameter("minPrice");
        String maxPrice = webRequest.getParameter("maxPrice");
        String auctionStatus = webRequest.getParameter("auctionStatus");
        // 파싱 및 변환
        AuctionCategory parsedAuctionCategory = auctionCategory != null ? AuctionCategory.fromDisplayName(auctionCategory) : null;
        ProductCategory parsedProductCategory = productCategory != null ? ProductCategory.fromDisplayName(productCategory) : null;
        LocalDateTime parsedStartTime = startTime != null ? LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDateTime parsedEndTime = endTime != null ? LocalDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME) : null;
        BigDecimal parsedMinPrice = minPrice != null ? new BigDecimal(minPrice) : null;
        BigDecimal parsedMaxPrice = maxPrice != null ? new BigDecimal(maxPrice) : null;
        AuctionStatus parsedAuctionStatus = auctionStatus != null ? AuctionStatus.fromDisplayName(auctionStatus) : null;

        // SearchCondition 객체 생성 및 반환
        return new AuctionRequest.SearchCondition(
            parsedAuctionCategory,
            parsedProductCategory,
            parsedStartTime,
            parsedEndTime,
            parsedMinPrice,
            parsedMaxPrice,
            parsedAuctionStatus
        );
    }
}
